package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DeamonController extends CustomEntityController {

    private ItemStack axe;

    public DeamonController(int weight) {
        super(weight);
        axe = new ItemStack(Material.GOLD_AXE);
        axe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        axe.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 5);
        }

    @Override
    public void update(LivingEntity e) {
        PigZombie p = (PigZombie) e;
        p.setBaby(true);
        p.setAngry(true);
        p.setAnger(10);
        p.playEffect(EntityEffect.HURT);

    }
    @Override
    public boolean canSpawnThere(Block b) {
        return b.getBiome().toString().contains("HELL");
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Deamon".equals(e.getCustomName()) && e instanceof PigZombie;
    }

    @Override
    public LivingEntity spawn(Location loc) {
        PigZombie zombie = (PigZombie) loc.getWorld().spawnEntity(loc, EntityType.PIG_ZOMBIE);
        EntityEquipment zomb = zombie.getEquipment();

        zombie.setCustomName("Deamon");
        zombie.setCustomNameVisible(false);

        zombie.setBaby(true);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        skull.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner("xINEFFABLEx");
        skull.setItemMeta(meta);


        zomb.setItemInHand(axe);

        zomb.setHelmet(skull);

        MobUtils.setMaxHealth(zombie, 100D);
        return zombie;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(axe);
        return drops;
    }

}
