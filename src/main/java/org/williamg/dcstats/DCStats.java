package org.williamg.dcstats;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.williamg.dcstats.database.DatabaseManager;
import org.williamg.dcstats.listener.ListenerHandler;
import org.williamg.dcstats.player.PlayerHandler;
import org.williamg.dcstats.tasks.ProcessStatistics;

public final class DCStats extends JavaPlugin {

    public boolean debug = true;

    private DatabaseManager dbManager;
    private ListenerHandler listenerHandler;
    private PlayerHandler playerHandler;
    private BukkitTask dbUpdateTask;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Save default config and get config sql params
        saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        String sqlHost = config.getString("sql.host");
        String sqlPort = config.getString("sql.port");
        String sqlDatabase = config.getString("sql.database");
        String sqlUser = config.getString("sql.username");
        String sqlPassword = config.getString("sql.password");
        String prefix = config.getString("sql.prefix");

        dbManager = new DatabaseManager(this, sqlHost, sqlPort, sqlDatabase, sqlUser, sqlPassword, prefix);
        dbManager.initialiseStatsManager();

        playerHandler = new PlayerHandler(this);

        listenerHandler = new ListenerHandler(this);

        this.dbUpdateTask = new ProcessStatistics(this).runTaskTimerAsynchronously(this, 2400L, 2400L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        safeShutdown();
    }

    public DatabaseManager getDatabaseManager() {
        return dbManager;
    }

    public PlayerHandler getPlayerHandler() {
        return this.playerHandler;
    }

    private void safeShutdown(){
        this.dbUpdateTask.cancel();
        this.playerHandler.processPlayerData();
    }

    public ListenerHandler getListenerHandler() {
        return listenerHandler;
    }
}
