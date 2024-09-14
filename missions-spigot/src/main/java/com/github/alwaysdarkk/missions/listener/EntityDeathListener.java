package com.github.alwaysdarkk.missions.listener;

import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionType;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.registry.MissionRegistry;
import com.github.alwaysdarkk.missions.common.registry.MissionUserRegistry;
import com.github.alwaysdarkk.missions.strategy.MissionCompleteStrategy;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@RequiredArgsConstructor
public class EntityDeathListener implements Listener {

    private final MissionUserRegistry userRegistry;
    private final MissionRegistry missionRegistry;

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final Player killer = entity.getKiller();

        final MissionUser user = userRegistry.find(killer.getName());
        if (user == null) {
            return;
        }

        final Mission mission = missionRegistry.find(user.getCurrentMission());
        if (mission == null) {
            return;
        }

        if (entity.getType() == EntityType.PLAYER) {
            if (mission.getType() != MissionType.KILL_PLAYERS) {
                return;
            }

            user.incrementProgress();

            if (user.getProgress() >= mission.getObjective()) {
                new MissionCompleteStrategy(missionRegistry).execute(killer, user, mission);
            }

            user.setDirty(true);
            return;
        }

        if (mission.getType() != MissionType.KILL_MOBS) {
            return;
        }

        user.incrementProgress();

        if (user.getProgress() >= mission.getObjective()) {
            new MissionCompleteStrategy(missionRegistry).execute(killer, user, mission);
        }

        user.setDirty(true);
    }
}