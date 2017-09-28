package org.ah.minecraft.machines;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class Machine implements MachineInterface, Listener {
    private Block block;
    private MachineType type;
    private double speed = 4;
    private MachineControlPanel menu;
    private long a = 0;
    private int coalCooldown = 0;
    private int coalSpeed = 3;
    private boolean running = true;
    private int coal = 0;

    private double succeses = 0.0;
    private double tries = 0.0;

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public MachineType getType() {
        return type;
    }

    @Override
    public void setType(MachineType type) {
        this.type = type;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void destroy() {
        block.setType(Material.AIR);
    }

    BlockFace getDirection() {
        if (block.getType() == Material.COMMAND) {
            CommandBlock state = (CommandBlock) block.getState();
            byte data = block.getData();
            if (data == 0x0) {
                return BlockFace.NORTH;
            } else if (data == 0x1) {
                return BlockFace.UP;
            } else if (data == 0x2) {
                return BlockFace.NORTH;
            } else if (data == 0x3) {
                return BlockFace.SOUTH;
            } else if (data == 0x4) {
                return BlockFace.WEST;
            } else if (data == 0x5) {
                return BlockFace.EAST;
            } else if (data == 0x6) {
                return BlockFace.DOWN;
            } else if (data == 0x7) {
                return BlockFace.UP;
            } else {
                return BlockFace.NORTH;
            }
        } else {

            return BlockFace.NORTH;
        }
    }

    @Override
    public MachineControlPanel getControlPanel() {
        return menu;
    }

    @Override
    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//                CommandBlock cb = (CommandBlock) block.getState();

                if (event.getClickedBlock().equals(block) && !event.getPlayer().isSneaking()) {// && cb.getCommand() != null) {
                    menu.open(event.getPlayer());
                    event.setCancelled(true);
                }
            }
        }
    }

    public double getEfficiency() {
        if (tries == 0) {
            return 0.0;
        }
        return Math.round((succeses / tries) * 100);
    }

    public boolean loop() {
        if (block.getType() != Material.COMMAND) {
            return false;
        }
        a++;
        menu.update();
        if (coal < 1) {
            setRunning(false);
        }
        if (running) {
            if (a % Math.round(20 / speed) == 0) {
                boolean run = run();
                tries++;
                if (run) {
                    succeses++;
                }
                if (coal > 0) {
                    takeOneCoal();
                }
            }
        }
        return true;
    }

    public int getCoal() {
        return coal;
    }

    public void setCoal(int coal) {
        this.coal = coal;
    }

    public int getCoalSpeed() {
        return coalSpeed;
    }

    public void setCoalSpeed(int coalSpeed) {
        this.coalSpeed = coalSpeed;
    }

    public void takeOneCoal() {
        coalCooldown--;
        if (coalCooldown < 0) {
            coalCooldown = getCoalSpeed();
            if (coal > 0) {
                coal--;
//                succeses = 1;
//                tries = 1;
            }
        }
    }

    public void setControlPanel(MachineControlPanel menu) {
        this.menu = menu;
    }

    public BlockFace turnMachineByOne() {
        block.setData((byte) (block.getData() + 1));
        if (block.getData() > 0x8) {
            block.setData((byte) 0x0);
        }
        return getDirection();
    }

    public void putItemInBack(ItemStack i) {
        if (block.getRelative(BlockFace.DOWN).getType() == Material.HOPPER) {
            Block relative = block.getRelative(BlockFace.DOWN);
            Hopper state = (Hopper) relative.getState();
            state.getInventory().addItem(i);
        } else if (block.getRelative(getDirection().getOppositeFace()).getType() != Material.AIR) {
            try {
                Block relative = block.getRelative(getDirection().getOppositeFace());
                if (relative.getState() instanceof InventoryHolder) {
                    InventoryHolder ih = (InventoryHolder) relative.getState();
                    ih.getInventory().addItem(i);
                } else {
                    block.getWorld().dropItem(relative.getLocation(), i);
                }
            } catch (Exception e) {
                e.printStackTrace();
                block.getWorld().dropItem(block.getRelative(getDirection().getOppositeFace()).getLocation(), i);
            }
        } else {
            block.getWorld().dropItem(block.getRelative(getDirection().getOppositeFace()).getLocation(), i);
        }
    }

    public Inventory getBackInv() {
        if (block.getRelative(getDirection().getOppositeFace()).getType() != Material.AIR) {
            try {
                Block relative = block.getRelative(getDirection().getOppositeFace());
                if (relative.getState() instanceof InventoryHolder) {
                    InventoryHolder ih = (InventoryHolder) relative.getState();
                    return ih.getInventory();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        }

        return null;
    }
    public String save() {
        String json = "{\"type\":" + getType().getId() + ",\"coal\":" + coal + ",\"running\":" + running + ",\"coalSpeed\":" + coalSpeed + ",\"speed\":" + menu.getPercentSpeed()
                + ",\"tries\":" + tries + ",\"successes\":" + succeses + "}";
        CommandBlock cb = (CommandBlock) block.getState();
        cb.setCommand(json);
        cb.update();
        System.out.println(cb.getCommand());
        return json;
    }

    public double getSuccesses() {
        return succeses;
    }

    public void setSuccesses(double successes) {
        this.succeses = successes;
    }

    public double getTries() {
        return tries;
    }

    public void setTries(double tries) {
        this.tries = tries;
    }
}
