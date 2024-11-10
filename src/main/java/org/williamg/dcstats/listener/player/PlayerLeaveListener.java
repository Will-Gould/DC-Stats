package org.williamg.dcstats.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.player.PlayerHandler;

public class PlayerLeaveListener implements Listener {

    private DCStats plugin;
    private final PlayerHandler playerHandler;

    public PlayerLeaveListener(DCStats plugin) {
        this.plugin = plugin;
        this.playerHandler = plugin.getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.playerHandler.setOffline(event.getPlayer());
    }

}
