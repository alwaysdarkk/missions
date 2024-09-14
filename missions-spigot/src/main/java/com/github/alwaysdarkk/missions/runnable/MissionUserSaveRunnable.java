package com.github.alwaysdarkk.missions.runnable;

import com.github.alwaysdarkk.missions.MissionsPlugin;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.registry.MissionUserRegistry;
import com.github.alwaysdarkk.missions.common.repository.MissionUserRepository;
import org.bukkit.Bukkit;

import java.util.List;

public class MissionUserSaveRunnable implements Runnable {

    private final MissionUserRegistry userRegistry;
    private final MissionUserRepository userRepository;

    public MissionUserSaveRunnable(MissionUserRegistry userRegistry, MissionUserRepository userRepository) {
        this.userRegistry = userRegistry;
        this.userRepository = userRepository;

        Bukkit.getScheduler().runTaskTimerAsynchronously(MissionsPlugin.getInstance(), this, 40L, 40L);
    }

    @Override
    public void run() {
        final List<MissionUser> dirtyUsers = userRegistry.getDirtyUsers();
        if (dirtyUsers.isEmpty()) {
            return;
        }

        userRepository.bulkInsert(dirtyUsers);
    }
}
