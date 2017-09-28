package org.ah.minecraft.machines;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class PlaceMachine extends Machine {
    private ItemStack pickaxe;

    public PlaceMachine(Block b) {
        setBlock(b);
        build();
        setType(MachineType.PLACE);
        setControlPanel(new MachineControlPanel(this));
        getControlPanel().setPercentSpeed(40);
        pickaxe = new ItemStack(Material.IRON_PICKAXE);
        pickaxe.addEnchantment(Enchantment.SILK_TOUCH, 1);
    }

    @Override
    public boolean run() {
        getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        boolean done = false;
        Block relative = getBlock().getRelative(getDirection());
        if (getBackInv() != null) {
            ItemStack i = null;
            for (ItemStack itemStack : getBackInv()) {
                if (itemStack != null) {
                    if (itemStack.getType().isBlock()) {
                        i = itemStack;
                        break;
                    }
                }
            }
            if (i != null && !relative.getType().isSolid()) {
                relative.getWorld().playEffect(relative.getLocation(), Effect.STEP_SOUND, relative.getType(), 10);
                relative.setType(i.getType());
                done = true;
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
