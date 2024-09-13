package com.github.alwaysdarkk.missions.common.item.adapter;

import com.github.alwaysdarkk.missions.common.item.ItemBuilder;
import com.github.alwaysdarkk.missions.common.item.util.SkullUtil;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class ItemStackAdapter {

    public ItemStack adaptItemStack(ConfigurationSection section, String path) {
        ItemBuilder itemBuilder;

        final String item = section.getString(path);
        if (!item.contains(":")) {
            itemBuilder = new ItemBuilder(SkullUtil.getSkullByUrl(item));
        } else {
            final String[] splitItem = item.split(":");
            if (splitItem.length != 2) {
                return null;
            }

            final String rawMaterial = splitItem[0];
            final Material material = Material.matchMaterial(rawMaterial);

            itemBuilder = new ItemBuilder(new ItemStack(material, 1, Byte.parseByte(splitItem[1])));
        }

        if (section.contains("display-name")) {
            itemBuilder.displayName(section.getString("display-name"));
        }

        if (section.contains("lore")) {
            itemBuilder.lore(section.getStringList("lore"));
        }

        if (section.contains("amount")) {
            itemBuilder.amount(section.getInt("amount"));
        }

        if (section.contains("enchantments")) {
            final ItemBuilder finalBuilder = itemBuilder;
            section.getStringList("enchantments").forEach(enchant -> {
                final String[] split = enchant.split(":");
                finalBuilder.enchantment(Enchantment.getByName(split[0]), Integer.parseInt(split[1]));
            });
        }

        return itemBuilder.build();
    }

    public ItemStack adaptItemStack(String path) {
        if (!path.contains(":")) {
            return SkullUtil.getSkullByUrl(path);
        }

        final String[] splitMaterial = path.split(":");
        if (splitMaterial.length != 2) {
            return null;
        }

        final String rawMaterial = splitMaterial[0];
        final Material material = Material.matchMaterial(rawMaterial);

        return new ItemStack(material, 1, Byte.parseByte(splitMaterial[1]));
    }
}
