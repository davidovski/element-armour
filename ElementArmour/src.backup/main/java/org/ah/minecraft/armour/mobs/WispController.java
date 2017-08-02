package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class WispController extends CustomEntityController {
    public WispController(int weight) {
        super(weight);
    }

    public long a = 0L;

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() < 20;
    }

    @Override
    public LivingEntity spawn(Location loc) {
        if (loc.getY() < 64.0D) {
            Bat bat = (Bat) loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.BAT);
            bat.setCustomName("Wisp");
            bat.setMaxHealth(30.0D);
            bat.setHealth(30.0D);
            byte blockData = 0;
            FallingBlock b = bat.getWorld().spawnFallingBlock(bat.getLocation().add(0.0D, 1.62D, 0.0D), Material.GLOWSTONE, blockData);
            b.setGlowing(true);
            b.setGravity(false);
            bat.setPassenger(b);
            return bat;
        }
        return null;
    }

    @Override
    public void update(LivingEntity e) {
        a += 1L;
        if (a % 4L == 0L) {
            e.getWorld().spawnArrow(e.getLocation().add(0.0D, -0.5D, 0.0D), new org.bukkit.util.Vector(0, -1, 0), 1.0F, 1.0F);
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Wisp".equalsIgnoreCase(e.getCustomName())) && ((e instanceof Bat));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.RABBIT_HIDE, 2, "Bat Wings"));
        return drops;
    }
}
