package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BanditController extends CustomEntityController {

    public BanditController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(LivingEntity e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getBiome().toString().contains("DESERT") && b.getY() > 60;
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Bandit".equals(e.getCustomName());
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        EntityEquipment zomb = zombie.getEquipment();
        zomb.setBoots(new ItemStack(Material.GOLD_BOOTS));
        zomb.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        zomb.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        zomb.setHelmet(new ItemStack(Material.IRON_HELMET));
        zomb.setItemInMainHand(new ItemStack(Material.WOOD_SWORD));
        zomb.setItemInOffHand(new ItemStack(Material.WOOD_SWORD));

        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1, false));
        zombie.setMaxHealth(50D);
        zombie.setHealth(50D);
        zombie.setCustomName("Bandit");
        zombie.setCustomNameVisible(false);
        zombie.setBaby(false);
        return zombie;
    }

    public List<ItemStack> getDrops(Zombie z) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.LEATHER, 3));
        drops.add(DropGenerator.i(Material.GOLD_NUGGET, 9));
        drops.add(z.getEquipment().getItemInMainHand());
        return drops;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.LEATHER, 3));
        drops.add(DropGenerator.i(Material.GOLD_NUGGET, 9));
        return drops;
    }

}
