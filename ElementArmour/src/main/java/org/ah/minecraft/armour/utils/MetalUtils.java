package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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

    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (checkForHelmet(p)) {

            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.IRON_FENCE) {
                    b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT, 6));
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.IRON_FENCE);
                    return;
                }
                if (b.getType() == Material.IRON_ORE) {
                    b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT, 2));
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.IRON_ORE);
                    return;
                }
                if (b.getType() == Material.IRON_TRAPDOOR) {
                    b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT, 4));
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.IRON_TRAPDOOR);
                    return;
                }
                if (b.getType() == Material.IRON_BLOCK) {
                    b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT, 9));
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);
                    return;
                }
                if (b.getType() == Material.IRON_DOOR_BLOCK) {
                    b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.IRON_INGOT, 6));
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.IRON_DOOR_BLOCK);
                    return;
                }
            } else if (e.getAction().toString().contains("LEFT")){
                ItemStack i = p.getEquipment().getItemInMainHand();
                if (i.getType().toString().contains("IRON") && !i.getType().isBlock()) {
//                    p.getWorld().spawnArrow(p.getEyeLocation().add(p.getLocation().getDirection().add(p.getVelocity())), p.getLocation().getDirection().add(p.getVelocity()), 1, 0);
                    Item it = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.IRON_NUGGET));
                    Vector v = p.getLocation().getDirection().add(p.getVelocity().setY(0)).multiply(4);

                    it.setVelocity(v);
                    it.setPickupDelay(Integer.MAX_VALUE);
                    it.setCustomName("Projectile-" + p.getName());
                    it.setCustomNameVisible(false);

                    if (i.getDurability() < i.getType().getMaxDurability()) {
                        i.setDurability((short) (i.getDurability() + 1));
                    } else {
                        if (i.getAmount() > 1) {
                            i.setAmount(i.getAmount() - 1);
                        } else {
                            p.getInventory().remove(i);
                        }
                    }
                }
            }

        }
    }

    public static void checkPunch(Player p) {

    }

    public static void constantPlayerChecks(Player p) {
        if (checkForHelmet(p)) {
            for (Entity e : p.getNearbyEntities(1, 2, 1)) {
                if (e instanceof Item) {
                    Item i = (Item) e;
                }
            }
        }
        if ((checkForBoots(p)) && (!p.isOnGround()) && (p.isSneaking())) {
            p.setVelocity(new Vector(0, -1, 0));
            Vector direction = p.getLocation().getDirection();
            direction.setY(0);
            p.damage(2);

            Location l = p.getLocation().add(direction).add(0, -1, 0);
            int t = 0;
            for (int i = 0; i < 5; i++) {
                t++;
                l.add(direction);
                if (!l.getBlock().getType().isSolid()) {
                    while (!l.getBlock().getType().isSolid()) {
                        l.add(0, -1, 0);
                    }
                }
                EvokerFangs f = (EvokerFangs) p.getWorld().spawnEntity(l.getBlock().getRelative(BlockFace.UP).getLocation(), org.bukkit.entity.EntityType.EVOKER_FANGS);
                f.setOwner(p);
                f.setTicksLived(t * 3);

            }
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(0, -1, 2).getBlock(), Material.IRON_ORE, 5);
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(-1, -1, 2).getBlock(), Material.IRON_ORE, 5);
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(-2, -1, 1).getBlock(), Material.IRON_ORE, 5);
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(2, -1, 0).getBlock(), Material.IRON_ORE, 5);
            //
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(0, -1, -2).getBlock(), Material.IRON_ORE, 5);
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(1, -1, -2).getBlock(), Material.IRON_ORE, 5);
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(2, -1, -1).getBlock(), Material.IRON_ORE, 5);
            // FrostedBlock.setBlock(p.getLocation().getBlock().getLocation().add(-2, -1, 0).getBlock(), Material.IRON_ORE, 5);
            //
            // for (Entity entity : p.getNearbyEntities(2, 2, 2)) {
            // if (entity instanceof LivingEntity) {
            // ((LivingEntity) entity).damage(5, p);
            // }
            // entity.setVelocity(new Vector(0, 0.5, 0));
            // }

        }

        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 40, 0));
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 1));
        }
    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Metal Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createMetalBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Metal Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_GREEN + "Unleash their mighty power by sneaking in mid jump!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
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

            return ("Metal Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createMetalHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Metal Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_GREEN + "Create spikes by punching!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 2);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Metal Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createMetalChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);

        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Metal Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 2);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack chestplate = p.getInventory().getLeggings();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Metal Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta)) && (GRAY.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createMetalLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Metal Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier II");
        meta.setLore(lores);

        meta.setColor(GRAY);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 2);

        return leggings;
    }
}
