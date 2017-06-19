package org.ah.minecraft.tweaks;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class BonesInMobs extends Tweak {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.PIG
                || event.getEntityType() == EntityType.SHEEP
                || event.getEntityType() == EntityType.CHICKEN
                || event.getEntityType() == EntityType.WOLF
                || event.getEntityType() == EntityType.COW
                || event.getEntityType() == EntityType.MUSHROOM_COW
                || event.getEntityType() == EntityType.RABBIT
                || event.getEntityType() == EntityType.HORSE
                || event.getEntityType() == EntityType.VILLAGER
                || event.getEntityType() == EntityType.POLAR_BEAR
                || event.getEntityType() == EntityType.WITCH
                || event.getEntityType() == EntityType.VINDICATOR
                || event.getEntityType() == EntityType.HORSE
                || event.getEntityType() == EntityType.DONKEY
                || event.getEntityType() == EntityType.MULE
                || event.getEntityType() == EntityType.OCELOT
                || event.getEntityType() == EntityType.EVOKER
                || event.getEntityType() == EntityType.LLAMA) {
            if (Math.random() < 0.5) {
                event.getDrops().add(new ItemStack(Material.BONE));
            }
        }
    }
}
