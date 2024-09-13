package com.github.alwaysdarkk.missions;

import com.github.alwaysdarkk.missions.common.MissionsAbstractPlugin;
import com.github.alwaysdarkk.missions.common.registry.ConfigurationRegistry;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;

public class MissionsPlugin extends MissionsAbstractPlugin {

    @Override
    public void onLoad() {
        ConfigurationRegistry.of(this).setup();
    }

    @Override
    public void onEnable() {
        missionRegistry = new MissionRegistry();
    }
}