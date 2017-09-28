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
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class HorseRiderController extends CustomEntityController {
    public HorseRiderController(int weight) {
        super(weight);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton s = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
        EntityEquipment skel = s.getEquipment();
        skel.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        skel.setHelmet(ArmourUtil.createWariorHelmet());
        skel.setItemInMainHand(new ItemStack(Material.STONE_SWORD));
        skel.setItemInOffHand(new ItemStack(Material.LEASH));
        s.setCustomName("Rider");
        s.setCustomNameVisible(false);
        MobUtils.setMaxHealth(s, 30);

        SkeletonHorse h = (SkeletonHorse) loc.getWorld().spawnEntity(loc, EntityType.SKELETON_HORSE);
        h.addPassenger(s);
        h.setTamed(true);
        h.setRemoveWhenFarAway(true);
        return s;
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() > 60;
    }

    @Override
    public void update(LivingEntity e) {
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Rider".equals(e.getCustomName())) && ((e instanceof org.bukkit.entity.Skeleton));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.BONE, 4));
        drops.add(DropGenerator.i(Material.LEASH, 1));
        if (Math.random() > 0.5D) {
            drops.add(ArmourUtil.createWariorHelmet());
        }
        return drops;
    }
}
