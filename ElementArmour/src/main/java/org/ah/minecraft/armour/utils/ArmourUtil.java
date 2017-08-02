package org.ah.minecraft.armour.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class ArmourUtil {
    private static final Color GREEN = Color.fromRGB(0, 150, 20);
    private static final Color GRAY = Color.fromRGB(100, 100, 100);
    private static final Color AQUA = Color.AQUA;

    private ArmourUtil() {
    }

    public static org.bukkit.inventory.ItemStack addArmourAttributes(org.bukkit.inventory.ItemStack i) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = nmsStack.getTag();
        if (compound == null) {
            compound = new NBTTagCompound();
            nmsStack.setTag(compound);
            compound = nmsStack.getTag();
        }
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound healthboost = new NBTTagCompound();
        healthboost.set("AttributeName", new NBTTagString("generic.armor"));
        healthboost.set("Name", new NBTTagString("generic.armor"));
        healthboost.set("Amount", new NBTTagInt(5));
        healthboost.set("Operation", new NBTTagInt(0));
        healthboost.set("UUIDLeast", new NBTTagInt(905983));
        healthboost.set("UUIDMost", new NBTTagInt(18915));

        if (i.getType().toString().contains("HELM")) {
            healthboost.set("Slot", new NBTTagString("head"));
        } else if (i.getType().toString().contains("CHEST")) {
            healthboost.set("Slot", new NBTTagString("chest"));
        }
        if (i.getType().toString().contains("LEG")) {
            healthboost.set("Slot", new NBTTagString("legs"));
        }
        if (i.getType().toString().contains("BOOT")) {
            healthboost.set("Slot", new NBTTagString("feet"));
        }
        modifiers.add(healthboost);
        compound.set("AttributeModifiers", modifiers);
        nmsStack.setTag(compound);
        i = CraftItemStack.asBukkitCopy(nmsStack);
        return i;
    }

    public static boolean checkForBouncerBoots(Player p) {
        org.bukkit.inventory.ItemStack boots = p.getInventory().getBoots();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ((ChatColor.DARK_PURPLE + "Bouncer Boots").equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (GREEN.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static org.bukkit.inventory.ItemStack createBouncerBoots() {
        org.bukkit.inventory.ItemStack boots = new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Bouncer Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.GREEN + "Bouncer Boots");
        lores.add(ChatColor.DARK_GREEN + "Bounce around with these on!");
        meta.setLore(lores);

        meta.setColor(GREEN);
        boots.setItemMeta(meta);

        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        return boots;
    }

    public static org.bukkit.inventory.ItemStack createLightEssence() {
        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(Material.QUARTZ, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.MAGIC +  "!!!" + ChatColor.RESET + ChatColor.WHITE + "Light Essence" + ChatColor.MAGIC  +  "!!!");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.WHITE + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.WHITE + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.WHITE + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.WHITE + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.WHITE + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.WHITE + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


        meta.setLore(lores);

        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        return item;
    }

    public static org.bukkit.inventory.ItemStack createDarkEssence() {
        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(Material.COAL, 1, (short) 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + "" + ChatColor.MAGIC +  "!!!" + ChatColor.RESET + ChatColor.BLACK + "Dark Essence" + ChatColor.MAGIC +  "!!!");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLACK + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.BLACK + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.BLACK + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.BLACK + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.BLACK + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        lores.add(ChatColor.BLACK + "" + ChatColor.MAGIC + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        meta.setLore(lores);

        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        item.setItemMeta(meta);

        item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        return item;
    }

    public static boolean checkForWariorHelmet(Player p) {
        org.bukkit.inventory.ItemStack helmet = p.getInventory().getHelmet();

        if ((helmet != null) && (helmet.hasItemMeta())) {
            ItemMeta itemMeta = helmet.getItemMeta();

            return ("Warrior Helmet".equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta));
        }

        return false;
    }

    public static org.bukkit.inventory.ItemStack createWariorHelmet() {
        org.bukkit.inventory.ItemStack helmet = new org.bukkit.inventory.ItemStack(Material.LEATHER_HELMET);

        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        meta.setDisplayName("Warrior Helmet");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.BLACK + "Worn by the mighty skeleton warriors.");
        meta.setLore(lores);

        helmet.setItemMeta(meta);

        helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);

        return helmet;
    }

    public static boolean checkForRiderLeggings(Player p) {
        org.bukkit.inventory.ItemStack boots = p.getInventory().getLeggings();

        if ((boots != null) && (boots.hasItemMeta())) {
            ItemMeta itemMeta = boots.getItemMeta();

            return ((ChatColor.DARK_PURPLE + "Chaser Leggings").equals(itemMeta.getDisplayName())) && ((itemMeta instanceof LeatherArmorMeta))
                    && (Color.MAROON.equals(((LeatherArmorMeta) itemMeta).getColor()));
        }
        return false;
    }

    public static org.bukkit.inventory.ItemStack createRiderLeggings() {
        org.bukkit.inventory.ItemStack boots = new org.bukkit.inventory.ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Chaser Leggings");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.DARK_AQUA + "Zoom around with these bad boys and sprint to unlock their full power!");
        meta.setLore(lores);

        meta.setColor(Color.MAROON);
        boots.setItemMeta(meta);

        return boots;
    }

    public static boolean checkHoldingWarpBow(Player p) {
        org.bukkit.inventory.ItemStack bow = p.getItemInHand();

        if ((bow != null) && (bow.hasItemMeta())) {
            ItemMeta itemMeta = bow.getItemMeta();

            return ((ChatColor.DARK_PURPLE + "Warp Bow").equals(itemMeta.getDisplayName())) && (bow.getType() == Material.BOW);
        }
        return false;
    }

    public static org.bukkit.inventory.ItemStack createWarpBow() {
        org.bukkit.inventory.ItemStack bow = new org.bukkit.inventory.ItemStack(Material.BOW);

        ItemMeta meta = bow.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Warp Bow");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.LIGHT_PURPLE + "Warp Bow");
        lores.add(ChatColor.LIGHT_PURPLE + "Shoot to teleport!");
        meta.setLore(lores);

        bow.setItemMeta(meta);

        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 20);

        return bow;
    }

    public static org.bukkit.inventory.ItemStack createAquaBoots() {
        org.bukkit.inventory.ItemStack boots = new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Auqatic Boots");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.AQUA + "Worn by the aquatic sea monsters.");

        meta.setLore(lores);
        meta.addEnchant(Enchantment.DEPTH_STRIDER, 16, true);
        meta.addEnchant(Enchantment.DURABILITY, 16, true);

        meta.setColor(AQUA);
        boots.setItemMeta(meta);

        return boots;
    }

    public static org.bukkit.inventory.ItemStack createAquaPants() {
        org.bukkit.inventory.ItemStack boots = new org.bukkit.inventory.ItemStack(Material.LEATHER_LEGGINGS);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Auqatic Pants");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.AQUA + "Worn by the aquatic sea monsters.");

        meta.setLore(lores);

        meta.addEnchant(Enchantment.DURABILITY, 16, true);

        meta.setColor(AQUA);
        boots.setItemMeta(meta);

        return boots;
    }

    public static org.bukkit.inventory.ItemStack createAquaShirt() {
        org.bukkit.inventory.ItemStack boots = new org.bukkit.inventory.ItemStack(Material.LEATHER_CHESTPLATE);

        LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Auqatic Shirt");

        List<String> lores = new ArrayList();
        lores.add(ChatColor.AQUA + "Worn by the aquatic sea monsters.");

        meta.setLore(lores);

        meta.addEnchant(Enchantment.DURABILITY, 16, true);

        meta.setColor(AQUA);
        boots.setItemMeta(meta);

        return boots;
    }
}
