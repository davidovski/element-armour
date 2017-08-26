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
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PoisonUtils {
    private static final Color GREEN = Color.fromRGB(100, 200, 10);

    private PoisonUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            Block tb = p.getTargetBlock((HashSet<Material>) null, 10).getRelative(BlockFace.UP);
            AreaEffectCloud c = (AreaEffectCloud) p.getWorld().spawnEntity(tb.getLocation(), EntityType.AREA_EFFECT_CLOUD);
            c.setSource(p);

            c.setBasePotionData(new PotionData(PotionType.POISON));
        }
    }

    public static void constantPlayerChecks(Player p) {
        if ((checkForChestplate(p)) && (p.hasPotionEffect(PotionEffectType.POISON))) {
            p.removePotionEffect(PotionEffectType.POISON);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 2));
        }

        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Poison Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Poison Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.GREEN + "Be immune to poison!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 3);
        boots = ArmourUtil.addArmourAttributes(boots);
        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Poison Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Poison Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.GREEN + "Punch to splash some Poison");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Poison Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Poison Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 3);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Poison Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Poison Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(GREEN);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 3);

        return leggings;
    }
}
