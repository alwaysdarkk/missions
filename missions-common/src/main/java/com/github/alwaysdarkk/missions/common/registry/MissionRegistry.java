package com.github.alwaysdarkk.missions.common.registry;

import com.github.alwaysdarkk.missions.common.adapter.MissionAdapter;
import com.github.alwaysdarkk.missions.common.data.Mission;

import java.util.*;

public class MissionRegistry {

    private final Map<Integer, Mission> missionMap;

    public MissionRegistry() {
        this.missionMap = new HashMap<>();

        new MissionAdapter().getMissions().stream()
                .filter(Objects::nonNull)
                .forEach(this::insert);
    }

    public void insert(Mission mission) {
        missionMap.put(mission.getId(), mission);
    }

    public Mission find(int missionId) {
        return missionMap.get(missionId);
    }

    public List<Mission> findAll() {
        return new ArrayList<>(missionMap.values());
    }
}