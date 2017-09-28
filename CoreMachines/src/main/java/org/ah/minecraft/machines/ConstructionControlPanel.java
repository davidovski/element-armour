package org.ah.minecraft.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ah.minecraft.machines.selection.MachineItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConstructionControlPanel implements Listener {
    private Inventory inv;
    private CorePlugin plugin;
    private Block block;

    private List<MachineItem> icons;

    public ConstructionControlPanel(Block block, CorePlugin plugin) {
        this.block = block;
        this.plugin = plugin;

        icons = new ArrayList<MachineItem>();
        Random random = new Random();
        int discrim = random.nextInt(9999);
        inv = Bukkit.createInventory(null, 45, ChatColor.BLACK + "Select Machine" + ChatColor.BLACK + "#" + discrim);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 15, ChatColor.GREEN + ""));
        }

        for (int i = 36; i < 45; i++) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 15, ChatColor.GREEN + ""));
        }

        for (int i = 0; i < 45; i += 9) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 15, ChatColor.GREEN + ""));
        }
        for (int i = 8; i < 45; i += 9) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 15, ChatColor.GREEN + ""));
        }

        inv.setItem(0, ItemUtils.getCustomItem(Material.BARRIER, ChatColor.RED + "Delete Contructor"));
        icons.add(new MachineItem(MachineType.BREAK));
        icons.add(new MachineItem(MachineType.PLACE));
        icons.add(new MachineItem(MachineType.FARM));

        for (MachineItem machineItem : icons) {
            inv.addItem(machineItem);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        HumanEntity p = event.getWhoClicked();
        Inventory inventory = event.getInventory();
        if (inventory.getName().equals(inv.getName())) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            Material type = item.getType();
            if (type == Material.BARRIER) {
                p.closeInventory();
                block.breakNaturally();
                block.getWorld().dropItemNaturally(block.getLocation(), ItemUtils.getCustomItem(Material.COMMAND_REPEATING, ChatColor.LIGHT_PURPLE + "Core Constructor", ChatColor.DARK_PURPLE + "Place it down to begin constructing your core machine."));
            }
            for (MachineItem i : icons) {
                if (i.isSimilar(item)) {
                    boolean got = true;
                    for (ItemStack itemStack : i.getIngredients()) {
                        if (!ItemUtils.hasInventoryItems(p.getInventory(), itemStack.getType(), itemStack.getAmount())) {
                            got = false;
                        }
                    }
                    if (got) {
                        for (ItemStack itemStack : i.getIngredients()) {
                            ItemUtils.removeInventoryItems(p.getInventory(), itemStack.getType(), itemStack.getAmount());
                        }
                        plugin.createMachine(block, i.getMachineType());
                    } else {
                        p.sendMessage("You do not have enough resources for that!");

                    }
                    p.closeInventory();
                }
            }
        }
    }

    public void open(Player p) {
        p.openInventory(inv);
    }

}
