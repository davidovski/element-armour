package org.ah.minecraft.generator;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.noise.PerlinNoiseGenerator;

public class SDWG extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        for (World world : Bukkit.getWorlds()) {
            world.getPopulators().clear();
            world.getPopulators().add(new AlmostVanillaPopulator());

        }
    }
    @EventHandler
    public void onclick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {

            Block block  = event.getClickedBlock();
            PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(event.getClickedBlock().getWorld().getSeed());

//            event.getPlayer().sendMessage(":" + perlinNoiseGenerator.noise(block.getX() / 10, block.getZ() / 10));
        }
    }


    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("")) {
                return true;

            }
        }
        return false;

    }
}
