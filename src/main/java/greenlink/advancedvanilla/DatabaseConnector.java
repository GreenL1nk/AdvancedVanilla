package greenlink.advancedvanilla;

import greenlink.advancedvanilla.auth.AuthPlayer;
import greenlink.advancedvanilla.professions.ProfessionBase;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.professions.Professions;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.units.qual.A;

import java.sql.*;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

public class DatabaseConnector {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    private static DatabaseConnector instance;

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    private DatabaseConnector() {
        try {
            FileConfiguration config = AdvancedVanilla.getInstance().getConfig();
            String host = config.getString("mySQL.host");
            int port = config.getInt("mySQL.port");
            user = config.getString("mySQL.user");
            String dbname = config.getString("mySQL.dbname");
            password = config.getString("mySQL.password");
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbname;

            connection = getConnection();
            AdvancedVanilla.getInstance().getLogger().info("Using MySQL");

            Statement statement = connection.createStatement();
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS `advanced_players` (
                    \t`uuid` VARCHAR(36) NOT NULL,
                    \t`current_profession` VARCHAR(50) NULL DEFAULT NULL,
                    \t`old_profession` VARCHAR(50) NULL DEFAULT NULL,
                    \tPRIMARY KEY (`uuid`)
                    )
                    COLLATE='utf8_unicode_ci'
                    ;""");


            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS `auth_players` (
                    \t`uuid` VARCHAR(36) NOT NULL,
                    \t`ip` VARCHAR(50) NOT NULL,
                    \t`discord_id` BIGINT NOT NULL,
                    \tPRIMARY KEY (`uuid`, `discord_id`)
                    )
                    COLLATE='utf8_unicode_ci'
                    ;""");


            statement.close();
        } catch (Exception e) {
            AdvancedVanilla.getInstance().getLogger().log(Level.WARNING,"Ошибка при подключении к базе данных");
        }
    }

    public RpPlayer getPlayer(UUID uuid) {
        RpPlayer rpPlayer;
        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(String.format("SELECT current_profession, old_profession FROM advanced_players WHERE uuid='%s'", uuid));

            if(!result.next()) {
                rpPlayer = new RpPlayer(uuid);
            }
            else {

                ProfessionBase currentProfession = result.getString("current_profession").equals("null") ? null
                        : ProfessionManager.getInstance().getProfession(Professions.valueOf(result.getString("current_profession")));
                ProfessionBase oldProfession = result.getString("old_profession").equals("null") ? null
                        : ProfessionManager.getInstance().getProfession(Professions.valueOf(result.getString("old_profession")));

                ResultSet authPlayerResult = statement.executeQuery(String.format("SELECT ip, discord_id FROM auth_players WHERE uuid='%s'", uuid));
                if (authPlayerResult.next()) {
                    String ip = authPlayerResult.getString("ip");
                    long discordID = authPlayerResult.getLong("discord_id");
                    rpPlayer = new RpPlayer(uuid,
                            currentProfession,
                            oldProfession,
                            ip,
                            discordID);
                }
                else {
                    rpPlayer = new RpPlayer(uuid);
                }

            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            rpPlayer = new RpPlayer(uuid);
        }
        return rpPlayer;
    }


    public void savePlayer(RpPlayer rpPlayer) {
        try {

            Statement statement = connection.createStatement();
            AuthPlayer authPlayer = rpPlayer.getAuthPlayer();

            if (authPlayer.isLinked()) {
                Professions currentProfession = Arrays.stream(Professions.values())
                        .filter(professions -> professions.getProfessionBase().getClass().isInstance(rpPlayer.getProfession()))
                        .findFirst().orElse(null);

                Professions oldProfession = Arrays.stream(Professions.values())
                        .filter(professions -> professions.getProfessionBase().getClass().isInstance(rpPlayer.getOldProfession()))
                        .findFirst().orElse(null);

                String professionName = currentProfession == null ? null : currentProfession.name();
                String oldProfessionName = oldProfession == null ? null : oldProfession.name();

                statement.executeUpdate(String.format("INSERT INTO advanced_players (uuid, current_profession, old_profession) VALUES ('%s', '%s', '%s') " +
                                "ON DUPLICATE KEY UPDATE current_profession='%s', old_profession='%s'",
                        rpPlayer.getUuid(), professionName, oldProfessionName,
                        professionName, oldProfessionName));

                statement.executeUpdate(String.format("INSERT INTO auth_players (uuid, ip, discord_id) VALUES ('%s', '%s', '%d') " +
                                "ON DUPLICATE KEY UPDATE ip='%s'",
                        rpPlayer.getUuid(), authPlayer.getAddress(), authPlayer.getDiscordID(),
                        authPlayer.getAddress()));
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return this.user != null ? DriverManager.getConnection(this.url, this.user, this.password) : DriverManager.getConnection(this.url);
    }
}