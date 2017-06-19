package org.ah.minecraft.tweaks;


import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DiggingTreasure extends Tweak {
    public ItemStack emerald;
    private ItemStack seeds;
    private ItemStack flint;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        emerald = new ItemStack(Material.EMERALD);
        seeds = new ItemStack(Material.SEEDS);
        flint = new ItemStack(Material.FLINT);


        Player p = event.getPlayer();
        if (event.getBlock().getType() == Material.LONG_GRASS && Math.random() < 0.1) {
            for (Entity e : p.getNearbyEntities(10, 10, 10)) {
                if (e.getType() == EntityType.VILLAGER) {

                    event.setDropItems(false);
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), emerald);
                    return;
                }
            }
        }
        if (event.getBlock().getType() == Material.GRASS && Math.random() < 0.2) {
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), seeds);
        }

        if (event.getBlock().getType() == Material.DIRT && Math.random() < 0.08) {
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), flint);
        }

    }
}
