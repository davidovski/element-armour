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
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SandUtils
{
  private static final Color YELLOW = Color.fromRGB(255, 255, 80);


  private SandUtils() {}


  public static void checkPunch(Player p) {}

  public static void constantPlayerChecks(Player p)
  {
    if (checkForChestplate(p)) {
      p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2, false, true, YELLOW));
    }
    if (checkForLeggings(p)) {
      if ((p.isSprinting()) && (p.getLocation().getBlock().getRelative(org.bukkit.block.BlockFace.DOWN).getType() == Material.SAND)) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 5, false, true, YELLOW), true);
      } else {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2, false, true, YELLOW), true);
      }
    }

    if (checkForBoots(p)) { Block bloc;
      if ((p.isOnGround()) && (p.isSprinting())) {
        Location behind = p.getLocation().subtract(p.getLocation().getDirection().setY(0)).add(0.0D, -1.0D, 0.0D);
        bloc = behind.getBlock();
        if (bloc.getType() == Material.SAND) {
          bloc.setType(Material.AIR);
          Byte blockData = Byte.valueOf((byte)0);
          FallingBlock b = p.getWorld().spawnFallingBlock(behind.add(0.0D, 1.0D, 0.0D), Material.SAND, blockData.byteValue());
          b.teleport(bloc.getLocation());
          b.setVelocity(b.getVelocity().setY(0.2D));
        }
      }

      if ((!p.isOnGround()) && (p.isSneaking())) {
        p.setVelocity(new Vector(0, -2, 0));
        p.setFallDistance(0.0F);





        for (Entity e : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
          if (((e instanceof FallingBlock)) &&
            (!e.equals(p))) {
            Location loc = p.getLocation();
            Location loc2 = e.getLocation();
            Vector vec = new Vector(loc2.getX() - loc.getX(), loc2.getY() - loc.getY(), loc2.getZ() - loc.getZ());
            vec.normalize();

            vec.multiply(0.2F);
            vec.setY(0.5F);

            e.setVelocity(vec);
          }
        }

        for (Entity e : p.getNearbyEntities(2.0D, 2.0D, 2.0D)) {
          if ((!e.equals(p)) &&
            ((e instanceof LivingEntity))) {
            ((LivingEntity)e).damage(2.0D, p);
          }
        }


        for (int x = -1; x <= 1; x++) {
          for (int y = -3; y <= -1; y++) {
            for (int z = -1; z <= 1; z++) {
              Block block = p.getLocation().add(x, y, z).getBlock();
              if (block.getType() == Material.SAND) {
                block.setType(Material.AIR);
                Byte blockData = Byte.valueOf((byte)0);
                FallingBlock localFallingBlock1 = p.getWorld().spawnFallingBlock(block.getLocation().add(0.0D, 1.0D, 0.0D), Material.SAND, blockData.byteValue());
              }
            }
          }
        }
      }
    }
  }

  public static boolean checkForBoots(Player p)
  {
    ItemStack boots = p.getInventory().getBoots();

    if ((boots != null) && (boots.hasItemMeta()))
    {
      ItemMeta itemMeta = boots.getItemMeta();

      return ("Sand Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (new Integer(3).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (YELLOW.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createBoots() {
    ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
    LeatherArmorMeta meta = (LeatherArmorMeta)boots.getItemMeta();
    meta.setDisplayName("Sand Boots");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.WHITE + "Jump and sneak at the same time to create a huge sandy groundpound");
    lores.add(ChatColor.WHITE + "Only works on sand.");
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR" + ChatColor.GREEN + "EARTH");
    lores.add(ChatColor.GRAY + "Tier III");
    meta.setLore(lores);

    meta.setColor(YELLOW);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    boots.setItemMeta(meta);

    boots.addEnchantment(Enchantment.DURABILITY, 3);
    boots = ArmourUtil.addArmourAttributes(boots);
    return boots;
  }

  public static boolean checkForHelmet(Player p) {
    ItemStack helmet = p.getInventory().getHelmet();

    if ((helmet != null) && (helmet.hasItemMeta()))
    {
      ItemMeta itemMeta = helmet.getItemMeta();

      return ("Sand Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (new Integer(3).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (YELLOW.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createHelmet() {
    ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
    LeatherArmorMeta meta = (LeatherArmorMeta)helmet.getItemMeta();
    meta.setDisplayName("Sand Helmet");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.BLUE + "Pick sand up by right clicking it!");
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR" + ChatColor.GREEN + "EARTH");
    lores.add(ChatColor.GRAY + "Tier III");
    meta.setLore(lores);

    meta.setColor(YELLOW);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    helmet.setItemMeta(meta);

    helmet.addEnchantment(Enchantment.DURABILITY, 3);

    return helmet;
  }

  public static boolean checkForChestplate(Player p) {
    ItemStack chestplate = p.getInventory().getChestplate();

    if ((chestplate != null) && (chestplate.hasItemMeta()))
    {
      ItemMeta itemMeta = chestplate.getItemMeta();

      return ("Sand Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (new Integer(3).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (YELLOW.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createChestplate() {
    ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    LeatherArmorMeta meta = (LeatherArmorMeta)chestplate.getItemMeta();
    meta.setDisplayName("Sand Chestplate");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR" + ChatColor.GREEN + "EARTH");
    lores.add(ChatColor.GRAY + "Tier III");
    meta.setLore(lores);

    meta.setColor(YELLOW);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    chestplate.setItemMeta(meta);

    chestplate.addEnchantment(Enchantment.DURABILITY, 3);

    return chestplate;
  }

  public static boolean checkForLeggings(Player p) {
    ItemStack leggings = p.getInventory().getLeggings();

    if ((leggings != null) && (leggings.hasItemMeta()))
    {
      ItemMeta itemMeta = leggings.getItemMeta();

      return ("Sand Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) &&
        (new Integer(3).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (YELLOW.equals(((LeatherArmorMeta)itemMeta).getColor()));
    }
    return false;
  }

  public static ItemStack createLeggings() {
    ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
    LeatherArmorMeta meta = (LeatherArmorMeta)leggings.getItemMeta();
    meta.setDisplayName("Sand Leggings");

    List<String> lores = new ArrayList();
    lores.add(ChatColor.BLUE + "Sprinting Speed x2");
    lores.add(ChatColor.WHITE + "   ");
    lores.add(ChatColor.GRAY + "Set: " + ChatColor.YELLOW + "AIR" + ChatColor.GREEN + "EARTH");
    lores.add(ChatColor.GRAY + "Tier III");
    meta.setLore(lores);

    meta.setColor(YELLOW);
    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
    leggings.setItemMeta(meta);

    leggings.addEnchantment(Enchantment.DURABILITY, 3);

    return leggings;
  }
}
