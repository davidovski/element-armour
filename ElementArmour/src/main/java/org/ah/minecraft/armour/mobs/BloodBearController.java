package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.MoonPhases;
import org.ah.minecraft.armour.Plugin;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PolarBear;
import org.bukkit.inventory.ItemStack;

public class BloodBearController extends CustomEntityController {

    public BloodBearController(int weight) {
        super(weight);
    }

    @Override
    public void update(LivingEntity e) {
        e.playEffect(EntityEffect.HURT);
        if (Plugin.day(e.getWorld()) || Plugin.getMoonPhase(e.getWorld()) != MoonPhases.NEW) {
            e.getWorld().playEffect(e.getLocation(), Effect.STEP_SOUND, Material.WEB, 10);
            e.remove();
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return e instanceof PolarBear && "Blood Bear".equals(e.getCustomName());
    }

    @Override
    public LivingEntity spawn(Location loc) {
        PolarBear b = (PolarBear)loc.getWorld().spawnEntity(loc, EntityType.POLAR_BEAR);
        b.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
        MobUtils.setMaxHealth(b, 40);
        b.setCustomName("Blood Bear");
        return  b;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        return drops;
    }
}
