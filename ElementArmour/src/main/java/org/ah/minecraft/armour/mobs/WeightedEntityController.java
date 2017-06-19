package org.ah.minecraft.armour.mobs;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public abstract class WeightedEntityController implements EntityTypeController {
    private int weight = 0;

    public WeightedEntityController(int weight) {
        this.weight = weight;
    }

    @Override
    public void checkAndUpdate(LivingEntity e) {
        if (isOne(e)) {
            update(e);
        }
    }


    @Override
    public int getWeight() {
        return weight;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean canSpawnThere(Block b) {
        return true;
    }


}
