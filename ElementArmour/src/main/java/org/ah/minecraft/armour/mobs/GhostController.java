package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GhostController extends CustomEntityController {

    public GhostController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Creeper creeper = (Creeper) loc.getWorld().spawnEntity(loc, EntityType.CREEPER);
        creeper.setPowered(true);
        creeper.setCustomName("Ghost");
        return creeper;
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Ghost".equalsIgnoreCase(e.getCustomName()) && e instanceof Creeper;
    }


    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() < 40;
    }

    @Override
    public void update(LivingEntity e) {
        e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 2));
        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.WEB, 1));
        return drops;
    }
}
