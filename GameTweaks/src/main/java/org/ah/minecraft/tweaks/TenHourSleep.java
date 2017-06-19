package org.ah.minecraft.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TenHourSleep extends Tweak {
    @Override
    public void init(final Server server, Plugin plugin) {
        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setSleepingIgnored(true);
                    if (p.isSleeping()) {
                        if (p.getSleepTicks() > 20) {
                            p.getWorld().setFullTime(p.getWorld().getFullTime() + 8000);
                        }
                    }
                }
            }
        }, 4, 4);
    }
}
