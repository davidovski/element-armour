package org.ah.minecraft.machines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.md_5.bungee.api.ChatColor;

public class CorePlugin extends JavaPlugin implements Listener {

    private List<Machine> machines;
    private long alpha = 0;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        machines = new ArrayList<Machine>();

        load();

        NamespacedKey flameBow = new NamespacedKey(this,"CoreConstructor");

        ShapedRecipe rec3 = new ShapedRecipe(flameBow, ItemUtils.getCustomItem(Material.COMMAND_REPEATING, ChatColor.LIGHT_PURPLE + "Core Constructor", ChatColor.DARK_PURPLE + "Place it down to begin constructing your core machine."));
        rec3.shape("-#-",
                   "#o#",
                   "-#-");
        rec3.setIngredient('-', Material.REDSTONE);
        rec3.setIngredient('#', Material.WOOD);
        rec3.setIngredient('o', Material.DIAMOND);
        getServer().addRecipe(rec3);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                alpha++;
                if (alpha % (20*60*5) == 0) {
                    save();
                }
                Iterator<Machine> iterator = machines.iterator();
                while (iterator.hasNext()) {
                    Machine machine = iterator.next();
                    boolean living = machine.loop();
                    if (!living) {
                        iterator.remove();
                    }
                }
            }
        }, 1L, 1L);
    }

    @Override
    public void onDisable() {
        save();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("CoreMachines")) {
                return true;

            } else if (cmd.getName().equalsIgnoreCase("machine")) {
                return true;
            } else if (cmd.getName().equalsIgnoreCase("commandblock")) {
               return false;
            }
        }
        return false;

    }

    public Machine createMachine(Block b, MachineType type) {
        if (type == MachineType.BREAK) {
            BreakMachine e = new BreakMachine(b);
            getServer().getPluginManager().registerEvents(e, this);
            getServer().getPluginManager().registerEvents(e.getControlPanel(), this);
            machines.add(e);
            return e;
        }
        return null;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            BlockFace face = event.getBlockFace();
            Block b = block.getRelative(face);
            if (block.getType() == Material.COMMAND_REPEATING && !p.isSneaking()) {
                ConstructionControlPanel constructionControlPanel = new ConstructionControlPanel(block, this);
                getServer().getPluginManager().registerEvents(constructionControlPanel, this);
                constructionControlPanel.open(p);
                event.setCancelled(true);
            }
            if (p.getItemInHand().getType() == Material.COMMAND_REPEATING) {
                ItemUtils.removeHandItems(p, 1);
                b.setType(Material.COMMAND_REPEATING);
                event.setCancelled(true);
            }
        }
    }

    public void loadMachine(Block b) {
        if (b.getType() == Material.COMMAND) {
            CommandBlock state = (CommandBlock) b.getState();
            String cmd = state.getCommand();

            JSONParser parser = new JSONParser();
            System.out.println(cmd + "... loading");
            try {
                JSONObject obj = (JSONObject) parser.parse(cmd);
                int typeID = Integer.parseInt(obj.get("type").toString());
                Machine machine = createMachine(b, MachineType.getById(typeID));
                boolean running = Boolean.parseBoolean(obj.get("running").toString());
                int coal = Integer.parseInt(obj.get("coal").toString());
                int coalSpeed = Integer.parseInt(obj.get("coalSpeed").toString());
                double speed = Double.parseDouble(obj.get("speed").toString());
                double successes = Double.parseDouble(obj.get("successes").toString());
                double tries = Double.parseDouble(obj.get("tries").toString());
                machine.setTries(tries);
                machine.setSuccesses(successes);
                machine.setRunning(running);
                machine.setCoal(coal);
                machine.setCoalSpeed(coalSpeed);
                machine.getControlPanel().setPercentSpeed(speed);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void save() {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("machine.locations");
        if (configurationSection == null) {
            configurationSection = getConfig().createSection("machine.locations");
        }
        for (int i = 0; i < machines.size(); i++) {
            machines.get(i).save();
            configurationSection.set(i + "", LocationUtil.locationToStringRounded(machines.get(i).getBlock().getLocation()));
        }
        saveConfig();
    }

    public void load() {
        ConfigurationSection configurationSection = getConfig().getConfigurationSection("machine.locations");
        if (configurationSection == null) {
            configurationSection = getConfig().createSection("machine.locations");
        }

        for (Map.Entry<String, Object> entry : configurationSection.getValues(false).entrySet()) {
            loadMachine(LocationUtil.stringToLocation(getServer(), entry.getValue().toString()).getBlock());
        }
    }
}
