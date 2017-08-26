package org.ah.minecraft.generator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.noise.PerlinNoiseGenerator;

public class AlmostVanillaPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        PerlinNoiseGenerator png = new PerlinNoiseGenerator(world.getSeed());

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int blockX = (chunkX * 16) + x;
                int blockZ = (chunkZ * 16) + z;
                final int MAX_Y = world.getHighestBlockYAt(blockX, blockZ) + 1;

                for (int y = 0; y < MAX_Y; y++) {

                    Block block = world.getBlockAt(blockX, y, blockZ);

                    // BUSH
                    if (random.nextInt(40) == 0) {
                        if (block.getType() == Material.SAND && block.getBiome() == Biome.BEACHES) {
                            Block last_block = block.getRelative(BlockFace.UP);
                            last_block.setType(Material.COBBLESTONE);
                        }
                    }
                    if (random.nextInt(50) == 0) {

                        if (block.getType() == Material.GRASS) {
                            int randomax = 100;
                            if (block.getBiome().toString().contains("FOREST")) {
                                randomax = 10;
                            } else if (block.getBiome().toString().contains("PLAINS")) {
                                randomax = 30;
                            } else if (block.getBiome().toString().contains("EXTREME")) {
                                randomax = 40;
                            }

                            if (random.nextInt(randomax) == 0) {
                                Block last_block = block.getRelative(BlockFace.UP);
                                last_block.setType(Material.LEAVES);
                                last_block.setData((byte) 4);
                                while (random.nextBoolean()) {
                                    int nextInt = random.nextInt(4);
                                    if (nextInt == 0) {
                                        last_block = last_block.getRelative(BlockFace.NORTH);
                                    } else if (nextInt == 1) {
                                        last_block = last_block.getRelative(BlockFace.EAST);
                                    } else if (nextInt == 2) {
                                        last_block = last_block.getRelative(BlockFace.SOUTH);
                                    } else if (nextInt == 3) {
                                        last_block = last_block.getRelative(BlockFace.WEST);
                                    }
                                    last_block.setType(Material.LEAVES);
                                    last_block.setData((byte) 4);
                                }
                            }
                        }
                    }
                    //SWAMPY

                    if (block.getType() == Material.GRASS || block.getType() == Material.SOIL) {
                        if (block.getBiome() == Biome.PLAINS) {
                            PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(world.getSeed());

                            double noise = perlinNoiseGenerator.noise(block.getX() / 1000D, block.getZ() / 1000D);
                            if (noise > 0.9D) {
                                if (random.nextInt(32) == 0) {
                                    block.setType(Material.STATIONARY_WATER);
                                    block.getRelative(BlockFace.UP).setType(Material.AIR);

                                    block.getRelative(BlockFace.NORTH).setType(Material.SOIL);
                                    block.getRelative(BlockFace.NORTH).setData((byte) 7);

                                    block.getRelative(BlockFace.SOUTH).setType(Material.SOIL);
                                    block.getRelative(BlockFace.SOUTH).setData((byte) 7);

                                    block.getRelative(BlockFace.EAST).setType(Material.SOIL);
                                    block.getRelative(BlockFace.EAST).setData((byte) 7);

                                    block.getRelative(BlockFace.WEST).setType(Material.SOIL);
                                    block.getRelative(BlockFace.WEST).setData((byte) 7);
                                } else {
                                    block.setType(Material.SOIL);
                                    block.setData((byte) 7);
                                    block.getLocation().add(0,1,0).getBlock().getLocation().add(0,1,0).getBlock().setType(Material.AIR);
                                    block.getLocation().add(0,1,0).getBlock().setType(Material.CROPS);
                                    block.getLocation().add(0,1,0).getBlock().setData((byte) 7);
                                }
                            }
                        }
                        if (block.getBiome() == Biome.SWAMPLAND) {
                            PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator(world.getSeed());

                            double noise = perlinNoiseGenerator.noise(block.getX() / 100D, block.getZ() / 100D);
                            if (noise < 0D) {
                                block.setType(Material.GRASS);
                            } else {
                                block.setType(Material.DIRT);
                                block.setData((byte) 2);

                                if (random.nextInt(20) == 0) {
                                    Block trunk = block.getRelative(BlockFace.UP);
                                    for (int i = 0; i < random.nextInt(40); i++) {
                                        trunk.setType(Material.LOG);
                                        trunk = trunk.getRelative(BlockFace.UP);
                                    }
                                    trunk.setType(Material.DIRT);
                                    if (!world.generateTree(trunk.getRelative(BlockFace.UP).getLocation(), TreeType.SWAMP)) {
                                        trunk.getRelative(BlockFace.UP).setType(Material.FENCE);
                                    }
                                    trunk.setType(Material.LOG);
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}