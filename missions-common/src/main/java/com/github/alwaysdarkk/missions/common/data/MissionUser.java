package com.github.alwaysdarkk.missions.common.data;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MissionUser {

    private final String playerName;

    @Builder.Default
    private int currentMission = 1;

    @Builder.Default
    private double progress = 0.0;

    @Builder.Default
    private List<Integer> completedMissions = new ArrayList<>();

    @Builder.Default
    private boolean dirty = true;

    public void addProgress(double amount) {
        progress += amount;
    }

    public boolean hasFinished(Mission mission) {
        final double objective = mission.getObjective();
        return progress >= objective;
    }
}