package org.ah.minecraft.items;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ItemsPlus extends JavaPlugin implements Listener  {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        NamespacedKey pik = new NamespacedKey(this,"Pie");

        ShapelessRecipe rec = new ShapelessRecipe(pik, ItemUtils.getCustomItem(Material.PUMPKIN_PIE, "Apple Pie"));
        rec.addIngredient(Material.EGG);
        rec.addIngredient(Material.SUGAR);
        rec.addIngredient(Material.APPLE);
        getServer().addRecipe(rec);

        NamespacedKey jik = new NamespacedKey(this,"Jelly");

        ShapelessRecipe rec1 = new ShapelessRecipe(jik, ItemUtils.getCustomItem(Material.BEETROOT_SOUP, "Jelly"));
        rec1.addIngredient(Material.INK_SACK, 15);
        rec1.addIngredient(Material.SUGAR);
        rec1.addIngredient(Material.WATER_BUCKET);
        getServer().addRecipe(rec1);


        NamespacedKey cactusJ = new NamespacedKey(this,"cactusJuice");

        Potion potion = new Potion(PotionType.JUMP);
        ItemStack potionstack = potion.toItemStack(1);
        ItemStack cactusJuice = ItemUtils.getCustomItem(potionstack, "Cactus Juice");

        ShapelessRecipe rec2 = new ShapelessRecipe(cactusJ, cactusJuice);
        rec2.addIngredient(Material.CACTUS);
        rec2.addIngredient(Material.GLASS_BOTTLE);
        getServer().addRecipe(rec2);

        NamespacedKey flameBow = new NamespacedKey(this,"FlameBow");

        ShapedRecipe rec3 = new ShapedRecipe(flameBow, ItemUtils.getCustomItem(Material.BOW, Enchantment.ARROW_FIRE));
        rec3.shape(" /o", "/ o", " /o");
        rec3.setIngredient('/', Material.BLAZE_ROD);
        rec3.setIngredient('o', Material.STRING);
        getServer().addRecipe(rec3);

        getServer().addRecipe(new FurnaceRecipe(ItemUtils.getCustomItem(Material.BEETROOT_SEEDS, "Popcorn"), Material.SEEDS));


    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT")) {
            Player p = event.getPlayer();
            ItemStack i1 = p.getEquipment().getItemInMainHand();
            if (i1 != null && i1.getType() == Material.BEETROOT_SEEDS && i1.hasItemMeta() && "Popcorn".equals(i1.getItemMeta().getDisplayName())) {
                if ((p.getFoodLevel() < 20)) {
                    ItemUtils.removeItems(i1, 1);
                    if (p.getFoodLevel() + 2 >= 18) {
                        p.setFoodLevel(20);
                        p.setSaturation(p.getSaturation() + 1);
                    } else {
                        p.setFoodLevel(p.getFoodLevel() + 2);
                    }


                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 5f, 1f);
                    p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.BONE_BLOCK.getId(), 10);
                }

            }
        }
    }


    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("")) {
                return true;

            }
        }
        return false;

    }
}
