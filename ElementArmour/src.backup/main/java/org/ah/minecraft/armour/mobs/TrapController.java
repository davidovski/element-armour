package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TrapController extends CustomEntityController {
    public TrapController(int weight) {
        super(weight);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setCollidable(false);
        as.setVisible(false);
        as.setGravity(false);
        as.setCustomName("Trap");
        return as;
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return (b.getY() > 60) && (b.getBiome() == Biome.ROOFED_FOREST);
    }

    @Override
    public void update(LivingEntity e) {
        if (Math.random() > 0.8999999761581421D) {
            e.getWorld().playEffect(e.getLocation(), org.bukkit.Effect.SMOKE, 0);
        }
        for (Player p : e.getWorld().getPlayers()) {
            if (p.getLocation().distanceSquared(e.getLocation()) < 1.0D) {
                EvokerFangs f = (EvokerFangs) e.getWorld().spawnEntity(e.getLocation(), EntityType.EVOKER_FANGS);
                f.setOwner(e);
                f.setTicksLived(5);
                e.remove();
            }
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Trap".equals(e.getCustomName())) && ((e instanceof ArmorStand));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        return drops;
    }
}
