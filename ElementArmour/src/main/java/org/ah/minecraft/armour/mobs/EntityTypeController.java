package org.ah.minecraft.armour.mobs;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface EntityTypeController {
    void checkAndUpdate(LivingEntity e);
    void update(LivingEntity e);
    boolean isOne(LivingEntity e);
    int getWeight();
    LivingEntity spawn(Location loc);
    List<ItemStack> getDrops();
}
