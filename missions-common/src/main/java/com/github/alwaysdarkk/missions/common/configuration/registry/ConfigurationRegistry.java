package com.github.alwaysdarkk.missions.common.configuration.registry;

import com.github.alwaysdarkk.missions.common.configuration.ConfigValue;
import com.github.alwaysdarkk.missions.common.configuration.InventoryValue;
import com.github.alwaysdarkk.missions.common.configuration.MessagesValue;
import com.henryfabio.minecraft.configinjector.bukkit.injector.BukkitConfigurationInjector;
import lombok.Data;
import org.bukkit.plugin.Plugin;

@Data(staticConstructor = "of")
public class ConfigurationRegistry {

    private final Plugin plugin;

    public void setup() {
        final BukkitConfigurationInjector configurationInjector = new BukkitConfigurationInjector(plugin);
        configurationInjector.saveDefaultConfiguration(plugin, "config.yml", "messages.yml", "inventory.yml");
        configurationInjector.injectConfiguration(
                ConfigValue.instance(), MessagesValue.instance(), InventoryValue.instance());
    }
}