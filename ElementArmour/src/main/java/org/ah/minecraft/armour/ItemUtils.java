package org.ah.minecraft.armour;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {
	 public static void removeInventoryItems(PlayerInventory inv, Material type, int amount) {
	        for (ItemStack is : inv.getContents()) {
	            if (is != null && is.getType() == type) {
	                int newamount = is.getAmount() - amount;
	                if (newamount > 0) {
	                    is.setAmount(newamount);
	                    break;
	                } else {
	                    inv.remove(is);
	                    amount = -newamount;
	                    if (amount == 0) break;
	                }
	            }
	        }
	    }
	 public static void removeItems(ItemStack i, int amount) {
	     int newamount = i.getAmount() - amount;
             if (newamount > 0) {
                 i.setAmount(newamount);
                 return;
             } else {
                 i.setAmount(0);
                 i.setType(Material.AIR);
                 amount = -newamount;
                 if (amount == 0) {
                     return;
                 }
             }
         }

    public static ItemStack getSkull(String skullOwner) {
        String display = ChatColor.GREEN + skullOwner;
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        skullMeta.setOwner(skullOwner);
        skullMeta.setDisplayName("" + display + "");
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public static ItemStack getCustomItem(Material m, String name) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomItem(Material m, Enchantment e) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(e, 1, true);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomItem(Material m) {
        ItemStack item = new ItemStack(m);
        return item;
    }

    public static ItemStack getCustomItem(ItemStack item, String name) {

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomItem(Material m, short v, String name) {
        ItemStack item = new ItemStack(m, 1, v);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomItem(Material m, short v, int amount, String name) {
        ItemStack item = new ItemStack(m, amount, v);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomItem(Material m, int amount, String name) {
        ItemStack item = new ItemStack(m, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }


    public static ItemStack getCustomItem(Material m, String name, List<String> lore) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack getCustomItem(Material watch, String name, String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] split = string.split("\n");
        for (String s : split) {
            arrayList.add(s);
        }
        return getCustomItem(watch, name, arrayList);

    }
}

