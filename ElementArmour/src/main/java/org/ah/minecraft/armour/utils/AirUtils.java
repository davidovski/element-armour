package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AirUtils {

    private static final Color LIGHT_BLUE = Color.fromRGB(200, 220, 255);

    private AirUtils() {
    }

    public static void checkPunch(Player p) {
        if (AirUtils.checkForHelmet(p)) {
            Location loc = p.getLocation();
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1f, 1f);
            for (Entity e : p.getNearbyEntities(3, 3, 3)) {
                if (!e.equals(p)) {
                    Location loc2 = e.getLocation();
                    Vector vec = new Vector(loc2.getX() - loc.getX(), loc2.getY() - loc.getY(), loc2.getZ() - loc.getZ());
                    vec.normalize();

                    vec.multiply(3f);
                    vec.setY(1f);

                    e.setVelocity(vec);
                    if (e instanceof LivingEntity) {
                        ((LivingEntity) e).damage(2D);
                    }
                }
            }
        }
    }

    public static void constantPlayerChecks(Player p) {
        if (AirUtils.checkForBoots(p)) {
            p.setMaxHealth(40D);


            if (p.isSneaking()) {
                p.setFallDistance(0f);
                if (p.getLocation().add(0, -1, 0).getBlock().getType().isSolid() || p.getLocation().add(0, -1.5, 0).getBlock().getType().isSolid() || (p.getLocation().add(0, -1, 0).getBlock().getType() == Material.STATIONARY_WATER && p.getLocation().add(0, 0, 0).getBlock().getType() == Material.STATIONARY_WATER)) {
                    p.setVelocity(p.getVelocity().setY(0.2).add(p.getLocation().getDirection().setY(0)).multiply(0.3));
                }

            }
        }
        if (AirUtils.checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 1));
        }
        if (AirUtils.checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 0));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return "Air Boots".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta
                    && new Integer(1).equals(boots.getEnchantments().get(Enchantment.DURABILITY)) && LIGHT_BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createAirBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Air Boots");

        List<String> lores = new ArrayList<String>();

        lores.add(ChatColor.WHITE + "Sneak to hover around!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW +"AIR");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(LIGHT_BLUE);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 1);

        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if (helmet != null && helmet.hasItemMeta()) {

            ItemMeta itemMeta = helmet.getItemMeta();

            return "Air Helmet".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta
                    && new Integer(1).equals(helmet.getEnchantments().get(Enchantment.DURABILITY)) && LIGHT_BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createAirHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Air Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "Creates a gust of wind to attack your enemies");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW +"AIR");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(LIGHT_BLUE);
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 1);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if (chestplate != null && chestplate.hasItemMeta()) {

            ItemMeta itemMeta = chestplate.getItemMeta();

            return "Air Chestplate".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta
                    && new Integer(1).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY)) && LIGHT_BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createAirChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Air Chestplate");

        List<String> lores = new ArrayList<String>();

        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW +"AIR");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(LIGHT_BLUE);
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 1);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if (leggings != null && leggings.hasItemMeta()) {

            ItemMeta itemMeta = leggings.getItemMeta();

            return "Air Leggings".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta
                    && new Integer(1).equals(leggings.getEnchantments().get(Enchantment.DURABILITY)) && LIGHT_BLUE.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createAirLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Air Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW +"AIR");
        lores.add(ChatColor.GRAY + "Tier I");
        meta.setLore(lores);

        meta.setColor(LIGHT_BLUE);
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 1);

        return leggings;
    }
}
