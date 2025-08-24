package org.williamg.dcstats.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.williamg.dcstats.DCStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;

public class AdvancementManager {

    private final DCStats plugin;
    private final DatabaseManager dbManager;
    private final String prefix;
    private final HashMap<String, Integer> advancementMap;

    public AdvancementManager(DCStats plugin) {
        this.plugin = plugin;
        this.dbManager = plugin.getDatabaseManager();
        this.prefix = plugin.getDatabaseManager().getPrefix();
        this.advancementMap = new HashMap<>();
        try{
            initialise();
        }catch (SQLException e){
            this.plugin.getLogger().severe("Failed to initialise stats manager");
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private void initialise() throws SQLException {
        initialiseMapTable();
        initialisePlayerAdvancementTable();
        getAdvancementMapping();
    }

    private void initialisePlayerAdvancementTable() throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS " + prefix + "advancements (" +
                "id serial PRIMARY KEY, " +
                "advancement integer NOT NULL, " +
                "player_uuid varchar(255) NOT NULL, " +
                "time integer" +
                ");"
        );
        ps.executeUpdate();
        ps.close();
    }

    private void initialiseMapTable() throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS " + prefix + "advancement_map (" +
                "row_id serial PRIMARY KEY, " +
                "id integer NOT NULL, " +
                "advancement varchar(255)" +
                ");"
        );
        ps.executeUpdate();
        ps.close();
    }

    private void getAdvancementMapping() throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM " + prefix + "advancement_map");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String advancement = rs.getString("advancement");
            this.advancementMap.put(advancement, id);
        }

    }

    public void processPlayerAdvancement(String uuid, String advancement) throws SQLException {
        int advancementId = getAdvancementId(advancement);
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO " + prefix + "advancements (advancement, player_uuid, time) VALUES (?, ?, ?);");
        ps.setInt(1, advancementId);
        ps.setString(2, uuid);
        ps.setInt(3, (int) Instant.now().getEpochSecond());
        ps.executeUpdate();
        ps.close();
    }

    public int getAdvancementId(String advancement) {
        if(advancementMap.containsKey(advancement)) {
            return advancementMap.get(advancement);
        }

        //No mapping for this advancement exists create new one
        int newAdvancementId = getNextAdvancementId();
        advancementMap.put(advancement, newAdvancementId);

        //Create new entry in the database for the advancement
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                saveAdvancementMapping(newAdvancementId, advancement);
            } catch (SQLException e) {
                this.plugin.getLogger().severe("Failed to save advancement " + advancement);
            }
        });

        return newAdvancementId;
    }

    private int getNextAdvancementId() {
        int largestId = 0;
        for(Integer id : advancementMap.values()) {
            if(id > largestId) {
                largestId = id;
            }
        }

        return largestId + 1;
    }

    private void saveAdvancementMapping(int id, String advancement) throws SQLException {
        Connection c = dbManager.getConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO " + prefix + "advancement_map (id, advancement) VALUES (?, ?);");
        ps.setInt(1, id);
        ps.setString(2, advancement);
        ps.executeUpdate();
        ps.close();
    }

}
