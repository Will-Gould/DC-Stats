package org.williamg.dcstats.player;

import org.bukkit.entity.Player;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.utility.Statistic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerHandler {

    private final DCStats plugin;
    private final ConcurrentHashMap<UUID, PlayerStatistics> playerStats;

    public PlayerHandler(DCStats plugin) {
        this.plugin = plugin;
        this.playerStats = new ConcurrentHashMap<>();
    }

    public void incrementPlayerStatistic(Player player, Statistic stat) {
        switch (stat) {
            case GHASTS_KILLED -> {
                playerStats.get(player.getUniqueId()).incGhastsKilled();
            }
            case EVOKERS_KILLED -> {
                playerStats.get(player.getUniqueId()).incEvokersKilled();
            }
            case WARDENS_KILLED -> {
                playerStats.get(player.getUniqueId()).incWardensKilled();
            }
            case WITCHES_KILLED -> {
                playerStats.get(player.getUniqueId()).incWitchesKilled();
            }
            case WITHERS_KILLED -> {
                playerStats.get(player.getUniqueId()).incWithersKilled();
            }
            case DIAMONDS_MINED -> {
                playerStats.get(player.getUniqueId()).incDiamondsMined();
            }
            case EMERALDS_MINED -> {
                playerStats.get(player.getUniqueId()).incEmeraldsMined();
            }
            case RAVAGERS_KILLED -> {
                playerStats.get(player.getUniqueId()).incRavagersKilled();
            }
            case ENDER_DRAGONS_KILL -> {
                playerStats.get(player.getUniqueId()).incEnderDragonsKilled();
            }
            case PIGLIN_BRUTES_KILLED -> {
                playerStats.get(player.getUniqueId()).incPiglinBrutesKilled();
            }
            case ELDER_GUARDIANS_KILLED -> {
                playerStats.get(player.getUniqueId()).incElderGuardiansKilled();
            }
            case MOBS_KILLED ->  {
                playerStats.get(player.getUniqueId()).incMobsKilled();
            }
        }
    }

    public void addDeathLog(Player p, DeathLog deathLog) {
        this.playerStats.get(p.getUniqueId()).addDeathLog(deathLog);
    }

    public void addCombatLog(Player p, CombatLog combatLog) {
        this.playerStats.get(p.getUniqueId()).addCombatLog(combatLog);
    }

    public void addPlayerStats(Player player, boolean recordExists){
        this.playerStats.put(player.getUniqueId(), new PlayerStatistics(player, recordExists));
    }

    public PlayerStatistics getPlayerStats(Player player){
        return this.playerStats.get(player.getUniqueId());
    }

    public void setOffline(Player player) {
        this.playerStats.get(player.getUniqueId()).setOnline(false);
    }

    public void setOnline(Player player) {
        this.playerStats.get(player.getUniqueId()).setOnline(true);
    }

    public void processPlayerData() {
        this.playerStats.forEach((uuid, statistics) -> {
            this.plugin.getDatabaseManager().runStatisticsQueries(statistics);
            statistics.setRecordExists(true);
            statistics.reset();
            //Remove from list if player is no longer online
            if(!statistics.isOnline()){
                playerStats.remove(uuid);
            }
        });
    }
}
