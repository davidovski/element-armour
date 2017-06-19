package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmourUtil {


    private static final Color GREEN = Color.fromRGB(0, 150, 20);
    private static final Color GRAY = Color.fromRGB(100, 100, 100);
    private static final Color AQUA = Color.AQUA;


    private ArmourUtil() {
    }

    public static boolean checkForBouncerBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return (ChatColor.DARK_PURPLE + "Bouncer Boots").equals(itemMeta.getDisplayName())
                    && itemMeta instanceof LeatherArmorMeta
                    && GREEN.equals(((LeatherArmorMeta)itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createBouncerBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Bouncer Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.GREEN + "Bouncer Boots");
        lores.add(ChatColor.DARK_GREEN + "Bounce around with these on!");
        meta.setLore(lores);



        meta.setColor(GREEN);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        return boots;
    }

    public static boolean checkForWariorHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if (helmet != null && helmet.hasItemMeta()) {

            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Warrior Helmet").equals(itemMeta.getDisplayName())
                    && itemMeta instanceof LeatherArmorMeta;

        }
        return false;
    }

    public static ItemStack createWariorHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

        LeatherArmorMeta meta = (LeatherArmorMeta)helmet.getItemMeta();
        meta.setDisplayName("Warrior Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.BLACK + "Worn by the mighty skeleton warriors.");
        meta.setLore(lores);


        helmet.setItemMeta(meta);

        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);

        return helmet;
    }

    public static boolean checkForRiderLeggings(Player p) {
        ItemStack boots = p.getInventory().getLeggings();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return (ChatColor.DARK_PURPLE + "Chaser Leggings").equals(itemMeta.getDisplayName())
                    && itemMeta instanceof LeatherArmorMeta
                    && Color.MAROON.equals(((LeatherArmorMeta)itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createRiderLeggings() {
        ItemStack boots = new ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Chaser Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.DARK_AQUA + "Zoom around with these bad boys and sprint to unlock their full power!");
        meta.setLore(lores);



        meta.setColor(Color.MAROON);
        boots.setItemMeta(meta);

        //boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        return boots;
    }

    public static boolean checkHoldingWarpBow(Player p) {
        ItemStack bow = p.getItemInHand();

        if (bow != null && bow.hasItemMeta()) {

            ItemMeta itemMeta = bow.getItemMeta();

            return (ChatColor.DARK_PURPLE + "Warp Bow").equals(itemMeta.getDisplayName())
                    && bow.getType() == Material.BOW;
        }
        return false;
    }

    public static ItemStack createWarpBow() {
        ItemStack bow = new ItemStack(Material.BOW);

        ItemMeta meta = bow.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Warp Bow");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.LIGHT_PURPLE + "Warp Bow");
        lores.add(ChatColor.LIGHT_PURPLE + "Shoot to teleport!");
        meta.setLore(lores);

        bow.setItemMeta(meta);


        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 20);

        return bow;
    }

    public static ItemStack createAquaBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Auqatic Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.AQUA + "Worn by the aquatic sea monsters.");

        meta.setLore(lores);
        meta.addEnchant(Enchantment.DEPTH_STRIDER, 16, true);
        meta.addEnchant(Enchantment.DURABILITY, 16, true);


        meta.setColor(AQUA);
        boots.setItemMeta(meta);



        return boots;
    }

    public static ItemStack createAquaPants() {
        ItemStack boots = new ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Auqatic Pants");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.AQUA + "Worn by the aquatic sea monsters.");

        meta.setLore(lores);

        meta.addEnchant(Enchantment.DURABILITY, 16, true);


        meta.setColor(AQUA);
        boots.setItemMeta(meta);



        return boots;
    }

    public static ItemStack createAquaShirt() {
        ItemStack boots = new ItemStack(Material.LEATHER_CHESTPLATE);

        LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Auqatic Shirt");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.AQUA + "Worn by the aquatic sea monsters.");

        meta.setLore(lores);

        meta.addEnchant(Enchantment.DURABILITY, 16, true);


        meta.setColor(AQUA);
        boots.setItemMeta(meta);



        return boots;
    }


}
