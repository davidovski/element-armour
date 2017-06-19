package org.ah.minecraft.armour;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public class LocationUtil {
    
    public static final String DIVIDER = ",";
    
    public static String locationToStringRounded(Location location) {
        String locationString = location.getWorld().getName() + DIVIDER + ((int)location.getX()) + DIVIDER + ((int)location.getY()) + DIVIDER + ((int)location.getZ());
        return locationString;
    }
    
    public static String locationToString(Location location) {
        String locationString = location.getWorld().getName() + DIVIDER + location.getX() + DIVIDER + location.getY() + DIVIDER + location.getZ();
        return locationString;
    }
    
    public static Location stringToLocation(Server server, String str) {
        String[] parts = str.split(DIVIDER);
        World world = server.getWorld(parts[0]);
        Double x = Double.parseDouble(parts[1]);
        Double y = Double.parseDouble(parts[2]);
        Double z = Double.parseDouble(parts[3]);
        
        Location location = new Location(world, x, y, z);
        return location;
    }
}
