package org.ah.minecraft.armour.mobs;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

public class MobUtils {
    private MobUtils() {}
    public static void setMaxHealth(LivingEntity e, double amount) {
        e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
    }
}
