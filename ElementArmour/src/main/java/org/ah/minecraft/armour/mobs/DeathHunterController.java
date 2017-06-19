package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.MoonPhases;
import org.ah.minecraft.armour.Plugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DeathHunterController extends CustomEntityController {

    public DeathHunterController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public LivingEntity spawn(Location loc) {
        if (Plugin.getMoonPhase(loc.getWorld()) == MoonPhases.NEW && !Plugin.day(loc.getWorld())) {
            Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
            EntityEquipment eqip = z.getEquipment();
            z.setCustomName("Death Hunter");
            z.setCustomNameVisible(false);

            eqip.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            eqip.setLeggings(new ItemStack(Material.GOLD_LEGGINGS));
            eqip.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
            eqip.setHelmet(new ItemStack(Material.SKULL_ITEM));

            ItemStack item = new ItemStack(Material.DIAMOND_AXE);
            item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 100);

            eqip.setItemInMainHand(item);
            eqip.setItemInOffHand(item);
            return z;
        } else {
            return null;
        }
    }

    @Override
    public void update(LivingEntity e) {
        Zombie z = (Zombie) e;
        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10, 20));
        double lowestDist = Double.MAX_VALUE;
        Player target = null;
        for (Player p : z.getWorld().getPlayers()) {
            double distanceSquared = z.getLocation().distanceSquared(p.getLocation());
            if (distanceSquared < lowestDist) {
                lowestDist = distanceSquared;
                target = p;
            }
        }

        z.setTarget(target);
        if (Plugin.getMoonPhase(z.getWorld()) != MoonPhases.NEW || Plugin.day(z.getWorld())) {
            z.remove();
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Death Hunter".equalsIgnoreCase(e.getCustomName());
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        return drops;
    }


}
