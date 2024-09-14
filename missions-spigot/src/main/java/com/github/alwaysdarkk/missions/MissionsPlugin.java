package com.github.alwaysdarkk.missions;

import com.github.alwaysdarkk.missions.common.registry.ConfigurationRegistry;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import com.github.alwaysdarkk.missions.common.registry.MissionUserRegistry;
import com.github.alwaysdarkk.missions.common.repository.MissionUserRepository;
import com.github.alwaysdarkk.missions.listener.BlockBreakListener;
import com.github.alwaysdarkk.missions.listener.BlockPlaceListener;
import com.github.alwaysdarkk.missions.listener.EntityDeathListener;
import com.github.alwaysdarkk.missions.listener.UserConnectionListener;
import com.github.alwaysdarkk.missions.runnable.MissionUserSaveRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MissionsPlugin extends JavaPlugin {

    public static MissionsPlugin getInstance() {
        return getPlugin(MissionsPlugin.class);
    }

    @Override
    public void onLoad() {
        ConfigurationRegistry.of(this).setup();
    }

    @Override
    public void onEnable() {
        final MissionUserRepository userRepository = new MissionUserRepository();
        final MissionUserRegistry userRegistry = new MissionUserRegistry();

        final MissionRegistry missionRegistry = new MissionRegistry();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new UserConnectionListener(userRepository, userRegistry), this);
        pluginManager.registerEvents(new BlockBreakListener(userRegistry, missionRegistry), this);
        pluginManager.registerEvents(new BlockPlaceListener(userRegistry, missionRegistry), this);
        pluginManager.registerEvents(new EntityDeathListener(userRegistry, missionRegistry), this);

        new MissionUserSaveRunnable(userRegistry, userRepository);
    }
}