package greenlink.advancedvanilla;

import greenlink.advancedvanilla.changelogNoteSystem.ChangelogContainer;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;

public class DatabaseConnector {
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private long lastTimeConnect;

    private static DatabaseConnector instance;

    public static DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    private DatabaseConnector() {
        lastTimeConnect = 0;
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
                    CREATE TABLE IF NOT EXISTS `players_settings` (
                     	`uuid` VARCHAR(36) NOT NULL,
                     	`settings_id` INT DEFAULT NULL,
                     	`value` INT DEFAULT NULL,
                     	PRIMARY KEY (`uuid`)
                     )""");

            statement.executeUpdate("""
                                CREATE TABLE IF NOT EXISTS `players_money` (
                                    `uuid` VARCHAR(36) NOT NULL,
                                    `money` INT NULL DEFAULT NULL,
                                    `pocket_money` INT NULL DEFAULT NULL,
                                    PRIMARY KEY (`uuid`)
                                 )""");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `changelog_notify` (\n" +
                    "\t`uuid` VARCHAR(36) NOT NULL,\n" +
                    "\t`notify_number` INT NOT NULL,\n" +
                    "\tPRIMARY KEY (`uuid`)\n" +
                    ")\n" +
                    "COLLATE='utf8_unicode_ci'\n" +
                    ";");


            statement.close();
        } catch (Exception e) {
            AdvancedVanilla.getInstance().getLogger().log(Level.WARNING,e.getMessage());
        }
    }

    public RpPlayer getPlayer(UUID uuid) {
        RpPlayer rpPlayer = new RpPlayer(uuid);
        Statement statement = null;
        try {
            statement = getConnection().createStatement();

            try {
                ResultSet resultSet = statement.executeQuery(String.format("SELECT money, pocket_money FROM players_money WHERE uuid='%s'", uuid));
                if (resultSet.next()) {
                    rpPlayer.setPocketMoney( resultSet.getInt("money") );
                    rpPlayer.setBankMoney( resultSet.getInt("pocket_money") );
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

            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }



        return rpPlayer;
    }


    public void savePlayer(RpPlayer rpPlayer) {
        try {

            Statement statement = getConnection().createStatement();


            try {
                statement.executeUpdate(String.format("INSERT INTO players_money (uuid, money, pocket_money) VALUES ('%s', '%d', '%d') " +
                                "ON DUPLICATE KEY UPDATE money='%d',pocket_money='%d'",
                        rpPlayer.getUuid(), rpPlayer.getPocketMoney(), rpPlayer.getBankMoney(), rpPlayer.getPocketMoney(), rpPlayer.getBankMoney() ));
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

    public int getChangelogNotify(UUID uuid ){
        //if (!useSQLdDB){ return; }

        int lastNotifNumber = -1;

        try {
            Statement statement = getConnection().createStatement();
            ResultSet result = statement.executeQuery(String.format(Locale.US,"SELECT notify_number FROM changelog_notify WHERE uuid='%s'", uuid) );

            if(result.next()) {
                lastNotifNumber = result.getInt("notify_number");
                //EventsCorePlugin.getInstance().getLogger().info("=lastNotifNumber= " + lastNotifNumber);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(String.format(Locale.US, "INSERT INTO changelog_notify (uuid, notify_number) VALUES('%s', %d) ON DUPLICATE KEY UPDATE notify_number='%d'", uuid, ChangelogContainer.getLastChangeNumber(), ChangelogContainer.getLastChangeNumber()));
        } catch (Exception e){
            e.printStackTrace();
        }

        return lastNotifNumber;

    }

    public void closeConnection(){
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        if ( System.currentTimeMillis() - lastTimeConnect > 1000*60*60 || connection == null) {
            if (connection != null) closeConnection();
            connection = this.user != null ? DriverManager.getConnection(this.url, this.user, this.password) : DriverManager.getConnection(this.url);
            lastTimeConnect = System.currentTimeMillis();
        }
        return connection;
    }
}