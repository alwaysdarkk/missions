package com.github.alwaysdarkk.missions.common.item.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

@UtilityClass
public class SkullUtil {

    private final String DEFAULT_SKULL_URL = "http://textures.minecraft.net/texture/";
    private final ItemStack DEFAULT_SKULL_ITEM = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

    public ItemStack getSkullByName(String playerName) {
        return Bukkit.getUnsafe()
                .modifyItemStack(DEFAULT_SKULL_ITEM.clone(), "{SkullOwner:\"" + playerName + "\"}")
                .clone();
    }

    public ItemStack getSkullByBase64(String base64) {
        final UUID uuid = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe()
                .modifyItemStack(
                        DEFAULT_SKULL_ITEM.clone(),
                        "{SkullOwner:{Id:\"" + uuid + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}")
                .clone();
    }

    public static ItemStack getSkullByUrl(String url) {
        if (!url.startsWith(DEFAULT_SKULL_URL)) {
            url = DEFAULT_SKULL_URL + url;
        }

        final String base64 = urlToBase64(url);
        return getSkullByBase64(base64);
    }

    private String urlToBase64(String url) {
        try {
            final URI uri = new URI(url);
            final String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + uri + "\"}}}";

            return Base64.getEncoder().encodeToString(toEncode.getBytes());
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}