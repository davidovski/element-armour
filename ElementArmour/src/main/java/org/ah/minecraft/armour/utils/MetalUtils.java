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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class MetalUtils {

    private static final Color GRAY = Color.fromRGB(100, 100, 100);

    private MetalUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            Block targetBlock = p.getTargetBlock((HashSet<Byte>) null, 10);

            if (targetBlock.getType().isSolid()) {
                EvokerFangs f = (EvokerFangs) p.getWorld().spawnEntity(targetBlock.getRelative(BlockFace.UP).getLocation(), EntityType.EVOKER_FANGS);
                f.setOwner(p);
            } else if (targetBlock.getRelative(BlockFace.DOWN).getType().isSolid()) {
                EvokerFangs f = (EvokerFangs) p.getWorld().spawnEntity(targetBlock.getLocation(), EntityType.EVOKER_FANGS);
                f.setOwner(p);
            }
        }

    }

    public static void constantPlayerChecks(Player p) {
        if (MetalUtils.checkForBoots(p)) {
            if (!p.isOnGround() && p.isSneaking()) {
                p.setVelocity(new Vector(0, -1, 0));
                FallingBlock b1 = p.getWorld().spawnFallingBlock(p.getLocation().add(1, 0, 0), Material.IRON_ORE, (byte) 0x0);
                FallingBlock b2 = p.getWorld().spawnFallingBlock(p.getLocation().add(-1, 0, 0), Material.IRON_ORE, (byte) 0x0);
                FallingBlock b3 = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 0, 1), Material.IRON_ORE, (byte) 0x0);
                FallingBlock b4 = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 0, -1), Material.IRON_ORE, (byte) 0x0);
                b1.setVelocity(new Vector(0, 0.5, 0));
                b2.setVelocity(new Vector(0, 0.5, 0));
                b3.setVelocity(new Vector(0, 0.5, 0));
                b4.setVelocity(new Vector(0, 0.5, 0));

                b1.setDropItem(false);
                b2.setDropItem(false);
                b3.setDropItem(false);
                b4.setDropItem(false);
            }
            p.setMaxHealth(40D);

        }

        if (MetalUtils.checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 0));
        }
        if (MetalUtils.checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 1));

        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if (boots != null && boots.hasItemMeta()) {

            ItemMeta itemMeta = boots.getItemMeta();

            return "Metal Boots".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta && GRAY.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createMetalBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Metal Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.DARK_GREEN + "Unleash their mighty power by sneaking in mid jump!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN +"EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 2);

        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if (helmet != null && helmet.hasItemMeta()) {

            ItemMeta itemMeta = helmet.getItemMeta();

            return "Metal Helmet".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta && GRAY.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createMetalHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Metal Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.DARK_GREEN + "Create spikes by punching!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN +"EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 2);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if (chestplate != null && chestplate.hasItemMeta()) {

            ItemMeta itemMeta = chestplate.getItemMeta();

            return "Metal Chestplate".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta && GRAY.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createMetalChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Metal Chestplate");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN +"EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 2);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack chestplate = p.getInventory().getLeggings();

        if (chestplate != null && chestplate.hasItemMeta()) {

            ItemMeta itemMeta = chestplate.getItemMeta();

            return "Metal Leggings".equals(itemMeta.getDisplayName()) && itemMeta instanceof LeatherArmorMeta && GRAY.equals(((LeatherArmorMeta) itemMeta).getColor());
        }
        return false;
    }

    public static ItemStack createMetalLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Metal Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN +"EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 2);

        return leggings;
    }

}
