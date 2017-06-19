package org.ah.minecraft.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ChickenEggs extends Tweak {

    private ItemStack item;

    @Override
    public void init(final Server server, Plugin plugin) {

        item = new ItemStack(Material.FEATHER);

        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (LivingEntity e : w.getLivingEntities()) {
                        if (e instanceof Chicken) {
                            if (Math.random() > 0.8) {
                                w.dropItem(e.getLocation(), item);
                            }
                        }
                    }
                }
            }
        }, 1200, 1200);
    }
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Chicken && Math.random() > 0.6) {
            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), item);
        }
    }

    @EventHandler
    public void onDespawn(ItemDespawnEvent event) {
        if (event.getEntity().getItemStack().getType() == Material.EGG) {
            if (Math.random() > 0.75) {
                Chicken c = (Chicken) event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.CHICKEN);
                c.setBaby();
            }
        }
    }
}
