package com.github.alwaysdarkk.missions.common.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Mission {

    private final int id;
    private final MissionType type;
    private final double objective;
    private final String command;
}