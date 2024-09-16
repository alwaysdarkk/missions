package com.github.alwaysdarkk.missions;

import com.github.alwaysdarkk.missions.command.registry.CommandRegistry;
import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.configuration.registry.ConfigurationRegistry;
import com.github.alwaysdarkk.missions.common.repository.MissionUserRepository;
import com.github.alwaysdarkk.missions.listener.registry.ListenerRegistry;
import com.github.alwaysdarkk.missions.runnable.MissionUserSaveRunnable;
import com.github.alwaysdarkk.missions.view.registry.ViewRegistry;
import lombok.Getter;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class MissionsPlugin extends JavaPlugin {

    private MissionUserRepository userRepository;
    private MissionUserCache userCache;
    private MissionCache missionCache;

    private ViewFrame viewFrame;

    public static MissionsPlugin getInstance() {
        return getPlugin(MissionsPlugin.class);
    }

    @Override
    public void onLoad() {
        ConfigurationRegistry.of(this).setup();
    }

    @Override
    public void onEnable() {
        userRepository = new MissionUserRepository();
        userCache = new MissionUserCache();

        missionCache = new MissionCache();

        viewFrame = ViewFrame.of(this);

        ViewRegistry.of(this).setup();
        ListenerRegistry.of(this).setup();
        CommandRegistry.of(this).setup();

        new MissionUserSaveRunnable(userCache, userRepository);
    }
}