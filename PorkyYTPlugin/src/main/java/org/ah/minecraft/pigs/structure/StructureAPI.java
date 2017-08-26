package org.ah.minecraft.pigs.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class StructureAPI {

    Plugin plugin;

    public StructureAPI(Plugin pl) {
        this.plugin = pl;
    }

    /**
     * Get all blocks between two points and return a 3d array
     */

    public Structure getStructure(Block block, Block block2) {
        int minX, minZ, minY;
        int maxX, maxZ, maxY;

        // Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();

        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();

        int[][][] blocks = new int[maxX - minX + 1][maxY - minY + 1][maxZ - minZ + 1];
        byte[][][] values = new byte[maxX - minX + 1][maxY - minY + 1][maxZ - minZ + 1];

        for (int x = minX; x <= maxX; x++) {

            for (int y = minY; y <= maxY; y++) {

                for (int z = minZ; z <= maxZ; z++) {

                    Block b = block.getWorld().getBlockAt(x, y, z);
                    blocks[x - minX][y - minY][z - minZ] = b.getTypeId();
                    values[x - minX][y - minY][z - minZ] = b.getData();
                }
            }
        }

        return new Structure(blocks, values);

    }

    /**
     * Pastes a structure to a desired location
     */

    public void paste(Structure blocks, Location l) {
        for (int x = 0; x < blocks.getIds().length; x++) {

            for (int y = 0; y < blocks.getIds()[x].length; y++) {

                for (int z = 0; z < blocks.getIds()[x][y].length; z++) {
                    Location neww = l.clone();
                    neww.add(x, y, z);
                    Block b = neww.getBlock();
                    if (blocks.getIds()[x][y][z] != 0) {
                        b.setTypeIdAndData(blocks.getIds()[x][y][z], blocks.getValues()[x][y][z], false);
                        b.getState().update(false);
                    } else {
                        b.setType(Material.AIR);
                    }
                }

            }
        }
    }

    public void pasteWithPigs(Structure blocks, Location l) {
        for (int x = 0; x < blocks.getIds().length; x++) {

            for (int y = 0; y < blocks.getIds()[x].length; y++) {

                for (int z = 0; z < blocks.getIds()[x][y].length; z++) {
                    Location neww = l.clone();
                    neww.add(x, y, z);
                    Block b = neww.getBlock();
                    if (blocks.getIds()[x][y][z] != 0) {
                        b.setTypeIdAndData(blocks.getIds()[x][y][z], blocks.getValues()[x][y][z], false);
                        b.getState().update(false);
                        if (b.getType() == Material.CHEST) {
                            Chest state = (Chest) b.getState();
                            Inventory inv = state.getInventory();

                            for (int i = 0; i < inv.getSize(); i++) {
                                Random random = new Random();
                                int n = random.nextInt(200);
                                if (n < 1) {
                                    inv.setItem(i, org.ah.minecraft.pigs.Plugin.getPigStaff());
                                } else if (n < 5) {
                                    inv.setItem(i, new ItemStack(Material.DIAMOND));
                                } else if (n < 20) {
                                    inv.setItem(i, new ItemStack(Material.GOLD_INGOT));
                                } else if (n < 50) {
                                    inv.setItem(i, new ItemStack(Material.GOLD_NUGGET));
                                } else if (n < 60) {
                                    inv.setItem(i, new ItemStack(Material.PORK));
                                } else if (n < 100) {
                                    inv.setItem(i, new ItemStack(Material.CARROT));
                                }
                            }
                        }
                    } else {
                        b.setType(Material.AIR);
                        if (b.getRelative(BlockFace.DOWN).getType() == Material.WOOD && Math.random() > 0.9) {
                            Pig pig = (Pig) b.getWorld().spawnEntity(b.getLocation(), EntityType.PIG);
                            pig.setCustomName("SoldierPig");
                        }
                    }
                }

            }
        }
    }

    /**
     * Save a structure with a desired name
     */

    public void save(String name, Structure b) {
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;

        File idsf = new File(plugin.getDataFolder() + "/schematics/" + name + ".schem.id");
        File valuesf = new File(plugin.getDataFolder() + "/schematics/" + name + ".schem.v");
        File dir = new File(plugin.getDataFolder() + "/schematics");

        int[][][] ids = b.getIds();
        byte[][][] values = b.getValues();

        try {
            dir.mkdirs();
            idsf.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            fout = new FileOutputStream(idsf);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(ids);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            dir.mkdirs();
            valuesf.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            fout = new FileOutputStream(valuesf);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load structure from file
     */

    public Structure load(String name) {

        File f = new File(plugin.getDataFolder() + "/schematics/" + name + ".schem.id");
        File f2 = new File(plugin.getDataFolder() + "/schematics/" + name + ".schem.v");

        Structure loaded = new Structure(new int[0][0][0], new byte[0][0][0]);

        try {
            FileInputStream streamIn = new FileInputStream(f);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            loaded.setIds((int[][][]) objectinputstream.readObject());

            objectinputstream.close();


            FileInputStream streamIn2 = new FileInputStream(f2);
            ObjectInputStream objectinputstream2 = new ObjectInputStream(streamIn2);
            loaded.setValues((byte[][][]) objectinputstream2.readObject());

            objectinputstream.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return loaded;
    }

    /**
     * Some methods I used to test
     *
     */

    public void printArray(int[][][] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(toString(a[i]));
        }
    }

    public String toString(int[][] a) {
        String s = "";
        for (int row = 0; row < a.length; ++row) {
            s += Arrays.toString(a[row]) + "\n";
        }
        return s;
    }

}
