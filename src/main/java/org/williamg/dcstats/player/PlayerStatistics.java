package org.williamg.dcstats.player;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SpellCheckingInspection", "SqlNoDataSourceInspection"})
public class PlayerStatistics {

    private final Player player;
    private boolean online;
    private boolean recordExists;
    private final List<CombatLog> combatLogs;
    private final List<DeathLog> deathLogs;
    private int enderDragonsKilled;
    private int withersKilled;
    private int wardensKilled;
    private int ravagersKilled;
    private int evokersKilled;
    private int piglinBrutesKilled;
    private int ghastsKilled;
    private int elderGuardiansKilled;
    private int witchesKilled;
    private int mobsKilled;
    private int diamondsMined;
    private int emeraldsMined;

    public PlayerStatistics(Player player, boolean recordExists) {
        this.player = player;
        this.online = true;
        this.recordExists = recordExists;
        this.combatLogs = new ArrayList<>();
        this.deathLogs = new ArrayList<>();
        this.enderDragonsKilled = 0;
        this.withersKilled = 0;
        this.wardensKilled = 0;
        this.ravagersKilled = 0;
        this.evokersKilled = 0;
        this.piglinBrutesKilled = 0;
        this.ghastsKilled = 0;
        this.elderGuardiansKilled = 0;
        this.witchesKilled = 0;
        this.mobsKilled = 0;
        this.diamondsMined = 0;
        this.emeraldsMined = 0;
    }

    public void reset(){
        this.combatLogs.clear();
        this.deathLogs.clear();
        this.enderDragonsKilled = 0;
        this.withersKilled = 0;
        this.wardensKilled = 0;
        this.ravagersKilled = 0;
        this.evokersKilled = 0;
        this.piglinBrutesKilled = 0;
        this.ghastsKilled = 0;
        this.elderGuardiansKilled = 0;
        this.witchesKilled = 0;
        this.mobsKilled = 0;
        this.diamondsMined = 0;
        this.emeraldsMined = 0;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    public void setRecordExists(boolean recordExists) {
        this.recordExists = recordExists;
    }

    public void addCombatLog(CombatLog combatLog) {
        this.combatLogs.add(combatLog);
    }

    public void addDeathLog(DeathLog deathLog) {
        this.deathLogs.add(deathLog);
    }

    public void incWithersKilled() {
        this.withersKilled++;
    }

    public void incEnderDragonsKilled() {
        this.enderDragonsKilled++;
    }

    public void incWardensKilled() {
        this.wardensKilled++;
    }

    public void incRavagersKilled() {
        this.ravagersKilled++;
    }

    public void incPiglinBrutesKilled() {
        this.piglinBrutesKilled++;
    }

    public void incEvokersKilled() {
        this.evokersKilled++;
    }

    public void incGhastsKilled() {
        this.ghastsKilled++;
    }

    public void incElderGuardiansKilled() {
        this.elderGuardiansKilled++;
    }

    public void incWitchesKilled() {
        this.witchesKilled++;
    }

    public void incMobsKilled() {
        this.mobsKilled++;
    }

    public void incDiamondsMined() {
        this.diamondsMined++;
    }

    public void incEmeraldsMined() {
        emeraldsMined++;
    }

    public List<PreparedStatement> getPreparedStatements(Connection c, String prefix) throws SQLException {
        List<PreparedStatement> preparedStatements = new ArrayList<>();

        //Create general statistics statement
        PreparedStatement genStatsStatement = null;
        if(recordExists){
            genStatsStatement = c.prepareStatement(new StringBuilder()
                    .append("UPDATE ").append(prefix).append("general_stats SET ")
                    .append("ender_dragons_killed = ender_dragons_killed + ?, ")
                    .append("withers_killed = withers_killed + ?, ")
                    .append("wardens_killed = wardens_killed + ?, ")
                    .append("ravagers_killed  = ravagers_killed + ?, ")
                    .append("evokers_killed = evokers_killed + ?, ")
                    .append("piglin_brutes_killed = piglin_brutes_killed + ?, ")
                    .append("ghasts_killed = ghasts_killed + ?, ")
                    .append("elder_guardians_killed = elder_guardians_killed + ?, ")
                    .append("witches_killed = witches_killed + ?, ")
                    .append("mobs_killed = mobs_killed + ?, ")
                    .append("diamonds_mined = diamonds_mined + ?, ")
                    .append("emeralds_mined = emeralds_mined + ? ")
                    .append("WHERE player_uuid = ?;")
                    .toString()
            );
            genStatsStatement.setInt(1, enderDragonsKilled);
            genStatsStatement.setInt(2, withersKilled);
            genStatsStatement.setInt(3, wardensKilled);
            genStatsStatement.setInt(4, ravagersKilled);
            genStatsStatement.setInt(5, evokersKilled);
            genStatsStatement.setInt(6, piglinBrutesKilled);
            genStatsStatement.setInt(7, ghastsKilled);
            genStatsStatement.setInt(8, elderGuardiansKilled);
            genStatsStatement.setInt(9, witchesKilled);
            genStatsStatement.setInt(10, mobsKilled);
            genStatsStatement.setInt(11, diamondsMined);
            genStatsStatement.setInt(12, emeraldsMined);
            genStatsStatement.setString(13, player.getUniqueId().toString());
        }else{
            genStatsStatement = c.prepareStatement(new StringBuilder()
                    .append("INSERT INTO ").append(prefix).append("general_stats (")
                    .append("player_uuid, ender_dragons_killed, withers_killed, wardens_killed, ravagers_killed, ")
                    .append("evokers_killed, piglin_brutes_killed, ghasts_killed, elder_guardians_killed, witches_killed, mobs_killed, diamonds_mined, emeralds_mined) ")
                    .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")
                    .toString());
            genStatsStatement.setString(1, player.getUniqueId().toString());
            genStatsStatement.setInt(2, enderDragonsKilled);
            genStatsStatement.setInt(3, withersKilled);
            genStatsStatement.setInt(4, wardensKilled);
            genStatsStatement.setInt(5, ravagersKilled);
            genStatsStatement.setInt(6, evokersKilled);
            genStatsStatement.setInt(7, piglinBrutesKilled);
            genStatsStatement.setInt(8, ghastsKilled);
            genStatsStatement.setInt(9, elderGuardiansKilled);
            genStatsStatement.setInt(10, witchesKilled);
            genStatsStatement.setInt(11, mobsKilled);
            genStatsStatement.setInt(12, diamondsMined);
            genStatsStatement.setInt(13, emeraldsMined);
        }

        preparedStatements.add(genStatsStatement);

        //Create death logs
        for(DeathLog deathLog : deathLogs){
            PreparedStatement s = c.prepareStatement(new StringBuilder()
                    .append("INSERT INTO ").append(prefix).append("deaths (")
                    .append("player_uuid, cause, death_time) ")
                    .append("VALUES (?, ?, ?);")
                    .toString()
            );
            s.setString(1, player.getUniqueId().toString());
            s.setInt(2, deathLog.getCause());
            s.setTimestamp(3, deathLog.getTimestamp());
            preparedStatements.add(s);
        }

        //Create combat logs
        for(CombatLog combatLog : combatLogs){
            PreparedStatement s = c.prepareStatement(new StringBuilder()
                    .append("INSERT INTO ").append(prefix).append("combat (")
                    .append("player_uuid, victim_uuid, combat_time) ")
                    .append("VALUES (?, ?, ?);")
                    .toString()
            );
            s.setString(1, player.getUniqueId().toString());
            s.setString(2, combatLog.getVictimUUID().toString());
            s.setTimestamp(3, combatLog.getTimestamp());
            preparedStatements.add(s);
        }
        return preparedStatements;
    }
}
