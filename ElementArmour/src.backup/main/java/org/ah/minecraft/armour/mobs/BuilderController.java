package org.ah.minecraft.armour.mobs;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BuilderController extends CustomEntityController {
    public BuilderController(int weight) {
        super(weight);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        return null;
    }

    @Override
    public void update(LivingEntity e) {
        Material blocktype = Material.COBBLESTONE;
        Zombie zomb = (Zombie) e;
        Block below = e.getLocation().add(0.0D, -1.0D, 0.0D).getBlock();
        Block belowfront = e.getLocation().add(0.0D, -1.0D, 0.0D).add(zomb.getLocation().getDirection().normalize().setY(0)).getBlock();

        if ((below.getType() == Material.AIR) && (zomb.getTarget() != null) && (zomb.getTarget().getLocation().getY() >= zomb.getLocation().getY())) {
            below.setType(blocktype);
            zomb.getEquipment().setItemInHand(new ItemStack(blocktype));
        }

        if ((belowfront.getType() == Material.AIR) && (zomb.getTarget() != null) && (zomb.getTarget().getLocation().getY() >= zomb.getLocation().getY())) {
            belowfront.setType(blocktype);
            zomb.getEquipment().setItemInHand(new ItemStack(blocktype));
        }

        Block infront = zomb.getLocation().add(zomb.getLocation().getDirection().normalize().setY(0)).getBlock();
        Block infront2 = infront.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
        if (infront.getType() != Material.AIR) {
            infront.setType(Material.AIR);
            zomb.getEquipment().setItemInHand(new ItemStack(Material.IRON_PICKAXE));
        }
        if (infront2.getType() != Material.AIR) {
            infront2.setType(Material.AIR);
            zomb.getEquipment().setItemInHand(new ItemStack(Material.IRON_PICKAXE));
        }

        if ((zomb.getTarget() != null) && (zomb.getTarget().getLocation().getY() - 1.0D >= zomb.getLocation().getY()) && (zomb.isOnGround())) {
            zomb.setVelocity(new Vector(0.0F, 0.6F, 0.0F));
            below.setType(blocktype);
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Builder".equalsIgnoreCase(e.getCustomName());
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new java.util.ArrayList();
        drops.add(DropGenerator.i(Material.COBBLESTONE, 64));
        drops.add(DropGenerator.i(Material.COBBLESTONE, 64));
        drops.add(DropGenerator.i(Material.COBBLESTONE, 64));

        drops.add(DropGenerator.i(Material.IRON_PICKAXE));
        return drops;
    }
}
