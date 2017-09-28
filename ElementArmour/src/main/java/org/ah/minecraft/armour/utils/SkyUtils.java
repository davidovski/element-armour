package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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
import org.bukkit.util.Vector;

public class SkyUtils
{
  private static final Color LIGHT_BLUE = Color.fromRGB(200, 220, 120);

  private SkyUtils() {}

  public static void checkPunch(Player p)
  {
    if (checkForHelmet(p)) {
      Location loc = p.getLocation();
      p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 1.0F);
      for (Entity e : p.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
        if (!e.equals(p)) {
          Location loc2 = e.getLocation();
          Vector vec = new Vector(loc2.getX() - loc.getX(), loc2.getY() - loc.getY(), loc2.getZ() - loc.getZ());
          vec.normalize();

          vec.multiply(3.0F);
          vec.setY(1.0F);

          e.setVelocity(vec);
          if ((e instanceof LivingEntity)) {
            ((LivingEntity)e).damage(7.0D);
          }
        }
      }
    }
  }

  public static void constantPlayerChecks(Player p) {
    if (checkForBoots(p)) {
      p.setFallDistance(0.0F);

      if (p.isSneaking()) {
        p.setGliding(true);
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 1.0F, 1.0F);
        Vector vec = p.getVelocity().add(p.getLocation().getDirection().multiply(0.05));
        if (vec.length() > 2) {
            vec.multiply(0.9);
        }
        p.setVelocity(vec);
      }
    }

    if (checkForChestplate(p)) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2));
    }
    if (checkForLeggings(p)) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1));
    }
  }

  public static boolean checkForBoots(Player p) {
    ItemStack boots = p.getInventory().getBoots();

    if ((boots != null) && (boots.hasItemMeta()))
    {
      ItemMeta itemMeta = boots.getItemMeta();

      return ("Sky Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (LIGHT_BLUE.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createSkyBoots() {
    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
    LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
    meta.setDisplayName("Sky Boots");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.DARK_GRAY + "Sneak to fly!!!");
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR");
    lores.add(ChatColor.GRAY + "Tier II");
    meta.setLore(lores);

    meta.setColor(LIGHT_BLUE);

    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    boots.setItemMeta(meta);

    boots.addEnchantment(Enchantment.DURABILITY, 2);
    boots = ArmourUtil.addArmourAttributes(boots);
    return boots;
  }

  public static boolean checkForHelmet(Player p) {
    ItemStack helmet = p.getInventory().getHelmet();

    if ((helmet != null) && (helmet.hasItemMeta()))
    {
      ItemMeta itemMeta = helmet.getItemMeta();

      return ("Sky Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (LIGHT_BLUE.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createSkyHelmet() {
    ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
    LeatherArmorMeta meta = (LeatherArmorMeta)helmet.getItemMeta();
    meta.setDisplayName("Sky Helmet");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.DARK_GRAY + "Launch your enimies by blowing them away!");
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR");
    lores.add(ChatColor.GRAY + "Tier II");
    meta.setLore(lores);

    meta.setColor(LIGHT_BLUE);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    helmet.setItemMeta(meta);

    helmet.addEnchantment(Enchantment.DURABILITY, 2);

    return helmet;
  }

  public static boolean checkForChestplate(Player p) {
    ItemStack chestplate = p.getInventory().getChestplate();

    if ((chestplate != null) && (chestplate.hasItemMeta()))
    {
      ItemMeta itemMeta = chestplate.getItemMeta();

      return ("Sky Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (LIGHT_BLUE.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createSkyChestplate() {
    ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    LeatherArmorMeta meta = (LeatherArmorMeta)chestplate.getItemMeta();
    meta.setDisplayName("Sky Chestplate");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR");
    lores.add(ChatColor.GRAY + "Tier II");
    meta.setLore(lores);

    meta.setColor(LIGHT_BLUE);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    chestplate.setItemMeta(meta);

    chestplate.addEnchantment(Enchantment.DURABILITY, 2);

    return chestplate;
  }

  public static boolean checkForLeggings(Player p) {
    ItemStack leggings = p.getInventory().getLeggings();

    if ((leggings != null) && (leggings.hasItemMeta()))
    {
      ItemMeta itemMeta = leggings.getItemMeta();

      return ("Sky Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (LIGHT_BLUE.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createSkyLeggings() {
    ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
    LeatherArmorMeta meta = (LeatherArmorMeta)leggings.getItemMeta();
    meta.setDisplayName("Sky Leggings");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR");
    lores.add(ChatColor.GRAY + "Tier II");
    meta.setLore(lores);

    meta.setColor(LIGHT_BLUE);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    leggings.setItemMeta(meta);

    leggings.addEnchantment(Enchantment.DURABILITY, 2);

    return leggings;
  }
}
