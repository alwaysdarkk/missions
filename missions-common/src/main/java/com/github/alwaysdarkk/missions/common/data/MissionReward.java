package com.github.alwaysdarkk.missions.common.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
@RequiredArgsConstructor
public class MissionReward {

    private final String id, command;
    private final ItemStack itemStack;

    public void give(Player player) {
        if (!command.isEmpty()) {
            final String formattedCommand = command.replace("{player}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
            return;
        }

        for (ItemStack restItemStack : player.getInventory().addItem(itemStack).values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), restItemStack);
        }
    }
}
