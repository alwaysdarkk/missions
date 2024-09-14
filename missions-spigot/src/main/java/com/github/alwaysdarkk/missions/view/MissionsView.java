package com.github.alwaysdarkk.missions.view;

import com.github.alwaysdarkk.missions.common.configuration.InventoryValue;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.item.ItemBuilder;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import com.github.alwaysdarkk.missions.common.registry.MissionUserRegistry;
import com.github.alwaysdarkk.missions.common.util.NumberUtil;
import com.github.alwaysdarkk.missions.common.util.ProgressBarUtil;
import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewItem;
import me.saiintbrisson.minecraft.ViewItemHandler;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class MissionsView extends PaginatedView<Mission> {

    private final MissionRegistry missionRegistry;
    private final MissionUserRegistry userRegistry;

    public MissionsView(MissionRegistry missionRegistry, MissionUserRegistry userRegistry) {
        super(InventoryValue.get(InventoryValue::size), InventoryValue.get(InventoryValue::name));

        setSource(context -> missionRegistry.findAll());
        setLayout(String.join(", ", InventoryValue.get(InventoryValue::layout)));

        this.missionRegistry = missionRegistry;
        this.userRegistry = userRegistry;
    }

    @Override
    protected void onItemRender(
            @NotNull PaginatedViewSlotContext<Mission> context, @NotNull ViewItem viewItem, @NotNull Mission mission) {
        final MissionUser user = userRegistry.find(context.getPlayer().getName());
        if (user == null) {
            context.close();
            return;
        }

        viewItem.onRender(getMissionIcon(mission, user));
    }

    private ViewItemHandler getMissionIcon(Mission mission, MissionUser user) {
        return context -> {
            final Mission previousMission = missionRegistry.findPrevious(mission);
            if (previousMission == null) {
                context.setItem(getUnlockedIcon(mission, user));
                return;
            }

            if (!user.hasCompleteMission(previousMission)) {
                final List<String> lore = InventoryValue.get(InventoryValue::notUnlockLore).stream()
                        .map(line -> line.replace("{mission_objective}", NumberUtil.format(mission.getObjective())))
                        .collect(Collectors.toList());

                final ItemStack itemStack = new ItemBuilder(mission.getItemStack())
                        .hideAll()
                        .displayName(InventoryValue.get(InventoryValue::notUnlockName))
                        .lore(lore)
                        .build();

                context.setItem(itemStack);
                return;
            }

            if (user.hasCompleteMission(previousMission)) {
                final List<String> lore = InventoryValue.get(InventoryValue::alreadyCompleteLore).stream()
                        .map(line -> line.replace("{mission_objective}", NumberUtil.format(mission.getObjective())))
                        .collect(Collectors.toList());

                final ItemStack itemStack = new ItemBuilder(mission.getItemStack())
                        .hideAll()
                        .displayName(InventoryValue.get(InventoryValue::alreadyCompleteName))
                        .lore(lore)
                        .build();

                context.setItem(itemStack);
                return;
            }

            context.setItem(getUnlockedIcon(mission, user));
        };
    }

    private ItemStack getUnlockedIcon(Mission mission, MissionUser user) {
        final double progress = user.getProgress();
        final double objective = mission.getObjective();

        if (progress >= objective) {
            final List<String> lore = InventoryValue.get(InventoryValue::completeLore).stream()
                    .map(line -> line.replace(
                                    "{progress_bar}", ProgressBarUtil.getSimpleProgressBar(progress, objective))
                            .replace("{mission_progress}", NumberUtil.format(progress))
                            .replace("{mission_objective}", NumberUtil.format(objective)))
                    .collect(Collectors.toList());

            return new ItemBuilder(mission.getItemStack())
                    .hideAll()
                    .displayName(InventoryValue.get(InventoryValue::completeName))
                    .lore(lore)
                    .build();
        }

        final List<String> lore = InventoryValue.get(InventoryValue::notCompleteLore).stream()
                .map(line -> line.replace("{progress_bar}", ProgressBarUtil.getSimpleProgressBar(progress, objective))
                        .replace("{mission_progress}", NumberUtil.format(progress))
                        .replace("{mission_objective}", NumberUtil.format(objective)))
                .collect(Collectors.toList());

        return new ItemBuilder(mission.getItemStack())
                .hideAll()
                .displayName(InventoryValue.get(InventoryValue::notCompleteName))
                .lore(lore)
                .build();
    }
}