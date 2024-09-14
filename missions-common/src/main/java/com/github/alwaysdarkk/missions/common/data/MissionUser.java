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
    private List<Integer> completeMissions = new ArrayList<>();

    @Builder.Default
    private boolean dirty = true;

    public void incrementProgress() {
        progress += 1;
    }

    public void addCompleteMission(Mission mission) {
        completeMissions.add(mission.getId());
    }

    public boolean hasCompleteMission(Mission mission) {
        return completeMissions.contains(mission.getId());
    }
}