package com.github.alwaysdarkk.missions.common;

import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class MissionsAbstractPlugin extends JavaPlugin {

    public MissionRegistry missionRegistry;

    public static MissionsAbstractPlugin getInstance() {
        return getPlugin(MissionsAbstractPlugin.class);
    }

}