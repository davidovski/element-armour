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
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class RedGuardianController extends CustomEntityController {

    public RedGuardianController(int weight) {
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
        return e instanceof Guardian && "Red Guardian".equals(e.getCustomName());
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Guardian b = (Guardian)loc.getWorld().spawnEntity(loc, EntityType.GUARDIAN);
        b.setCustomName("Red Guardian");
        b.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(20);
        b.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);
        return  b;
    }

    @Override
    public boolean canSpawnThere(Block b) {
        if (b.getType() == Material.STATIONARY_WATER) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        return drops;
    }
}
