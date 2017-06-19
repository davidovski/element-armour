package org.ah.minecraft.armour;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Ninja implements Listener {

    public Ninja() {

    }

    public static ItemStack createShurikenItemStack() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Shuriken");
        item.setItemMeta(meta);
        return item;
    }

}
