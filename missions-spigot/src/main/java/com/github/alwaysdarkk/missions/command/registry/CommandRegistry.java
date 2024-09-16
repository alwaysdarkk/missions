package com.github.alwaysdarkk.missions.command.registry;

import com.github.alwaysdarkk.missions.MissionsPlugin;
import com.github.alwaysdarkk.missions.command.MissionsCommand;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

@Data(staticConstructor = "of")
public class CommandRegistry {

    private final MissionsPlugin plugin;

    @SneakyThrows
    public void setup() {
        final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);

        final MissionsCommand missionsCommand = new MissionsCommand(plugin.getViewFrame());

        final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
        commandMap.register(missionsCommand.getName(), missionsCommand);
    }
}