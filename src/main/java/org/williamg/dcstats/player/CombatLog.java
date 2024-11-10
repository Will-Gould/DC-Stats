package org.williamg.dcstats.player;

import java.sql.Timestamp;
import java.util.UUID;

public class CombatLog {

    private final UUID playerUUID;
    private final UUID victimUUID;
    private final Timestamp timestamp;

    public CombatLog(UUID playerUUID, UUID victimUUID) {
        this.playerUUID = playerUUID;
        this.victimUUID = victimUUID;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public UUID getVictimUUID() {
        return this.victimUUID;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

}
