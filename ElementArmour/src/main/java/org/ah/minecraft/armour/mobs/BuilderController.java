package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
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
        // TODO Auto-generated constructor stub
    }

    @Override
    public LivingEntity spawn(Location loc) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(LivingEntity e) {
        Material blocktype = Material.COBBLESTONE;
        Zombie zomb = (Zombie) e;
        Block below = e.getLocation().add(0, -1, 0).getBlock();
        Block belowfront = e.getLocation().add(0, -1, 0).add(zomb.getLocation().getDirection().normalize().setY(0)).getBlock();

        if (below.getType() == Material.AIR) {
            if (zomb.getTarget() != null && !(zomb.getTarget().getLocation().getY() < zomb.getLocation().getY())) {
                below.setType(blocktype);
                zomb.getEquipment().setItemInHand(new ItemStack(blocktype));

            }
        }

        if (belowfront.getType() == Material.AIR) {
            if (zomb.getTarget() != null && !(zomb.getTarget().getLocation().getY() < zomb.getLocation().getY())) {
                belowfront.setType(blocktype);
                zomb.getEquipment().setItemInHand(new ItemStack(blocktype));
            }
        }
        Block infront = zomb.getLocation().add(zomb.getLocation().getDirection().normalize().setY(0)).getBlock();
        Block infront2 = infront.getLocation().add(0, 1, 0).getBlock();
        if (infront.getType() != Material.AIR) {
            infront.setType(Material.AIR);
            zomb.getEquipment().setItemInHand(new ItemStack(Material.IRON_PICKAXE));
        }
        if (infront2.getType() != Material.AIR) {
            infront2.setType(Material.AIR);
            zomb.getEquipment().setItemInHand(new ItemStack(Material.IRON_PICKAXE));
        }

        if (zomb.getTarget() != null && (zomb.getTarget().getLocation().getY() - 1 >= zomb.getLocation().getY())) {
            if (zomb.isOnGround()) {
                zomb.setVelocity(new Vector(0, 0.6f, 0));
                below.setType(blocktype);
            }
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Builder".equalsIgnoreCase(e.getCustomName());
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.COBBLESTONE, 64));
        drops.add(DropGenerator.i(Material.COBBLESTONE, 64));
        drops.add(DropGenerator.i(Material.COBBLESTONE, 64));

        drops.add(DropGenerator.i(Material.IRON_PICKAXE));
        return drops;
    }

}
