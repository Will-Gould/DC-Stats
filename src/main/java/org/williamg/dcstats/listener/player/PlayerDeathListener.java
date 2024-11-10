package org.williamg.dcstats.listener.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.Util;
import org.williamg.dcstats.player.CombatLog;
import org.williamg.dcstats.player.DeathLog;
import org.williamg.dcstats.player.PlayerHandler;

public class PlayerDeathListener implements Listener {

    private final PlayerHandler playerHandler;

    public PlayerDeathListener(DCStats plugin) {
        this.playerHandler = plugin.getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();

        DeathLog deathLog = new DeathLog(deadPlayer.getUniqueId(), Util.deathCauseTranslation(event));
        playerHandler.addDeathLog(deadPlayer, deathLog);

        if(event.getPlayer().getKiller() != null) {
            playerHandler.addCombatLog(event.getPlayer().getKiller(), new CombatLog(event.getPlayer().getKiller().getUniqueId(), deadPlayer.getUniqueId()));
        }

    }

}
