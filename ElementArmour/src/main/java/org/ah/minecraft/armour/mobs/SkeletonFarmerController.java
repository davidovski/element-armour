package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class SkeletonFarmerController extends CustomEntityController {

    public SkeletonFarmerController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);

        EntityEquipment eqip = skeleton.getEquipment();

        ItemStack helmet = ArmourUtil.createWariorHelmet();
        eqip.setHelmet(helmet);
        eqip.setHelmetDropChance(1f);

        eqip.setItemInHand(new ItemStack(Material.IRON_HOE));
        eqip.setItemInHandDropChance(1f);
        skeleton.setCustomName("Skeleton Farmer");
        skeleton.setCustomNameVisible(false);
        return skeleton;
    }

    @Override
    public void update(LivingEntity e) {
        Block bl = e.getLocation().add(0, -1, 0).getBlock();
        if (bl.getType() == Material.GRASS || bl.getType() == Material.DIRT) {
            bl.setType(Material.SOIL);
        }
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() > 63 && b.getY() < 65;
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Skeleton Farmer".equalsIgnoreCase(e.getCustomName()) && e instanceof Skeleton;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.SEEDS, 8));
        drops.add(DropGenerator.i(Material.WOOD_HOE, 3, "Farmer's Hoe"));
        return drops;
    }

}
