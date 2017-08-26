package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ah.minecraft.armour.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class LavaUtils {
    private static final Color RED = Color.fromRGB(255, 180, 10);

    private LavaUtils() {
    }

    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (checkForHelmet(p)) {
            Block b = p.getTargetBlock((Set<Material>) null, 5);
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (b.getType() == Material.STATIONARY_LAVA) {
                    b.getLocation().subtract(p.getLocation().getDirection().multiply(1)).getBlock().setType(Material.LAVA);

                }
            }
        }
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            p.getWorld().playSound(p.getLocation(), org.bukkit.Sound.ENTITY_GHAST_SHOOT, 1.0F, 1.0F);
            Location loc = p.getEyeLocation();
            Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc.add(loc.getDirection().multiply(2)), org.bukkit.entity.EntityType.FIREBALL);
            fireball.setShooter(p);
            fireball.setYield(1.0F);
            fireball.setIsIncendiary(true);
            Vector v = loc.getDirection().multiply(1.5D);
            fireball.setVelocity(v);
        }
    }

    public static void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (checkForBoots(p)) {
            int down = -1;
            if (e.getFrom().getY() - e.getTo().getY() > 0) {
                down = -2;

                // e.setCancelled(true);
            }
            Random r = new Random();
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    Block b = p.getLocation().getBlock().getLocation().add(x, down, z).getBlock();
                    if ((b.getType() == Material.STATIONARY_LAVA || b.getType() == Material.LAVA) && b.getRelative(BlockFace.UP).getType() != Material.STATIONARY_LAVA) {
                        b.setType(Material.MAGMA);
                        Plugin.s.add(new FrostedBlock(b, Material.LAVA, r.nextInt(100)));
                        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.MAGMA);
                        p.setFallDistance(0);

                    }
                }
            }
        }
    }

    public static void constantPlayerChecks(Player p) {

        p.setFireTicks(-1);
        // if (checkForBoots(p)) {
        // Location loc = p.getLocation();
        // Location loc2 = p.getLocation().add(0.0D, -1.0D, 0.0D);
        //
        // if ((p.isSneaking())
        // && ((Material.STATIONARY_LAVA == p.getWorld().getBlockAt(loc).getType()) || (Material.STATIONARY_LAVA == p.getWorld().getBlockAt(loc2).getType()))) {
        //
        // Vector vec = p.getLocation().getDirection();
        // vec.multiply(0.4F);
        // vec.setY(0.01F);
        //
        // p.setVelocity(vec);
        // }
        // }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.FIRE_RESISTANCE, 200, 5), true);
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, 40, 1));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Lava Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(2).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLavaBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Lava Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.RED + "Walk ON Lava!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 2);
        boots = ArmourUtil.addArmourAttributes(boots);
        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Lava Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(2).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLavaHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Lava Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.RED + "Throw huge ghast fireballs by punching");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 2);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Lava Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(2).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLavaChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Lava Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 2);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Lava Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(2).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (RED.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLavaLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Lava Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(RED);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 2);

        return leggings;
    }
}
