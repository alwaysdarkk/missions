package com.github.alwaysdarkk.missions.common.data;

import com.github.alwaysdarkk.missions.common.MissionsAbstractPlugin;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class MissionUser {

    private final String playerName;
    private final Map<Integer, Double> missionMap;

    public void addProgress(Mission mission, double progress) {
        missionMap.put(mission.getId(), progress);
    }

    public double getProgress(Mission mission) {
        return missionMap.getOrDefault(mission.getId(), 0.0);
    }

    public boolean hasFinished(Mission mission) {
        final double objective = mission.getObjective();
        final double progress = getProgress(mission);

        return progress >= objective;
    }

    public List<Mission> getCompletedMissions() {
        final MissionsAbstractPlugin plugin = MissionsAbstractPlugin.getInstance();
        final MissionRegistry missionRegistry = plugin.getMissionRegistry();

        return missionMap.keySet().stream()
                .map(missionRegistry::find)
                .filter(Objects::nonNull)
                .filter(this::hasFinished)
                .collect(Collectors.toList());
    }
}