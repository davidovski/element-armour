package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LightningUtils {

    private static final Color WHITE = Color.fromRGB(250, 250, 250);

    private LightningUtils() {
    }

    public static void checkPunch(Player p) {
        if (LightningUtils.checkForHelmet(p)) {
            Block tb = p.getTargetBlock((HashSet<Byte>) null, 10).getRelative(BlockFace.UP);
            tb.getWorld().strikeLightning(tb.getLocation());
        }

    }

    public static void constantPlayerChecks(Player p) {
        p.setFireTicks(-1);
        if (checkForBoots(p) && p.isSneaking() && !p.isOnGround()) {
            p.setFallDistance(0f);
            if (p.getVelocity().getY() < 0) {
                p.getWorld().strikeLightning(p.getLocation().add(4, 0, 0));
                p.getWorld().strikeLightning(p.getLocation().add(2, 0, 2));
                p.getWorld().strikeLightning(p.getLocation().add(0, 0, 4));
                p.getWorld().strikeLightning(p.getLocation().add(2, 0, -2));
                p.getWorld().strikeLightning(p.getLocation().add(4, 0, -4));
                p.getWorld().strikeLightning(p.getLocation().add(-4, 0, -4));
                p.getWorld().strikeLightning(p.getLocation().add(-2, 0, -2));
                p.getWorld().strikeLightning(p.getLocation().add(-2, 0, 2));
                p.setVelocity(new Vector(0, -3, 0));
            }
        }

        if (LightningUtils.checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2));
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 2));

        }
        if (LightningUtils.checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 3));
        }

    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return "Storm Boots".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(boots.getEnchantments().get(Enchantment.DURABILITY)) && WHITE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Storm Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.RED + "");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 3);

        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if (helmet != null && helmet.hasItemMeta()) {

            ItemMeta itemMeta = helmet.getItemMeta();

            return "Storm Helmet".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(helmet.getEnchantments().get(Enchantment.DURABILITY)) && WHITE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Storm Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.RED + "Lightning");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if (chestplate != null && chestplate.hasItemMeta()) {

            ItemMeta itemMeta = chestplate.getItemMeta();

            return "Storm Chestplate".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY)) && WHITE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Storm Chestplate");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 3);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if (leggings != null && leggings.hasItemMeta()) {

            ItemMeta itemMeta = leggings.getItemMeta();

            return "Storm Leggings".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(leggings.getEnchantments().get(Enchantment.DURABILITY)) && WHITE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Storm Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 3);

        return leggings;
    }

}
