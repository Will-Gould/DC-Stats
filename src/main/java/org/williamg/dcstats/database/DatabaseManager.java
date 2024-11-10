package org.williamg.dcstats.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.player.PlayerStatistics;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {

    private final DCStats plugin;
    private Connection connection = null;
    private final HikariDataSource dataSource;
    private final String prefix;
    private StatsManager statsManager;
    //private ProfileManager profileManager;
    //private NameManager nameManager;
    //private NotesManager notesManager;

    public DatabaseManager(DCStats plugin, String host, String port, String database, String user, String pass, String prefix) {
        this.plugin = plugin;
        this.prefix = prefix;

        String url = "jdbc:postgresql://" + host + ":" + port;

        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", user);
        props.setProperty("dataSource.password", pass);
        props.setProperty("dataSource.databaseName", database);

        HikariConfig config = new HikariConfig(props);
        config.setJdbcUrl(url);
        this.dataSource = new HikariDataSource(config);

        //Connect to database disable plugin if unable to connect
        try{
            createConnection();
            this.plugin.getLogger().info("Successfully connected to database ");
        } catch (SQLException e) {
            this.plugin.getLogger().severe("Failed to connect to database disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }

    public void initialiseStatsManager() {
        this.statsManager = new StatsManager(this.plugin);
    }

    public boolean playerRecordExists(Player p) throws SQLException{
        return statsManager.doesPlayerRecordExist(p.getUniqueId());
    }

    public Connection getConnection() {
        if(validConnection()){
            return connection;
        }

        //Attempt to connect to database if unsuccessful disable plugin
        try{
            this.plugin.getLogger().info("Attempting to re-connect to database...");
            createConnection();
            return this.connection;
        }catch (SQLException e) {
            this.plugin.getLogger().severe("Failed to connect to database disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
        return null;
    }

    private void createConnection() throws SQLException {
        this.connection = this.dataSource.getConnection();
    }

    private boolean validConnection() {
        //Check if connection is null first
        if(this.connection == null){
            return false;
        }
        //Check if connection is open
        try{
            this.connection.isValid(5);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void runStatisticsQueries(PlayerStatistics playerStats) {
        try{
            statsManager.processStatistics(playerStats);
        }catch (Exception e){
            this.plugin.getLogger().severe("Failed process statistics");
        }
    }
}
