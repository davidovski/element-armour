package org.ah.minecraft.machines;

public enum MachineType {
    IDLE(0),
    BREAK(1),
    PLACE(2);

    private int numVal;

    MachineType(int numVal) {
        this.numVal = numVal;
    }

    public int getId() {
        return numVal;
    }

    public static MachineType getById(int id) {
        for(MachineType e : values()) {
            if(e.getId() == id) return e;
        }
        return null;
     }

    public static MachineType getByName(String name) {
        for(MachineType e : values()) {
            if(e.toString().equals(name)) return e;
        }
        return null;
     }
}
