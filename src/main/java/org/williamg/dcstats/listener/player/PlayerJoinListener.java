package org.williamg.dcstats.listener.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.player.PlayerHandler;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private DCStats plugin;
    private final PlayerHandler playerHandler;

    public PlayerJoinListener(DCStats plugin) {
        this.plugin = plugin;
        this.playerHandler = plugin.getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean recordExists = true;

        //Check if they have an existing statistics record in db
        try{
            recordExists = this.plugin.getDatabaseManager().playerRecordExists(player);
        } catch (SQLException e) {
            this.plugin.getLogger().severe("Failed to access statistics table");
            //No guarantee a record doesn't already exist so best to avoid possible collision and not collect at all
            player.sendMessage(Component.text("There was a problem accessing the statistics database, your statistics are not being tracked", NamedTextColor.RED));
            return;
        }

        //Check if player has logged back in before their stats have been processed and deleted if so keep the existing entry
        if(playerHandler.getPlayerStats(player) != null) {
            playerHandler.getPlayerStats(player).setOnline(true);
        }else{
            playerHandler.addPlayerStats(player, recordExists);
        }
    }

}
