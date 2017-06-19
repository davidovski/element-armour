package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class KnightController extends CustomEntityController {

    public KnightController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton skeleton = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
        EntityEquipment eqip = skeleton.getEquipment();
        eqip.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
        eqip.setBootsDropChance(1f);
        eqip.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        eqip.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        eqip.setHelmet(new ItemStack(Material.FURNACE));
        eqip.setItemInMainHand(new ItemStack(Material.STONE_SWORD));
        eqip.setItemInOffHand(new ItemStack(Material.SHIELD));
        skeleton.setCustomName("Knight");
        return skeleton;
    }

    @Override
    public void update(LivingEntity e) {
    }


    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() > 60;
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Knight".equals(e.getCustomName()) && e instanceof Skeleton;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.SHIELD, 1));
        drops.add(DropGenerator.i(Material.FURNACE, 1));
        drops.add(DropGenerator.i(Material.IRON_INGOT, 1));
        drops.add(DropGenerator.i(Material.STONE_SWORD, 1));
        return drops;
    }
}
