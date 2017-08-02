package org.ah.minecraft.armour;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

public class CustomVillagerV2 {
    private Object ev;
    private Object list;
    private static final String bukkitversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

    public CustomVillagerV2(Villager villager) {
        try {
            Class<?> merchantrlist = Class.forName("net.minecraft.server." + bukkitversion + ".MerchantRecipeList");
            list = merchantrlist.newInstance();
            Class<?> craftvillager = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".entity.CraftVillager");
            Method handle = craftvillager.getMethod("getHandle", null);
            ev = handle.invoke(craftvillager.cast(villager), null);
        } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public CustomVillagerV2 addRecipe(ItemStack slotOne, ItemStack slotTwo, ItemStack output) {
        try {
            Class<?> mechantRecipe = Class.forName("net.minecraft.server." + bukkitversion + ".MerchantRecipe");
            Method add = list.getClass().getDeclaredMethod("a", mechantRecipe);
            Class<?> nmsItemStack = Class.forName("net.minecraft.server." + bukkitversion + ".ItemStack");
            Constructor<?> merchantRecipeConstructor = mechantRecipe.getDeclaredConstructor(nmsItemStack, nmsItemStack, nmsItemStack);
            Object merchantRecipeObj = merchantRecipeConstructor.newInstance(toNMSItemStack(slotOne), toNMSItemStack(slotTwo), toNMSItemStack(output));
            add.invoke(list, merchantRecipeObj);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }

    public CustomVillagerV2 addRecipe(ItemStack slotOne, ItemStack output) {
        try {
            Class<?> mechantRecipe = Class.forName("net.minecraft.server." + bukkitversion + ".MerchantRecipe");
            Method add = list.getClass().getDeclaredMethod("a", mechantRecipe);
            Class<?> nmsItemStack = Class.forName("net.minecraft.server." + bukkitversion + ".ItemStack");
            Constructor<?> merchantRecipeConstructor = mechantRecipe.getDeclaredConstructor(nmsItemStack, nmsItemStack);
            Object merchantRecipeObj = merchantRecipeConstructor.newInstance(toNMSItemStack(slotOne), toNMSItemStack(output));
            add.invoke(list, merchantRecipeObj);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | InstantiationException e) {
            e.printStackTrace();
        }
        return this;
    }

    private Object toNMSItemStack(ItemStack i) {
        try {
            Class<?> craftItemstack = Class.forName("org.bukkit.craftbukkit." + bukkitversion + ".inventory.CraftItemStack");
            Method nmsCopy = craftItemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
            return nmsCopy.invoke(craftItemstack, i);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean finish() {
        try {
            Field f = ev.getClass().getDeclaredField("bu");
            f.setAccessible(true);
            f.set(ev, list);
            return true;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            return false;
        }
    }
}
