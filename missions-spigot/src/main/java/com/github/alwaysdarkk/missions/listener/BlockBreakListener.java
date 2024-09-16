package com.github.alwaysdarkk.missions.listener;

import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionType;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@RequiredArgsConstructor
public class BlockBreakListener implements Listener {

    private final MissionUserCache userCache;
    private final MissionCache missionCache;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (event.isCancelled()) {
            return;
        }

        final MissionUser user = userCache.find(player.getName());
        if (user == null) {
            return;
        }

        final Mission mission = missionCache.find(user.getCurrentMission());
        if (mission == null) {
            return;
        }

        if (mission.getType() != MissionType.BREAK_BLOCKS) {
            return;
        }

        user.incrementProgress();
        user.setDirty(true);
    }
}