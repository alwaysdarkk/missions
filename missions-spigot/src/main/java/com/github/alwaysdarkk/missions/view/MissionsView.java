package com.github.alwaysdarkk.missions.view;

import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.configuration.InventoryValue;
import com.github.alwaysdarkk.missions.common.configuration.MessagesValue;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.item.ItemBuilder;
import com.github.alwaysdarkk.missions.common.util.NumberUtil;
import com.github.alwaysdarkk.missions.common.util.ProgressBarUtil;
import com.github.alwaysdarkk.missions.strategy.MissionCompleteStrategy;
import me.saiintbrisson.minecraft.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MissionsView extends PaginatedView<Mission> {

    private final MissionCache missionCache;
    private final MissionUserCache userCache;

    public MissionsView(MissionCache missionCache, MissionUserCache userCache) {
        super(5, "Missões");

        setSource(context -> missionCache.findAll());
        setLayout("XXXXXXXXX", "XXOOOOOXX", "<XOOOOOX>", "XXOOOOOXX", "XXXXXXXXX");

        this.missionCache = missionCache;
        this.userCache = userCache;
    }

    @Override
    protected void onItemRender(
            @NotNull PaginatedViewSlotContext<Mission> context, @NotNull ViewItem viewItem, @NotNull Mission mission) {
        final MissionUser user = userCache.find(context.getPlayer().getName());
        if (user == null) {
            context.close();
            return;
        }

        viewItem.onRender(getMissionIcon(mission, user)).onClick(handleMissionClick(mission, user));
    }

    private ViewItemHandler getMissionIcon(Mission mission, MissionUser user) {
        return context -> {
            if (user.hasCompleteMission(mission)) {
                context.setItem(getAlreadyCompleteIcon(mission));
                return;
            }

            final Mission previousMission = missionCache.findPrevious(mission);
            if (previousMission == null) {
                context.setItem(getUnlockIcon(mission, user));
                return;
            }

            if (!user.hasCompleteMission(previousMission)) {
                context.setItem(getNotUnlockIcon(mission));
                return;
            }

            context.setItem(getUnlockIcon(mission, user));
        };
    }

    private Consumer<ViewSlotClickContext> handleMissionClick(Mission mission, MissionUser user) {
        return context -> {
            final Player player = context.getPlayer();
            context.close();

            if (user.hasCompleteMission(mission)) {
                player.sendMessage(MessagesValue.get(MessagesValue::alreadyCompleteMission));
                return;
            }

            final Mission previousMission = missionCache.findPrevious(mission);
            if (previousMission == null) {
                if (mission.getObjective() > user.getProgress()) {
                    player.sendMessage(MessagesValue.get(MessagesValue::cantCompleteMission));
                    return;
                }

                new MissionCompleteStrategy(missionCache).execute(player, user, mission);
                return;
            }

            if (!user.hasCompleteMission(previousMission)) {
                player.sendMessage(MessagesValue.get(MessagesValue::notUnlockMission));
                return;
            }

            if (mission.getObjective() > user.getProgress()) {
                player.sendMessage(MessagesValue.get(MessagesValue::cantCompleteMission));
                return;
            }

            new MissionCompleteStrategy(missionCache).execute(player, user, mission);
        };
    }

    private ItemStack getNotUnlockIcon(Mission mission) {
        final String displayName = InventoryValue.get(InventoryValue::notUnlockName)
                .replace("{mission_id}", String.valueOf(mission.getId()));

        final List<String> lore = InventoryValue.get(InventoryValue::notUnlockLore).stream()
                .map(line -> line
                        .replace("{mission_objective}", NumberUtil.format(mission.getObjective()))
                        .replace("{mission_type}", mission.getType().getName())
                )
                .collect(Collectors.toList());

        return new ItemBuilder(mission.getItemStack())
                .hideAll()
                .displayName(displayName)
                .lore(lore)
                .build();
    }

    private ItemStack getAlreadyCompleteIcon(Mission mission) {
        final String displayName = InventoryValue.get(InventoryValue::alreadyCompleteName)
                .replace("{mission_id}", String.valueOf(mission.getId()));

        final List<String> lore = InventoryValue.get(InventoryValue::alreadyCompleteLore).stream()
                .map(line -> line.replace("{mission_objective}", NumberUtil.format(mission.getObjective()))
                        .replace("{mission_type}", mission.getType().getName()))
                .collect(Collectors.toList());

        return new ItemBuilder(mission.getItemStack())
                .hideAll()
                .displayName(displayName)
                .lore(lore)
                .build();
    }

    private ItemStack getUnlockIcon(Mission mission, MissionUser user) {
        final double progress = user.getProgress();
        final double objective = mission.getObjective();

        if (progress >= objective) {
            final String displayName = InventoryValue.get(InventoryValue::completeName)
                    .replace("{mission_id}", String.valueOf(mission.getId()));

            final List<String> lore = InventoryValue.get(InventoryValue::completeLore).stream()
                    .map(line -> line.replace(
                                    "{progress_bar}", ProgressBarUtil.getSimpleProgressBar(progress, objective))
                            .replace("{mission_type}", mission.getType().getName())
                            .replace("{mission_progress}", NumberUtil.format(progress))
                            .replace("{mission_objective}", NumberUtil.format(objective)))
                    .collect(Collectors.toList());

            return new ItemBuilder(mission.getItemStack())
                    .hideAll()
                    .displayName(displayName)
                    .lore(lore)
                    .build();
        }

        final String displayName = InventoryValue.get(InventoryValue::notCompleteName)
                .replace("{mission_id}", String.valueOf(mission.getId()));

        final List<String> lore = InventoryValue.get(InventoryValue::notCompleteLore).stream()
                .map(line -> line.replace("{progress_bar}", ProgressBarUtil.getSimpleProgressBar(progress, objective))
                        .replace("{mission_type}", mission.getType().getName())
                        .replace("{mission_progress}", NumberUtil.format(progress))
                        .replace("{mission_objective}", NumberUtil.format(objective)))
                .collect(Collectors.toList());

        return new ItemBuilder(mission.getItemStack())
                .hideAll()
                .displayName(displayName)
                .lore(lore)
                .build();
    }
}