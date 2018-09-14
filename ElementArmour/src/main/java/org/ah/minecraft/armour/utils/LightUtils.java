package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class LightUtils {
    private static final Color WHITE = Color.fromRGB(250, 255, 250);

    private LightUtils() {
    }

    public static void onClick(PlayerInteractEntityEvent e) {


        if (checkForHelmet(e.getPlayer())) {
            if (e.getRightClicked() instanceof LivingEntity) {
                LivingEntity en = (LivingEntity) e.getRightClicked();
                if (en instanceof Monster) {
                    en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*60, 1));


                } else {
                    if (en.getHealth() + 4 > en.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
                        en.setHealth(en.getMaxHealth());
                    } else {
                        en.setHealth(en.getHealth() + 4);
                    }
                }
            }
        }
    }

    public static void checkPunch(Player p) {
        if (checkForBoots(p)) {
            if (p.isGliding()) {
//                p.setVelocity(p.getLocation().getDirection().multiply(16));
            }
        }
        if (checkForHelmet(p)) {
            Location loc = p.getLocation();
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1.0F, 1.0F);
            p.getWorld().spawnParticle(Particle.END_ROD, p.getLocation(), 1000);
            for (Entity e : p.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                if (!e.equals(p)) {
                    Location loc2 = e.getLocation();
                    Vector vec = new Vector(loc2.getX() - loc.getX(), loc2.getY() - loc.getY(), loc2.getZ() - loc.getZ());
                    vec.normalize();

                    vec.multiply(3.0F);
                    vec.setY(1.0F);

                    e.setVelocity(vec);
                    if ((e instanceof LivingEntity)) {
                        ((LivingEntity) e).damage(7.0D);
                    }
                }
            }
        }
    }

    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (checkForHelmet(p)) {
            Block b = e.getClickedBlock();
            if (b != null) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (b.getType() == Material.CROPS) {
                        b.setData((byte) (b.getData() + 1));
                        if (b.getData() > 7) {
                            b.setData((byte) 7);
                        }
                    }
                }
            }
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

                        if (b.getType() == Material.DIRT) {
                            b.setType(Material.GRASS);
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
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 3), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 0), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 40, 2), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 2), true);

        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 4), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 2));
            p.setFallDistance(0);
        }
        if (checkForBoots(p)) {
//            if (p.isSneaking() && p.isGliding()) {
//                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
//                Vector vec = p.getLocation().getDirection();
//                vec.multiply(1.3f);
//                p.setVelocity(vec);
//            }
            if (p.isSneaking()) {
                p.setGliding(true);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
                Vector vec = p.getVelocity().add(p.getLocation().getDirection().multiply(0.2));
                if (vec.length() > 3) {
                    vec.multiply(0.9);
                }
                p.setVelocity(vec);
            }

        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Light Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (WHITE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Light Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "Sneak to fly, and turn ice into water");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.LIGHT_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        boots.setItemMeta(meta);

        boots.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        boots = ArmourUtil.addEpicArmourAttributes(boots);
        return boots;
    }

    public static boolean checkForHelmet(Player p) {
        ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Light Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (WHITE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Light Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLUE + "See in the Dark, blow away enemies");
        lores.add(ChatColor.BLUE + "and heal (or make them float skywards...) them by right clicking ");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.LIGHT_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addUnsafeEnchantment(Enchantment.DURABILITY, 4);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Light Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (WHITE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Light Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.LIGHT_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addUnsafeEnchantment(Enchantment.DURABILITY, 4);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Light Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(4).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (WHITE.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Light Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLUE + "Sprinting Speed x2");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.LIGHT_PURPLE + "CRYSTAL");
        lores.add(ChatColor.GRAY + "Tier IV");
        meta.setLore(lores);

        meta.setColor(WHITE);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 4);

        return leggings;
    }
}
