package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class FireUtils {
    private static final Color RED = Color.fromRGB(255, 60, 60);

    private FireUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            p.getWorld().playSound(p.getLocation(), org.bukkit.Sound.ENTITY_GHAST_SHOOT, 1.0F, 1.0F);
            Location loc = p.getEyeLocation();
            Entity fireball = loc.getWorld().spawnEntity(loc.add(loc.getDirection().multiply(2)), org.bukkit.entity.EntityType.SMALL_FIREBALL);
            Vector v = loc.getDirection().multiply(1.5D);
            fireball.setVelocity(v);
        }
    }

    public static void constantPlayerChecks(Player p) {
        if (checkForBoots(p)) {
            p.setFireTicks(0);

            if ((p.isSneaking()) && (!p.isOnGround())) {
                List<Block> locs = new ArrayList();
                Location pl = p.getLocation();
                locs.add(pl.add(pl.getDirection().setY(0).multiply(1)).getBlock());
                locs.add(pl.add(pl.getDirection().setY(0).multiply(2)).getBlock());
                locs.add(pl.add(pl.getDirection().setY(0).multiply(3)).getBlock());

                for (Block loc : locs) {
                    Byte data = Byte.valueOf((byte) 0);
                    p.getWorld().spawnFallingBlock(loc.getLocation(), Material.FIRE, data.byteValue());
                }
            }
        }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 3), true);
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Fire Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createFireBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Fire Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_RED + "Unleash a kick of fire by sneaking mid jump");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(RED);
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

            return ("Fire Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createFireHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Fire Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_RED + "launch a fire ball by puching!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 1);
        helmet = ArmourUtil.addArmourAttributes(helmet);
        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Fire Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createFireChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Fire Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 1);
        chestplate = ArmourUtil.addArmourAttributes(chestplate);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Fire Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(1).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createFireLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Fire Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 1);
        leggings = ArmourUtil.addArmourAttributes(leggings);
        return leggings;
    }
}
