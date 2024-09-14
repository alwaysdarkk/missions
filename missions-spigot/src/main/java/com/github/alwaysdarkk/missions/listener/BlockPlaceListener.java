package com.github.alwaysdarkk.missions.listener;

import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionType;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import com.github.alwaysdarkk.missions.common.registry.MissionUserRegistry;
import com.github.alwaysdarkk.missions.strategy.MissionCompleteStrategy;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@RequiredArgsConstructor
public class BlockPlaceListener implements Listener {

    private final MissionUserRegistry userRegistry;
    private final MissionRegistry missionRegistry;

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (event.isCancelled()) {
            return;
        }

        final MissionUser user = userRegistry.find(player.getName());
        if (user == null) {
            return;
        }

        final Mission mission = missionRegistry.find(user.getCurrentMission());
        if (mission == null) {
            return;
        }

        if (mission.getType() != MissionType.PLACE_BLOCKS) {
            return;
        }

        user.incrementProgress();

        if (user.getProgress() >= mission.getObjective()) {
            new MissionCompleteStrategy(missionRegistry).execute(player, user, mission);
        }

        user.setDirty(true);
    }
}