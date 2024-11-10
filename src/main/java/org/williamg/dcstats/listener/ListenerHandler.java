package org.williamg.dcstats.listener;

import org.bukkit.plugin.PluginManager;
import org.williamg.dcstats.DCStats;
import org.williamg.dcstats.listener.block.BlockBreakListener;
import org.williamg.dcstats.listener.entity.EntityDeathListener;
import org.williamg.dcstats.listener.player.PlayerDeathListener;
import org.williamg.dcstats.listener.player.PlayerJoinListener;
import org.williamg.dcstats.listener.player.PlayerLeaveListener;

public class ListenerHandler {

    public ListenerHandler(DCStats plugin) {

        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new PlayerDeathListener(plugin), plugin);
        pm.registerEvents(new EntityDeathListener(plugin), plugin);
        pm.registerEvents(new PlayerJoinListener(plugin), plugin);
        pm.registerEvents(new PlayerLeaveListener(plugin), plugin);
        pm.registerEvents(new BlockBreakListener(plugin), plugin);

    }

}
