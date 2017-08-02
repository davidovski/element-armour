package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class WaterUtils {
    private static final Color BLUE = Color.fromRGB(60, 60, 255);

    private WaterUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GENERIC_SPLASH, 1.0F, 1.0F);
            Block tb = p.getTargetBlock(new HashSet<Material>(), 10);
            Location loc = new Location(tb.getWorld(), tb.getX(), tb.getY() + 1, tb.getZ());
            Location loc2 = new Location(tb.getWorld(), tb.getX(), tb.getY() + 2, tb.getZ());
            Block bl = loc.getBlock();
            Block bl2 = loc2.getBlock();

            if ((bl.getType() == Material.AIR) && (bl2.getType() == Material.AIR)) {
                bl.setType(Material.WATER);
                bl.setData((byte) 15);
                bl2.setType(Material.WATER);
                bl2.setData((byte) 15);

                for (Entity e : org.ah.minecraft.armour.Plugin.getNearbyEntities(loc, 5)) {
                    if ((!e.equals(p)) && ((e instanceof LivingEntity))) {
                        ((LivingEntity) e).damage(13.0D);

                        ((LivingEntity) e).setLastDamageCause(new EntityDamageEvent(e, EntityDamageEvent.DamageCause.DROWNING, 10.0D));
                    }
                }
            }
        }
    }

    public static void constantPlayerChecks(Player p) {
        if (checkForBoots(p)) {
            Location loc = p.getLocation();

            if ((p.isSneaking()) && (Material.STATIONARY_WATER == p.getWorld().getBlockAt(loc).getType())) {

                Vector vec = p.getLocation().getDirection();
                vec.multiply(0.4F);
                vec.setY(0.01F);

                p.setVelocity(vec);
            }
        }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 5));
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Water Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createWaterBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Water Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_BLUE + "Walk ON water by sneaking!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Teir I");
        meta.setLore(lores);

        meta.setColor(BLUE);
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

            return ("Water Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createWaterHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Water Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_BLUE + "Punch to splash some water");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Teir I");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 1);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Water Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createWaterChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Water Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Teir I");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 1);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Water Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createWaterLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Water Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Teir I");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 1);

        return leggings;
    }
}
