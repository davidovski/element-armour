package org.ah.minecraft.tweaks;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class AnimalBreading extends Tweak {
    @Override
    public void init(final Server server, Plugin plugin) {
        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (World w : server.getWorlds()) {
                    for (LivingEntity e : w.getLivingEntities()) {
                        if (Math.random() < 0.05) {
                            if (e instanceof Ageable) {
                                ((Ageable) e).setBreed(true);

                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }
}
