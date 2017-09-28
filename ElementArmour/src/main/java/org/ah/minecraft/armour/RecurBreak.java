package org.ah.minecraft.armour;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class RecurBreak {

    private Block start;
    private Map<Block, Boolean> map;
    private Map<Block, Boolean> toadd;
    private Material type;
    private org.bukkit.inventory.ItemStack tool;
    private boolean down = true;

    public RecurBreak(Block start, org.bukkit.inventory.ItemStack tool, boolean down) {
        this.tool = tool;
        this.down = down;
        type = start.getType();
        this.start = start;
        map = new HashMap<Block, Boolean>();
        toadd = new HashMap<Block, Boolean>();

        map.put(start, true);
        map.put(start.getRelative(BlockFace.NORTH), false);
        map.put(start.getRelative(BlockFace.EAST), false);
        map.put(start.getRelative(BlockFace.SOUTH), false);
        map.put(start.getRelative(BlockFace.WEST), false);
        map.put(start.getRelative(BlockFace.UP), false);
        if (!down) {
            map.put(start.getRelative(BlockFace.DOWN), false);
        }

        start.getWorld().playEffect(start.getLocation(), Effect.STEP_SOUND, start.getType(), 10);
        start.getWorld().dropItem(start.getLocation(), new ItemStack(start.getType()));
        start.setType(Material.AIR);
    }

    /* returns true if done */
    public boolean iterate() {
        Iterator<Entry<Block, Boolean>> it = map.entrySet().iterator();

        while (it.hasNext()) {
            Entry<Block, Boolean> entry = it.next();
            boolean checked = entry.getValue();
            Block block = entry.getKey();
            if (checked == false) {

                if (block.getType() == type) {

                    block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType(), 10);
                    block.getWorld().dropItem(block.getLocation(), new ItemStack(block.getType()));
                    block.setType(Material.AIR);

                    map.put(block, true);
                    toadd.put(block.getRelative(BlockFace.NORTH), false);
                    toadd.put(block.getRelative(BlockFace.EAST), false);
                    toadd.put(block.getRelative(BlockFace.SOUTH), false);
                    toadd.put(block.getRelative(BlockFace.WEST), false);
                    toadd.put(block.getRelative(BlockFace.UP), false);

                    if (down) {
                        toadd.put(block.getRelative(BlockFace.DOWN), false);
                    }

                } else {
                    it.remove();
                }
            }
        }

        for (Entry<Block, Boolean> entry : toadd.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        toadd.clear();

        boolean done = true;
        for (Boolean b : map.values()) {
            if (b == false) {
                done = false;
            }
        }
        //
        return done;
    }
}
