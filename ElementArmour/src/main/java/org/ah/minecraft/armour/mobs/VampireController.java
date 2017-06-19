package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampireController extends CustomEntityController {
    public VampireController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    public long alpha = 0;


    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() < 60;
    }

    @Override
    public LivingEntity spawn(Location loc) {
        WitherSkeleton skeleton = (WitherSkeleton) loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
        EntityEquipment eqip = skeleton.getEquipment();
        skeleton.setCustomName("Vampire");
        skeleton.setCustomNameVisible(false);

        skeleton.setMaxHealth(40D);

        ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
        bmeta.setColor(Color.fromRGB(0, 0, 0));
        body.setItemMeta(bmeta);

        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) legs.getItemMeta();
        lmeta.setColor(Color.fromRGB(0, 0, 0));
        legs.setItemMeta(lmeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
        bometa.setColor(Color.fromRGB(100, 0, 0));
        boots.setItemMeta(bometa);

        ItemStack fang = new ItemStack(Material.GHAST_TEAR);
        ItemMeta meta = fang.getItemMeta();
        meta.setDisplayName("Vampire Fang");
        fang.setItemMeta(meta);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal());

        eqip.setBoots(boots);
        eqip.setLeggings(legs);
        eqip.setChestplate(body);
        eqip.setItemInHand(fang);
        eqip.setHelmet(skull);

        return skeleton;
    }

    public LivingEntity spawnBat(Location loc) {
        Bat bat = (Bat) loc.getWorld().spawnEntity(loc, EntityType.BAT);
        bat.setCustomName("Vampire");
        bat.setCustomNameVisible(false);
        bat.setMaxHealth(40D);
        return bat;
    }

    @Override
    public void update(LivingEntity e) {
        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 50, 2));
        e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 50, 3));
        e.getWorld().playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
        alpha++;
        if (alpha % 40 == 0) {
            if (Math.random() > 0.5) {
                e.getWorld().playEffect(e.getLocation(), Effect.SMOKE, 1);
                e.getWorld().playSound(e.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 2f, 2f);
                if (e.getType() == EntityType.WITHER_SKELETON) {
                    LivingEntity b = spawnBat(e.getLocation());
                    b.setHealth(e.getHealth());
                    e.remove();

                } else {
                    LivingEntity s = spawn(e.getLocation());
                    s.setHealth(e.getHealth());
                    e.remove();
                }

            }
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Vampire".equalsIgnoreCase(e.getCustomName()) && (e instanceof Bat || e instanceof WitherSkeleton);
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.SULPHUR, 4));
        drops.add(DropGenerator.i(Material.APPLE, 1));
        return drops;
    }

}
