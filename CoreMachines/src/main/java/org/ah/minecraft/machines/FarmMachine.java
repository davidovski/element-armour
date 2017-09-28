package org.ah.minecraft.machines;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

public class FarmMachine extends Machine {
    private ItemStack pickaxe;

    public FarmMachine(Block b) {
        setBlock(b);
        build();
        setType(MachineType.FARM);
        setControlPanel(new MachineControlPanel(this));
        getControlPanel().setPercentSpeed(10);

    }

    @Override
    public boolean run() {
        getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        boolean done = false;
        Block down = getBlock().getRelative(BlockFace.DOWN);
        Block relative = getBlock().getRelative(getDirection());
        Block farm = relative.getRelative(BlockFace.DOWN);

        if (down.getType() != Material.STATIONARY_WATER) {
            if (down.getType() == Material.AIR) {
                down.setType(Material.STATIONARY_WATER);
                return true;
            } else {
                for (ItemStack itemStack : down.getDrops()) {
                    putItemInBack(itemStack);
                }
                down.getWorld().playEffect(relative.getLocation(), Effect.STEP_SOUND, relative.getType(), 10);
                down.setType(Material.AIR);
                return true;
            }
        }

        if (farm.getType() != Material.SOIL) {
            if (farm.getType() == Material.DIRT) {
                farm.setType(Material.SOIL);
                return true;
            }
            return false;
        }
        if (relative.getType() == Material.AIR) {
            if (getBackInv() != null) {
                ItemStack i = null;
                for (ItemStack itemStack : getBackInv()) {
                    if (itemStack != null) {
                        if (itemStack.getType() == Material.CARROT_ITEM || itemStack.getType() == Material.POTATO_ITEM || itemStack.getType() == Material.SEEDS
                                || itemStack.getType() == Material.BEETROOT_SEEDS) {
                            i = itemStack;
                            break;
                        }
                    }
                }
                if (i != null) {
                    if (i.getType() == Material.CARROT_ITEM) {
                        relative.setType(Material.CARROT);
                        return true;
                    }
                    if (i.getType() == Material.POTATO_ITEM) {
                        relative.setType(Material.POTATO);
                        return true;
                    }
                    if (i.getType() == Material.SEEDS) {
                        relative.setType(Material.CROPS);
                        return true;
                    }
                    if (i.getType() == Material.BEETROOT_SEEDS) {
                        relative.setType(Material.BEETROOT_BLOCK);
                        return true;
                    }
                    done = true;
                }
            }
        } else {
            boolean break1 = false;
            if (relative.getType() == Material.CROPS) {
                if (relative.getData() > 0x6) {
                    break1 = true;
                }
            }
            if (relative.getType() == Material.BEETROOT_BLOCK) {
                if (relative.getData() > 0x2) {
                    break1 = true;
                }
            }

            if (relative.getType() == Material.CARROT) {
                if (relative.getData() > 0x6) {
                    break1 = true;
                }
            }

            if (relative.getType() == Material.POTATO) {
                if (relative.getData() > 0x6) {
                    break1 = true;
                }
            }


            if (break1) {
                for (ItemStack itemStack : relative.getDrops()) {
                    putItemInBack(itemStack);
                }
                relative.getWorld().playEffect(relative.getLocation(), Effect.STEP_SOUND, relative.getType(), 10);
                relative.setType(Material.AIR);
                return true;
            }
        }
        return done;
    }

    @Override
    public void build() {
        if (getBlock().getType() != Material.COMMAND)
            getBlock().setType(Material.COMMAND);
    }
}
