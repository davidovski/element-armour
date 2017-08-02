package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class BouncerController extends CustomEntityController {
    public BouncerController(int weight) {
        super(weight);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        EntityEquipment zomb = z.getEquipment();
        zomb.setBoots(ArmourUtil.createBouncerBoots());
        zomb.setBootsDropChance(1.0F);
        zomb.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        zomb.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        zomb.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        zomb.setItemInHand(new ItemStack(Material.STONE_HOE));
        z.setCustomName("Bouncer");
        z.setCustomNameVisible(false);
        return z;
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() > 60;
    }

    @Override
    public void update(LivingEntity e) {
        if (e.isOnGround()) {
            e.setVelocity(e.getLocation().getDirection().setY(1.0F));
            e.getWorld().playSound(e.getLocation(), org.bukkit.Sound.ENTITY_SLIME_ATTACK, 1.0F, 1.0F);
            for (Entity n : e.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
                n.setFallDistance(10.0F);
                if (n.getType() == EntityType.ARROW) {
                    ((Arrow) n).setBounce(true);
                }
            }
        }
        e.setFallDistance(0.0F);
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Bouncer".equals(e.getCustomName())) && ((e instanceof Zombie));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.SLIME_BALL, 4));
        drops.add(DropGenerator.i(Material.IRON_INGOT, 4));
        if (Math.random() > 0.5D) {
            drops.add(ArmourUtil.createBouncerBoots());
        }
        return drops;
    }
}
