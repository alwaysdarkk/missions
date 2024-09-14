package com.github.alwaysdarkk.missions.common.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Getter
@TranslateColors
@Accessors(fluent = true)
@ConfigFile("inventory.yml")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryValue implements ConfigurationInjectable {

    @Getter
    private static final InventoryValue instance = new InventoryValue();

    @ConfigField("settings.name")
    private String name;

    @ConfigField("settings.size")
    private int size;

    @ConfigField("settings.layout")
    private List<String> layout;

    @ConfigField("icons.not-unlock-icon.displayName")
    private String notUnlockName;

    @ConfigField("icons.not-unlock-icon.lore")
    private List<String> notUnlockLore;

    @ConfigField("icons.not-complete-icon.displayName")
    private String notCompleteName;

    @ConfigField("icons.not-complete-icon.lore")
    private List<String> notCompleteLore;

    @ConfigField("icons.already-complete-icon.displayName")
    private String alreadyCompleteName;

    @ConfigField("icons.already-complete-icon.lore")
    private List<String> alreadyCompleteLore;

    @ConfigField("icons.complete-icon.displayName")
    private String completeName;

    @ConfigField("icons.complete-icon.lore")
    private List<String> completeLore;

    public static <T> T get(Function<InventoryValue, T> function) {
        return function.apply(instance);
    }
}