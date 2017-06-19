package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SeaMonsterController extends CustomEntityController {

    public SeaMonsterController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public   LivingEntity spawn(Location loc) {
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        EntityEquipment zomb = zombie.getEquipment();
        zombie.setMaxHealth(60D);
        zombie.setHealth(60D);

        zombie.setVillager(false);

        zomb.setBoots(ArmourUtil.createAquaBoots());
        zomb.setBootsDropChance(1f);
        zomb.setLeggings(ArmourUtil.createAquaPants());
        zomb.setChestplate(ArmourUtil.createAquaShirt());
        zomb.setHelmet(new ItemStack(Material.SEA_LANTERN));
        // zomb.setItemInHand(new ItemStack(Material.STONE_HOE));
        zombie.setCustomName("Sea Monster");
        return zombie;
    }

    @Override
    public void update(LivingEntity e) {
        e.setRemainingAir(20);
        if (e.getLocation().getBlock().getType() == Material.WATER) {
            e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, 10));
        } else {
            e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3, 10));

        }

        if (e.getLocation().getBlock().getType() == Material.FIRE) {
            e.damage(10D);
        }

        if (!e.isOnGround()) {
            e.setVelocity(new Vector(e.getVelocity().getBlockX(), -10, e.getVelocity().getZ()));
        }

        if (e.getLocation().add(e.getLocation().getDirection().setY(0)).getBlock().getType().isSolid()) {
            e.setVelocity(e.getLocation().getDirection().setY(0.5));
        } else {
            if (e.getLocation().getBlock().getType() == Material.WATER) {
                e.setVelocity(e.getLocation().getDirection().multiply(0.25f).setY(-10));
            }
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Sea Monster".equalsIgnoreCase(e.getCustomName());
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.INK_SACK, 4));
        drops.add(DropGenerator.i(Material.RAW_FISH, 3));
        drops.add(DropGenerator.i(Material.SEA_LANTERN, 1));
        return drops;
    }

}
