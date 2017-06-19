package org.ah.minecraft.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NaturalDeaths extends Tweak {

    private ItemStack item;

    @Override
    public void init(final Server server, Plugin plugin) {

        item = new ItemStack(Material.FEATHER);

        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (LivingEntity e : w.getLivingEntities()) {
//                        if (e.getTicksLived() > (24*60 * 60 * 20)) {
//                            e.damage(100D);
//                        }
                    }
                }
            }
        }, 20, 20);
    }

}
