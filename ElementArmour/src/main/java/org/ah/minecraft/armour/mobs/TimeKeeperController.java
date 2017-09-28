package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.armour.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class TimeKeeperController extends CustomEntityController {
    private ItemStack skull;
    private String string;

    public TimeKeeperController(int weight) {
        super(weight);
        skull = new ItemStack(Material.SKULL_ITEM);
        skull.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        string = ChatColor.GRAY + "TimeKeeper's Head";
        meta.setDisplayName(string);
        meta.setOwner("TikTok");
        skull.setItemMeta(meta);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Skeleton z = (Skeleton) loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
        EntityEquipment zomb = z.getEquipment();
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) legs.getItemMeta();
        lmeta.setColor(Color.MAROON);
        legs.setItemMeta(lmeta);

        ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
        bmeta.setColor(Color.OLIVE);
        body.setItemMeta(bmeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
        bometa.setColor(Color.YELLOW);
        boots.setItemMeta(bometa);

        zomb.setBoots(boots);
        zomb.setLeggings(legs);
        zomb.setChestplate(body);
        zomb.setItemInMainHand(new ItemStack(Material.STICK));


        zomb.setHelmet(skull);

        z.setCustomName("Time Keeper");
        z.setRemoveWhenFarAway(true);
        MobUtils.setMaxHealth(z, 40);
        z.setCustomNameVisible(false);
        try {
//            z = makeRun(z);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

//    public Zombie makeRun(Zombie z) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//        EntityZombie ent = ((CraftZombie) z).getHandle();
//
//        PathfinderGoalSelector goalSelector = null;
//        PathfinderGoalSelector targetSelector = null;
//        Field bField = null;
//        Field cField = null;
//        try {
//            bField = PathfinderGoalSelector.class.getDeclaredField("b");
//            bField.setAccessible(true);
//            cField = PathfinderGoalSelector.class.getDeclaredField("c");
//            cField.setAccessible(true);
//            bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
//            bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
//            cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
//            cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
//        } catch (Exception exc) {
//            exc.printStackTrace();
//        }
//        goalSelector.a(1, new PathfinderGoalAvoidTarget(ent, EntityHuman.class, 8.0F, 0.6D, 0.6D));
//        goalSelector.a(3, new PathfinderGoalRandomStroll(ent, 0.2F));
//        targetSelector.a(1, new PathfinderGoalHurtByTarget(ent, false));
//
//        bField.set(ent, goalSelector);
//        cField.set(ent, targetSelector);
//        return z;
//    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getY() > 80;
    }

    @Override
    public void update(LivingEntity e) {
        if (e.getTicksLived() % 120 == 0) {
//            e.getWorld().dropItemNaturally(e.getLocation(), new ItemStack(Material.WATCH, 2));
//
//            e.getWorld().dropItemNaturally(e.getLocation(), new ItemStack(Material.WATCH, 2));
        }
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return (((e instanceof Skeleton) && e.getEquipment().getHelmet() != null && e.getEquipment().getHelmet().hasItemMeta() && string.equals(e.getEquipment().getHelmet().getItemMeta().getDisplayName())));
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.WATCH, 2));
        drops.add(DropGenerator.i(Material.REDSTONE, 4));
        if (Math.random() > 0.5) {
            ItemStack customItem = ItemUtils.getCustomItem(Material.WATCH, "Rewind Clock", ChatColor.GOLD + "Dextra ledo rewind ut sit locus." + ChatColor.DARK_GRAY + "#" + Math.random());
            customItem.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            drops.add(customItem);
        }
        return drops;
    }
}
