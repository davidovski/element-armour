package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UndeathController extends CustomEntityController {
    private ItemStack skull;
    private ItemStack legs;
    private ItemStack body;
    private ItemStack boots;
    private ItemStack air;

    public UndeathController(int weight) {
        super(weight);

        skull = new ItemStack(Material.OBSIDIAN);

        legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta hmeta = (LeatherArmorMeta) legs.getItemMeta();
        hmeta.setColor(Color.BLACK);
        legs.setItemMeta(hmeta);

        body = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
        bmeta.setColor(Color.BLACK);
        body.setItemMeta(bmeta);

        boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
        bometa.setColor(Color.BLACK);
        boots.setItemMeta(bometa);

        air = new ItemStack(Material.AIR);
    }

    @Override
    public void update(LivingEntity e) {
        e.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 40, 10, true, false), true);

        for (Player p : e.getWorld().getPlayers()) {
            double distanceSquared = p.getLocation().distanceSquared(e.getLocation());
            if (((distanceSquared < 25.0D) && (p.equals(((Zombie) e).getTarget()))) || (distanceSquared < 9.0D)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30, 180, true, false), true);
                if (p.getTicksLived() % 5 == 0) {
                    p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.OBSIDIAN, 10);
                    p.getWorld().playEffect(p.getEyeLocation(), Effect.STEP_SOUND, Material.OBSIDIAN, 10);
                }
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1, true, false), true);
                e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30, 1, true, false), true);
            }

            if (distanceSquared > 81.0D) {
                EntityEquipment equipment = e.getEquipment();
                equipment.setHelmet(air);
                equipment.setLeggings(air);
                equipment.setChestplate(air);
                equipment.setBoots(air);
            } else {
                EntityEquipment equipment = e.getEquipment();
                equipment.setHelmet(skull);
                equipment.setLeggings(legs);
                equipment.setChestplate(body);
                equipment.setBoots(boots);
                break;
            }
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Undeath".equals(e.getCustomName())) && ((e instanceof Zombie));
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(64.0D);
        zombie.setHealth(64.0D);
        zombie.setSilent(true);
        zombie.setCustomName("Undeath");
        zombie.setCustomNameVisible(false);

        EntityEquipment equipment = zombie.getEquipment();
        equipment.setHelmet(skull);
        equipment.setLeggings(legs);
        equipment.setChestplate(body);
        equipment.setBoots(boots);

        return zombie;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.INK_SACK, 4));
        if (Math.random() < 0.01) {
            drops.add(DropGenerator.i(ArmourUtil.createDarkEssence(), 2));
        }
        return drops;
    }
}
