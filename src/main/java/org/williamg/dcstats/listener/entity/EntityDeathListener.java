package org.williamg.dcstats.listener.entity;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.player.PlayerHandler;
import org.williamg.dcstats.player.PlayerStatistics;
import org.williamg.dcstats.utility.Statistic;

public class EntityDeathListener implements Listener {

    private final PlayerHandler playerHandler;

    public EntityDeathListener(DCStats plugin) {
        this.playerHandler = plugin.getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    void onEntityDeath(EntityDeathEvent event) {

        //Check if it was killed by a player
        if(event.getEntity().getKiller() == null) {
            return;
        }

//        Multiple values may need to be incremented upon killing of an entity
//        Those currently being:
//         - Special mobs e.g. Ghast, evoker, warden
//         - Non-spawned hostile mobs
        boolean validSpawn = true;
        if (
                event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.COMMAND)
                        || event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)
                        || event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.NETHER_PORTAL)
                        || event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)
                        || event.getEntity().getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG))
        {
            validSpawn = false;
        }
        if(!(event.getEntity() instanceof Monster)) {
            //Is it a ghast or ender dragon
            if(event.getEntity() instanceof Ghast) {
                playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.GHASTS_KILLED);
                playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.MOBS_KILLED);
                return;
            }
            if(event.getEntity() instanceof EnderDragon) {
                playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.ENDER_DRAGONS_KILL);
                playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.MOBS_KILLED);
                return;
            }
            return;
        }

        //Increment total mobs killed
        if(validSpawn) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.MOBS_KILLED);
        }

        //Event is a monster kill
        if(event.getEntity() instanceof Witch) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.WITCHES_KILLED);
            return;
        }
        if(event.getEntity() instanceof Evoker){
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.EVOKERS_KILLED);
            return;
        }
        if(event.getEntity() instanceof PiglinBrute) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.PIGLIN_BRUTES_KILLED);
            return;
        }
        if(event.getEntity() instanceof Ravager) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.RAVAGERS_KILLED);
            return;
        }
        if(event.getEntity() instanceof ElderGuardian) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.ELDER_GUARDIANS_KILLED);
            return;
        }
        if(event.getEntity() instanceof Wither) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.WITHERS_KILLED);
            return;
        }
        if(event.getEntity() instanceof Warden) {
            playerHandler.incrementPlayerStatistic(event.getEntity().getKiller(), Statistic.WARDENS_KILLED);
            return;
        }

    }

}
