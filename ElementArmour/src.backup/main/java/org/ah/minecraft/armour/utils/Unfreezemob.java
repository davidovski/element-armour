package org.ah.minecraft.armour.utils;

import org.bukkit.entity.LivingEntity;

public class Unfreezemob implements Runnable
{
  private LivingEntity e;
  
  public Unfreezemob(LivingEntity e)
  {
    this.e = e;
  }
  
  public void run()
  {
    e.setAI(true);
    e.setGravity(true);
    e.getWorld().playEffect(e.getLocation().add(0.0D, 0.5D, 0.0D), org.bukkit.Effect.STEP_SOUND, org.bukkit.Material.PACKED_ICE, 10);
    e.removePotionEffect(org.bukkit.potion.PotionEffectType.WITHER);
  }
}
