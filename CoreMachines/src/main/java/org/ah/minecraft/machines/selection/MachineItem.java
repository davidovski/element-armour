package org.ah.minecraft.machines.selection;

import java.util.ArrayList;
import java.util.List;

import org.ah.minecraft.machines.MachineType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class MachineItem extends ItemStack {
    private List<ItemStack> ingredients;
    private MachineType type;

    public MachineItem(MachineType type) {
        super(Material.COMMAND);
        this.type = type;
        ingredients = new ArrayList<ItemStack>();
        if (type == MachineType.BREAK) {
            ingredients.add(new ItemStack(Material.IRON_PICKAXE));
            ingredients.add(new ItemStack(Material.COBBLESTONE, 8));
            setType(Material.IRON_PICKAXE);
            ItemMeta meta = getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + "Block Breaker");
            setItemMeta(meta);
        }
        if (type == MachineType.PLACE) {
            ingredients.add(new ItemStack(Material.REDSTONE));
            ingredients.add(new ItemStack(Material.STONE, 8));
            setType(Material.COBBLESTONE);
            ItemMeta meta = getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + "Block Placer");
            setItemMeta(meta);
        }
        if (type == MachineType.FARM) {
            ingredients.add(new ItemStack(Material.IRON_HOE));
            ingredients.add(new ItemStack(Material.COBBLESTONE, 8));
            ingredients.add(new ItemStack(Material.WATER_BUCKET));
            setType(Material.WOOD_HOE);
            ItemMeta meta = getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + "Mini Farmer");
            setItemMeta(meta);
        }
        listIngredients();
    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<ItemStack> ingredients) {
        this.ingredients = ingredients;
    }

    public MachineType getMachineType() {
        return type;
    }

    public void setMachineType(MachineType type) {
        this.type = type;
    }

    public void listIngredients() {

        List<String> l = new ArrayList<String>();
        l.add(ChatColor.BLUE + "Requires:");
        for (ItemStack i : ingredients) {
            String e = ChatColor.WHITE + "" + i.getAmount() + " " + i.getType().toString().replace("_", " ").toLowerCase();
            l.add(e);
            System.out.println(e);
        }
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(l);
        setItemMeta(itemMeta);
    }
}
