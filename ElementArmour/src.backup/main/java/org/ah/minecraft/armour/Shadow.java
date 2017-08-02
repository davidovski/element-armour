package org.ah.minecraft.armour;

import org.bukkit.Location;

public class Shadow {
  Location loc;
  
  public Shadow(Location loc) {
    this.loc = loc;
  }
  
  public void move() {
    loc.add(loc.getDirection());
    if (Math.random() > 0.5D) {
      float yaw = (float)Math.random() * 360.0F;
      float pitch = (float)Math.random() * 360.0F;
      loc.setYaw(yaw);
      loc.setPitch(pitch);
    }
  }
}
