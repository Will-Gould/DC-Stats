package org.williamg.dcstats.database;

import org.bukkit.Bukkit;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.player.PlayerStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("SqlNoDataSourceInspection")
public class StatsManager {

    private final DCStats plugin;
    private final DatabaseManager dbManager;
    private final String prefix;

    public StatsManager(DCStats plugin) {
        this.plugin = plugin;
        this.dbManager = plugin.getDatabaseManager();
        this.prefix = plugin.getDatabaseManager().getPrefix();
        try{
            initialise();
        }catch (SQLException e){
            this.plugin.getLogger().severe("Failed to initialise stats manager");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private void initialise() throws SQLException {
        initialiseStatsTable();
        initialiseCombatTable();
        initialiseDeathTable();
    }

    public void processStatistics(PlayerStatistics playerStats) throws SQLException {
        List<PreparedStatement> preparedStatements;
        Connection c = dbManager.getConnection();
        preparedStatements = playerStats.getPreparedStatements(c, prefix);
        for(PreparedStatement ps : preparedStatements){
            ps.execute();
            ps.close();
        }
    }

    public boolean doesPlayerRecordExist(UUID uuid) throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM " + prefix + "general_stats WHERE player_uuid = ?");
        ps.setString(1, uuid.toString());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("player_uuid").equals(uuid.toString());
        }
        return false;
    }

    private void initialiseStatsTable() throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS " + prefix + "general_stats (" +
                "id serial PRIMARY KEY, " +
                "player_uuid varchar(255), " +
                "ender_dragons_killed int, " +
                "withers_killed int, " +
                "wardens_killed int, " +
                "ravagers_killed int, " +
                "evokers_killed int, " +
                "piglin_brutes_killed int, "+
                "ghasts_killed int, " +
                "elder_guardians_killed int, " +
                "witches_killed int, " +
                "mobs_killed int, " +
                "diamonds_mined int, " +
                "emeralds_mined int " +
                ");"
        );
        ps.executeUpdate();
        ps.close();
    }

    private void initialiseCombatTable() throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS " + prefix + "combat (" +
                "id serial PRIMARY KEY, " +
                "player_uuid varchar(255), " +
                "victim_uuid varchar(255), " +
                "combat_time timestamp DEFAULT CURRENT_TIMESTAMP " +
                ");"
        );
        ps.executeUpdate();
        ps.close();
    }

    private void initialiseDeathTable() throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS " + prefix + "deaths (" +
                "id serial PRIMARY KEY, " +
                "player_uuid varchar(255), " +
                "cause int, " +
                "death_time timestamp DEFAULT CURRENT_TIMESTAMP " +
                ");"
        );
        ps.executeUpdate();
        ps.close();
    }

}
