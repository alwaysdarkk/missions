package com.github.alwaysdarkk.missions.common.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MissionType {
    BREAK_BLOCKS("Quebrar blocos"),
    PLACE_BLOCKS("Colocar blocos"),
    KILL_MOBS("Matar mobs"),
    KILL_PLAYERS("Matar jogadores");

    private final String name;
}