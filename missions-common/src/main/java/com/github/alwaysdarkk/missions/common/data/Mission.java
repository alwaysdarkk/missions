package com.github.alwaysdarkk.missions.common.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Mission {

    private final int id;
    private final ItemStack itemStack;
    private final MissionType type;
    private final double objective;
    private final List<MissionReward> rewards;

    public void giveRewards(Player player) {
        rewards.forEach(reward -> reward.give(player));
    }
}