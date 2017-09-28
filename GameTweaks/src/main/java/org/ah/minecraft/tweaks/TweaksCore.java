package org.ah.minecraft.tweaks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TweaksCore extends JavaPlugin implements Listener {
    public List<Tweak> tweaks;
    @Override
    public void onEnable() {

        PluginManager manager = getServer().getPluginManager();
        getServer().getPluginManager().registerEvents(this, this);

        tweaks = new ArrayList<Tweak>();
        tweaks.add(new TenHourSleep());
        tweaks.add(new DiggingTreasure());
        tweaks.add(new DelicateGlass());
        tweaks.add(new BonesInMobs());
        tweaks.add(new ExplosiveFurnaces());
        tweaks.add(new ChickenEggs());
        tweaks.add(new HarderHunger());
        tweaks.add(new PrimitiveTools());
        tweaks.add(new NaturalDeaths());

        for (Tweak t : tweaks) {
            t.init(getServer(), this);
            getServer().getPluginManager().registerEvents(t, this);

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