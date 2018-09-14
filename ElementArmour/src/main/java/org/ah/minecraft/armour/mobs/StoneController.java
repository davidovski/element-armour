package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StoneController extends CustomEntityController {
    private PotionEffect potionEffect;

    public StoneController(int weight) {
        super(weight);
        potionEffect = new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 100000, true, false);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        z.setSilent(true);
        z.setCustomName("Stone");
        z.addPotionEffect(potionEffect, true);
        z.setCustomNameVisible(false);
        z.setBaby(true);

        FallingBlock b = loc.getWorld().spawnFallingBlock(loc, new MaterialData(Material.STONE));
        z.addPassenger(b);
        b.setCustomName("r");
        b.setDropItem(false);
        b.setGravity(false);
        return z;
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getRelative(BlockFace.DOWN).getType() == Material.STONE;
    }

    @Override
    public void update(LivingEntity e) {
        e.addPotionEffect(potionEffect, true);
        if (e.getLocation().getPitch() % 90 != 0) {
            Location location = e.getLocation();
            location.setPitch(Math.round(location.getPitch() /90) * 90);
            e.teleport(location);
        }

        e.setFallDistance(0);
        e.setCustomNameVisible(false);
//        for (Entity el : e.getNearbyEntities(10, 10, 10)) {
//            if (el instanceof Player) {
//                Player p = (Player) el;
//                if (e.getTicksLived() % 10 == 0) {
//                    if (e.hasLineOfSight(p) && e.isOnGround() && (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)) {
//                        Vector normalize = p.getEyeLocation().subtract(e.getLocation()).toVector().normalize().multiply(1);
//                        e.setVelocity(normalize);
//                        ((Pig) e).setTarget(p);
//                    }
//                }
//                if (p.getLocation().distanceSquared(e.getLocation()) < 2) {
//                    if (!p.isBlocking()) {
//                        p.damage(0.5D, e);
//                    } else {
//                        p.setVelocity(new Vector(0, 0, 0));
//                    }
//                }
//            }
//        }

    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Stone".equals(e.getCustomName())) && ((e instanceof Zombie));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.STONE));
        return drops;
    }
}
