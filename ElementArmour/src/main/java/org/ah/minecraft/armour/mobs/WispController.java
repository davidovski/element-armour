package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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
            MobUtils.setMaxHealth(bat, 30);
            bat.setHealth(30.0D);
            bat.setAwake(true);
            bat.setGlowing(true);
            bat.setSilent(true);
            return bat;
        }
        return null;
    }

    @Override
    public void update(LivingEntity e) {
        a += 1L;
        if (a % 4L == 0L) {
            if (e.getTicksLived() % 30 == 0) {
                e.getWorld().playSound(e.getLocation(), Sound.ENTITY_ITEMFRAME_REMOVE_ITEM, 1f, 1f);
            }
            Arrow ar = (Arrow) e.getWorld().spawnEntity((e.getLocation().add(0.0D, -0.5D, 0.0D)), EntityType.ARROW);
            ar.setVelocity(new Vector(0, -1, 0));
            ar.setShooter(e);
            ar.setGlowing(true);
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Wisp".equalsIgnoreCase(e.getCustomName())) && ((e instanceof Bat));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(ArmourUtil.createLightEssence(), 3));
        return drops;
    }
}
