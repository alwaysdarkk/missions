package com.github.alwaysdarkk.missions.strategy;

import com.github.alwaysdarkk.missions.common.configuration.MessagesValue;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MissionCompleteStrategy {

    private final MissionRegistry missionRegistry;

    public void execute(Player player, MissionUser user, Mission mission) {
        user.addCompleteMission(mission);

        final String message = MessagesValue.get(MessagesValue::completedMission)
                .replace("{mission_id}", String.valueOf(mission.getId()));
        player.sendMessage(message);

        final Mission nextMission = missionRegistry.findNext(mission);
        if (nextMission == null) {
            return;
        }

        user.setCurrentMission(nextMission.getId());
        user.setProgress(0.0);
    }
}