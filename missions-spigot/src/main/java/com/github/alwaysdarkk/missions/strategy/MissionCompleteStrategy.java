package com.github.alwaysdarkk.missions.strategy;

import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.configuration.MessagesValue;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionReward;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class MissionCompleteStrategy {

    private final MissionCache missionCache;

    public void execute(Player player, MissionUser user, Mission mission) {
        user.addCompleteMission(mission);
        user.setDirty(true);

        final List<MissionReward> rewards = mission.getRewards();
        rewards.forEach(reward -> reward.give(player));

        final String message = MessagesValue.get(MessagesValue::completeMission)
                .replace("{mission_id}", String.valueOf(mission.getId()));
        player.sendMessage(message);

        final Mission nextMission = missionCache.findNext(mission);
        if (nextMission == null) {
            return;
        }

        user.setCurrentMission(nextMission.getId());
        user.setProgress(0.0);

        user.setDirty(true);
    }
}