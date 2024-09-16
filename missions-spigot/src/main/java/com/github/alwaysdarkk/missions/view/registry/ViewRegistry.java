package com.github.alwaysdarkk.missions.view.registry;

import com.github.alwaysdarkk.missions.MissionsPlugin;
import com.github.alwaysdarkk.missions.common.cache.MissionCache;
import com.github.alwaysdarkk.missions.common.cache.MissionUserCache;
import com.github.alwaysdarkk.missions.common.configuration.InventoryValue;
import com.github.alwaysdarkk.missions.common.item.adapter.ItemStackAdapter;
import com.github.alwaysdarkk.missions.view.MissionsView;
import lombok.Data;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.inventory.ItemStack;

@Data(staticConstructor = "of")
public class ViewRegistry {

    private final MissionsPlugin plugin;

    public void setup() {
        final MissionCache missionCache = plugin.getMissionCache();
        final MissionUserCache userCache = plugin.getUserCache();

        final ViewFrame viewFrame = plugin.getViewFrame();
        viewFrame.setNextPageItem(
                (context, viewItem) -> viewItem.rendered(() -> context.hasNextPage() ? getNextPageItem() : null));

        viewFrame.setPreviousPageItem((context, viewItem) ->
                viewItem.rendered(() -> context.hasPreviousPage() ? getPreviousPageItem() : null));

        viewFrame.with(new MissionsView(missionCache, userCache));
        viewFrame.register();
    }

    private ItemStack getNextPageItem() {
        return ItemStackAdapter.adaptItemStack(InventoryValue.get(InventoryValue::nextPageSection), "material");
    }

    private ItemStack getPreviousPageItem() {
        return ItemStackAdapter.adaptItemStack(InventoryValue.get(InventoryValue::previousPageSection), "material");
    }
}