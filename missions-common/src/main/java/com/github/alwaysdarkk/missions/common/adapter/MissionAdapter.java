package com.github.alwaysdarkk.missions.common.adapter;

import com.github.alwaysdarkk.missions.common.configuration.ConfigValue;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionReward;
import com.github.alwaysdarkk.missions.common.data.MissionType;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MissionAdapter {

    public List<Mission> getMissions() {
        final ConfigurationSection configurationSection = ConfigValue.get(ConfigValue::missionsSection);
        if (configurationSection == null) {
            return null;
        }

        return configurationSection.getKeys(false).stream()
                .map(configurationSection::getConfigurationSection)
                .filter(Objects::nonNull)
                .map(this::adaptMission)
                .collect(Collectors.toList());
    }

    private Mission adaptMission(ConfigurationSection section) {
        final int id = Integer.parseInt(section.getName());

        final MissionType type = MissionType.valueOf(section.getString("type"));
        final double objective = section.getDouble("objective");

        final ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");
        final List<MissionReward> rewards = new MissionRewardAdapter(rewardsSection).getRewards();

        return new Mission(id, type, objective, rewards);
    }
}
