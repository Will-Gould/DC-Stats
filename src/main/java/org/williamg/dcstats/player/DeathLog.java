package org.williamg.dcstats.player;

import java.sql.Timestamp;
import java.util.UUID;

public class DeathLog {

    private final UUID playerUUID;
    private final int cause;
    private final Timestamp timestamp;

    public DeathLog(UUID playerUUID, int cause) {
        this.playerUUID = playerUUID;
        this.cause = cause;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public int getCause() {
        return this.cause;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

}
