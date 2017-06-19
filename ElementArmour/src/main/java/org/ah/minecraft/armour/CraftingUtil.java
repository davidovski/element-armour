package org.ah.minecraft.armour;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.ah.minecraft.armour.utils.AirUtils;
import org.ah.minecraft.armour.utils.ArmourUtil;
import org.ah.minecraft.armour.utils.EarthUtils;
import org.ah.minecraft.armour.utils.FireUtils;
import org.ah.minecraft.armour.utils.IceUtils;
import org.ah.minecraft.armour.utils.LavaUtils;
import org.ah.minecraft.armour.utils.MetalUtils;
import org.ah.minecraft.armour.utils.SkyUtils;
import org.ah.minecraft.armour.utils.WaterUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftingUtil {

    private CraftingUtil() {

    }

    public static void craft(ItemStack i, Inventory inv, Location loc) {
        loc.getWorld().playSound(loc, Sound.ENTITY_GHAST_SHOOT, 1, 1);
        ListIterator<ItemStack> it = inv.iterator();
        while (it.hasNext()) {
            ItemStack item = it.next();
            if (item != null) {
                if (item.getType() != Material.LAVA_BUCKET && item.getType() != Material.WATER_BUCKET) {
                    inv.remove(item);
                } else {
                    item.setType(Material.BUCKET);
                }
            }
        }
        inv.setItem(4, i);
    }

    public static boolean surroundedin(Inventory inv, Material m) {

        return inv.getItem(0) != null && inv.getItem(0).getType() == m && inv.getItem(1) != null && inv.getItem(1).getType() == m && inv.getItem(2) != null
                && inv.getItem(2).getType() == m && inv.getItem(3) != null && inv.getItem(3).getType() == m && inv.getItem(5) != null && inv.getItem(5).getType() == m
                && inv.getItem(6) != null && inv.getItem(6).getType() == m && inv.getItem(7) != null && inv.getItem(7).getType() == m && inv.getItem(8) != null
                && inv.getItem(8).getType() == m;

    }

    public static void crafterCheck(Plugin plugin) {
        Iterator<String> it = plugin.crafterLocations.iterator();

        while (it.hasNext()) {
            String locationString = it.next();

            Location loc = LocationUtil.stringToLocation(plugin.getServer(), locationString);

            if (loc.getWorld() != null) {
                loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);

                if (loc.getBlock().getType() == Material.DISPENSER) {
                    for (Entity e : plugin.getNearbyEntities(loc, 2)) {
                        if (e instanceof LivingEntity) {
                            ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 2));
                        }
                    }

                    Inventory inv = ((Dispenser) loc.getBlock().getState()).getInventory();
                    ItemStack centre = inv.getItem(4);
                    for (ItemStack item : inv) {
                        if (item != null && item.getType() == Material.AIR) {
                            return;
                        }
                    }

                    if (centre != null) {
                        if (centre.getType() == Material.IRON_HELMET) {
                            if (surroundedin(inv, Material.FEATHER)) {
                                craft(AirUtils.createAirHelmet(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.NETHERRACK)) {
                                craft(FireUtils.createFireHelmet(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.WATER_BUCKET)) {
                                craft(WaterUtils.createWaterHelmet(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.STONE)) {
                                craft(EarthUtils.createEarthHelmet(), inv, loc);
                                return;
                            }
                        }
                        if (centre.getType() == Material.IRON_CHESTPLATE) {
                            if (surroundedin(inv, Material.FEATHER)) {
                                craft(AirUtils.createAirChestplate(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.NETHERRACK)) {
                                craft(FireUtils.createFireChestplate(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.WATER_BUCKET)) {
                                craft(WaterUtils.createWaterChestplate(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.STONE)) {
                                craft(EarthUtils.createEarthChestplate(), inv, loc);
                                return;
                            }
                        }
                        if (centre.getType() == Material.IRON_LEGGINGS) {
                            if (surroundedin(inv, Material.FEATHER)) {
                                craft(AirUtils.createAirLeggings(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.NETHERRACK)) {
                                craft(FireUtils.createFireLeggings(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.WATER_BUCKET)) {
                                craft(WaterUtils.createWaterLeggings(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.STONE)) {
                                craft(EarthUtils.createEarthLeggings(), inv, loc);
                                return;
                            }

                        }
                        if (centre.getType() == Material.IRON_BOOTS) {
                            if (surroundedin(inv, Material.FEATHER)) {
                                craft(AirUtils.createAirBoots(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.NETHERRACK)) {
                                craft(FireUtils.createFireBoots(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.WATER_BUCKET)) {
                                craft(WaterUtils.createWaterBoots(), inv, loc);
                                return;
                            }
                            if (surroundedin(inv, Material.STONE)) {
                                craft(EarthUtils.createEarthBoots(), inv, loc);
                                return;
                            }
                        }
                        if (surroundedin(inv, Material.RABBIT_HIDE)) {
                            if (AirUtils.createAirHelmet().equals(centre)) {
                                craft(SkyUtils.createSkyHelmet(), inv, loc);
                                return;
                            }
                            if (AirUtils.createAirChestplate().equals(centre)) {
                                craft(SkyUtils.createSkyChestplate(), inv, loc);
                                return;
                            }
                            if (AirUtils.createAirLeggings().equals(centre)) {
                                craft(SkyUtils.createSkyLeggings(), inv, loc);
                                return;
                            }
                            if (AirUtils.createAirBoots().equals(centre)) {
                                craft(SkyUtils.createSkyBoots(), inv, loc);
                                return;
                            }
                        }
                        if (surroundedin(inv, Material.IRON_ORE)) {
                            if (EarthUtils.createEarthHelmet().equals(centre)) {
                                craft(MetalUtils.createMetalHelmet(), inv, loc);
                                return;
                            }
                            if (EarthUtils.createEarthChestplate().equals(centre)) {
                                craft(MetalUtils.createMetalChestplate(), inv, loc);
                                return;
                            }
                            if (EarthUtils.createEarthLeggings().equals(centre)) {
                                craft(MetalUtils.createMetalLeggings(), inv, loc);
                                return;
                            }
                            if (EarthUtils.createEarthBoots().equals(centre)) {
                                craft(MetalUtils.createMetalBoots(), inv, loc);
                                return;
                            }
                        }
                        if (surroundedin(inv, Material.LAVA_BUCKET)) {
                            if (FireUtils.createFireHelmet().equals(centre)) {
                                craft(LavaUtils.createLavaHelmet(), inv, loc);
                                return;
                            }
                            if (FireUtils.createFireChestplate().equals(centre)) {
                                craft(LavaUtils.createLavaChestplate(), inv, loc);
                                return;
                            }
                            if (FireUtils.createFireLeggings().equals(centre)) {
                                craft(LavaUtils.createLavaLeggings(), inv, loc);
                                return;
                            }
                            if (FireUtils.createFireBoots().equals(centre)) {
                                craft(LavaUtils.createLavaBoots(), inv, loc);
                                return;
                            }
                        }
                        if (surroundedin(inv, Material.ICE)) {
                            if (WaterUtils.createWaterHelmet().equals(centre)) {
                                craft(IceUtils.createIceHelmet(), inv, loc);
                                return;
                            }
                            if (WaterUtils.createWaterChestplate().equals(centre)) {
                                craft(IceUtils.createIceChestplate(), inv, loc);
                                return;
                            }
                            if (WaterUtils.createWaterLeggings().equals(centre)) {
                                craft(IceUtils.createIceLeggings(), inv, loc);
                                return;
                            }
                            if (WaterUtils.createWaterBoots().equals(centre)) {
                                craft(IceUtils.createIceBoots(), inv, loc);
                                return;
                            }
                        }
                    }

                    if (new ItemStack(Material.BOW).equals(centre)) {
                        if (new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(0)) && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(1))
                                && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(2)) && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(3))
                                && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(5)) && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(6))
                                && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(7)) && new ItemStack(Material.ENDER_PEARL).equals(inv.getItem(8))) {

                            loc.getWorld().playSound(loc, Sound.ENTITY_GHAST_SHOOT, 1, 1);
                            inv.clear();

                            inv.setItem(4, ArmourUtil.createWarpBow());
                        }
                    }
                } else {
                    it.remove();
                    Plugin.saveAll(plugin);
                }
            }
        }
    }

    public static ItemStack getCustomItem(Material m, String name) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getCustomItem(Material m, String name, List<String> lore) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ShapelessRecipe airBootsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(AirUtils.createAirBoots());

        recipie.addIngredient(Material.IRON_BOOTS);
        recipie.addIngredient(Material.FEATHER);

        return recipie;
    }

    public static ShapelessRecipe airLeggingsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(AirUtils.createAirLeggings());

        recipie.addIngredient(Material.IRON_LEGGINGS);
        recipie.addIngredient(Material.FEATHER);

        return recipie;
    }

    public static ShapelessRecipe airChestplateRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(AirUtils.createAirChestplate());

        recipie.addIngredient(Material.IRON_CHESTPLATE);
        recipie.addIngredient(Material.FEATHER);

        return recipie;
    }

    public static ShapelessRecipe airHelmetRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(AirUtils.createAirHelmet());

        recipie.addIngredient(Material.IRON_HELMET);
        recipie.addIngredient(Material.FEATHER);

        return recipie;
    }

    public static ShapelessRecipe fireBootsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(FireUtils.createFireBoots());

        recipie.addIngredient(Material.IRON_BOOTS);
        recipie.addIngredient(Material.LAVA_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe fireLeggingsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(FireUtils.createFireLeggings());

        recipie.addIngredient(Material.IRON_LEGGINGS);
        recipie.addIngredient(Material.LAVA_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe fireChestplateRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(FireUtils.createFireChestplate());

        recipie.addIngredient(Material.IRON_CHESTPLATE);
        recipie.addIngredient(Material.LAVA_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe fireHelmetRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(FireUtils.createFireHelmet());

        recipie.addIngredient(Material.IRON_HELMET);
        recipie.addIngredient(Material.LAVA_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe earthBootsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(EarthUtils.createEarthBoots());

        recipie.addIngredient(Material.IRON_BOOTS);
        recipie.addIngredient(Material.STONE);

        return recipie;
    }

    public static ShapelessRecipe earthLeggingsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(EarthUtils.createEarthLeggings());

        recipie.addIngredient(Material.IRON_LEGGINGS);
        recipie.addIngredient(Material.STONE);

        return recipie;
    }

    public static ShapelessRecipe earthChestplateRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(EarthUtils.createEarthChestplate());

        recipie.addIngredient(Material.IRON_CHESTPLATE);
        recipie.addIngredient(Material.STONE);

        return recipie;
    }

    public static ShapelessRecipe earthHelmetRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(EarthUtils.createEarthHelmet());

        recipie.addIngredient(Material.IRON_HELMET);
        recipie.addIngredient(Material.STONE);

        return recipie;
    }

    public static ShapelessRecipe waterBootsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(WaterUtils.createWaterBoots());

        recipie.addIngredient(Material.IRON_BOOTS);
        recipie.addIngredient(Material.WATER_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe waterLeggingsRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(WaterUtils.createWaterLeggings());

        recipie.addIngredient(Material.IRON_LEGGINGS);
        recipie.addIngredient(Material.WATER_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe waterChestplateRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(WaterUtils.createWaterChestplate());

        recipie.addIngredient(Material.IRON_CHESTPLATE);
        recipie.addIngredient(Material.WATER_BUCKET);

        return recipie;
    }

    public static ShapelessRecipe waterHelmetRecipe() {

        ShapelessRecipe recipie = new ShapelessRecipe(WaterUtils.createWaterHelmet());

        recipie.addIngredient(Material.IRON_HELMET);
        recipie.addIngredient(Material.WATER_BUCKET);

        return recipie;
    }

}
