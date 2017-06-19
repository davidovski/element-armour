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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MinerController extends CustomEntityController {

    public MinerController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(LivingEntity e) {
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getBiome() == Biome.EXTREME_HILLS || b.getY() < 50;
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Miner".equals(e.getCustomName());
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
        EntityEquipment skell = skeleton.getEquipment();
        skell.setHelmet(new ItemStack(Material.GOLD_HELMET));
        skell.setItemInMainHand(new ItemStack(Material.IRON_PICKAXE));
        skell.setItemInOffHand(new ItemStack(Material.TORCH));

        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000, 0, false));
        MobUtils.setMaxHealth(skeleton, 30);
        skeleton.setHealth(30D);
        skeleton.setCustomName("Miner");
        skeleton.setCustomNameVisible(false);
        return skeleton;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.IRON_INGOT, 2));
        drops.add(DropGenerator.i(Material.GOLD_INGOT, 2));

        drops.add(DropGenerator.i(Material.IRON_ORE, 4));
        drops.add(DropGenerator.i(Material.GOLD_ORE, 4));
        return drops;
    }

}
