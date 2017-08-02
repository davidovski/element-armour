package org.ah.minecraft.armour.mobs;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DropGenerator {
    public DropGenerator() {
    }

    public static ItemStack i(Material m, int max) {
        return new ItemStack(m, new java.util.Random().nextInt(max));
    }

    public static ItemStack i(Material m) {
        return new ItemStack(m);
    }

    public static ItemStack i(Material m, String name) {
        ItemStack i = new ItemStack(m);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack i(Material m, int max, String name) {
        ItemStack i = new ItemStack(m, new java.util.Random().nextInt(max));
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(net.md_5.bungee.api.ChatColor.RESET + name);
        i.setItemMeta(im);
        return i;
    }
}
