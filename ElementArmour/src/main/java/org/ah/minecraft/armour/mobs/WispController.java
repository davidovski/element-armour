package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class WispController extends CustomEntityController {
    public WispController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    public long a = 0;

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() < 20;
    }

    @Override
    public LivingEntity spawn(Location loc) {
        if (loc.getY() < 64) {
            Bat bat = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
            bat.setCustomName("Wisp");
            bat.setMaxHealth(30D);
            bat.setHealth(30D);
            byte blockData = 0;
            FallingBlock b = bat.getWorld().spawnFallingBlock(bat.getLocation().add(0, 1.62, 0), Material.GLOWSTONE, blockData);
            b.setGlowing(true);
            b.setGravity(false);
            bat.setPassenger(b);
            return bat;
        } else {
            return null;
        }
    }

    @Override
    public void update(LivingEntity e) {
        a++;
        if (a % 4 == 0) {
            e.getWorld().spawnArrow(e.getLocation().add(0, -0.5D, 0), new Vector(0, -1, 0), 1f, 1f);
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Wisp".equalsIgnoreCase(e.getCustomName()) && e instanceof Bat;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.RABBIT_HIDE, 2, "Bat Wings"));
        return drops;
    }

}
