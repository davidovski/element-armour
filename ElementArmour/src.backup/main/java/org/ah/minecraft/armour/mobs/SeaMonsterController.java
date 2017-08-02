package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;
import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SeaMonsterController extends CustomEntityController
{
  public SeaMonsterController(int weight)
  {
    super(weight);
  }
  

  public LivingEntity spawn(Location loc)
  {
    Zombie zombie = (Zombie)loc.getWorld().spawnEntity(loc, org.bukkit.entity.EntityType.ZOMBIE);
    
    EntityEquipment zomb = zombie.getEquipment();
    zombie.setMaxHealth(60.0D);
    zombie.setHealth(60.0D);
    
    zombie.setVillager(false);
    
    zomb.setBoots(ArmourUtil.createAquaBoots());
    zomb.setBootsDropChance(1.0F);
    zomb.setLeggings(ArmourUtil.createAquaPants());
    zomb.setChestplate(ArmourUtil.createAquaShirt());
    zomb.setHelmet(new ItemStack(Material.SEA_LANTERN));
    
    zombie.setCustomName("Sea Monster");
    return zombie;
  }
  
  public void update(LivingEntity e)
  {
    e.setRemainingAir(20);
    if (e.getLocation().getBlock().getType() == Material.WATER) {
      e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, 10));
    } else {
      e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3, 10));
    }
    

    if (e.getLocation().getBlock().getType() == Material.FIRE) {
      e.damage(10.0D);
    }
    
    if (!e.isOnGround()) {
      e.setVelocity(new Vector(e.getVelocity().getBlockX(), -10.0D, e.getVelocity().getZ()));
    }
    
    if (e.getLocation().add(e.getLocation().getDirection().setY(0)).getBlock().getType().isSolid()) {
      e.setVelocity(e.getLocation().getDirection().setY(0.5D));
    }
    else if (e.getLocation().getBlock().getType() == Material.WATER) {
      e.setVelocity(e.getLocation().getDirection().multiply(0.25F).setY(-10));
    }
  }
  

  public boolean isOne(LivingEntity e)
  {
    return "Sea Monster".equalsIgnoreCase(e.getCustomName());
  }
  
  public List<ItemStack> getDrops()
  {
    List<ItemStack> drops = new ArrayList();
    drops.add(DropGenerator.i(Material.INK_SACK, 4));
    drops.add(DropGenerator.i(Material.RAW_FISH, 3));
    drops.add(DropGenerator.i(Material.SEA_LANTERN, 1));
    return drops;
  }
}
