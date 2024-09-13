package com.github.alwaysdarkk.missions.common.registry;

import com.github.alwaysdarkk.missions.common.data.MissionUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionUserRegistry {

    private final Map<String, MissionUser> userMap = new HashMap<>();

    public void insert(MissionUser user) {
        userMap.put(user.getPlayerName(), user);
    }

    public void remove(MissionUser user) {
        userMap.remove(user.getPlayerName());
    }

    public MissionUser find(String playerName) {
        return userMap.get(playerName);
    }

    public List<MissionUser> getDirtyUsers() {
        return userMap.values().stream().filter(MissionUser::isDirty).collect(Collectors.toList());
    }
}