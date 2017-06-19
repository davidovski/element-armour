package org.ah.minecraft.tweaks;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HarderHunger extends Tweak {
    @Override
    public void init(final Server server, Plugin plugin) {
        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Math.random() > 0.5 && p.getSaturation() > 1) {
                        p.setSaturation(p.getSaturation() - 1);
                    }
                }
            }
        }, 1200, 1200);
//
//        server.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
//            @Override
//            public void run() {
//                for (Player p : Bukkit.getOnlinePlayers()) {
//                    p.sendMessage(ChatColor.RED +  "Hunger: " + ChatColor.YELLOW + p.getFoodLevel() + ChatColor.RED +  "SATURATION: " + ChatColor.YELLOW + p.getSaturation());
//                }
//            }
//        }, 20, 20);
    }
}
