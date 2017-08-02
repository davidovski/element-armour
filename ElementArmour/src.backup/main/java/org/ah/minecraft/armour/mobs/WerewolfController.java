package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.MoonPhases;
import org.ah.minecraft.armour.Plugin;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class WerewolfController extends CustomEntityController {
    public WerewolfController(int weight) {
        super(weight);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Wolf wolf = (Wolf) loc.getWorld().spawnEntity(loc, EntityType.WOLF);
        wolf.setCustomName("Werewolf");
        wolf.setCustomNameVisible(false);
        return wolf;
    }

    @Override
    public void update(LivingEntity e) {
        if ("Werewolf".equals(e.getCustomName())) {
            if (e.getType() == EntityType.WOLF) {
                Wolf wolf = (Wolf) e;

                wolf.setAngry(true);
                for (Entity a : wolf.getNearbyEntities(20.0D, 20.0D, 20.0D)) {
                    if (((a instanceof Player)) || ((a instanceof Villager))) {
                        wolf.setTarget((LivingEntity) a);
                    }
                }

                wolf.setMaxHealth(20.0D);
                e.addPotionEffect(new PotionEffect(org.bukkit.potion.PotionEffectType.SPEED, 10, 2));

                if ((Plugin.getMoonPhase(wolf.getWorld()) != MoonPhases.FULL) || (Plugin.day(wolf.getWorld()))) {
                    Villager vilager = (Villager) e.getWorld().spawnEntity(e.getLocation(), EntityType.VILLAGER);
                    e.remove();
                    vilager.setProfession(Villager.Profession.BUTCHER);
                }

            } else if ((Plugin.getMoonPhase(e.getWorld()) == MoonPhases.FULL) && (!Plugin.day(e.getWorld()))) {
                spawn(e.getLocation());
                e.remove();
            }
        }

        if (((e instanceof Villager)) && (((Villager) e).getProfession() == Villager.Profession.BUTCHER) && (Plugin.getMoonPhase(e.getWorld()) == MoonPhases.FULL)
                && (!Plugin.day(e.getWorld()))) {
            spawn(e.getLocation());

            e.remove();
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return true;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        return drops;
    }
}
