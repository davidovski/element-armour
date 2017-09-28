package org.ah.minecraft.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftAnimals;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class NaturalDeaths extends Tweak {

    @Override
    public void init(final Server server, Plugin plugin) {

        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (LivingEntity e : w.getLivingEntities()) {
                        if (e instanceof Ageable && e.getCustomName() == null) {

                            long lifetime = (12 * 60 * 60 * 20);
                            if (e.getType() == EntityType.CHICKEN) {
                                lifetime = (20*60*20*16);
                            } else if (e.getType() == EntityType.COW) {
                                lifetime = (20*60*20*40);
                            } else if (e.getType() == EntityType.SHEEP) {
                                lifetime = (20*60*20*24);
                            } else if (e.getType() == EntityType.PIG) {
                                lifetime = (20*60*20*32);
                            } else if (e.getType() == EntityType.HORSE) {
                                lifetime = (20*60*20*60);
                            } else if (e.getType() == EntityType.WOLF) {
                                lifetime = (20*60*20*28);
                            } else if (e.getType() == EntityType.RABBIT) {
                                lifetime = (20*60*20*10);
                            } else {
                                lifetime = (20*60*20*20);
                            }
                            long check = (long) (lifetime / e.getMaxHealth());
                            if (e.getTicksLived() % check == check - 2) {
                                e.damage(1D);
                            }

                            int breedtime = 7 * 20 * 60 * 20;
                            if (e.getType() == EntityType.RABBIT) {
                                breedtime = 4 * 20 * 60 * 20;
                            }
                            if (e.getTicksLived() % breedtime == breedtime - 10) {
                                if (e instanceof Ageable) {
                                    NBTTagCompound tag = new NBTTagCompound();
                                    ((CraftAnimals) e).getHandle().b(tag);
                                    tag.setInt("InLove", 100);
                                    ((CraftAnimals) e).getHandle().a(tag);
                                }
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }

}
