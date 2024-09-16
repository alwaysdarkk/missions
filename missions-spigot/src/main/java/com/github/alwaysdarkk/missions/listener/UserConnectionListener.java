package com.github.alwaysdarkk.missions.listener;

import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.data.MissionUser;
import com.github.alwaysdarkk.missions.common.repository.MissionUserRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class UserConnectionListener implements Listener {

    private final MissionUserRepository userRepository;
    private final MissionUserCache userCache;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final MissionUser user = Optional.ofNullable(userRepository.find(player.getName()))
                .orElse(MissionUser.builder().playerName(player.getName()).build());

        userCache.insert(user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final MissionUser user = userCache.find(player.getName());

        if (user == null) {
            return;
        }

        if (!user.isDirty()) {
            return;
        }

        userCache.remove(user);
        userRepository.insert(user);
    }
}