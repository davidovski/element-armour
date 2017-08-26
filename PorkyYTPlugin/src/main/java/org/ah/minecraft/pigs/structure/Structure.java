package org.ah.minecraft.pigs.structure;

public class Structure {
    private int[][][] ids;
    private byte[][][] values;

    public Structure( int[][][] ids, byte[][][] values) {
        this.ids = ids;
        // TODO Auto-generated constructor stub
        this.values = values;
    }

    public int[][][] getIds() {
        return ids;
    }

    public void setIds(int[][][] ids) {
        this.ids = ids;
    }

    public byte[][][] getValues() {
        return values;
    }

    public void setValues(byte[][][] values) {
        this.values = values;
    }
}
