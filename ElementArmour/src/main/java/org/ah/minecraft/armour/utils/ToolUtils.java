package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolUtils {

    public static ItemStack createPickaxe() {
        ItemStack pickaxe = new ItemStack(Material.GOLD_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.setDisplayName("Elemental Pickaxe");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.GRAY + "Breaks all of the surrounding blocks of that ore");
        meta.setLore(lores);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pickaxe.setItemMeta(meta);

        pickaxe.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        pickaxe.addEnchantment(Enchantment.DIG_SPEED, 3);
        pickaxe.addEnchantment(Enchantment.SILK_TOUCH, 1);
        return pickaxe;
    }

    public static ItemStack createAxe() {
        ItemStack axe = new ItemStack(Material.GOLD_AXE);
        ItemMeta meta = axe.getItemMeta();
        meta.setDisplayName("Elemental Axe");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.GRAY + "Breaks down a whole tree in one blow.");
        meta.setLore(lores);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        axe.setItemMeta(meta);

        axe.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
        axe.addEnchantment(Enchantment.DIG_SPEED, 3);
        return axe;
    }
}
