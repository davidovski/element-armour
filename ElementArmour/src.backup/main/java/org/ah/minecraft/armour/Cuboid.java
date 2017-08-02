package org.ah.minecraft.armour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Cuboid implements Iterable<Block>, Cloneable, ConfigurationSerializable {
    protected final String worldName;
    protected final int x1;
    protected final int y1;
    protected final int z1;
    protected final int x2;
    protected final int y2;
    protected final int z2;

    public Cuboid(Location l1, Location l2) {
        if (!l1.getWorld().equals(l2.getWorld()))
            throw new IllegalArgumentException("Locations must be on the same world");
        worldName = l1.getWorld().getName();
        x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());
    }

    public Cuboid(Location l1) {
        this(l1, l1);
    }

    public Cuboid(Cuboid other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2);
    }

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        worldName = world.getName();
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    private Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.worldName = worldName;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.y2 = Math.max(y1, y2);
        this.z1 = Math.min(z1, z2);
        this.z2 = Math.max(z1, z2);
    }

    public Cuboid(Map<String, Object> map) {
        worldName = ((String) map.get("worldName"));
        x1 = ((Integer) map.get("x1")).intValue();
        x2 = ((Integer) map.get("x2")).intValue();
        y1 = ((Integer) map.get("y1")).intValue();
        y2 = ((Integer) map.get("y2")).intValue();
        z1 = ((Integer) map.get("z1")).intValue();
        z2 = ((Integer) map.get("z2")).intValue();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap();
        map.put("worldName", worldName);
        map.put("x1", Integer.valueOf(x1));
        map.put("y1", Integer.valueOf(y1));
        map.put("z1", Integer.valueOf(z1));
        map.put("x2", Integer.valueOf(x2));
        map.put("y2", Integer.valueOf(y2));
        map.put("z2", Integer.valueOf(z2));
        return map;
    }

    public Location getLowerNE() {
        return new Location(getWorld(), x1, y1, z1);
    }

    public Location getUpperSW() {
        return new Location(getWorld(), x2, y2, z2);
    }

    public List<Block> getBlocks() {
        Iterator<Block> blockI = iterator();
        List<Block> copy = new ArrayList();
        while (blockI.hasNext())
            copy.add(blockI.next());
        return copy;
    }

    public Location getCenter() {
        int x1 = getUpperX() + 1;
        int y1 = getUpperY() + 1;
        int z1 = getUpperZ() + 1;
        return new Location(getWorld(), getLowerX() + (x1 - getLowerX()) / 2.0D, getLowerY() + (y1 - getLowerY()) / 2.0D, getLowerZ() + (z1 - getLowerZ()) / 2.0D);
    }

    public World getWorld() {
        World world = Bukkit.getWorld(worldName);
        if (world == null)
            throw new IllegalStateException("World '" + worldName + "' is not loaded");
        return world;
    }

    public int getSizeX() {
        return x2 - x1 + 1;
    }

    public int getSizeY() {
        return y2 - y1 + 1;
    }

    public int getSizeZ() {
        return z2 - z1 + 1;
    }

    public int getLowerX() {
        return x1;
    }

    public int getLowerY() {
        return y1;
    }

    public int getLowerZ() {
        return z1;
    }

    public int getUpperX() {
        return x2;
    }

    public int getUpperY() {
        return y2;
    }

    public int getUpperZ() {
        return z2;
    }

    public Block[] corners() {
        Block[] res = new Block[8];
        World w = getWorld();
        res[0] = w.getBlockAt(x1, y1, z1);
        res[1] = w.getBlockAt(x1, y1, z2);
        res[2] = w.getBlockAt(x1, y2, z1);
        res[3] = w.getBlockAt(x1, y2, z2);
        res[4] = w.getBlockAt(x2, y1, z1);
        res[5] = w.getBlockAt(x2, y1, z2);
        res[6] = w.getBlockAt(x2, y2, z1);
        res[7] = w.getBlockAt(x2, y2, z2);
        return res;
    }

    public Cuboid expand(CuboidDirection dir, int amount) {
        switch (dir) {
        case Both:
            return new Cuboid(worldName, x1 - amount, y1, z1, x2, y2, z2);
        case East:
            return new Cuboid(worldName, x1, y1, z1, x2 + amount, y2, z2);
        case Down:
            return new Cuboid(worldName, x1, y1, z1 - amount, x2, y2, z2);
        case Horizontal:
            return new Cuboid(worldName, x1, y1, z1, x2, y2, z2 + amount);
        case South:
            return new Cuboid(worldName, x1, y1 - amount, z1, x2, y2, z2);
        case North:
            return new Cuboid(worldName, x1, y1, z1, x2, y2 + amount, z2);
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public Cuboid shift(CuboidDirection dir, int amount) {
        return expand(dir, amount).expand(dir.opposite(), -amount);
    }

    public Cuboid outset(CuboidDirection dir, int amount) {
        Cuboid c;
        switch (dir) {
        case Unknown:
            c = expand(CuboidDirection.North, amount).expand(CuboidDirection.South, amount).expand(CuboidDirection.East, amount).expand(CuboidDirection.West, amount);
            break;
        case Up:
            c = expand(CuboidDirection.Down, amount).expand(CuboidDirection.Up, amount);
            break;
        case Vertical:
            c = outset(CuboidDirection.Horizontal, amount).outset(CuboidDirection.Vertical, amount);
            break;
        default:
            throw new IllegalArgumentException("Invalid direction " + dir);
        }
        return c;
    }

    public Cuboid inset(CuboidDirection dir, int amount) {
        return outset(dir, -amount);
    }

    public boolean contains(int x, int y, int z) {
        return (x >= x1) && (x <= x2) && (y >= y1) && (y <= y2) && (z >= z1) && (z <= z2);
    }

    public boolean contains(Block b) {
        return contains(b.getLocation());
    }

    public boolean contains(Location l) {
        if (!worldName.equals(l.getWorld().getName()))
            return false;
        return contains(l.getBlockX(), l.getBlockY(), l.getBlockZ());
    }

    public int getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    public byte getAverageLightLevel() {
        long total = 0L;
        int n = 0;
        for (Block b : this) {
            if (b.isEmpty()) {
                total += b.getLightLevel();
                n++;
            }
        }
        return n > 0 ? (byte) (int) (total / n) : 0;
    }

    public Cuboid contract() {
        return contract(CuboidDirection.Down).contract(CuboidDirection.South).contract(CuboidDirection.East).contract(CuboidDirection.Up).contract(CuboidDirection.North)
                .contract(CuboidDirection.West);
    }

    public Cuboid contract(CuboidDirection dir) {
        Cuboid face = getFace(dir.opposite());
        switch (dir) {
        case South:
            while ((face.containsOnly(0)) && (face.getLowerY() > getLowerY())) {
                face = face.shift(CuboidDirection.Down, 1);
            }
            return new Cuboid(worldName, x1, y1, z1, x2, face.getUpperY(), z2);
        case North:
            while ((face.containsOnly(0)) && (face.getUpperY() < getUpperY())) {
                face = face.shift(CuboidDirection.Up, 1);
            }
            return new Cuboid(worldName, x1, face.getLowerY(), z1, x2, y2, z2);
        case Both:
            while ((face.containsOnly(0)) && (face.getLowerX() > getLowerX())) {
                face = face.shift(CuboidDirection.North, 1);
            }
            return new Cuboid(worldName, x1, y1, z1, face.getUpperX(), y2, z2);
        case East:
            while ((face.containsOnly(0)) && (face.getUpperX() < getUpperX())) {
                face = face.shift(CuboidDirection.South, 1);
            }
            return new Cuboid(worldName, face.getLowerX(), y1, z1, x2, y2, z2);
        case Down:
            while ((face.containsOnly(0)) && (face.getLowerZ() > getLowerZ())) {
                face = face.shift(CuboidDirection.East, 1);
            }
            return new Cuboid(worldName, x1, y1, z1, x2, y2, face.getUpperZ());
        case Horizontal:
            while ((face.containsOnly(0)) && (face.getUpperZ() < getUpperZ())) {
                face = face.shift(CuboidDirection.West, 1);
            }
            return new Cuboid(worldName, x1, y1, face.getLowerZ(), x2, y2, z2);
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public Cuboid getFace(CuboidDirection dir) {
        switch (dir) {
        case South:
            return new Cuboid(worldName, x1, y1, z1, x2, y1, z2);
        case North:
            return new Cuboid(worldName, x1, y2, z1, x2, y2, z2);
        case Both:
            return new Cuboid(worldName, x1, y1, z1, x1, y2, z2);
        case East:
            return new Cuboid(worldName, x2, y1, z1, x2, y2, z2);
        case Down:
            return new Cuboid(worldName, x1, y1, z1, x2, y2, z1);
        case Horizontal:
            return new Cuboid(worldName, x1, y1, z2, x2, y2, z2);
        }
        throw new IllegalArgumentException("Invalid direction " + dir);
    }

    public boolean containsOnly(int blockId) {
        for (Block b : this) {
            if (b.getTypeId() != blockId)
                return false;
        }
        return true;
    }

    public Cuboid getBoundingCuboid(Cuboid other) {
        if (other == null) {
            return this;
        }
        int xMin = Math.min(getLowerX(), other.getLowerX());
        int yMin = Math.min(getLowerY(), other.getLowerY());
        int zMin = Math.min(getLowerZ(), other.getLowerZ());
        int xMax = Math.max(getUpperX(), other.getUpperX());
        int yMax = Math.max(getUpperY(), other.getUpperY());
        int zMax = Math.max(getUpperZ(), other.getUpperZ());

        return new Cuboid(worldName, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public Block getRelativeBlock(int x, int y, int z) {
        return getWorld().getBlockAt(x1 + x, y1 + y, z1 + z);
    }

    public Block getRelativeBlock(World w, int x, int y, int z) {
        return w.getBlockAt(x1 + x, y1 + y, z1 + z);
    }

    public List<Chunk> getChunks() {
        List<Chunk> res = new ArrayList();

        World w = getWorld();
        int x1 = getLowerX() & 0xFFFFFFF0;
        int x2 = getUpperX() & 0xFFFFFFF0;
        int z1 = getLowerZ() & 0xFFFFFFF0;
        int z2 = getUpperZ() & 0xFFFFFFF0;
        for (int x = x1; x <= x2; x += 16) {
            for (int z = z1; z <= z2; z += 16) {
                res.add(w.getChunkAt(x >> 4, z >> 4));
            }
        }
        return res;
    }

    @Override
    public Iterator<Block> iterator() {
        return new CuboidIterator(getWorld(), x1, y1, z1, x2, y2, z2);
    }

    @Override
    public Cuboid clone() {
        return new Cuboid(this);
    }

    @Override
    public String toString() {
        return new String("Cuboid: " + worldName + "," + x1 + "," + y1 + "," + z1 + "=>" + x2 + "," + y2 + "," + z2);
    }

    public class CuboidIterator implements Iterator<Block> {
        private World w;
        private int baseX;
        private int baseY;
        private int baseZ;
        private int x;

        public CuboidIterator(World w, int x1, int y1, int z1, int x2, int y2, int z2) {
            this.w = w;
            baseX = x1;
            baseY = y1;
            baseZ = z1;
            sizeX = (Math.abs(x2 - x1) + 1);
            sizeY = (Math.abs(y2 - y1) + 1);
            sizeZ = (Math.abs(z2 - z1) + 1);
            x = (this.y = this.z = 0);
        }

        private int y;

        @Override
        public boolean hasNext() {
            return (x < sizeX) && (y < sizeY) && (z < sizeZ);
        }

        private int z;

        @Override
        public Block next() {
            Block b = w.getBlockAt(baseX + x, baseY + y, baseZ + z);
            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY) {
                    y = 0;
                    z += 1;
                }
            }
            return b;
        }

        private int sizeX;
        private int sizeY;
        private int sizeZ;

        @Override
        public void remove() {
        }
    }

    public static enum CuboidDirection {
        North, East, South, West, Up, Down, Horizontal, Vertical, Both, Unknown;

        public CuboidDirection opposite() {
            switch (this) {
            case Both:
                return South;
            case Down:
                return West;
            case East:
                return North;
            case Horizontal:
                return East;
            case Unknown:
                return Vertical;
            case Up:
                return Horizontal;
            case North:
                return Down;
            case South:
                return Up;
            case Vertical:
                return Both;
            }
            return Unknown;
        }
    }
}
