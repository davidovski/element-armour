package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EyeController extends CustomEntityController {

    public EyeController(int weight) {
        super(weight);
    }

    @Override
    public void update(LivingEntity e) {
        e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 255), true);
        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0), true);


    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Eyeball".equals(e.getCustomName()) && e.hasPotionEffect(PotionEffectType.INVISIBILITY);
    }


    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() < 70;
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        EntityEquipment eq = z.getEquipment();
        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        skull.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner("Edna_I");
        skull.setItemMeta(meta);
        eq.setHelmet(skull);
        z.setBaby(false);
        z.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 255), true);
        z.setCustomName("Eyeball");
        z.setSilent(true);
        z.setCustomNameVisible(false);
        z.setRemoveWhenFarAway(true);
        z.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6);
        z.setHealth(6D);
        return z;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.EYE_OF_ENDER));
        return drops;
    }

}
