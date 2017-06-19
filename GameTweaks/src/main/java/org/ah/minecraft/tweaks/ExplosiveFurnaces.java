package org.ah.minecraft.tweaks;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ExplosiveFurnaces extends Tweak {
    private ItemStack bang;

    @Override
    public void init(final Server server, Plugin plugin) {
        bang = new ItemStack(Material.STRUCTURE_VOID);
        server.addRecipe(new FurnaceRecipe(bang, Material.TNT));
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent event) {
        if (event.getResult().equals(bang)) {
            event.getBlock().breakNaturally();
            event.getBlock().getWorld().createExplosion(event.getBlock().getLocation(), 5);
        }
    }

}
