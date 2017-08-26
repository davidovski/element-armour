package org.ah.minecraft.machines;

import org.bukkit.Location;

public class LocationUtil
{
  public static final String DIVIDER = ",";
  
  public LocationUtil() {}
  
  public static String locationToStringRounded(Location location) {
    String locationString = location.getWorld().getName() + "," + (int)location.getX() + "," + (int)location.getY() + "," + (int)location.getZ();
    return locationString;
  }
  
  public static String locationToString(Location location) {
    String locationString = location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    return locationString;
  }
  
  public static Location stringToLocation(org.bukkit.Server server, String str) {
    String[] parts = str.split(",");
    org.bukkit.World world = server.getWorld(parts[0]);
    Double x = Double.valueOf(Double.parseDouble(parts[1]));
    Double y = Double.valueOf(Double.parseDouble(parts[2]));
    Double z = Double.valueOf(Double.parseDouble(parts[3]));
    
    Location location = new Location(world, x.doubleValue(), y.doubleValue(), z.doubleValue());
    return location;
  }
}
