package org.ah.minecraft.armour;

import java.util.Iterator;
import java.util.List;

import org.ah.minecraft.armour.utils.AirUtils;
import org.ah.minecraft.armour.utils.ArmourUtil;
import org.ah.minecraft.armour.utils.EarthUtils;
import org.ah.minecraft.armour.utils.FireUtils;
import org.ah.minecraft.armour.utils.FreezeUtils;
import org.ah.minecraft.armour.utils.IceUtils;
import org.ah.minecraft.armour.utils.LavaUtils;
import org.ah.minecraft.armour.utils.LightningUtils;
import org.ah.minecraft.armour.utils.MetalUtils;
import org.ah.minecraft.armour.utils.ObsidianUtils;
import org.ah.minecraft.armour.utils.PoisonUtils;
import org.ah.minecraft.armour.utils.RainUtils;
import org.ah.minecraft.armour.utils.SandUtils;
import org.ah.minecraft.armour.utils.SkyUtils;
import org.ah.minecraft.armour.utils.WaterUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CombiningUtil {
    private static Location add;
    public static long alpha = 0L;

    private CombiningUtil() {
    }

    public static void crafterCheck(Plugin plugin) {
        alpha += 1L;
        Iterator<String> it = plugin.combinerLocations.iterator();

        while (it.hasNext()) {
            String locationString = it.next();

            Location cauldronLoc = LocationUtil.stringToLocation(plugin.getServer(), locationString);

            if (cauldronLoc.getWorld() != null) {
                cauldronLoc = LocationUtil.stringToLocation(plugin.getServer(), locationString);

                cauldronLoc = LocationUtil.stringToLocation(plugin.getServer(), locationString);
                Location dispenser1Loc = cauldronLoc.clone().add(1.0D, 1.0D, 0.0D);
                Location dispenser2Loc = cauldronLoc.clone().add(-1.0D, 1.0D, 0.0D);

                if ((cauldronLoc.getBlock().getType() == Material.CAULDRON) && (dispenser1Loc.getBlock().getType() == Material.DISPENSER)
                        && (dispenser2Loc.getBlock().getType() == Material.DISPENSER)) {
                    for (Entity e : Plugin.getNearbyEntities(cauldronLoc, 2)) {
                        if ((e instanceof LivingEntity)) {
                            ((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 2));
                        }
                    }

                    Inventory inv1 = ((Dispenser) dispenser1Loc.getBlock().getState()).getInventory();
                    Inventory inv2 = ((Dispenser) dispenser2Loc.getBlock().getState()).getInventory();
                    ItemStack item1 = null;
                    for (ItemStack itemStack : inv1) {
                        if (itemStack != null) {
                            item1 = itemStack;
                        }
                    }

                    ItemStack item2 = null;
                    for (ItemStack itemStack : inv2) {
                        if (itemStack != null) {
                            item2 = itemStack;
                        }
                    }

                    if ((item1 != null) && (item2 != null)) {
                        ItemStack result = null;

                        cauldronLoc.getWorld().playEffect(cauldronLoc, Effect.STEP_SOUND, Material.WATER, 2);

                        cauldronLoc.getWorld().playEffect(cauldronLoc, Effect.STEP_SOUND, Material.GREEN_SHULKER_BOX, 2);

                        Block b = LocationUtil.stringToLocation(plugin.getServer(), locationString).add(0.0D, -1.0D, -1.0D).getBlock();
                        Block b2 = LocationUtil.stringToLocation(plugin.getServer(), locationString).add(0.0D, -1.0D, 1.0D).getBlock();

                        if ((b.isBlockPowered()) || (b2.isBlockPowered())) {
                            if (ArmourUtil.compare(item1, IceUtils.createIceBoots())) {
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaBoots())) {
                                    result = PoisonUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2,MetalUtils.createMetalBoots())) {
                                    result = FreezeUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyBoots())) {
                                    result = RainUtils.createBoots();
                                }
                            } else if (ArmourUtil.compare(item1, LavaUtils.createLavaBoots())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceBoots())) {
                                    result = PoisonUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalBoots())) {
                                    result = ObsidianUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyBoots())) {
                                    result = LightningUtils.createBoots();
                                }
                            } else if (ArmourUtil.compare(item1, SkyUtils.createSkyBoots())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceBoots())) {
                                    result = RainUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalBoots())) {
                                    result = SandUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaBoots())) {
                                    result = LightningUtils.createBoots();
                                }
                            } else if (ArmourUtil.compare(item1, MetalUtils.createMetalBoots())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceBoots())) {
                                    result = FreezeUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyBoots())) {
                                    result = SandUtils.createBoots();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaBoots())) {
                                    result = ObsidianUtils.createBoots();
                                }
                            }
                            if (ArmourUtil.compare(item1, IceUtils.createIceLeggings())) {
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaLeggings())) {
                                    result = PoisonUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalLeggings())) {
                                    result = FreezeUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyLeggings())) {
                                    result = RainUtils.createLeggings();
                                }
                            } else if (ArmourUtil.compare(item1, LavaUtils.createLavaLeggings())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceLeggings())) {
                                    result = PoisonUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalLeggings())) {
                                    result = ObsidianUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyLeggings())) {
                                    result = LightningUtils.createLeggings();
                                }
                            } else if (ArmourUtil.compare(item1, SkyUtils.createSkyLeggings())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceLeggings())) {
                                    result = RainUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalLeggings())) {
                                    result = SandUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaLeggings())) {
                                    result = LightningUtils.createLeggings();
                                }
                            } else if (ArmourUtil.compare(item1, MetalUtils.createMetalLeggings())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceLeggings())) {
                                    result = FreezeUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyLeggings())) {
                                    result = SandUtils.createLeggings();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaLeggings())) {
                                    result = ObsidianUtils.createLeggings();
                                }
                            }
                            if (ArmourUtil.compare(item1, IceUtils.createIceChestplate())) {
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaChestplate())) {
                                    result = PoisonUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalChestplate())) {
                                    result = FreezeUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyChestplate())) {
                                    result = RainUtils.createChestplate();
                                }
                            } else if (ArmourUtil.compare(item1, LavaUtils.createLavaChestplate())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceChestplate())) {
                                    result = PoisonUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalChestplate())) {
                                    result = ObsidianUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyChestplate())) {
                                    result = LightningUtils.createChestplate();
                                }
                            } else if (ArmourUtil.compare(item1, SkyUtils.createSkyChestplate())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceChestplate())) {
                                    result = RainUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalChestplate())) {
                                    result = SandUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaChestplate())) {
                                    result = LightningUtils.createChestplate();
                                }
                            } else if (ArmourUtil.compare(item1, MetalUtils.createMetalChestplate())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceChestplate())) {
                                    result = FreezeUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyChestplate())) {
                                    result = SandUtils.createChestplate();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaChestplate())) {
                                    result = ObsidianUtils.createChestplate();
                                }
                            }
                            if (ArmourUtil.compare(item1, IceUtils.createIceHelmet())) {
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaHelmet())) {
                                    result = PoisonUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalHelmet())) {
                                    result = FreezeUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyHelmet())) {
                                    result = RainUtils.createHelmet();
                                }
                            } else if (ArmourUtil.compare(item1, LavaUtils.createLavaHelmet())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceHelmet())) {
                                    result = PoisonUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalHelmet())) {
                                    result = ObsidianUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyHelmet())) {
                                    result = LightningUtils.createHelmet();
                                }
                            } else if (ArmourUtil.compare(item1, SkyUtils.createSkyHelmet())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceHelmet())) {
                                    result = RainUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, MetalUtils.createMetalHelmet())) {
                                    result = SandUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaHelmet())) {
                                    result = LightningUtils.createHelmet();
                                }
                            } else if (ArmourUtil.compare(item1, MetalUtils.createMetalHelmet())) {
                                if (ArmourUtil.compare(item2, IceUtils.createIceHelmet())) {
                                    result = FreezeUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, SkyUtils.createSkyHelmet())) {
                                    result = SandUtils.createHelmet();
                                }
                                if (ArmourUtil.compare(item2, LavaUtils.createLavaHelmet())) {
                                    result = ObsidianUtils.createHelmet();
                                }
                            }

                            if (result != null) {
                                System.out.println("made");
                                inv1.clear();
                                inv2.clear();
                                cauldronLoc.getWorld().dropItem(cauldronLoc.add(0.0D, 1.0D, 0.0D), result);
                                cauldronLoc.getBlock().setData((byte) (cauldronLoc.getBlock().getData() - 1));

                                cauldronLoc.getWorld().playEffect(dispenser1Loc, Effect.STEP_SOUND, Material.GLOWSTONE, 3);
                                cauldronLoc.getWorld().playEffect(dispenser2Loc, Effect.STEP_SOUND, Material.GLOWSTONE, 3);
                            }
                        }
                    }
                } else {
                    it.remove();
                    plugin.saveAll(plugin);
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
