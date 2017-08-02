package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.MoonPhases;
import org.ah.minecraft.armour.Plugin;
import org.ah.minecraft.armour.utils.ArmourUtil;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChaserController extends CustomEntityController {
    public ChaserController(int weight) {
        super(weight);
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return (b.getBiome() == Biome.PLAINS) || (b.getBiome() == Biome.DESERT) || (b.getBiome() == Biome.ICE_FLATS) || (b.getBiome() == Biome.SAVANNA);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);

        ItemStack head = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta hmeta = (LeatherArmorMeta) head.getItemMeta();
        hmeta.setColor(Color.MAROON);
        head.setItemMeta(hmeta);

        ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
        bmeta.setColor(Color.MAROON);
        body.setItemMeta(bmeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
        bometa.setColor(Color.MAROON);
        boots.setItemMeta(bometa);

        EntityEquipment zomb = zombie.getEquipment();
        zomb.setBoots(boots);
        zomb.setLeggings(ArmourUtil.createRiderLeggings());
        zomb.setChestplate(body);
        zomb.setHelmet(head);
        zomb.setItemInHand(new ItemStack(Material.BONE));

        zomb.setLeggingsDropChance(2.0F);

        zombie.setMaxHealth(100.0D);
        zombie.setHealth(100.0D);
        zombie.setCustomName("Chaser");
        zombie.setCustomNameVisible(false);
        zombie.setRemoveWhenFarAway(true);
        return zombie;
    }

    @Override
    public void update(LivingEntity e) {
        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 64, 10));
        e.playEffect(EntityEffect.HURT);
        if ((Plugin.day(e.getWorld())) || (Plugin.getMoonPhase(e.getWorld()) != MoonPhases.NEW)) {
            e.getWorld().playEffect(e.getLocation(), org.bukkit.Effect.STEP_SOUND, Material.WEB, 10);
            e.remove();
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return ("Chaser".equalsIgnoreCase(e.getCustomName())) && ((e instanceof Zombie));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.REDSTONE, 2, "Chaser essence"));
        drops.add(ArmourUtil.createRiderLeggings());
        return drops;
    }
}
