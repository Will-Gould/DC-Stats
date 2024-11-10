package org.williamg.dcstats.listener.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.player.PlayerHandler;
import org.williamg.dcstats.utility.Statistic;

public class BlockBreakListener implements Listener {

    private final PlayerHandler playerHandler;

    public BlockBreakListener(DCStats plugin) {
        playerHandler = plugin.getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(block.getBlockData().getMaterial() != Material.DIAMOND_ORE && block.getBlockData().getMaterial() != Material.EMERALD_ORE){
            return;
        }

        //Check if block was broken and items dropped, mining with silk touch will not count towards stats
        if(event.isDropItems() && event.getExpToDrop() > 0){
            if(block.getBlockData().getMaterial() == Material.DIAMOND_ORE){
                playerHandler.incrementPlayerStatistic(event.getPlayer(), Statistic.DIAMONDS_MINED);
            }
            if(block.getBlockData().getMaterial() == Material.EMERALD_ORE){
                playerHandler.incrementPlayerStatistic(event.getPlayer(), Statistic.EMERALDS_MINED);
            }
        }
    }

}
