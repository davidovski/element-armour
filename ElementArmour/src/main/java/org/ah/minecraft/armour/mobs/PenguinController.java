package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
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
import org.bukkit.inventory.meta.SkullMeta;

public class PenguinController extends CustomEntityController {
    private ItemStack skull;

    public PenguinController(int weight) {
        super(weight);
        skull = new ItemStack(Material.SKULL_ITEM);
        skull.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Penguin Head");
        meta.setOwner("Penguin");
        skull.setItemMeta(meta);
    }

    @Override
    public void update(LivingEntity e) {
        Zombie z = (Zombie) e;
        z.setTarget(null);
    }

    @Override
    public boolean canSpawnThere(Block b) {
        return b.getBiome() == Biome.ICE_FLATS;
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return e instanceof Zombie && e.getEquipment().getHelmet().equals(skull);
    }

    @Override
    public LivingEntity spawn(Location loc) {
        Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        EntityEquipment zomb = z.getEquipment();
        z.setCanPickupItems(false);
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta lmeta = (LeatherArmorMeta) legs.getItemMeta();
        lmeta.setColor(Color.BLACK);
        legs.setItemMeta(lmeta);

        ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
        bmeta.setColor(Color.WHITE);
        body.setItemMeta(bmeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
        bometa.setColor(Color.BLACK);
        boots.setItemMeta(bometa);

        zomb.setBoots(boots);
        zomb.setLeggings(legs);
        zomb.setChestplate(body);
        zomb.setItemInMainHand(new ItemStack(Material.AIR));


        zomb.setHelmet(skull);

        z.setCustomName("Penguin");
        z.setBaby(true);
        MobUtils.setMaxHealth(z, 3);
        z.setCustomNameVisible(false);
        try {
//            z = makeRun(z);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList();
        drops.add(DropGenerator.i(Material.RAW_FISH, 4));
        return drops;
    }
}
