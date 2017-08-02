package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ah.minecraft.armour.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
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

public class ObsidianUtils {
    private static final Color BLACK = Color.fromRGB(70, 0, 70);

    private ObsidianUtils() {
    }

    public static void checkPunch(Player p) {

    }

    public static void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (checkForHelmet(p)) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                Block b = e.getClickedBlock();
                if (b.getType() == Material.OBSIDIAN) {
                    b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.OBSIDIAN));
                    b.setType(Material.AIR);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.OBSIDIAN);
                }
            }

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.AIR) {
                Block b = e.getClickedBlock();
                FrostedBlock.setBlock(b.getRelative(BlockFace.UP, 1), Material.OBSIDIAN, 20);

                FrostedBlock.setBlock(b.getRelative(BlockFace.UP, 2), Material.OBSIDIAN, 20);

            }

            Block b = p.getTargetBlock((Set<Material>) null, 5);
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (b.getType() == Material.STATIONARY_LAVA) {
                    b.setType(Material.OBSIDIAN);
                    b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.OBSIDIAN);

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
                    if ((b.getType() == Material.STATIONARY_LAVA || b.getType() == Material.LAVA) && b.getRelative(BlockFace.UP).getType() != Material.STATIONARY_LAVA) {

                        b.setType(Material.OBSIDIAN);
                        if (r.nextBoolean()) {
                            Plugin.s.add(new FrostedBlock(b, Material.LAVA, r.nextInt(20 * 60)));
                        }
                        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.OBSIDIAN);
                        p.setFallDistance(0);

                    }
                }
            }
        }
    }

    public static void constantPlayerChecks(Player p) {
        if (checkForChestplate(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40, 1), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 40, 1), true);
        }
        if (checkForLeggings(p)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 2), true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 1));
        }

    }

    public static boolean checkForBoots(Player p) {
        ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ("Obsidian Boots".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(boots.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createBoots() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName("Obsidian Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLACK);
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

            return ("Obsidian Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(helmet.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createHelmet() {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Obsidian Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLUE + "Right-click lava to turn it solid, and break obsidian with your bare hands!");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        helmet.setItemMeta(meta);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);

        return helmet;
    }

    public static boolean checkForChestplate(Player p) {
        ItemStack chestplate = p.getInventory().getChestplate();

        if ((chestplate != null) && (chestplate.hasItemMeta())) {
            ItemMeta itemMeta = chestplate.getItemMeta();

            return ("Obsidian Chestplate".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(chestplate.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createChestplate() {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
        meta.setDisplayName("Obsidian Chestplate");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        chestplate.setItemMeta(meta);

        chestplate.addEnchantment(Enchantment.DURABILITY, 3);

        return chestplate;
    }

    public static boolean checkForLeggings(Player p) {
        ItemStack leggings = p.getInventory().getLeggings();

        if ((leggings != null) && (leggings.hasItemMeta())) {
            ItemMeta itemMeta = leggings.getItemMeta();

            return ("Obsidian Leggings".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (new Integer(3).equals(leggings.getEnchantments().get(Enchantment.DURABILITY))) && (BLACK.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static ItemStack createLeggings() {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
        meta.setDisplayName("Obsidian Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLUE + "Sprinting Speed x2");
        lores.add(ChatColor.WHITE + "   ");
        lores.add(ChatColor.GRAY + "Set: " + ChatColor.RED + "FIRE" + ChatColor.GREEN + "EARTH");
        lores.add(ChatColor.GRAY + "Tier III");
        meta.setLore(lores);

        meta.setColor(BLACK);
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        leggings.setItemMeta(meta);

        leggings.addEnchantment(Enchantment.DURABILITY, 3);

        return leggings;
    }
}
