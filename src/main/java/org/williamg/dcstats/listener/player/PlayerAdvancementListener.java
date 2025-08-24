package org.williamg.dcstats.listener.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.williamg.dcstats.DCStats;

public class PlayerAdvancementListener implements Listener {

    private final DCStats plugin;

    public PlayerAdvancementListener(DCStats plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        String[] advancementKeys = event.getAdvancement().getKey().getKey().split("/");

        if (advancementKeys[0].equals("recipes")) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
           plugin.getDatabaseManager().processPlayerAdvancement(event.getPlayer(), event.getAdvancement().getKey().toString());
        });
    }

}
