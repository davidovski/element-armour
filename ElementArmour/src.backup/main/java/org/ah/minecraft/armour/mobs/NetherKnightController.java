package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class NetherKnightController extends CustomEntityController {
    public NetherKnightController(int weight) {
        super(weight);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
        EntityEquipment eqip = skeleton.getEquipment();
        eqip.setBoots(new ItemStack(Material.IRON_BOOTS));
        eqip.setBootsDropChance(1.0F);
        eqip.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
        eqip.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
        eqip.setHelmet(new ItemStack(Material.FURNACE));
        eqip.setItemInMainHand(new ItemStack(Material.GOLD_SWORD));
        eqip.setItemInOffHand(new ItemStack(Material.SHIELD));
        skeleton.setCustomName("Nether Knight");
        return skeleton;
    }

    @Override
    public void update(LivingEntity e) {
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Nether Kinght".equals(e.getCustomName())) && ((e instanceof Skeleton));
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getBiome() == Biome.HELL;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.SHIELD, 1));
        drops.add(DropGenerator.i(Material.FURNACE, 1));
        drops.add(DropGenerator.i(Material.GOLD_INGOT, 1));
        drops.add(DropGenerator.i(Material.GOLD_SWORD, 1));
        return drops;
    }
}
