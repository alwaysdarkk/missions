package com.github.alwaysdarkk.missions.listener.registry;

import com.github.alwaysdarkk.missions.MissionsPlugin;
import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.repository.MissionUserRepository;
import com.github.alwaysdarkk.missions.listener.BlockBreakListener;
import com.github.alwaysdarkk.missions.listener.BlockPlaceListener;
import com.github.alwaysdarkk.missions.listener.EntityDeathListener;
import com.github.alwaysdarkk.missions.listener.UserConnectionListener;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

@Data(staticConstructor = "of")
public class ListenerRegistry {

    private final MissionsPlugin plugin;

    public void setup() {
        final MissionUserRepository userRepository = plugin.getUserRepository();
        final MissionUserCache userCache = plugin.getUserCache();

        final MissionCache missionCache = plugin.getMissionCache();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new UserConnectionListener(userRepository, userCache), plugin);
        pluginManager.registerEvents(new BlockBreakListener(userCache, missionCache), plugin);
        pluginManager.registerEvents(new BlockPlaceListener(userCache, missionCache), plugin);
        pluginManager.registerEvents(new EntityDeathListener(userCache, missionCache), plugin);
    }
}