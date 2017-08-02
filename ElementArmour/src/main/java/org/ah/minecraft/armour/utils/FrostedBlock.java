package org.ah.minecraft.armour.utils;

import org.ah.minecraft.armour.Plugin;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class FrostedBlock {
    private Block b;
    public long time = 0;
    public long max = 30;
    private Material to;
    private Material type;

    public FrostedBlock(Block b, Material to) {
        this.b = b;
        this.to = to;
        type = b.getType();
    }

    public FrostedBlock(Block b, Material to, long time) {
        this.b = b;
        this.to = to;
        max = time;
        type = b.getType();

    }

    public boolean tick() {
        time++;
        if (b.getType() != type) {
            return true;
        }
        b.setType(type);
        if (time > max * 0.9) {
        }
        if (time > max) {
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
            b.setType(to);
            return true;
        }
        return false;

    }

    public Block getB() {
        return b;
    }

    public void setB(Block b) {
        this.b = b;
    }

    public static void setBlock(Block b, Material m) {
        setBlock(b, m, 100);
    }

    public static void setBlock(Block b, Material m, int t) {
        Material ty = b.getType();
        b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, m);
        b.setType(m);
        Plugin.s.add(new FrostedBlock(b, ty, t));
    }
}
