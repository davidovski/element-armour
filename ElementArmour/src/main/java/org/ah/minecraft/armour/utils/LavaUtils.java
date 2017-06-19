package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LavaUtils {

    private static final Color RED = Color.fromRGB(255, 180, 10);

    private LavaUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1f, 1f);
            Location loc = p.getEyeLocation();
            Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc.add(loc.getDirection().multiply(2)), EntityType.FIREBALL);
            fireball.setShooter(p);
            fireball.setYield(1f);
            fireball.setIsIncendiary(true);
            Vector v = loc.getDirection().multiply(1.5);
            fireball.setVelocity(v);
        }
        }

    public static void constantPlayerChecks(Player p) {
        p.setFireTicks(-1);
        if (LavaUtils.checkForBoots(p)) {
            p.setMaxHealth(40D);
            Location loc = p.getLocation();
            Location loc2 = p.getLocation().add(0, -1, 0);



            if (p.isSneaking()) {
                if (Material.STATIONARY_LAVA == p.getWorld().getBlockAt(loc).getType() || Material.STATIONARY_LAVA == p.getWorld().getBlockAt(loc2).getType()) {
                    // p.sendMessage("On Lava");

                    Vector vec = p.getLocation().getDirection();
                    vec.multiply(0.4f);
                    vec.setY(0.01f);

                    p.setVelocity(vec);

                }
            }
        }

        if (LavaUtils.checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 5), true);
        }
        if (LavaUtils.checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return "Lava Boots".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(2).equals(boots.getEnchantments().get(Enchantment.DURABILITY)) && RED.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createLavaBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Lava Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.RED + "Walk ON Lava by sneaking!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED +"FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 2);

        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if (helmet != null && helmet.hasItemMeta()) {

            ItemMeta itemMeta = helmet.getItemMeta();

            return "Lava Helmet".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(2).equals(helmet.getEnchantments().get(Enchantment.DURABILITY)) && RED.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createLavaHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Lava Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.RED + "Punch to splash some lava");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED +"FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 2);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if (chestplate != null && chestplate.hasItemMeta()) {

            ItemMeta itemMeta = chestplate.getItemMeta();

            return "Lava Chestplate".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(2).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY)) && RED.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createLavaChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Lava Chestplate");


        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED +"FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 2);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if (leggings != null && leggings.hasItemMeta()) {

            ItemMeta itemMeta = leggings.getItemMeta();

            return "Lava Leggings".equals(itemMeta.getDisplayName()) && (itemMeta instanceof LeatherArmorMeta)
                    && new Integer(2).equals(leggings.getEnchantments().get(Enchantment.DURABILITY)) && RED.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createLavaLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Lava Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED +"FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        leggings.setItemMeta(meta);


        leggings.addEnchantment(Enchantment.DURABILITY, 2);

        return leggings;
    }

}
