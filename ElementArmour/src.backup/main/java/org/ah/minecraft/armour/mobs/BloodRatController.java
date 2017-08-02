package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.MoonPhases;
import org.ah.minecraft.armour.Plugin;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class BloodRatController extends CustomEntityController {
    public BloodRatController(int weight) {
        super(weight);
    }

    @Override
    public void update(LivingEntity e) {
        e.playEffect(EntityEffect.HURT);
        if ((Plugin.day(e.getWorld())) || (Plugin.getMoonPhase(e.getWorld()) != MoonPhases.NEW)) {
            e.getWorld().playEffect(e.getLocation(), org.bukkit.Effect.STEP_SOUND, Material.WEB, 10);
            e.remove();
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ((e instanceof Bat)) && ("Blood Bat".equals(e.getCustomName()));
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Bat b = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        b.setCustomName("Blood Bat");
        return b;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        return drops;
    }
}
