package greenlink.advancedvanilla;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;

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


            statement.close();
        } catch (Exception e) {
            AdvancedVanilla.getInstance().getLogger().info("Ошибка при подключении к базе данных");
        }
    }

    public RpPlayer getPlayer(UUID uuid) {
        RpPlayer rpPlayer = null;
        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(String.format("SELECT current_profession, old_profession FROM advanced_players WHERE uuid='%s'", uuid));

            if(!result.next()) {
                rpPlayer = new RpPlayer(uuid);
                String sql = String.format("INSERT INTO current_profession (uuid, current_profession, old_profession) VALUES ('%s', '%s', '%s')",
                        uuid, null, null);
                statement.executeUpdate(sql);
            }
            else {
                //TODO equals профессий
//
//                result.getString("current_profession")
//                rpPlayer = new RpPlayer(uuid, result.getInt("level"), result.getInt("xp"));
            }

            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
            rpPlayer = new RpPlayer(uuid);
        }
        return rpPlayer;
    }

    private Connection getConnection() throws SQLException {
        return this.user != null ? DriverManager.getConnection(this.url, this.user, this.password) : DriverManager.getConnection(this.url);
    }
}