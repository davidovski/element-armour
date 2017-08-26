package org.ah.minecraft.machines;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class BreakMachine extends Machine {
    private ItemStack pickaxe;

    public BreakMachine(Block b) {
        setBlock(b);
        build();
        setType(MachineType.BREAK);
        setControlPanel(new MachineControlPanel(this));

        pickaxe = new ItemStack(Material.IRON_PICKAXE);
        pickaxe.addEnchantment(Enchantment.SILK_TOUCH, 1);
    }

    @Override
    public boolean run() {
        getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        boolean done = false;
        Block relative = getBlock().getRelative(getDirection());
        if (relative.getType() == Material.AIR) {
            done = false;
        } else {
            done = true;
            for (ItemStack itemStack : relative.getDrops(pickaxe)) {
                putItemInBack(itemStack);
            }
        }
        relative.getWorld().playEffect(relative.getLocation(), Effect.STEP_SOUND, relative.getType(), 10);
        relative.setType(Material.AIR);
        return done;
    }

    @Override
    public void build() {
        if (getBlock().getType() != Material.COMMAND)
        getBlock().setType(Material.COMMAND);
    }
}
