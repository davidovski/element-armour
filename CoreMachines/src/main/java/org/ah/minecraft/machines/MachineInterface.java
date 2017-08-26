package org.ah.minecraft.machines;

import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public interface MachineInterface {
    Block getBlock();
    void setBlock(Block block);
    MachineType getType();
    void setType(MachineType type);
    double getSpeed();
    void setSpeed(double speed);


    boolean run();
    void destroy();
    void build();

    MachineControlPanel getControlPanel();

    void onClick(PlayerInteractEvent event);

    boolean isRunning();
    void setRunning(boolean running);
}
