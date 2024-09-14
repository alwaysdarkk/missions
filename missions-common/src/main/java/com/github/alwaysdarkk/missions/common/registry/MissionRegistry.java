package com.github.alwaysdarkk.missions.common.registry;

import com.github.alwaysdarkk.missions.common.adapter.MissionAdapter;
import com.github.alwaysdarkk.missions.common.data.Mission;

import java.util.*;
import java.util.stream.Collectors;

public class MissionRegistry {

    private final Map<Integer, Mission> missionMap;

    public MissionRegistry() {
        this.missionMap = new HashMap<>();

        new MissionAdapter().getMissions().stream().filter(Objects::nonNull).forEach(this::insert);
    }

    public void insert(Mission mission) {
        missionMap.put(mission.getId(), mission);
    }

    public Mission find(int missionId) {
        return missionMap.get(missionId);
    }

    public Mission findNext(Mission mission) {
        return missionMap.get(mission.getId() + 1);
    }

    public Mission findPrevious(Mission mission) {
        return missionMap.get(mission.getId() - 1);
    }

    public List<Mission> findAll() {
        return missionMap.values().stream()
                .sorted(Comparator.comparingInt(Mission::getId))
                .collect(Collectors.toList());
    }
}