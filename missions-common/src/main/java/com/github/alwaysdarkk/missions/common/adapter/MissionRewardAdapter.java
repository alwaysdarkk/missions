package com.github.alwaysdarkk.missions.common.adapter;

import com.github.alwaysdarkk.missions.common.data.MissionReward;
import com.github.alwaysdarkk.missions.common.item.adapter.ItemStackAdapter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MissionRewardAdapter {

    private final ConfigurationSection configurationSection;

    public List<MissionReward> getRewards() {
        return configurationSection.getKeys(false).stream()
                .map(configurationSection::getConfigurationSection)
                .filter(Objects::nonNull)
                .map(this::adaptReward)
                .collect(Collectors.toList());
    }

    private MissionReward adaptReward(ConfigurationSection section) {
        final String id = section.getName();
        final String command = section.getString("command");

        final ConfigurationSection itemSection = section.getConfigurationSection("item-stack");
        final ItemStack itemStack = ItemStackAdapter.adaptItemStack(itemSection, "material");

        return new MissionReward(id, command, itemStack);
    }
}