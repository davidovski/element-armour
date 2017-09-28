package org.ah.minecraft.items;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import net.md_5.bungee.api.ChatColor;

public class ItemsPlus extends JavaPlugin implements Listener {
    private Map<String, Location> locations;
    private Map<String, Double> hearts;

    @Override
    public void onEnable() {

        locations = new HashMap<String, Location>();
        hearts = new HashMap<String, Double>();

        getServer().getPluginManager().registerEvents(this, this);

        NamespacedKey pik = new NamespacedKey(this, "Pie");

        ShapelessRecipe rec = new ShapelessRecipe(pik, ItemUtils.getCustomItem(Material.PUMPKIN_PIE, "Apple Pie"));
        rec.addIngredient(Material.EGG);
        rec.addIngredient(Material.SUGAR);
        rec.addIngredient(Material.APPLE);
        getServer().addRecipe(rec);

        NamespacedKey jik = new NamespacedKey(this, "Jelly");

        ShapelessRecipe rec1 = new ShapelessRecipe(jik, ItemUtils.getCustomItem(Material.BEETROOT_SOUP, "Jelly"));
        rec1.addIngredient(Material.INK_SACK, 15);
        rec1.addIngredient(Material.SUGAR);
        rec1.addIngredient(Material.WATER_BUCKET);
        getServer().addRecipe(rec1);

        NamespacedKey cactusJ = new NamespacedKey(this, "cactusJuice");

        Potion potion = new Potion(PotionType.JUMP);
        ItemStack potionstack = potion.toItemStack(1);
        ItemStack cactusJuice = ItemUtils.getCustomItem(potionstack, "Cactus Juice");

        ShapelessRecipe rec2 = new ShapelessRecipe(cactusJ, cactusJuice);
        rec2.addIngredient(Material.CACTUS);
        rec2.addIngredient(Material.GLASS_BOTTLE);
        getServer().addRecipe(rec2);

        NamespacedKey flameBow = new NamespacedKey(this, "FlameBow");

        ShapedRecipe rec3 = new ShapedRecipe(flameBow, ItemUtils.getCustomItem(Material.BOW, Enchantment.ARROW_FIRE));
        rec3.shape(" /o", "/ o", " /o");
        rec3.setIngredient('/', Material.BLAZE_ROD);
        rec3.setIngredient('o', Material.STRING);
        getServer().addRecipe(rec3);

        NamespacedKey getaway = new NamespacedKey(this, "getaway");
        ItemStack customItem = ItemUtils.getCustomItem(Material.ENDER_PEARL, "Get-Away Pearl");
        customItem.addUnsafeEnchantment(Enchantment.MENDING, 1);
        ShapelessRecipe rec4 = new ShapelessRecipe(getaway, customItem);

        rec4.addIngredient(Material.ENDER_PEARL);
        rec4.addIngredient(Material.ENDER_PEARL);
        rec4.addIngredient(Material.ENDER_PEARL);

        rec4.addIngredient(Material.ENDER_PEARL);
        rec4.addIngredient(Material.DIAMOND);
        rec4.addIngredient(Material.ENDER_PEARL);

        rec4.addIngredient(Material.ENDER_PEARL);
        rec4.addIngredient(Material.ENDER_PEARL);
        rec4.addIngredient(Material.ENDER_PEARL);

        getServer().addRecipe(rec4);

        getServer().addRecipe(new FurnaceRecipe(ItemUtils.getCustomItem(Material.BEETROOT_SEEDS, "Popcorn"), Material.SEEDS));

        loadAll(this);

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER && ( (LivingEntity)event.getEntity()).getHealth() - event.getFinalDamage() < 0) {
            Player p = (Player) event.getEntity();

            if (locations.containsKey(p.getName())) {
                for (ItemStack i1 : p.getInventory()) {
                    if (i1 != null && i1.getType() == Material.WATCH && i1.hasItemMeta() && "Rewind Clock".equals(i1.getItemMeta().getDisplayName()) && i1.containsEnchantment(Enchantment.DURABILITY)) {
                        ItemUtils.removeItem(i1);
                        event.setCancelled(true);
                        p.teleport(locations.get(p.getName()));
                        p.setHealth(hearts.get(p.getName()));
                        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));
                        FireworkEffectPlayer f = new FireworkEffectPlayer();
                        try {
                            f.playFirework(p.getWorld(), p.getLocation(), FireworkEffect.builder().withColor(Color.OLIVE).withFade(Color.BLUE).build());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT")) {
            Player p = event.getPlayer();
            ItemStack i1 = p.getEquipment().getItemInMainHand();
            if (i1 != null && i1.getType() == Material.ENDER_PEARL && i1.hasItemMeta() && "Get-Away Pearl".equals(i1.getItemMeta().getDisplayName())) {

                ItemUtils.removeItems(i1, 1);
                if (p.getBedSpawnLocation() == null) {
                    p.teleport(Bukkit.getWorld("world").getSpawnLocation());
                } else {
                    p.teleport(p.getBedSpawnLocation());
                }
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1f, 1f);
                // p.getWorld().playEffect(p.getLocation(), Effect.ITEM_BREAK, Material.ENDER_PEARL);

                event.setCancelled(true);
            }

            if (i1 != null && i1.getType() == Material.WATCH && i1.hasItemMeta() && "Rewind Clock".equals(i1.getItemMeta().getDisplayName()) && i1.containsEnchantment(Enchantment.DURABILITY)) {
                locations.put(p.getName(), p.getLocation());
                hearts.put(p.getName(), p.getHealth());
                p.sendMessage("Rewind Location set! " + ChatColor.GRAY + "[" + p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ()
                        + "]");
                event.setCancelled(true);
                saveAll(this);
            }
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
        saveAll(this);
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

    public void loadAll(JavaPlugin plugin) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("rewind.locations");
        if (configurationSection == null) {
            configurationSection = plugin.getConfig().createSection("rewind.locations");
        }
        Map<String, Object> homeLocations = configurationSection.getValues(false);

        if (homeLocations != null) {
            Bukkit.getServer().getLogger().info("loading rewind...");
            for (Map.Entry<String, Object> entry : homeLocations.entrySet()) {
                String playerName = entry.getKey();
                Object locationString = entry.getValue();

                if (locationString != null) {
                    locations.put(playerName, LocationUtil.stringToLocation(this.getServer(), locationString.toString()));
                }
            }
        }

        ConfigurationSection configurationSectionHP = plugin.getConfig().getConfigurationSection("rewind.hearts");
        if (configurationSectionHP == null) {
            configurationSectionHP = plugin.getConfig().createSection("rewind.hearts");
        }
        Map<String, Object> hearts = configurationSectionHP.getValues(false);

        if (hearts != null) {
            Bukkit.getServer().getLogger().info("loading rewind...");
            for (Map.Entry<String, Object> entry : hearts.entrySet()) {
                String playerName = entry.getKey();
                Object locationString = entry.getValue();

                if (locationString != null) {
                    this.hearts.put(playerName, Double.parseDouble(entry.getValue().toString()));
                }
            }
        }
    }

    public void saveAll(JavaPlugin plugin) {

        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("rewind.hearts");
        if (configurationSection == null) {
            configurationSection = plugin.getConfig().createSection("rewind.hearts");
        }
        for (Map.Entry<String, Double> entry : hearts.entrySet()) {
            configurationSection.set(entry.getKey(), entry.getValue().toString());
        }

        ConfigurationSection configurationSection2 = plugin.getConfig().getConfigurationSection("rewind.locations");
        if (configurationSection2 == null) {
            configurationSection2 = plugin.getConfig().createSection("rewind.locations");
        }
        for (Map.Entry<String, Location> entry : locations.entrySet()) {
            configurationSection2.set(entry.getKey(), LocationUtil.locationToString(entry.getValue()));
        }
        plugin.saveConfig();
    }
}
