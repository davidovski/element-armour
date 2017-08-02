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
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class IceUtils {
    private static final Color GRAY = Color.fromRGB(150, 249, 255);

    private IceUtils() {
    }

    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (checkForHelmet(p)) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.ICE) {
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.ICE);
                }
            }

            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.PACKED_ICE) {
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.PACKED_ICE);
                }
            }



            Block b = p.getTargetBlock((Set<Material>) null, 5);
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (b.getType() == Material.STATIONARY_WATER) {
                    b.setType(Material.ICE);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.ICE);

                }
            }
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
                    if ((b.getType() == Material.STATIONARY_WATER || b.getType() == Material.WATER) && b.getRelative(BlockFace.UP).getType() != Material.STATIONARY_WATER) {
                        b.setType(Material.ICE);
                        Plugin.s.add(new FrostedBlock(b, Material.WATER, r.nextInt(100)));
                        p.setFallDistance(0);

                    }
                }
            }
        }
    }

    public static void checkPunch(Player p) {

        if (checkForHelmet(p)) {
            Location loc = p.getLocation();
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 1.0F);
            Vector direction = p.getLocation().getDirection();
            Location l = p.getEyeLocation().add(direction);
            l.add(direction);
            l.add(direction);
            int t = 0;
            for (int i = 0; i < 3; i++) {
                t++;
                l.add(direction);
                if (l.getBlock().getType() == Material.AIR) {
                    FrostedBlock.setBlock(l.getBlock(), Material.ICE, (t * 2) + 20);
                }
                for (Entity en : Plugin.getNearbyEntities(l, 2)) {
                    if (en instanceof LivingEntity) {
                        ((LivingEntity) en).damage(5, p);
                    }
                }
            }
            // for (Entity e : p.getNearbyEntities(3.0D, 3.0D, 3.0D)) {
            // if (!e.equals(p)) {
            // if ((e instanceof LivingEntity)) {
            // ((LivingEntity) e).damage(4.0D);
            // ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 255));
            // ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 50, 255));
            // ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 255));
            // ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 50, 255));
            // Block block = e.getLocation().getBlock();
            // Block block2 = block.getRelative(BlockFace.UP);
            // block.setType(Material.ICE);
            // block2.setType(Material.ICE);
            // }
            // }
            // }
        }
    }

    public static void constantPlayerChecks(Player p) {
        if ((checkForBoots(p)) && (!p.isOnGround()) && (p.isSneaking())) {
            p.setVelocity(new Vector(0, -1, 0));
            Vector direction = p.getLocation().getDirection();
            direction.setY(0);

            Location l = p.getLocation().add(direction).add(0, -1, 0);
            int t = 100;

            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(0, 0, 2)), Material.ICE, t);
            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(0, 0, -2)), Material.ICE, t);
            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(1, 0, 1)), Material.ICE, t);
            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(1, 0, -1)), Material.ICE, t);

            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(2, 0, 0)), Material.ICE, t);
            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(-1, 0, -1)), Material.ICE, t);
            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(-1, 0, 1)), Material.ICE, t);
            FrostedBlock.setBlock(p.getWorld().getHighestBlockAt(p.getLocation().getBlock().getLocation().add(-2, 0, 0)), Material.ICE, t);


        }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 1));
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Ice Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createIceBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Ice Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_GREEN + "Unleash their mighty power by sneaking in mid jump!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });

        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.DURABILITY, 2);
        // boots.addUnsafeEnchantment(Enchantment.FROST_WALKER, 4);
        boots = ArmourUtil.addArmourAttributes(boots);

        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Ice Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createIceHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Ice Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_GREEN + "Create spikes by punching!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });

        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 2);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Ice Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createIceChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Ice Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });

        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 2);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack chestplate = p.getInventory().getLeggings();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Ice Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createIceLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Ice Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.BLUE + "WATER");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });

        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 2);

        return leggings;
    }
}
