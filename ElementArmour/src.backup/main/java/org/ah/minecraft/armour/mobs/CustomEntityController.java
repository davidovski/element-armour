package org.ah.minecraft.armour.mobs;

import org.bukkit.entity.LivingEntity;

public abstract class CustomEntityController extends WeightedEntityController {
    public CustomEntityController(int weight) {
        super(weight);
    }

    @Override
    public void checkAndUpdate(LivingEntity e) {
        if (isOne(e)) {
            update(e);
        }
    }
}
