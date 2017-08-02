package com.gmail.fedmanddev;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Villager;

import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.MerchantRecipeList;

public final class VillagerTradeApi extends org.bukkit.plugin.java.JavaPlugin
{
  public VillagerTradeApi() {}

  @Override
public void onEnable()
  {
    System.out.print("[VillagerTradeApi] Enabled VillagerTradeApi by Fedmand");
  }

  public static void clearTrades(Villager villager) {
    net.minecraft.server.v1_12_R1.EntityVillager entityVillager = ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftVillager)villager).getHandle();
    try {
      Field recipes = entityVillager.getClass().getDeclaredField("bu");
      recipes.setAccessible(true);
      net.minecraft.server.v1_12_R1.MerchantRecipeList list = new net.minecraft.server.v1_12_R1.MerchantRecipeList();
      recipes.set(entityVillager, list);
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  public static void addTrade(Villager villager, VillagerTrade villagerTrade) {
    net.minecraft.server.v1_12_R1.EntityVillager entityVillager = ((org.bukkit.craftbukkit.v1_12_R1.entity.CraftVillager)villager).getHandle();
    try {
      Field recipes = entityVillager.getClass().getDeclaredField("bu");
      recipes.setAccessible(true);
      MerchantRecipeList list = (MerchantRecipeList)recipes
        .get(entityVillager);
      if (VillagerTrade.hasItem2(villagerTrade)) {
        ItemStack item1 =
          CraftItemStack.asNMSCopy(VillagerTrade.getItem1(villagerTrade));
        ItemStack item2 =
          CraftItemStack.asNMSCopy(VillagerTrade.getItem2(villagerTrade));
        ItemStack rewardItem =
          CraftItemStack.asNMSCopy(VillagerTrade.getRewardItem(villagerTrade));
        list.add(new net.minecraft.server.v1_12_R1.MerchantRecipe(item1, item2, rewardItem));
      } else {
        ItemStack item1 =
          CraftItemStack.asNMSCopy(VillagerTrade.getItem1(villagerTrade));
        ItemStack rewardItem =
          CraftItemStack.asNMSCopy(VillagerTrade.getRewardItem(villagerTrade));
        list.add(new net.minecraft.server.v1_12_R1.MerchantRecipe(item1, rewardItem));
      }
      recipes.set(entityVillager, list);
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }
}
