package org.ah.minecraft.armour.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class MonkeyController extends CustomEntityController {

    public MonkeyController(int weight) {
        super(weight);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(LivingEntity e) {
        // TODO Auto-generated method stub

    }
    @Override
    public boolean canSpawnThere(Block b) {
        return b.getBiome().toString().contains("JUNGLE");
    }

    @Override
    public boolean isOne(LivingEntity e) {
        return "Monkey".equals(e.getCustomName());
    }




    @Override
    public LivingEntity spawn(Location loc) {
        Zombie zombie = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        EntityEquipment zomb = zombie.getEquipment();
        zomb.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        zomb.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        zomb.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));

        zombie.setCustomName("Monkey");
        zombie.setCustomNameVisible(true);

        zombie.setBaby(true);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM);
        skull.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Monkey Head");
        meta.setOwner("Monkeycapers");
        skull.setItemMeta(meta);

        zomb.setHelmet(skull);

        zomb.setHelmetDropChance(1f);

        zombie.setMaxHealth(10);
        return zombie;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(DropGenerator.i(Material.INK_SACK, 4));
        drops.add(DropGenerator.i(Material.LEATHER, 3, "Monkey Skin"));
        return drops;
    }

}
