package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EarthUtils {
    private static final Color GREEN = Color.fromRGB(60, 255, 60);

    private EarthUtils() {
    }

    public static void checkPunch(Player p) {
    }

    public static void constantPlayerChecks(Player p) {
        if (checkForBoots(p)) {
            if ((!p.isOnGround()) && (p.isSneaking())) {
                p.setVelocity(new Vector(0, -2, 0));
                for (Entity e : p.getNearbyEntities(5.0D, 2.0D, 5.0D)) {
                    if (!e.equals(p)) {
                        e.setFallDistance(15.0F);
                    }
                }
            }
        }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 0));
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Earth Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createEarthBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Earth Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_GREEN + "Unleash their mighty power by pressing LSHIFT in mid jump!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 1);
        boots = ArmourUtil.addArmourAttributes(boots);
        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Earth Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createEarthHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Earth Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_GREEN + "right-click on rocks to pick them up, and punch them towards your enemies");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 1);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Earth Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createEarthChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Earth Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 1);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack chestplate = p.getInventory().getLeggings();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Earth Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createEarthLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Earth Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 1);

        return leggings;
    }
}
