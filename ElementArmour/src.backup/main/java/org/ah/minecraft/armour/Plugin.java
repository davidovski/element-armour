package org.ah.minecraft.armour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.ah.minecraft.armour.mobs.BanditController;
import org.ah.minecraft.armour.mobs.BloodBatController;
import org.ah.minecraft.armour.mobs.BloodRatController;
import org.ah.minecraft.armour.mobs.BouncerController;
import org.ah.minecraft.armour.mobs.ChaserController;
import org.ah.minecraft.armour.mobs.EntityTypeController;
import org.ah.minecraft.armour.mobs.EyeController;
import org.ah.minecraft.armour.mobs.GhostController;
import org.ah.minecraft.armour.mobs.KnightController;
import org.ah.minecraft.armour.mobs.MinerController;
import org.ah.minecraft.armour.mobs.MobUtils;
import org.ah.minecraft.armour.mobs.MonkeyController;
import org.ah.minecraft.armour.mobs.RedGuardianController;
import org.ah.minecraft.armour.mobs.SkeletonFarmerController;
import org.ah.minecraft.armour.mobs.VampireController;
import org.ah.minecraft.armour.mobs.WeightedEntityController;
import org.ah.minecraft.armour.mobs.WispController;
import org.ah.minecraft.armour.utils.AirUtils;
import org.ah.minecraft.armour.utils.ArmourUtil;
import org.ah.minecraft.armour.utils.EarthUtils;
import org.ah.minecraft.armour.utils.FireUtils;
import org.ah.minecraft.armour.utils.FreezeUtils;
import org.ah.minecraft.armour.utils.IceUtils;
import org.ah.minecraft.armour.utils.LavaUtils;
import org.ah.minecraft.armour.utils.LightningUtils;
import org.ah.minecraft.armour.utils.MetalUtils;
import org.ah.minecraft.armour.utils.PoisonUtils;
import org.ah.minecraft.armour.utils.SandUtils;
import org.ah.minecraft.armour.utils.SkyUtils;
import org.ah.minecraft.armour.utils.WaterUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Difficulty;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public final class Plugin extends JavaPlugin implements Listener {
    public List<String> crafterLocations;
    public List<String> combinerLocations;
    protected ItemStack monkeySkull;
    private HashMap<Player, FallingBlock> holdingblock;
    public static Player p;
    public List<WeightedEntityController> controllers;
    public List<WeightedEntityController> nethercontrollers;
    private int maxweight;
    private HashMap<Player, Integer> punchDelay;
    private BossBar bb;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Element Armour has been enabled!");

        PluginManager manager = getServer().getPluginManager();
        getServer().getPluginManager().registerEvents(this, this);

        crafterLocations = new ArrayList();
        combinerLocations = new ArrayList();

        holdingblock = new HashMap();
        punchDelay = new HashMap();

        loadAll(this);

        controllers = new ArrayList();
        nethercontrollers = new ArrayList();
        controllers.add(new BanditController(90));
        controllers.add(new BouncerController(40));

        controllers.add(new GhostController(60));
        controllers.add(new KnightController(70));
        controllers.add(new BloodBatController(0));
        controllers.add(new BloodRatController(0));
        controllers.add(new RedGuardianController(0));

        controllers.add(new MinerController(40));
        controllers.add(new org.ah.minecraft.armour.mobs.TrapController(10));

        controllers.add(new MonkeyController(140));
        controllers.add(new ChaserController(0));

        controllers.add(new SkeletonFarmerController(90));
        controllers.add(new org.ah.minecraft.armour.mobs.SkeletonWariorController(120));

        controllers.add(new VampireController(60));
        controllers.add(new EyeController(40));

        controllers.add(new WispController(10));
        controllers.add(new org.ah.minecraft.armour.mobs.UndeathController(1));

        nethercontrollers.add(new org.ah.minecraft.armour.mobs.NetherKnightController(70));
        nethercontrollers.add(new org.ah.minecraft.armour.mobs.DeamonController(50));

        setDifficulties();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                doEveryTick();
            }
        }, 1L, 1L);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {

                    for (LivingEntity e : w.getLivingEntities()) {

                        if ("Bow Master".equalsIgnoreCase(e.getCustomName())) {
                            if (Math.random() > 0.5D) {
                                for (int i = 0; i < 2; i++) {
                                    Bat wisp = (Bat) w.spawnEntity(e.getLocation().add(0.0D, 2.0D, 0.0D), EntityType.BAT);
                                    wisp.setCustomName("Wisp");
                                    wisp.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 10));
                                }
                            } else {
                                for (int i = 0; i < 4; i++) {
                                    for (Entity n : e.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
                                        if ((n instanceof LivingEntity)) {
                                            Arrow a = w.spawnArrow(e.getEyeLocation().add(0.0D, 1.0D, 0.0D),
                                                    n.getLocation().toVector().add(e.getEyeLocation().add(0.0D, 1.0D, 0.0D).toVector()), 1.0F, 1.0F);
                                            a.setShooter(e);
                                            a.setCritical(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

                , 300L, 300L);

    }

    private void setDifficulties() {
        System.out.println("Seting Difficulties");
        maxweight = 0;
        for (WeightedEntityController w : controllers) {
            maxweight += w.getWeight();
        }
        int mulitplier = 1;
        Difficulty d = getServer().getWorlds().get(0).getDifficulty();
        if (d == Difficulty.PEACEFUL) {
            mulitplier = 6;
        } else if (d == Difficulty.EASY) {
            mulitplier = 5;
        } else if (d == Difficulty.NORMAL) {
            mulitplier = 4;
        } else if (d == Difficulty.HARD) {
            mulitplier = 3;
        } else {
            mulitplier = 0;
        }

        maxweight *= mulitplier;
    }

    @Override
    public void onDisable() {
        saveAll(this);
        Bukkit.getLogger().info("Element Armour has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("difficulty")) {
                setDifficulties();
            }
            if (cmd.getName().equalsIgnoreCase("ElementArmour")) {
                p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Element Armour version 1.0");
                p.sendMessage(ChatColor.AQUA + "craft the armour by placing an iron armour piece in a " + ChatColor.GOLD + "Element Crafter" + ChatColor.AQUA
                        + " with the element of your choice above it:");
                p.sendMessage(ChatColor.BLUE + "Water: " + ChatColor.DARK_BLUE + "water bucket,");
                p.sendMessage(ChatColor.GREEN + "Earth: " + ChatColor.DARK_GREEN + "smooth stone,");
                p.sendMessage(ChatColor.GRAY + "Air: " + ChatColor.DARK_GRAY + "feather,");
                p.sendMessage(ChatColor.RED + "Fire: " + ChatColor.DARK_RED + "lava bucket,");
                p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "======================================");
                p.sendMessage(ChatColor.BOLD + "Mod creator: andavdor (davidovski)");
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Earth")) {
                p.getInventory().addItem(new ItemStack[] { EarthUtils.createEarthBoots() });
                p.getInventory().addItem(new ItemStack[] { EarthUtils.createEarthHelmet() });
                p.getInventory().addItem(new ItemStack[] { EarthUtils.createEarthChestplate() });
                p.getInventory().addItem(new ItemStack[] { EarthUtils.createEarthLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Metal")) {
                p.getInventory().addItem(new ItemStack[] { MetalUtils.createMetalBoots() });
                p.getInventory().addItem(new ItemStack[] { MetalUtils.createMetalChestplate() });
                p.getInventory().addItem(new ItemStack[] { MetalUtils.createMetalHelmet() });
                p.getInventory().addItem(new ItemStack[] { MetalUtils.createMetalLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Sky")) {
                p.getInventory().addItem(new ItemStack[] { SkyUtils.createSkyBoots() });
                p.getInventory().addItem(new ItemStack[] { SkyUtils.createSkyChestplate() });
                p.getInventory().addItem(new ItemStack[] { SkyUtils.createSkyHelmet() });
                p.getInventory().addItem(new ItemStack[] { SkyUtils.createSkyLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Lava")) {
                p.getInventory().addItem(new ItemStack[] { LavaUtils.createLavaBoots() });
                p.getInventory().addItem(new ItemStack[] { LavaUtils.createLavaChestplate() });
                p.getInventory().addItem(new ItemStack[] { LavaUtils.createLavaHelmet() });
                p.getInventory().addItem(new ItemStack[] { LavaUtils.createLavaLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Ice")) {
                p.getInventory().addItem(new ItemStack[] { IceUtils.createIceBoots() });
                p.getInventory().addItem(new ItemStack[] { IceUtils.createIceChestplate() });
                p.getInventory().addItem(new ItemStack[] { IceUtils.createIceHelmet() });
                p.getInventory().addItem(new ItemStack[] { IceUtils.createIceLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Poison")) {
                p.getInventory().addItem(new ItemStack[] { PoisonUtils.createBoots() });
                p.getInventory().addItem(new ItemStack[] { PoisonUtils.createChestplate() });
                p.getInventory().addItem(new ItemStack[] { PoisonUtils.createHelmet() });
                p.getInventory().addItem(new ItemStack[] { PoisonUtils.createLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Freeze")) {
                p.getInventory().addItem(new ItemStack[] { FreezeUtils.createBoots() });
                p.getInventory().addItem(new ItemStack[] { FreezeUtils.createChestplate() });
                p.getInventory().addItem(new ItemStack[] { FreezeUtils.createHelmet() });
                p.getInventory().addItem(new ItemStack[] { FreezeUtils.createLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Sand")) {
                p.getInventory().addItem(new ItemStack[] { SandUtils.createBoots() });
                p.getInventory().addItem(new ItemStack[] { SandUtils.createChestplate() });
                p.getInventory().addItem(new ItemStack[] { SandUtils.createHelmet() });
                p.getInventory().addItem(new ItemStack[] { SandUtils.createLeggings() });
                return true;
            }
            if (cmd.getName().equalsIgnoreCase("Lightning")) {
                p.getInventory().addItem(new ItemStack[] { LightningUtils.createBoots() });
                p.getInventory().addItem(new ItemStack[] { LightningUtils.createChestplate() });
                p.getInventory().addItem(new ItemStack[] { LightningUtils.createHelmet() });
                p.getInventory().addItem(new ItemStack[] { LightningUtils.createLeggings() });
                return true;
            }

            if (cmd.getName().equalsIgnoreCase("Shop")) {
                MerchantInventory inv = (MerchantInventory) getServer().createInventory(p, org.bukkit.event.inventory.InventoryType.MERCHANT);

                p.openInventory(inv);
            } else if (!cmd.getName().equalsIgnoreCase("sea")) {
                if (cmd.getName().equalsIgnoreCase("spawnTower")) {
                    createTower(p.getLocation().getBlock());
                } else {
                    if (cmd.getName().equalsIgnoreCase("Fire")) {
                        p.getInventory().addItem(new ItemStack[] { FireUtils.createFireBoots() });
                        p.getInventory().addItem(new ItemStack[] { FireUtils.createFireHelmet() });
                        p.getInventory().addItem(new ItemStack[] { FireUtils.createFireChestplate() });
                        p.getInventory().addItem(new ItemStack[] { FireUtils.createFireLeggings() });
                        return true;
                    }
                    if (cmd.getName().equalsIgnoreCase("Air")) {
                        p.getInventory().addItem(new ItemStack[] { AirUtils.createAirBoots() });
                        p.getInventory().addItem(new ItemStack[] { AirUtils.createAirHelmet() });
                        p.getInventory().addItem(new ItemStack[] { AirUtils.createAirChestplate() });
                        p.getInventory().addItem(new ItemStack[] { AirUtils.createAirLeggings() });
                        return true;
                    }
                    if (cmd.getName().equalsIgnoreCase("Water")) {
                        p.getInventory().addItem(new ItemStack[] { WaterUtils.createWaterBoots() });
                        p.getInventory().addItem(new ItemStack[] { WaterUtils.createWaterHelmet() });
                        p.getInventory().addItem(new ItemStack[] { WaterUtils.createWaterChestplate() });
                        p.getInventory().addItem(new ItemStack[] { WaterUtils.createWaterLeggings() });
                        return true;
                    }
                    if (cmd.getName().equalsIgnoreCase("Ninja")) {
                        p.getInventory().addItem(new ItemStack[] { Ninja.createShurikenItemStack() });
                        p.getInventory().addItem(new ItemStack[] { ArmourUtil.createDarkEssence() });
                        p.getInventory().addItem(new ItemStack[] { ArmourUtil.createLightEssence() });

                        return true;
                    }
                    if (cmd.getName().equalsIgnoreCase("Bouncer")) {
                        p.getInventory().addItem(new ItemStack[] { ArmourUtil.createBouncerBoots() });
                        return true;
                    }
                    if (cmd.getName().equalsIgnoreCase("setMonkeyHead")) {
                        monkeySkull = new ItemStack(p.getItemInHand());
                        saveAll(this);
                        return true;
                    }
                    if (cmd.getName().equalsIgnoreCase("giveColorArmour")) {
                        if ((args.length > 0) && (args.length < 4)) {
                            int r = Integer.parseInt(args[0]);
                            int g = Integer.parseInt(args[1]);
                            int b = Integer.parseInt(args[2]);
                            Inventory inv = p.getInventory();

                            ItemStack head = new ItemStack(Material.LEATHER_HELMET);
                            LeatherArmorMeta hmeta = (LeatherArmorMeta) head.getItemMeta();
                            hmeta.setColor(Color.fromRGB(r, g, b));
                            head.setItemMeta(hmeta);

                            ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
                            LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
                            bmeta.setColor(Color.fromRGB(r, g, b));
                            body.setItemMeta(bmeta);

                            ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
                            LeatherArmorMeta lmeta = (LeatherArmorMeta) legs.getItemMeta();
                            lmeta.setColor(Color.fromRGB(r, g, b));
                            legs.setItemMeta(lmeta);

                            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                            LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
                            bometa.setColor(Color.fromRGB(r, g, b));
                            boots.setItemMeta(bometa);

                            inv.addItem(new ItemStack[] { head });
                            inv.addItem(new ItemStack[] { body });
                            inv.addItem(new ItemStack[] { legs });
                            inv.addItem(new ItemStack[] { boots });

                            return true;
                        }
                        return false;
                    }
                }
            }
        }

        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if ((!p.hasPlayedBefore()) && (getServer().getOperators().isEmpty())) {
            p.setOp(true);
        }
    }

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.ARROW) {
            Arrow a = (Arrow) event.getEntity();
            ProjectileSource shooter = a.getShooter();
            if ((shooter instanceof Player)) {
                Player p = (Player) shooter;
                if (ArmourUtil.checkHoldingWarpBow(p)) {
                    p.teleport(new Location(a.getWorld(), a.getLocation().getX(), a.getLocation().getY(), a.getLocation().getZ()));
                    a.remove();
                    p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.ARROW) });
                }
            }

            if ((shooter instanceof Skeleton)) {
                Skeleton s = (Skeleton) shooter;
                if ("Bow Master".equals(s.getCustomName())) {
                    a.remove();
                }
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if ((event.getEntity() instanceof Arrow)) {
            Arrow a = (Arrow) event.getEntity();
            if ((a.getShooter() instanceof Skeleton)) {
                Skeleton s = (Skeleton) a.getShooter();
                if ("Bow Master".equals(s.getCustomName())) {
                    a.setCritical(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        FreezeUtils.onPlayerInteractEntity(event, this);
    }

    public static void hakaiOutToChat(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.ENTITY_CAT_PURR, 1.0F, 1.0F);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0F, 1.0F);
            p.sendMessage(ChatColor.BLACK + "<" + ChatColor.BOLD + ChatColor.DARK_RED + "Hakai" + ChatColor.RESET + ChatColor.BLACK + "> " + ChatColor.RED + message);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if ((p.getItemInHand().getType() == Material.FLINT_AND_STEEL) && (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Block bl = event.getClickedBlock();
            if (bl.getType() == Material.NETHERRACK) {
                getLogger().info("lit a block");
                List<Block> glowstone = new ArrayList();
                glowstone.add(bl.getLocation().add(2.0D, 0.0D, 0.0D).getBlock());
                glowstone.add(bl.getLocation().add(2.0D, 0.0D, 1.0D).getBlock());
                glowstone.add(bl.getLocation().add(2.0D, 0.0D, -1.0D).getBlock());

                glowstone.add(bl.getLocation().add(-1.0D, 0.0D, 2.0D).getBlock());
                glowstone.add(bl.getLocation().add(0.0D, 0.0D, 2.0D).getBlock());
                glowstone.add(bl.getLocation().add(1.0D, 0.0D, 2.0D).getBlock());

                glowstone.add(bl.getLocation().add(-1.0D, 0.0D, -2.0D).getBlock());
                glowstone.add(bl.getLocation().add(0.0D, 0.0D, -2.0D).getBlock());
                glowstone.add(bl.getLocation().add(1.0D, 0.0D, -2.0D).getBlock());

                glowstone.add(bl.getLocation().add(-2.0D, 0.0D, 0.0D).getBlock());
                glowstone.add(bl.getLocation().add(-2.0D, 0.0D, 1.0D).getBlock());
                glowstone.add(bl.getLocation().add(-2.0D, 0.0D, -1.0D).getBlock());

                if (areAllOneType(Material.GLOWSTONE, glowstone)) {
                    getLogger().info("has lapis!");
                    List<Block> lapiss = new ArrayList();
                    lapiss.add(bl.getLocation().add(-1.0D, 0.0D, 1.0D).getBlock());
                    lapiss.add(bl.getLocation().add(0.0D, 0.0D, 1.0D).getBlock());
                    lapiss.add(bl.getLocation().add(1.0D, 0.0D, 1.0D).getBlock());

                    lapiss.add(bl.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock());
                    lapiss.add(bl.getLocation().add(1.0D, 0.0D, 0.0D).getBlock());

                    lapiss.add(bl.getLocation().add(-1.0D, 0.0D, -1.0D).getBlock());
                    lapiss.add(bl.getLocation().add(0.0D, 0.0D, -1.0D).getBlock());
                    lapiss.add(bl.getLocation().add(1.0D, 0.0D, -1.0D).getBlock());

                    if (areAllOneType(Material.LAPIS_BLOCK, lapiss)) {
                        getLogger().info("has glowstone!");
                        List<Block> wood = new ArrayList();
                        wood.add(bl.getLocation().add(-2.0D, 1.0D, -2.0D).getBlock());
                        wood.add(bl.getLocation().add(-2.0D, 2.0D, -2.0D).getBlock());

                        wood.add(bl.getLocation().add(2.0D, 1.0D, -2.0D).getBlock());
                        wood.add(bl.getLocation().add(2.0D, 2.0D, -2.0D).getBlock());

                        wood.add(bl.getLocation().add(-2.0D, 1.0D, 2.0D).getBlock());
                        wood.add(bl.getLocation().add(-2.0D, 2.0D, 2.0D).getBlock());

                        wood.add(bl.getLocation().add(2.0D, 1.0D, 2.0D).getBlock());
                        wood.add(bl.getLocation().add(2.0D, 2.0D, 2.0D).getBlock());

                        if (areAllOneType(Material.LOG, wood)) {
                            getLogger().info("has wood!");
                            bl.getWorld().playSound(bl.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 2.0F, 1.0F);
                            Block fire = bl.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
                            fire.setType(Material.AIR);

                            Block crafter = bl.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
                            crafter.setType(Material.DISPENSER);

                            Dispenser dispenser = (Dispenser) crafter.getState();
                            Inventory inventory = dispenser.getInventory();
                            dispenser.setCustomName("Elemental Crafter");
                            inventory.setItem(4, new ItemStack(Material.DIAMOND));

                            crafterLocations.add(LocationUtil.locationToString(crafter.getLocation()));

                            saveAll(this);

                            bl.getWorld().strikeLightningEffect(bl.getLocation());
                            bl.getWorld().spawnEntity(bl.getLocation().add(2.0D, 2.0D, 2.0D), EntityType.THROWN_EXP_BOTTLE);
                            bl.getWorld().spawnEntity(bl.getLocation().add(-2.0D, 2.0D, 2.0D), EntityType.THROWN_EXP_BOTTLE);
                            bl.getWorld().spawnEntity(bl.getLocation().add(2.0D, 2.0D, -2.0D), EntityType.THROWN_EXP_BOTTLE);
                            bl.getWorld().spawnEntity(bl.getLocation().add(-2.0D, 2.0D, -2.0D), EntityType.THROWN_EXP_BOTTLE);
                            crafter.getWorld().createExplosion(crafter.getX(), crafter.getY(), crafter.getZ(), 3.0F, false, false);
                            for (Block b : glowstone) {
                                Block block = b.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
                                block.setType(Material.STEP);
                                block.setData((byte) 6);
                            }

                            for (Block b : lapiss) {
                                Block block = b.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
                                block.setType(Material.STEP);
                                block.setData((byte) 6);

                                b.setType(Material.STONE);
                            }

                            crafter.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
                            crafter.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
                            crafter.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
                            crafter.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);

                            crafter.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setData((byte) 1);
                            crafter.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setData((byte) 0);
                            crafter.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setData((byte) 3);
                            crafter.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setData((byte) 2);

                            for (Block b : wood) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }

        if (p.getItemInHand().getType() == Material.AIR) {
            if ((!punchDelay.containsKey(p)) || (punchDelay.get(p).intValue() < 0)) {
                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    EarthUtils.checkPunch(p);
                    WaterUtils.checkPunch(p);
                    FireUtils.checkPunch(p);
                    SkyUtils.checkPunch(p);
                    IceUtils.checkPunch(p);
                    LavaUtils.checkPunch(p);
                    PoisonUtils.checkPunch(p);

                    punchDelay.put(p, Integer.valueOf(5));
                }

                if ((event.getAction() == Action.LEFT_CLICK_BLOCK) || (event.getAction() == Action.LEFT_CLICK_AIR)) {
                    punchDelay.put(p, Integer.valueOf(10));
                    MetalUtils.checkPunch(p);
                    LightningUtils.checkPunch(p);
                    AirUtils.checkPunch(p);
                }
            }

            if (EarthUtils.checkForHelmet(p)) {
                if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType() == Material.STONE)) {
                    Block block = event.getClickedBlock();
                    block.setType(Material.AIR);
                    Byte blockData = Byte.valueOf((byte) 5);
                    FallingBlock b = p.getWorld().spawnFallingBlock(p.getLocation().add(0.0D, 1.62D, 0.0D), Material.STONE, blockData.byteValue());
                    holdingblock.put(p, b);
                    b.setGravity(false);
                }

                if ((event.getAction() == Action.LEFT_CLICK_AIR) && (holdingblock.containsKey(p))) {
                    holdingblock.get(p).setGravity(true);
                    holdingblock.get(p).setVelocity(p.getLocation().getDirection().multiply(2));
                    holdingblock.remove(p);
                }
            }
        }
    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        Entity e = event.getEntity();
        if ((event.getEntityType() == EntityType.FALLING_BLOCK) && (!holdingblock.containsValue(e))) {
            Byte blockData = Byte.valueOf((byte) 5);
            if (((FallingBlock) e).getBlockData() == blockData.byteValue()) {
                event.setCancelled(true);
                Location explosion = e.getLocation();
                World w = explosion.getWorld();
                w.createExplosion(explosion.getX(), explosion.getY(), explosion.getZ(), 5.0F, false, false);
                w.playSound(e.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, -4.0F, 12.0F);
            }
        }
    }

    public void doEveryTick() {
        CraftingUtil.crafterCheck(this);
        CombiningUtil.crafterCheck(this);
        boolean hasBoss = false;
        for (World w : Bukkit.getWorlds()) {
            for (Entity entity : w.getEntities()) {
                if ((entity instanceof LivingEntity)) {
                    LivingEntity e = (LivingEntity) entity;
                    for (EntityTypeController c : controllers) {
                        c.checkAndUpdate(e);
                    }
                    for (EntityTypeController c : nethercontrollers) {
                        c.checkAndUpdate(e);
                    }
                    if ("Hakai".equals(e.getCustomName())) {
                        Skeleton hakai = (Skeleton) e;

                        hasBoss = true;
                        if (bb != null) {
                            for (Player player : getServer().getOnlinePlayers()) {
                                bb.addPlayer(player);
                            }
                            bb.setProgress(e.getHealth() / 1000.0D);
                            if (e.getHealth() < 200.0D) {
                                if (e.getHealth() < 100.0D) {
                                    if (e.getHealth() < 50.0D) {
                                        if (e.getTicksLived() % 2 == 0) {
                                            bb.setColor(BarColor.WHITE);
                                        } else {
                                            bb.setColor(BarColor.RED);
                                        }

                                    } else if (e.getTicksLived() / 5 % 2 == 0) {
                                        bb.setColor(BarColor.WHITE);
                                    } else {
                                        bb.setColor(BarColor.RED);
                                    }

                                } else if (e.getTicksLived() / 20 % 2 == 0) {
                                    bb.setColor(BarColor.WHITE);
                                } else {
                                    bb.setColor(BarColor.RED);
                                }

                            } else {
                                bb.setColor(BarColor.RED);
                            }
                        }

                        hakai.playEffect(EntityEffect.SHEEP_EAT);
                        hakai.setFallDistance(0.0F);
                        hakai.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100000, 255));
                        hakai.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 2));
                        hakai.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100000, 0));
                        hakai.setSilent(true);
                        hakai.setCustomNameVisible(true);
                        hakai.eject();

                        hakai.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 1));

                        if (e.getTicksLived() % 400 == 20) {
                            Random r = new Random();
                            int i = r.nextInt(4);
                            if (i == 0) {
                                hakaiOutToChat("Face my wrath!");
                            } else if (i == 1) {
                                hakaiOutToChat("You unleased this upon yourself!");
                            } else if (i == 2) {
                                hakaiOutToChat("Now die!");
                            } else if (i == 3) {
                                hakaiOutToChat("Muhahaha!");
                            }
                        }

                        if (e.getTicksLived() % 60 == 0) {
                            hakai.getWorld().playSound(hakai.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1.0F, 1.0F);
                        }
                        if (e.getTicksLived() % 2 == 0) {
                            hakai.getWorld().playSound(hakai.getLocation(), Sound.ENTITY_ZOMBIE_STEP, 0.4F, 1.0F);
                        }
                        if (((e.getTicksLived() % 100 == 0) || (e.getTicksLived() % 100 == 5) || (e.getTicksLived() % 100 == 15)) && (hakai.getTarget() != null)) {
                            SmallFireball fireBall = (SmallFireball) hakai.getWorld().spawnEntity(hakai.getEyeLocation().add(hakai.getLocation().getDirection()),
                                    EntityType.SMALL_FIREBALL);
                            fireBall.setDirection(hakai.getTarget().getEyeLocation().subtract(hakai.getEyeLocation()).toVector());
                            fireBall.setGlowing(true);
                            fireBall.setShooter(hakai);
                            fireBall.setFireTicks(40);
                            fireBall.setVelocity(hakai.getTarget().getEyeLocation().subtract(hakai.getEyeLocation()).toVector());
                            hakai.getWorld().playSound(hakai.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.4F, 1.0F);
                        }

                    }

                } else if (entity.getType() == EntityType.DROPPED_ITEM) {
                    Item i = (Item) entity;
                    ItemStack is = i.getItemStack();
                    Dispenser dispenser2;
                    if (is.getType() == Material.EMERALD) {
                        if (i.getLocation().getBlock().getType() == Material.CAULDRON) {
                            Block bl = i.getLocation().getBlock();
                            Object glowstone = new ArrayList();
                            ((List) glowstone).add(bl.getLocation().add(1.0D, -1.0D, 0.0D).getBlock());
                            ((List) glowstone).add(bl.getLocation().add(0.0D, -1.0D, 0.0D).getBlock());
                            ((List) glowstone).add(bl.getLocation().add(-1.0D, -1.0D, 0.0D).getBlock());
                            if (areAllOneType(Material.GLOWSTONE, (List) glowstone)) {
                                combinerLocations.add(LocationUtil.locationToStringRounded(bl.getLocation()));
                                i.remove();

                                bl.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().setType(Material.DISPENSER);
                                bl.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().setData((byte) 6);
                                Dispenser dispenser1 = (Dispenser) bl.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().getState();
                                dispenser1.setCustomName("Elemental Combiner Slot 1");

                                bl.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setType(Material.DISPENSER);
                                bl.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setData((byte) 6);
                                dispenser2 = (Dispenser) bl.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().getState();
                                dispenser2.setCustomName("Elemental Combiner Slot 2");

                                bl.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setType(Material.NETHER_FENCE);
                                bl.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setType(Material.NETHER_FENCE);

                                bl.getLocation().add(1.0D, -1.0D, 0.0D).getBlock().setType(Material.OBSIDIAN);
                                bl.getLocation().add(-1.0D, -1.0D, 0.0D).getBlock().setType(Material.OBSIDIAN);
                                bl.getLocation().add(0.0D, -1.0D, -1.0D).getBlock().setType(Material.OBSIDIAN);
                                bl.getLocation().add(0.0D, -1.0D, 1.0D).getBlock().setType(Material.OBSIDIAN);

                                bl.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setType(Material.STONE_BUTTON);
                                bl.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setType(Material.STONE_BUTTON);
                                bl.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setData((byte) 5);
                                bl.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setType(Material.STONE_BUTTON);
                                bl.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setData((byte) 5);

                                bl.getLocation().getBlock().setData((byte) 3);
                            }
                        }
                    } else if (is.getType() == Material.TOTEM) {
                        Block bl = i.getLocation().getBlock();
                        if ((i.getLocation().getBlock().getType() == Material.CAULDRON) && (is.hasItemMeta()) && ("Hakai".equals(is.getItemMeta().getDisplayName()))) {
                            List<Block> stairs = new ArrayList<Block>();
                            ((List) stairs).add(bl.getLocation().add(1.0D, -1.0D, 0.0D).getBlock());
                            ((List) stairs).add(bl.getLocation().add(-1.0D, -1.0D, 0.0D).getBlock());
                            ((List) stairs).add(bl.getLocation().add(0.0D, -1.0D, -1.0D).getBlock());
                            ((List) stairs).add(bl.getLocation().add(0.0D, -1.0D, 1.0D).getBlock());
                            if (areAllOneType(Material.NETHER_BRICK_STAIRS, stairs)) {
                                i.remove();
                                for (Block block : stairs) {
                                    block.getWorld().createExplosion(block.getLocation(), 4.0F);
                                }

                                Skeleton skeleton = (Skeleton) i.getWorld().spawnEntity(i.getLocation(), EntityType.SKELETON);
                                EntityEquipment eq = skeleton.getEquipment();
                                int r = 50;

                                ItemStack body = new ItemStack(Material.LEATHER_CHESTPLATE);
                                LeatherArmorMeta bmeta = (LeatherArmorMeta) body.getItemMeta();
                                bmeta.setColor(Color.fromRGB(r, 0, 0));
                                body.setItemMeta(bmeta);

                                ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
                                LeatherArmorMeta lmeta = (LeatherArmorMeta) legs.getItemMeta();
                                lmeta.setColor(Color.fromRGB(r, 0, 0));
                                legs.setItemMeta(lmeta);

                                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                                LeatherArmorMeta bometa = (LeatherArmorMeta) boots.getItemMeta();
                                bometa.setColor(Color.fromRGB(r, 0, 0));
                                boots.setItemMeta(bometa);

                                eq.setHelmet(new ItemStack(Material.NETHER_BRICK));
                                eq.setChestplate(body);
                                eq.setLeggings(legs);
                                eq.setBoots(boots);

                                skeleton.setCustomName("Hakai");

                                eq.setItemInMainHand(new ItemStack(Material.STICK));
                                eq.setItemInOffHand(new ItemStack(Material.STICK));

                                MobUtils.setMaxHealth(skeleton, 1000.0D);
                                skeleton.setHealth(1000.0D);
                                skeleton.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(4.0D);

                                skeleton.setRemoveWhenFarAway(false);
                                bb = Bukkit.createBossBar("Hakai", BarColor.RED, BarStyle.SOLID, new BarFlag[] { BarFlag.CREATE_FOG });
                                hakaiOutToChat("You have awoken me from my slumber!");
                                for (Player player : getServer().getOnlinePlayers()) {
                                    bb.addPlayer(player);
                                }
                            }
                        }
                    }
                }
            }

            if (!hasBoss) {

                if (bb != null) {
                    bb.removeAll();
                }
            }

            for (Map.Entry<Player, Integer> e : punchDelay.entrySet()) {
                punchDelay.put(e.getKey(), Integer.valueOf(e.getValue().intValue() - 1));
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (holdingblock.get(p) != null) {
                    Location targetLoc = p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(2));
                    holdingblock.get(p).setVelocity(targetLoc.subtract(holdingblock.get(p).getLocation()).toVector());
                }
                WaterUtils.constantPlayerChecks(p);
                FireUtils.constantPlayerChecks(p);
                AirUtils.constantPlayerChecks(p);
                EarthUtils.constantPlayerChecks(p);
                MetalUtils.constantPlayerChecks(p);
                SkyUtils.constantPlayerChecks(p);
                IceUtils.constantPlayerChecks(p);
                LavaUtils.constantPlayerChecks(p);
                PoisonUtils.constantPlayerChecks(p);
                LightningUtils.constantPlayerChecks(p);
                FreezeUtils.constantPlayerChecks(p);
                SandUtils.constantPlayerChecks(p);
                if (ArmourUtil.checkForBouncerBoots(p)) {
                    if ((!p.isSneaking()) && (p.isOnGround())) {
                        if (p.isSprinting()) {
                            p.setVelocity(p.getLocation().getDirection().multiply(2.5F).setY(1.0F));
                        } else {
                            p.setVelocity(p.getLocation().getDirection().setY(1.0F));
                        }
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1.0F, 1.0F);
                        for (Entity n : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
                            if (!(n instanceof Player)) {
                                n.setFallDistance(10.0F);
                                if (n.getType() == EntityType.ARROW) {
                                    ((Arrow) n).setBounce(true);
                                }
                            }
                        }
                    }

                    p.setFallDistance(0.0F);
                }

                if (ArmourUtil.checkForRiderLeggings(p)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 64, 10));
                    if ((p.isOnGround()) && (p.isSprinting())) {
                        p.setVelocity(p.getLocation().getDirection().setY(0).multiply(8.0F));
                    }
                }
                if (ArmourUtil.checkHoldingWarpBow(p)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 255));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 255));

                    for (Entity e : p.getNearbyEntities(2.0D, 2.0D, 2.0D)) {
                        if (e.getType() == EntityType.ARROW) {
                            e.setVelocity(e.getVelocity().multiply(1.5F));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    }

    @EventHandler
    public void onHurt(EntityDamageEvent event) {
        if ((event.getEntity() instanceof Player)) {
            Player p = (Player) event.getEntity();
            if ((LightningUtils.checkForHelmet(p)) && (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING)) {
                event.setCancelled(true);
            }
        }
    }

    public static Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - radius % 16) / 16;
        HashSet<Entity> radiusEntities = new HashSet();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX();
                int y = (int) l.getY();
                int z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + chX * 16, y, z + chZ * 16).getChunk().getEntities()) {
                    if ((e.getLocation().distance(l) <= radius) && (e.getLocation().getBlock() != l.getBlock())) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    @EventHandler
    public void onSpawnEvent(CreatureSpawnEvent event) {
        if (((event.getEntity() instanceof Monster)) && (event.getEntity().getCustomName() == null)
                && (event.getSpawnReason() == org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.NATURAL)) {
            Random random = new Random();
            if (event.getLocation().getBlock().getBiome() != Biome.SKY) {
                if (event.getLocation().getBlock().getBiome() == Biome.HELL) {
                    if ((random.nextBoolean()) && (event.getEntityType() == EntityType.PIG_ZOMBIE)) {
                        int randomweight = random.nextInt(maxweight);
                        for (WeightedEntityController c : nethercontrollers) {
                            randomweight -= c.getWeight();
                            if ((randomweight < 0) && (c.canSpawnThere(event.getLocation().getBlock()))) {
                                c.spawn(event.getLocation());
                                event.setCancelled(true);
                                return;
                            }
                        }

                        if (randomweight > 0) {
                            event.setCancelled(false);
                        }
                    }
                } else {
                    if (getMoonPhase(event.getLocation().getWorld()) == MoonPhases.NEW) {
                        if (event.getEntity().getType() == EntityType.SQUID) {
                            new RedGuardianController(0).spawn(event.getLocation());
                            event.setCancelled(true);
                            return;
                        }
                        EntityTypeController c = null;
                        int entity = random.nextInt(10);
                        if (entity == 0) {
                            c = new ChaserController(0);
                        } else {
                            if (entity == 1) {
                                event.setCancelled(true);

                                return;
                            }
                            if (random.nextBoolean()) {
                                c = new BloodBatController(0);
                            } else {
                                c = new BloodRatController(0);
                            }
                        }

                        c.spawn(event.getLocation());
                        event.setCancelled(true);
                        return;
                    }

                    int randomweight = random.nextInt(maxweight);
                    for (WeightedEntityController c : controllers) {
                        randomweight -= c.getWeight();
                        if ((randomweight < 0) && (c.canSpawnThere(event.getLocation().getBlock()))) {
                            c.spawn(event.getLocation());
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

    public boolean areAllOneType(Material material, List<Block> list) {
        for (Block b : list) {
            if (b.getType() != material) {
                return false;
            }
        }
        return true;
    }

    public void loadAll(JavaPlugin plugin) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("crafters.locations");
        if (configurationSection == null) {
            configurationSection = plugin.getConfig().createSection("crafters.locations");
        }

        for (Map.Entry<String, Object> entry : configurationSection.getValues(false).entrySet()) {
            crafterLocations.add(entry.getValue().toString());
        }

        ConfigurationSection configurationSectiona = plugin.getConfig().getConfigurationSection("combiners.locations");
        if (configurationSectiona == null) {
            configurationSectiona = plugin.getConfig().createSection("combiners.locations");
        }

        for (Object entry : configurationSectiona.getValues(false).entrySet()) {
            combinerLocations.add(((Map.Entry) entry).getValue().toString());
        }

        ConfigurationSection configurationSection1 = plugin.getConfig().getConfigurationSection("skulls");
        if (configurationSection1 == null) {
            configurationSection1 = plugin.getConfig().createSection("skulls");
        }
        configurationSection1.getItemStack("MonkeySkull");
    }

    public void saveAll(JavaPlugin plugin) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("crafters.locations");
        if (configurationSection == null) {
            configurationSection = plugin.getConfig().createSection("crafters.locations");
        }
        for (int i = 0; i < crafterLocations.size(); i++) {
            configurationSection.set(Integer.toString(i), crafterLocations.get(i));
        }

        ConfigurationSection configurationSectiocn = plugin.getConfig().getConfigurationSection("combiners.locations");
        if (configurationSectiocn == null) {
            configurationSectiocn = plugin.getConfig().createSection("combiners.locations");
        }
        for (int i = 0; i < combinerLocations.size(); i++) {
            configurationSectiocn.set(Integer.toString(i), combinerLocations.get(i));
        }

        ConfigurationSection configurationSection1 = plugin.getConfig().getConfigurationSection("skulls");
        if (configurationSection1 == null) {
            configurationSection1 = plugin.getConfig().createSection("skulls");
        }

        plugin.saveConfig();
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        BanditController b = new BanditController(0);

        if (b.isOne(entity)) {
            event.getDrops().clear();
            for (ItemStack i : b.getDrops((Zombie) entity)) {
                event.getDrops().add(i);
            }
        }
        for (EntityTypeController c : controllers) {
            if (c.isOne(entity)) {
                event.getDrops().clear();
                for (ItemStack i : c.getDrops()) {
                    event.getDrops().add(i);
                }
            }
        }

        if ("Hakai".equals(entity.getCustomName())) {
            event.getDrops().clear();
            event.getDrops().add(ArmourUtil.createDarkEssence());
            event.getDrops().add(ArmourUtil.createDarkEssence());
            event.getDrops().add(ArmourUtil.createDarkEssence());
            event.getDrops().add(ArmourUtil.createDarkEssence());
            event.getDrops().add(new ItemStack(Material.TOTEM));
            event.getDrops().add(new ItemStack(Material.GOLDEN_APPLE, 2, (short) 1));
            event.getDrops().add(new ItemStack(Material.BONE, 6));
            hakaiOutToChat("This will not be the end!");
            event.getEntity().remove();
            entity.getWorld().playEffect(entity.getLocation(), org.bukkit.Effect.END_GATEWAY_SPAWN, 0);
        }
    }

    public static MoonPhases getMoonPhase(World world) {
        long days = world.getFullTime() / 24000L;
        long phase = days % 8L;

        if (phase == 0L)
            return MoonPhases.FULL;
        if (phase == 1L)
            return MoonPhases.WAINING_GIBBUS;
        if (phase == 2L)
            return MoonPhases.WAINING_HALF;
        if (phase == 3L)
            return MoonPhases.WAINING_CRESCENT;
        if (phase == 4L)
            return MoonPhases.NEW;
        if (phase == 5L)
            return MoonPhases.WAXING_CRESCENT;
        if (phase == 6L)
            return MoonPhases.WAXING_HALF;
        if (phase == 7L) {
            return MoonPhases.WAXING_GIBBUS;
        }
        return MoonPhases.UNKNOWN;
    }

    @EventHandler
    public void toggleGlideEvent(EntityToggleGlideEvent event) {
        if ((event.getEntity() instanceof Player)) {
            Player p = (Player) event.getEntity();
            if ((SkyUtils.checkForBoots(p)) && (p.isGliding())) {
                if (!p.isOnGround()) {
                    event.setCancelled(true);
                    p.setGliding(true);
                }
            }
        }
    }

    public static boolean day(World w) {
        long time = w.getTime();

        return (time < 12300L) || (time > 23850L);
    }

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        Chunk chunk = event.getChunk();

        if ((Math.random() < 0.01D) && (chunk.getBlock(0, 0, 0).getBiome() == Biome.SWAMPLAND)) {
            Block centre = chunk.getBlock(2, 0, 2);

            for (int i = 100; i > 40; i--) {
                if ((chunk.getBlock(2, i, 2).getType() == Material.DIRT) || (chunk.getBlock(2, i, 2).getType() == Material.GRASS)) {
                    centre = chunk.getBlock(2, i, 2);
                    System.out.println("break");

                    break;
                }
            }
            if (centre.equals(chunk.getBlock(2, 0, 2))) {
                System.out.println("none");
                return;
            }
            System.out.println(centre.getX() + " " + centre.getY() + " " + centre.getZ());

            centre.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(-1.0D, 0.0D, 0.0D).getBlock().setData((byte) 0);

            centre.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(1.0D, 0.0D, 0.0D).getBlock().setData((byte) 1);

            centre.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(0.0D, 0.0D, -1.0D).getBlock().setData((byte) 2);

            centre.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(0.0D, 0.0D, 1.0D).getBlock().setData((byte) 3);

            centre.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(-1.0D, 1.0D, 0.0D).getBlock().setData((byte) 4);

            centre.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(1.0D, 1.0D, 0.0D).getBlock().setData((byte) 5);

            centre.getLocation().add(0.0D, 1.0D, -1.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(0.0D, 1.0D, -1.0D).getBlock().setData((byte) 6);

            centre.getLocation().add(0.0D, 1.0D, 1.0D).getBlock().setType(Material.NETHER_BRICK_STAIRS);
            centre.getLocation().add(0.0D, 1.0D, 1.0D).getBlock().setData((byte) 7);

            centre.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().setType(Material.CAULDRON);
            centre.getLocation().add(0.0D, 1.0D, 0.0D).getBlock().setData((byte) 3);

            Block chest1 = centre.getLocation().add(2.0D, 0.0D, 0.0D).getBlock();
            Chest fillChest = fillChest(chest1);
            ItemStack item = fillChest.getInventory().getItem(13);
            item = new ItemStack(Material.TOTEM);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("Hakai");
            List<String> lores = new ArrayList();
            lores.add(ChatColor.WHITE + "I wonder what will happen if i place it in the cauldron...");
            itemMeta.setLore(lores);
            item.setItemMeta(itemMeta);
            fillChest.getInventory().setItem(13, item);
            Block chest2 = centre.getLocation().add(-2.0D, 0.0D, 0.0D).getBlock();
            fillChest(chest2);
            Block chest3 = centre.getLocation().add(0.0D, 0.0D, 2.0D).getBlock();
            fillChest(chest3);
            Block chest4 = centre.getLocation().add(0.0D, 0.0D, -2.0D).getBlock();
            fillChest(chest4);
        }
    }

    public Chest fillChest(Block chest1) {
        chest1.setType(Material.CHEST);
        Chest state = (Chest) chest1.getState();
        Inventory inv1 = state.getInventory();
        for (int i = 0; i < inv1.getSize(); i++) {
            Random random = new Random();
            int n = random.nextInt(200);
            if (n < 1) {
                inv1.setItem(i, new ItemStack(Material.DIAMOND_BLOCK));
            } else if (n < 5) {
                inv1.setItem(i, new ItemStack(Material.DIAMOND));
            } else if (n < 20) {
                inv1.setItem(i, new ItemStack(Material.GOLD_INGOT));
            } else if (n < 50) {
                inv1.setItem(i, new ItemStack(Material.GOLD_NUGGET));
            } else if (n < 60) {
                inv1.setItem(i, new ItemStack(Material.IRON_INGOT));
            } else if (n < 100) {
                inv1.setItem(i, new ItemStack(Material.APPLE));
            }
        }
        return state;
    }

    public void fill(Block[] blocks, Material type) {
        for (Block b : blocks) {
            b.setType(type);
        }
    }

    public void fill(Location loc1, Location loc2, Material type) {
        Cuboid cuboid = new Cuboid(loc1, loc2);
        for (Block block : cuboid) {
            block.setType(type, false);
        }
    }

    public void setSpawner(Block block, EntityType type) {
        BlockState blockState = block.getState();
        CreatureSpawner spawner = (CreatureSpawner) blockState;
        spawner.setSpawnedType(type);
        blockState.update();
    }

    public void createSeaHouse(Block current) {
        current.setType(Material.PRISMARINE);
        fill(current.getLocation().add(3.0D, 0.0D, 3.0D), current.getLocation().add(-3.0D, 0.0D, -3.0D), Material.STONE);
        fill(current.getLocation().add(3.0D, 1.0D, 3.0D), current.getLocation().add(-3.0D, 5.0D, -3.0D), Material.PRISMARINE);
        fill(current.getLocation().add(2.0D, 1.0D, 2.0D), current.getLocation().add(-2.0D, 4.0D, -2.0D), Material.WATER);
        fill(current.getLocation().add(3.0D, 1.0D, 0.0D), current.getLocation().add(3.0D, 2.0D, 0.0D), Material.WATER);
        fill(current.getLocation().add(0.0D, 5.0D, 0.0D), current.getLocation().add(0.0D, 8.0D, 0.0D), Material.SEA_LANTERN);

        fill(current.getLocation().add(3.0D, 0.0D, 3.0D), current.getLocation().add(3.0D, 6.0D, 3.0D), Material.GOLD_BLOCK);
        fill(current.getLocation().add(-3.0D, 0.0D, 3.0D), current.getLocation().add(-3.0D, 6.0D, 3.0D), Material.GOLD_BLOCK);
        fill(current.getLocation().add(3.0D, 0.0D, -3.0D), current.getLocation().add(3.0D, 6.0D, -3.0D), Material.GOLD_BLOCK);
        fill(current.getLocation().add(-3.0D, 0.0D, -3.0D), current.getLocation().add(-3.0D, 6.0D, -3.0D), Material.GOLD_BLOCK);

        current.setType(Material.MOB_SPAWNER);
        setSpawner(current, EntityType.SQUID);

        Block b = current.getLocation().add(0.0D, 1.0D, 0.0D).getBlock();
        b.setType(Material.CHEST);

        Chest chest = (Chest) b.getState();

        Inventory inv = chest.getInventory();

        ItemStack fish = new ItemStack(Material.RAW_FISH);
        ItemMeta fm = fish.getItemMeta();
        fm.setDisplayName("Smelly Fish");
        fish.setItemMeta(fm);

        for (int i = 0; i < 27; i++) {
            fish.setDurability((short) (i % 2));
            if (i == 0) {
                inv.setItem(i, WaterUtils.createWaterBoots());
            } else if (i == 8) {
                inv.setItem(i, WaterUtils.createWaterLeggings());
            } else if (i == 18) {
                inv.setItem(i, WaterUtils.createWaterChestplate());
            } else if (i == 26) {
                inv.setItem(i, WaterUtils.createWaterHelmet());
            } else {
                inv.setItem(i, fish);
            }
        }

        for (int i = 0; i < 4; i++) {
            Zombie zombie = (Zombie) current.getWorld().spawnEntity(current.getLocation(), EntityType.ZOMBIE);

            EntityEquipment zomb = zombie.getEquipment();
            zombie.setMaxHealth(60.0D);
            zombie.setHealth(60.0D);

            zombie.setVillager(false);
            zombie.setBaby(false);

            zomb.setBoots(ArmourUtil.createAquaBoots());
            zomb.setBootsDropChance(1.0F);
            zomb.setLeggings(ArmourUtil.createAquaPants());
            zomb.setChestplate(ArmourUtil.createAquaShirt());
            zomb.setHelmet(new ItemStack(Material.SEA_LANTERN));

            zombie.setCustomName("Sea Monster");
            zombie.teleport(current.getLocation());
        }
        for (int i = 0; i < 5; i++) {
            Zombie zombie = (Zombie) current.getWorld().spawnEntity(current.getLocation().add(0.0D, 1.0D, 0.0D), EntityType.ZOMBIE);

            EntityEquipment zomb = zombie.getEquipment();
            zombie.setMaxHealth(60.0D);
            zombie.setHealth(60.0D);

            zombie.setVillager(false);
            zombie.setBaby(true);

            zomb.setBoots(ArmourUtil.createAquaBoots());
            zomb.setBootsDropChance(1.0F);
            zomb.setLeggings(ArmourUtil.createAquaPants());
            zomb.setChestplate(ArmourUtil.createAquaShirt());
            zomb.setHelmet(new ItemStack(Material.SEA_LANTERN));

            zombie.setCustomName("Sea Monster");
            zombie.teleport(current.getLocation());
        }
    }

    public void createTower(Block current) {
        current.setType(Material.STONE);
        fill(current.getLocation().add(3.0D, 0.0D, 3.0D), current.getLocation().add(-3.0D, -20.0D, -3.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(3.0D, 10.0D, 3.0D), current.getLocation().add(-3.0D, 0.0D, -3.0D), Material.STONE);
        fill(current.getLocation().add(2.0D, 10.0D, 2.0D), current.getLocation().add(-2.0D, 1.0D, -2.0D), Material.AIR);

        fill(current.getLocation().add(0.0D, 1.0D, 3.0D), current.getLocation().add(0.0D, 11.0D, 3.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(0.0D, 1.0D, -3.0D), current.getLocation().add(0.0D, 11.0D, -3.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(3.0D, 1.0D, 0.0D), current.getLocation().add(3.0D, 11.0D, 0.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(-3.0D, 1.0D, 0.0D), current.getLocation().add(-3.0D, 11.0D, 0.0D), Material.COBBLESTONE);

        fill(current.getLocation().add(3.0D, 10.0D, 3.0D), current.getLocation().add(3.0D, 0.0D, 3.0D), Material.AIR);
        fill(current.getLocation().add(-3.0D, 10.0D, -3.0D), current.getLocation().add(-3.0D, 0.0D, -3.0D), Material.AIR);
        fill(current.getLocation().add(3.0D, 10.0D, -3.0D), current.getLocation().add(3.0D, 0.0D, -3.0D), Material.AIR);
        fill(current.getLocation().add(-3.0D, 10.0D, 3.0D), current.getLocation().add(-3.0D, 0.0D, 3.0D), Material.AIR);

        fill(current.getLocation().add(0.0D, 1.0D, 3.0D), current.getLocation().add(0.0D, 2.0D, 3.0D), Material.AIR);
        fill(current.getLocation().add(0.0D, 1.0D, -3.0D), current.getLocation().add(0.0D, 2.0D, -3.0D), Material.AIR);
        fill(current.getLocation().add(3.0D, 1.0D, 0.0D), current.getLocation().add(3.0D, 2.0D, 0.0D), Material.AIR);
        fill(current.getLocation().add(-3.0D, 1.0D, 0.0D), current.getLocation().add(-3.0D, 2.0D, 0.0D), Material.AIR);

        fill(current.getLocation().add(3.0D, 1.0D, -1.0D), current.getLocation().add(3.0D, 3.0D, 1.0D), Material.AIR);

        fill(current.getLocation().add(0.0D, 5.0D, 3.0D), current.getLocation().add(0.0D, 6.0D, 3.0D), Material.AIR);
        fill(current.getLocation().add(0.0D, 5.0D, -3.0D), current.getLocation().add(0.0D, 6.0D, -3.0D), Material.AIR);
        fill(current.getLocation().add(3.0D, 5.0D, 0.0D), current.getLocation().add(3.0D, 6.0D, 0.0D), Material.AIR);
        fill(current.getLocation().add(-3.0D, 5.0D, 0.0D), current.getLocation().add(-3.0D, 6.0D, 0.0D), Material.AIR);

        fill(current.getLocation().add(2.0D, 4.0D, 2.0D), current.getLocation().add(-2.0D, 4.0D, -2.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(1.0D, 4.0D, 1.0D), current.getLocation().add(-1.0D, 4.0D, -1.0D), Material.AIR);

        fill(current.getLocation().add(2.0D, 9.0D, 2.0D), current.getLocation().add(-2.0D, 9.0D, -2.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(1.0D, 9.0D, 1.0D), current.getLocation().add(-1.0D, 9.0D, -1.0D), Material.AIR);

        fill(current.getLocation().add(2.0D, 11.0D, 3.0D), current.getLocation().add(2.0D, 0.0D, 3.0D), Material.COBBLESTONE);

        fill(current.getLocation().add(-2.0D, 11.0D, -3.0D), current.getLocation().add(-2.0D, 0.0D, -3.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(2.0D, 11.0D, -3.0D), current.getLocation().add(2.0D, 0.0D, -3.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(-2.0D, 11.0D, 3.0D), current.getLocation().add(-2.0D, 0.0D, 3.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(3.0D, 11.0D, 2.0D), current.getLocation().add(3.0D, 0.0D, 2.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(-3.0D, 11.0D, -2.0D), current.getLocation().add(-3.0D, 0.0D, -2.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(3.0D, 11.0D, -2.0D), current.getLocation().add(3.0D, 0.0D, -2.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(-3.0D, 11.0D, 2.0D), current.getLocation().add(-3.0D, 0.0D, 2.0D), Material.COBBLESTONE);

        fill(current.getLocation().add(0.0D, 11.0D, 0.0D), current.getLocation().add(0.0D, 0.0D, 0.0D), Material.COBBLESTONE);
        fill(current.getLocation().add(0.0D, 11.0D, 0.0D), current.getLocation().add(0.0D, 11.0D, 0.0D), Material.GLOWSTONE);
        fill(current.getLocation().add(0.0D, 1.0D, 0.0D), current.getLocation().add(0.0D, 1.0D, 0.0D), Material.GLOWSTONE);

        Block b = current.getLocation().add(0.0D, 12.0D, 0.0D).getBlock();
        b.setType(Material.CHEST);

        Chest chest = (Chest) b.getState();

        Inventory inv = chest.getInventory();

        for (int i = 0; i < 27; i++) {
            if (i == 0) {
                inv.setItem(i, EarthUtils.createEarthBoots());
            } else if (i == 8) {
                inv.setItem(i, EarthUtils.createEarthLeggings());
            } else if (i == 18) {
                inv.setItem(i, EarthUtils.createEarthChestplate());
            } else if (i == 26) {
                inv.setItem(i, EarthUtils.createEarthHelmet());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (("Eyeball".equals(event.getEntity().getCustomName())) && (event.getDamage() <= 4.0D)) {
            EyeController c = new EyeController(0);
            c.spawn(event.getEntity().getLocation());
        }

        if ("Hakai".equals(event.getEntity().getCustomName())) {
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_WITHER_HURT, 1.0F, 1.0F);
        }
        if ("Hakai".equals(event.getDamager().getCustomName())) {
            if (event.getDamager().getTicksLived() % 5 != 0) {
                event.getEntity()
                        .setVelocity(event.getEntity().getLocation().add(0.0D, 1.0D, 0.0D).subtract(event.getDamager().getLocation()).toVector().multiply(2.0F).setY(2.0D));
                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, 1.0F, 1.0F);
            } else {
                Entity e = event.getEntity();
                e.setVelocity(new Vector(0, 0, 0));
                e.getLocation().getBlock().setType(Material.WATER);
                e.getLocation().getBlock().setData((byte) 15);
            }
        }

        if (("Bandit".equals(event.getDamager().getCustomName())) && ((event.getDamager() instanceof Zombie))) {
            Zombie z = (Zombie) event.getDamager();
            if ((event.getEntity() instanceof Player)) {
                Player p = (Player) event.getEntity();
                if ((z.getEquipment().getItemInMainHand().getType() == Material.WOOD_SWORD) && (p.getEquipment().getItemInMainHand().getType() != Material.WOOD_SWORD)
                        && (p.getEquipment().getItemInMainHand().getType().toString().contains("SWORD"))) {
                    z.getEquipment().setItemInMainHand(p.getItemInHand());
                    z.getEquipment().setItemInMainHandDropChance(1.0F);
                    p.getItemInHand().setType(Material.AIR);
                    p.getItemInHand().setType(Material.WOOD_SWORD);
                    p.getItemInHand().setDurability((short) 0);
                }
            }
        }
    }

    @EventHandler
    public void onbreak(BlockBreakEvent event) {
        Block b = event.getBlock();
        if (b.getType() == Material.LONG_GRASS) {
            Random random = new Random();
            if (random.nextInt(20) == 0) {
                ArmorStand a = (ArmorStand) b.getWorld().spawnEntity(b.getLocation().add(0.0D, -0.5D, 0.0D), EntityType.ARMOR_STAND);
                a.setCustomName(ChatColor.RED + "♥");
                a.setCustomNameVisible(true);
                a.setGravity(false);
                a.setVisible(false);
                a.setSmall(true);
                a.setAI(false);
                a.setVelocity(new Vector(0, 0, 0));
                a.setInvulnerable(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        for (Entity entity : p.getNearbyEntities(1.0D, 1.0D, 1.0D)) {
            if ((ChatColor.RED + "♥").equals(entity.getCustomName())) {
                entity.remove();
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                if (p.getHealth() + 2.0D <= p.getMaxHealth()) {
                    p.setHealth(p.getHealth() + 2.0D);
                } else {
                    p.setHealth(p.getMaxHealth());
                }
            }
            if ((ChatColor.BOLD + "" + ChatColor.RED + "[♥]").equals(entity.getCustomName())) {
                entity.remove();
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);

                p.setMaxHealth(p.getMaxHealth() + 2.0D);
            }
        }
    }
}
