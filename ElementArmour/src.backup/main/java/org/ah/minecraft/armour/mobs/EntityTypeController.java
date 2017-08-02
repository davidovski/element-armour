package org.ah.minecraft.armour.mobs;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public abstract interface EntityTypeController {
    public abstract void checkAndUpdate(LivingEntity paramLivingEntity);

    public abstract void update(LivingEntity paramLivingEntity);

    public abstract boolean isOne(LivingEntity paramLivingEntity);

    public abstract int getWeight();

    public abstract LivingEntity spawn(Location paramLocation);

    public abstract List<ItemStack> getDrops();
}
