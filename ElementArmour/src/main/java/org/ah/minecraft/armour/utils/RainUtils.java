package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.ah.minecraft.armour.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RainUtils {

    private static final Color BLUE =  Color.fromRGB(60, 60, 255);

    private RainUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 1f, 1f);
            Block tb = p.getTargetBlock((HashSet<Byte>) null, 10);
            Block b1 = new Location(tb.getWorld(), tb.getX(), tb.getY() + 1, tb.getZ()).getBlock();
            Block b2 = new Location(tb.getWorld(), tb.getX(), tb.getY() + 2, tb.getZ()).getBlock();
            Block b3 = new Location(tb.getWorld(), tb.getX(), tb.getY() + 3, tb.getZ()).getBlock();
            Block b4 = new Location(tb.getWorld(), tb.getX(), tb.getY() + 2, tb.getZ() + 1).getBlock();
            Block b5 = new Location(tb.getWorld(), tb.getX(), tb.getY() + 2, tb.getZ() - 1).getBlock();
            Block b6 = new Location(tb.getWorld(), tb.getX() + 1, tb.getY() + 2, tb.getZ()).getBlock();
            Block b7 = new Location(tb.getWorld(), tb.getX() - 1, tb.getY() + 2, tb.getZ()).getBlock();

            FrostedBlock.setBlock(b1, Material.WATER, 3);
            FrostedBlock.setBlock(b2, Material.PACKED_ICE, 3);
            FrostedBlock.setBlock(b3, Material.WATER, 3);
            FrostedBlock.setBlock(b4, Material.WATER, 3);
            FrostedBlock.setBlock(b5, Material.WATER, 3);
            FrostedBlock.setBlock(b6, Material.WATER, 3);
            FrostedBlock.setBlock(b7, Material.WATER, 3);

            for (Entity e : Plugin.getNearbyEntities(b2.getLocation(), 3)) {
                if (!e.equals(p)) {
                    if (e instanceof LivingEntity) {

                        ((LivingEntity) e).damage(8, p);

                    }
                }
            }
        }

    }

    public static void constantPlayerChecks(Player p) {

        if (checkForBoots(p)) {
            p.setFallDistance(p.getFallDistance() / 2);
            Location loc = p.getLocation();

            if (p.isSneaking()) {
                if (p.getWorld().hasStorm()) {
                    p.setGravity(false);
                } else {
                    p.setGravity(true);
                }
            } else {
                p.setGravity(true);
            }
        }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 4));
        }
        if (checkForLeggings(p)) {
            p.setFlySpeed(0.1f);
            if (!p.hasGravity()) {

            } else {

            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return "Rain Boots".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(boots.getEnchantments().get(Enchantment.DURABILITY)) && BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Rain Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.DARK_BLUE + "Sneak in midair when it is raining to not fall down...");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Teir III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 3);
        boots = ArmourUtil.addArmourAttributes(boots);
        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if (helmet != null && helmet.hasItemMeta()) {

            ItemMeta itemMeta = helmet.getItemMeta();

            return "Rain Helmet".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(helmet.getEnchantments().get(Enchantment.DURABILITY)) && BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Rain Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.DARK_BLUE + "Throw Hail at your enemies by punching.");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Teir III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);
        helmet = ArmourUtil.addArmourAttributes(helmet);
        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if (chestplate != null && chestplate.hasItemMeta()) {

            ItemMeta itemMeta = chestplate.getItemMeta();

            return "Rain Chestplate".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY)) && BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Rain Chestplate");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Teir III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        chestplate = ArmourUtil.addArmourAttributes(chestplate);
        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if (leggings != null && leggings.hasItemMeta()) {

            ItemMeta itemMeta = leggings.getItemMeta();

            return "Rain Leggings".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(3).equals(leggings.getEnchantments().get(Enchantment.DURABILITY)) && BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Rain Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.YELLOW + "AIR");
        lores.add(ChatColor.GRAY + "Teir III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        leggings = ArmourUtil.addArmourAttributes(leggings);
        return leggings;
    }

}