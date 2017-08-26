package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeUtils {
    private static final Color BLUE = Color.fromRGB(61, 219, 169);

    private FreezeUtils() {
    }

    public static void checkPunch(Player p) {
    }

    public static void onPlayerInteractEntity(PlayerInteractAtEntityEvent event, Plugin plugin) {
        if ((event.getRightClicked() instanceof LivingEntity)) {
            LivingEntity e = (LivingEntity) event.getRightClicked();
            Player p = event.getPlayer();
            if ((checkForHelmet(p)) && (e.hasAI())) {
                e.setAI(false);
                e.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0), true);

                e.setGravity(false);
                org.bukkit.Bukkit.getScheduler().runTaskLater(plugin, new Unfreezemob(e), 20L);
            }
        }
    }

    public static void constantPlayerChecks(Player p) {
        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2));
        }
        if (checkForLeggings(p)) {
            if (p.isSprinting()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 4), true);
            } else {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1), true);
            }
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Freeze Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Freeze Boots");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLUE);
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

            return ("Freeze Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Freeze Helmet");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.BLUE + "Right-click your enemies to freeze them solid");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Freeze Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Freeze Chestplate");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 3);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Freeze Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (BLUE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Freeze Leggings");

        List<String> lores = new ArrayList<String>();
        lores.add(ChatColor.BLUE + "Sprinting Speed x2");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLUE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 3);

        return leggings;
    }
}
