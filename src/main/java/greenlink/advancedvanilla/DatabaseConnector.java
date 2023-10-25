package greenlink.advancedvanilla;

import greenlink.advancedvanilla.professions.ProfessionBase;
import greenlink.advancedvanilla.professions.ProfessionManager;
import greenlink.advancedvanilla.professions.Professions;
import org.bukkit.configuration.file.FileConfiguration;

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
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbname + "?autoReconnect=true";

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
                    rpPlayer.getCountReferrals();
                }
                else {
                    rpPlayer = new RpPlayer(uuid);
                }

            }

            try {
                ResultSet resultSet = statement.executeQuery(String.format("SELECT money FROM players_money WHERE uuid='%s'", uuid));
                if (resultSet.next()) {
                    rpPlayer.setMoney( resultSet.getInt("money") );
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            try {
                ResultSet resultSet = statement.executeQuery(String.format("SELECT value FROM players_settings WHERE uuid='%s' AND settings_id='0'", uuid));
                if (resultSet.next()) {
                    rpPlayer.setDisplaySidebarInfo(resultSet.getInt("value") != 0);
                    //System.out.println(rpPlayer.isDisplaySidebarInfo() + " " + resultSet.getInt("value"));
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
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

            try {
                statement.executeUpdate(String.format("INSERT INTO players_money (uuid, money, pocket_money) VALUES ('%s', '%d', '%d') " +
                                "ON DUPLICATE KEY UPDATE money='%d',pocket_money='%d'",
                        rpPlayer.getUuid(), rpPlayer.getMoney(), rpPlayer.getPocketMoney(), rpPlayer.getMoney(), rpPlayer.getPocketMoney() ));
            } catch (Exception e){
                e.printStackTrace();
            }

            try {
                int temp = 0;
                if (rpPlayer.isDisplaySidebarInfo()) temp = 1;
                statement.executeUpdate(String.format("INSERT INTO players_settings (uuid, settings_id, value) VALUES ('%s', '0', '%d') " +
                                "ON DUPLICATE KEY UPDATE value='%d'",
                        rpPlayer.getUuid(), temp, temp ));
            } catch (Exception e){
                e.printStackTrace();
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getCountRpPlayerReferrals(UUID uuid) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("ALTER TABLE advanced_players ADD COLUMN IF NOT EXISTS count_referrals INT DEFAULT 0");
            ResultSet result = statement.executeQuery(String.format("SELECT count_referrals FROM advanced_players WHERE uuid='%s'", uuid));
            if(result.next()) {
                return result.getInt("count_referrals");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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