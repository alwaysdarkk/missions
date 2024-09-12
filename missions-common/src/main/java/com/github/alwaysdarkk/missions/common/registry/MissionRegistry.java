package com.github.alwaysdarkk.missions.common.registry;

import com.github.alwaysdarkk.missions.common.data.Mission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionRegistry {

    private final Map<Integer, Mission> missionMap = new HashMap<>();

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