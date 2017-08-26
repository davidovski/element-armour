package org.ah.minecraft.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.CommandBlock;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MachineControlPanel implements Listener {
    private Machine machine;
    private Inventory inv;
    private ItemStack offItem;
    private ItemStack onItem;

    private double percentSpeed = 90;
    private String speedDescription = "";

    public MachineControlPanel(Machine machine) {
        this.setMachine(machine);
        Random random = new Random();
        int discrim = random.nextInt(9999);
        inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN + "Machine Control Panel" + ChatColor.BLACK + "#" + discrim);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + ""));
        }

        for (int i = 36; i < 45; i++) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + ""));
        }

        for (int i = 0; i < 45; i += 9) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + ""));
        }
        for (int i = 8; i < 45; i += 9) {
            inv.setItem(i, ItemUtils.getCustomItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + ""));
        }

        inv.setItem(0, ItemUtils.getCustomItem(Material.BARRIER, ChatColor.RED + "Close Menu"));
        offItem = ItemUtils.getCustomItem(Material.WOOL, (short) 14, ChatColor.RED + "Machine is not running", ChatColor.WHITE + "Click to enable\n" + ChatColor.GRAY + "[!] When there is enough coal in the machine, turn it on.");
        onItem = ItemUtils.getCustomItem(Material.WOOL, (short) 5, ChatColor.GREEN + "Machine is running", ChatColor.WHITE + "Click to disable\n" + ChatColor.GRAY + "[!] Turn of your machine when you are not using it.");
        inv.setItem(22, onItem);
        inv.setItem(10, ItemUtils.getCustomItem(Material.TORCH, "Increase Speed", ChatColor.RED + "Left-click = " + ChatColor.DARK_AQUA + "1%\n" + ChatColor.RED + "Right-click = " + ChatColor.DARK_AQUA + "5%"));
        inv.setItem(19, ItemUtils.getCustomItem(Material.STRUCTURE_VOID, "Speed"));
        inv.setItem(28, ItemUtils.getCustomItem(Material.REDSTONE_TORCH_ON, "Decrease Speed", ChatColor.RED + "Left-click = " + ChatColor.DARK_AQUA + "1%\n" + ChatColor.RED + "Right-click = " + ChatColor.DARK_AQUA + "5%"));
        inv.setItem(23, ItemUtils.getCustomItem(Material.COAL, (short) 1, ChatColor.WHITE + "Coal"));
        inv.setItem(24, ItemUtils.getCustomItem(Material.COAL_BLOCK, ChatColor.WHITE + "Coal Upgrade"));
        inv.setItem(13, ItemUtils.getCustomItem(Material.COMMAND, (short) 0, ChatColor.WHITE + "Facing: " + ChatColor.GOLD + "north"));
        inv.setItem(21, ItemUtils.getCustomItem(Material.WATCH, ChatColor.WHITE + "Efficiency"));
        inv.setItem(31, ItemUtils.getCustomItem(Material.TNT, ChatColor.DARK_RED + "Destroy the machine",
                ChatColor.RED + "Removes the machine permanently,\n" + ChatColor.RED + "and drops the core, and the core only."));
//         inv.addItem(ItemUtils.getCustomItem(Material.BOOK, "saveMachine"));

    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void update() {
        setSpeedFromPercent();
        for (ItemStack item : inv) {
            if (item != null) {
                if (item.getType() == Material.WOOL) {

                    if (!machine.isRunning()) {
                        if (item.getDurability() == 5) {
                            item.setDurability((short) 14);
                            item.setItemMeta(offItem.getItemMeta());
                            machine.setRunning(false);
                        }
                    } else {
                        if (item.getDurability() == 14) {
                            item.setDurability((short) 5);
                            item.setItemMeta(onItem.getItemMeta());
                            machine.setRunning(true);
                        }
                    }

                }
                if (item.getType() == Material.STAINED_GLASS_PANE) {
                    if (machine.isRunning()) {
                        item.setDurability((short) 5);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.GREEN + "");
                        item.setItemMeta(meta);
                    } else {
                        item.setDurability((short) 14);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.RED + "");
                        item.setItemMeta(meta);
                    }

                }
                if (item.getType() == Material.COAL && item.getDurability() == (short) 1) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.WHITE + "" + machine.getCoal() + " coal in store.");
                    List<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.WHITE + "Click to add more (16 at a time).");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
                if (item.getType() == Material.COAL_BLOCK) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.YELLOW + "Level " + machine.getCoalSpeed() + " coal usage");
                    List<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.GOLD + "Click to upgrade.");
                    lore.add(ChatColor.RED + "Costs " + (machine.getCoalSpeed() - 2) + " coal blocks.");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
                if (item.getType() == Material.COMMAND) {
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.WHITE + "Facing: " + ChatColor.GOLD + machine.getDirection().toString().toLowerCase().replace("_", " "));
                    List<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.WHITE + "Click to change");
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                }

                if (item.getType() == Material.WATCH) {
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.WHITE + "" + machine.getEfficiency() + "% efficiency");
                    List<String> lore = new ArrayList<String>();
                    lore.add(ChatColor.GRAY + "Click to reset");
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                    item.removeEnchantment(Enchantment.DIG_SPEED);
                    int d = (int) Math.round(machine.getEfficiency() / 10);
                    if (d > 0) {
                        item.addUnsafeEnchantment((Enchantment.DIG_SPEED), d);
                    }
                }

                if (item.getType() == Material.BOOK) {
                    CommandBlock cb = (CommandBlock) machine.getBlock().getState();
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName("Command: " + cb.getCommand());
                    item.setItemMeta(meta);
                }
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {

        Inventory inventory = event.getInventory();
        if (inventory.getName().equals(inv.getName())) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            Material type = item.getType();
            if (item.getType() == Material.TORCH) {
               if (event.getAction() == InventoryAction.PICKUP_HALF) {
                   percentSpeed += 5;
               } else {
                   percentSpeed += 1;
               }
               if (percentSpeed > 100) {
                   percentSpeed = 100;
               }
               setSpeedFromPercent();
            }
            if (item.getType() == Material.REDSTONE_TORCH_ON) {
                if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    percentSpeed -= 5;
                } else {
                    percentSpeed -= 1;
                }
                if (percentSpeed < 1) {
                    percentSpeed = 1;
                }
                setSpeedFromPercent();
             }
            if (item.getType() == Material.COAL && item.getDurability() == (short) 1) {
                int ammount = ItemUtils.removeInventoryItems(event.getWhoClicked().getInventory(), Material.COAL, 16);
                machine.setCoal(machine.getCoal() + ammount);
            } else if (item.getType() == Material.COAL_BLOCK) {
                int ammount = machine.getCoalSpeed() - 2;
                if (ItemUtils.hasInventoryItems(event.getWhoClicked().getInventory(), Material.COAL_BLOCK, ammount)) {
                    ItemUtils.removeInventoryItems(event.getWhoClicked().getInventory(), Material.COAL_BLOCK, ammount);
                    machine.setCoalSpeed(machine.getCoalSpeed() + 1);
                }

            } else if (type == Material.BARRIER) {
                event.getWhoClicked().closeInventory();
            } else if (type == Material.WATCH) {
                machine.setTries(0);
                machine.setSuccesses(0);
            } else if (type == Material.BOOK) {
                machine.save();
            } else if (type == Material.TNT) {
                machine.getBlock().getWorld().playEffect(machine.getBlock().getLocation(), Effect.STEP_SOUND, machine.getBlock().getType());
                machine.getBlock().setType(Material.AIR);
                machine.getBlock().getWorld().createExplosion(machine.getBlock().getLocation(), 0f);
                event.getWhoClicked().closeInventory();
            } else if (type == Material.WOOL) {

                if (item.getDurability() == 5) {
                    item.setDurability((short) 14);
                    item.setItemMeta(offItem.getItemMeta());
                    machine.setRunning(false);
                } else if (item.getDurability() == 14) {
                    item.setDurability((short) 5);
                    item.setItemMeta(onItem.getItemMeta());
                    machine.setRunning(true);
                }
            } else if (type == Material.COMMAND) {
                machine.turnMachineByOne();

            }
        }
    }

    public void open(Player p) {
        p.openInventory(inv);
    }

    public void setSpeedFromPercent() {
        try {
        if (percentSpeed > 80) {
            machine.setSpeed(percentSpeed - 80);
            speedDescription = "Does it's action " + machine.getSpeed() + " times a second";
        } else {
            machine.setSpeed(percentSpeed / 80);
            speedDescription = "Does it's action every " + (Math.round(((20 / machine.getSpeed()) / 20) * 100.0) / 100.0) + " seconds";
        }
        for (ItemStack itemStack : inv) {
            if (itemStack != null && itemStack.getType() == Material.STRUCTURE_VOID) {
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + percentSpeed + "% " + ChatColor.DARK_AQUA  + "Machine Speed");
                List<String> lore = new ArrayList<String>();
                lore.add(ChatColor.BLUE + speedDescription);
                lore.add(ChatColor.GRAY + "[!] Adjust this so that the efficiency is as high as possible");
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }
        }
        } catch (Exception e) {

        }
    }

    public void setPercentFromSpeed() {

    }

    public double getPercentSpeed() {
        return percentSpeed;
    }

    public void setPercentSpeed(double percentSpeed) {
        this.percentSpeed = percentSpeed;
    }
}
