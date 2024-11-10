package org.williamg.dcstats;

import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Util {

    public static int monsterTypeTranslation(EntityType type){
        switch(type){
            case BLAZE -> {
                return 1;
            }
            case BOGGED -> {
                return 2;
            }
            case BREEZE -> {
                return 3;
            }
            case CAVE_SPIDER -> {
                return 4;
            }
            case CREEPER -> {
                return 5;
            }
            case DROWNED -> {
                return 6;
            }
            case ELDER_GUARDIAN -> {
                return 7;
            }
            case ENDERMAN -> {
                return 8;
            }
            case ENDERMITE -> {
                return 9;
            }
            case EVOKER -> {
                return 10;
            }
            case GUARDIAN -> {
                return 11;
            }
            case HUSK -> {
                return 12;
            }
            case ILLUSIONER -> {
                return 13;
            }
            case PIGLIN -> {
                return 14;
            }
            case PIGLIN_BRUTE -> {
                return 15;
            }
            case ZOMBIFIED_PIGLIN -> {
                return 16;
            }
            case PILLAGER -> {
                return 17;
            }
            case RAVAGER -> {
                return 18;
            }
            case SILVERFISH -> {
                return 19;
            }
            case SKELETON -> {
                return 20;
            }
            case SPIDER -> {
                return 21;
            }
            case STRAY -> {
                return 22;
            }
            case VEX -> {
                return 23;
            }
            case VINDICATOR -> {
                return 24;
            }
            case WARDEN -> {
                return 25;
            }
            case WITCH -> {
                return 26;
            }
            case WITHER -> {
                return 27;
            }
            case WITHER_SKELETON -> {
                return 28;
            }
            case ZOGLIN -> {
                return 29;
            }
            case ZOMBIE -> {
                return 30;
            }
            case ZOMBIE_VILLAGER -> {
                return 31;
            }
            case SHULKER -> {
                return 32;
            }
            case MAGMA_CUBE -> {
                return 33;
            }
            case ENDER_DRAGON -> {
                return 34;
            }
            default -> {
                return 0;
            }

        }
    }

    public static int deathCauseTranslation(PlayerDeathEvent event){
        DamageSource source = event.getDamageSource();
        DamageType damage = source.getDamageType();

        return 0;
    }

}
