package com.github.alwaysdarkk.missions.listener;

import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.data.Mission;
import com.github.alwaysdarkk.missions.common.data.MissionType;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@RequiredArgsConstructor
public class EntityDeathListener implements Listener {

    private final MissionUserCache userCache;
    private final MissionCache missionCache;

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        final Player killer = entity.getKiller();

        if (killer == null || killer.getType() != EntityType.PLAYER) {
            return;
        }

        final MissionUser user = userCache.find(killer.getName());
        if (user == null) {
            return;
        }

        final Mission mission = missionCache.find(user.getCurrentMission());
        if (mission == null) {
            return;
        }

        if (entity.getType() == EntityType.PLAYER) {
            if (mission.getType() != MissionType.KILL_PLAYERS) {
                return;
            }

            user.incrementProgress();
            user.setDirty(true);
            return;
        }

        if (mission.getType() != MissionType.KILL_MOBS) {
            return;
        }

        user.incrementProgress();
        user.setDirty(true);
    }
}