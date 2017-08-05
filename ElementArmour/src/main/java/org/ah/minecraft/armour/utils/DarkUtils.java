package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ah.minecraft.armour.ExperienceManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DarkUtils {
    private static final Color BLACK = Color.fromRGB(0, 0, 0);

    private DarkUtils() {
    }

    public static void checkPunch(Player p) {
        if (checkForHelmet(p)) {
            p.getWorld().playSound(p.getLocation(), org.bukkit.Sound.ENTITY_GHAST_SHOOT, 1.0F, 1.0F);
            Location loc = p.getEyeLocation();
            Fireball fireball = (Fireball) loc.getWorld().spawnEntity(loc.add(loc.getDirection().multiply(2)), org.bukkit.entity.EntityType.FIREBALL);
            fireball.setShooter(p);
            fireball.setYield(2.0F);
            fireball.setIsIncendiary(true);
            Vector v = loc.getDirection().multiply(1.5D);
            fireball.setVelocity(v);
            fireball.setBounce(true);
        }
    }

    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (checkForHelmet(p)) {
            // if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            // Block b = e.getClickedBlock();
            // if (b.getType() == Material.Dark) {
            // b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.Dark));
            // b.setType(Material.AIR);
            // b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.Dark);
            // }
            // }
            //
            // if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.AIR) {
            // Block b = e.getClickedBlock();
            // FrostedBlock.setBlock(b.getRelative(BlockFace.UP, 1), Material.Dark, 20);
            //
            // FrostedBlock.setBlock(b.getRelative(BlockFace.UP, 2), Material.Dark, 20);
            //
            // }
            //
            // Block b = p.getTargetBlock((Set<Material>) null, 5);
            // if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // if (b.getType() == Material.STATIONARY_LAVA) {
            // b.setType(Material.Dark);
            // b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.Dark);
            //
            // }
            // }
        }
    }

    public static void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (checkForBoots(p)) {

            Random r = new Random();
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    for (int y = -2; y <= 2; y++) {
                        Block b = p.getLocation().getBlock().getLocation().add(x, y, z).getBlock();

                        if (b.getType() == Material.GRASS) {
                            b.setType(Material.DIRT);
                        }
                        if (b.getType() == Material.LONG_GRASS) {
                            b.setData((byte) 0);
                        }

                        if (b.getType() == Material.YELLOW_FLOWER) {
                            b.setType(Material.AIR);
                        }

                        if (b.getType() == Material.LEAVES) {
                            FrostedBlock.setBlock(b, Material.AIR, 200);
                        }
                        if (b.getType() == Material.LEAVES_2) {
                            FrostedBlock.setBlock(b, Material.AIR, 200);
                        }

                        if (p.getEyeLocation().getBlock().getType() != Material.WATER && p.getEyeLocation().getBlock().getType() != Material.STATIONARY_WATER) {
                            if (b.getType() == Material.STATIONARY_WATER) {
                                FrostedBlock.setBlock(b, Material.ICE, 100);
                            }
                        }
                        if (p.getEyeLocation().getBlock().getType() != Material.LAVA && p.getEyeLocation().getBlock().getType() != Material.STATIONARY_LAVA) {
                            if (b.getType() == Material.LAVA) {
                                FrostedBlock.setBlock(b, Material.MAGMA, 100);
                            }
                        }

                    }
                }
            }
        }
    }

    public static void constantPlayerChecks(Player p) {
        if (checkForHelmet(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 2), true);
        }
        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 5), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 2), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 2), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 2), true);

        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 4), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2));
            p.setFallDistance(0);
        }
        if (checkForBoots(p)) {
            if (p.isSneaking() && p.isGliding()) {
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
                Vector vec = p.getLocation().getDirection();
                vec.multiply(1.3f);
                p.setVelocity(vec);
            }
            if (p.getLocation().getPitch() > 89.0) {
                if (p.isSneaking() && !p.isOnGround() && p.getVelocity().getY() < 0) {
                    p.setVelocity(p.getVelocity().setY(-1));
                    ExperienceManager expMan = new ExperienceManager(p);
                    if (expMan.getCurrentExp() > 0) {
                        expMan.changeExp(-1);
                        for (int x = -1; x <= 1; x++) {
                            for (int z = -1; z <= 1; z++) {
                                for (int y = -2; y <= 0; y++) {
                                    Block b = p.getLocation().getBlock().getLocation().add(x, y, z).getBlock();
                                    if (b.getType() != Material.BEDROCK) {
                                        for (ItemStack itemStack : b.getDrops()) {
                                            b.getWorld().dropItem(b.getLocation(), itemStack);
                                        }
                                        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
                                        b.setType(Material.AIR);

                                    }
                                }
                            }
                        }
                    }
                }
            } else if (p.getLocation().getPitch() < -80.0) {
                if (p.isSneaking()) {
                    p.setGliding(true);
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
                    Vector vec = p.getLocation().getDirection();
                    vec.multiply(1.3f);
                    p.setVelocity(vec);
                }
            }
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Dark Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Dark Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.DARK_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        boots.setItemMeta(meta);

        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        boots = ArmourUtil.addArmourAttributes(boots);
        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Dark Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Dark Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLUE + "Right-click lava to turn it solid, and break Dark with your bare hands!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.DARK_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 4);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Dark Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Dark Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.DARK_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 4);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Dark Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Dark Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLUE + "Sprinting Speed x2");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.DARK_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 4);

        return leggings;
    }
}
