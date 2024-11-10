package org.williamg.dcstats.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.williamg.dcstats.DCStats;

public class ProcessStatistics extends BukkitRunnable {

    private final DCStats plugin;

    public ProcessStatistics(DCStats plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if(this.plugin.debug){
            plugin.getLogger().info("Attempting to process statistics");
        }
        this.plugin.getPlayerHandler().processPlayerData();
    }
}
