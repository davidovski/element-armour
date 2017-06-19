package org.ah.minecraft.armour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.ah.minecraft.armour.mobs.BanditController;
import org.ah.minecraft.armour.mobs.BloodBatController;
import org.ah.minecraft.armour.mobs.BloodRatController;
import org.ah.minecraft.armour.mobs.BouncerController;
import org.ah.minecraft.armour.mobs.ChaserController;
import org.ah.minecraft.armour.mobs.DeamonController;
import org.ah.minecraft.armour.mobs.EntityTypeController;
import org.ah.minecraft.armour.mobs.EyeController;
import org.ah.minecraft.armour.mobs.GhostController;
import org.ah.minecraft.armour.mobs.KnightController;
import org.ah.minecraft.armour.mobs.MinerController;
import org.ah.minecraft.armour.mobs.MonkeyController;
import org.ah.minecraft.armour.mobs.NetherKnightController;
import org.ah.minecraft.armour.mobs.RedGuardianController;
import org.ah.minecraft.armour.mobs.SkeletonFarmerController;
import org.ah.minecraft.armour.mobs.SkeletonWariorController;
import org.ah.minecraft.armour.mobs.TrapController;
import org.ah.minecraft.armour.mobs.UndeathController;
import org.ah.minecraft.armour.mobs.VampireController;
import org.ah.minecraft.armour.mobs.WeightedEntityController;
import org.ah.minecraft.armour.mobs.WispController;
import org.ah.minecraft.armour.utils.AirUtils;
import org.ah.minecraft.armour.utils.ArmourUtil;
import org.ah.minecraft.armour.utils.EarthUtils;
import org.ah.minecraft.armour.utils.FireUtils;
import org.ah.minecraft.armour.utils.IceUtils;
import org.ah.minecraft.armour.utils.LavaUtils;
import org.ah.minecraft.armour.utils.LightningUtils;
import org.ah.minecraft.armour.utils.MetalUtils;
import org.ah.minecraft.armour.utils.PoisonUtils;
import org.ah.minecraft.armour.utils.SkyUtils;
import org.ah.minecraft.armour.utils.WaterUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dispenser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryType;
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

public final class Plugin extends JavaPlugin implements Listener {

    public static List<String> crafterLocations;
    protected ItemStack monkeySkull;
    private HashMap<Player, FallingBlock> holdingblock;
    public static Player p;

    public List<WeightedEntityController> controllers;
    public List<WeightedEntityController> nethercontrollers;

    private int maxweight;
    private HashMap<Player, Integer> punchDelay;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Element Armour has been enabled!");

        PluginManager manager = getServer().getPluginManager();
        getServer().getPluginManager().registerEvents(this, this);

        crafterLocations = new ArrayList<String>();

        holdingblock = new HashMap<Player, FallingBlock>();
        punchDelay = new HashMap<Player, Integer>();

        loadAll(this);

        controllers = new ArrayList<WeightedEntityController>();
        nethercontrollers = new ArrayList<WeightedEntityController>();
        controllers.add(new BanditController(90));
        controllers.add(new BouncerController(40));
        // controllers.add(new DeathHunterController(160));
        controllers.add(new GhostController(60));
        controllers.add(new KnightController(70));
        controllers.add(new BloodBatController(0));
        controllers.add(new BloodRatController(0));
        controllers.add(new RedGuardianController(0));

        controllers.add(new MinerController(40));
        controllers.add(new TrapController(10));


        controllers.add(new MonkeyController(140));
        controllers.add(new ChaserController(0));
        //controllers.add(new BloodBearController(0));

        controllers.add(new SkeletonFarmerController(90));
        controllers.add(new SkeletonWariorController(120));

        controllers.add(new VampireController(60));
        controllers.add(new EyeController(40));

        controllers.add(new WispController(10));
        controllers.add(new UndeathController(10));

        nethercontrollers.add(new NetherKnightController(70));
        nethercontrollers.add(new DeamonController(50));

        setDifficulties();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                doEveryTick();
            }
        }, 1, 1);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (LivingEntity e : w.getLivingEntities()) {

                        if ("Bow Master".equalsIgnoreCase(e.getCustomName())) {
                            if (Math.random() > 0.5) {
                                for (int i = 0; i < 2; i++) {
                                    Bat wisp = (Bat) w.spawnEntity(e.getLocation().add(0, 2, 0), EntityType.BAT);
                                    wisp.setCustomName("Wisp");
                                    wisp.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10, 10));
                                }
                            } else {
                                for (int i = 0; i < 4; i++) {
                                    for (Entity n : e.getNearbyEntities(10, 10, 10)) {
                                        if (n instanceof LivingEntity) {
                                            Arrow a = w.spawnArrow(e.getEyeLocation().add(0, 1, 0), n.getLocation().toVector().add(e.getEyeLocation().add(0, 1, 0).toVector()), 1f,
                                                    1f);
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
        }, 300, 300);

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (LivingEntity e : w.getLivingEntities()) {
                        if ("Vampire".equalsIgnoreCase(e.getCustomName())) {

                        }
                    }
                }
            }
        }, 60, 60);

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
            mulitplier = 4;
        } else if (d == Difficulty.EASY) {
            mulitplier = 3;
        } else if (d == Difficulty.NORMAL) {
            mulitplier = 2;
        } else if (d == Difficulty.HARD) {
            mulitplier = 1;
        } else {
            mulitplier = 0;
        }

        maxweight = maxweight * mulitplier;
    }

    @Override
    public void onDisable() {
        saveAll(this);
        Bukkit.getLogger().info("Element Armour has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
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
            } else if (cmd.getName().equalsIgnoreCase("Earth")) {
                p.getInventory().addItem(EarthUtils.createEarthBoots());
                p.getInventory().addItem(EarthUtils.createEarthHelmet());
                p.getInventory().addItem(EarthUtils.createEarthChestplate());
                p.getInventory().addItem(EarthUtils.createEarthLeggings());
                return true;

            } else if (cmd.getName().equalsIgnoreCase("Metal")) {
                p.getInventory().addItem(MetalUtils.createMetalBoots());
                p.getInventory().addItem(MetalUtils.createMetalChestplate());
                p.getInventory().addItem(MetalUtils.createMetalHelmet());
                p.getInventory().addItem(MetalUtils.createMetalLeggings());
                return true;

            } else if (cmd.getName().equalsIgnoreCase("Sky")) {
                p.getInventory().addItem(SkyUtils.createSkyBoots());
                p.getInventory().addItem(SkyUtils.createSkyChestplate());
                p.getInventory().addItem(SkyUtils.createSkyHelmet());
                p.getInventory().addItem(SkyUtils.createSkyLeggings());
                return true;

            } else if (cmd.getName().equalsIgnoreCase("Lava")) {
                p.getInventory().addItem(LavaUtils.createLavaBoots());
                p.getInventory().addItem(LavaUtils.createLavaChestplate());
                p.getInventory().addItem(LavaUtils.createLavaHelmet());
                p.getInventory().addItem(LavaUtils.createLavaLeggings());
                return true;

            } else if (cmd.getName().equalsIgnoreCase("Ice")) {
                p.getInventory().addItem(IceUtils.createIceBoots());
                p.getInventory().addItem(IceUtils.createIceChestplate());
                p.getInventory().addItem(IceUtils.createIceHelmet());
                p.getInventory().addItem(IceUtils.createIceLeggings());
                return true;

            } else if (cmd.getName().equalsIgnoreCase("Poison")) {
                p.getInventory().addItem(PoisonUtils.createBoots());
                p.getInventory().addItem(PoisonUtils.createChestplate());
                p.getInventory().addItem(PoisonUtils.createHelmet());
                p.getInventory().addItem(PoisonUtils.createLeggings());
                return true;

            } else if (cmd.getName().equalsIgnoreCase("Lightning")) {
                p.getInventory().addItem(LightningUtils.createBoots());
                p.getInventory().addItem(LightningUtils.createChestplate());
                p.getInventory().addItem(LightningUtils.createHelmet());
                p.getInventory().addItem(LightningUtils.createLeggings());
                return true;

//            } else if (cmd.getName().equalsIgnoreCase("Poison")) {
//                p.getInventory().addItem(PoisonUtils.createBoots());
//                p.getInventory().addItem(PoisonUtils.createChestplate());
//                p.getInventory().addItem(PoisonUtils.createHelmet());
//                p.getInventory().addItem(PoisonUtils.createLeggings());
//                return true;
//
//            } else if (cmd.getName().equalsIgnoreCase("Poison")) {
//                p.getInventory().addItem(PoisonUtils.createBoots());
//                p.getInventory().addItem(PoisonUtils.createChestplate());
//                p.getInventory().addItem(PoisonUtils.createHelmet());
//                p.getInventory().addItem(PoisonUtils.createLeggings());
//                return true;
//
//            } else if (cmd.getName().equalsIgnoreCase("Poison")) {
//                p.getInventory().addItem(PoisonUtils.createBoots());
//                p.getInventory().addItem(PoisonUtils.createChestplate());
//                p.getInventory().addItem(PoisonUtils.createHelmet());
//                p.getInventory().addItem(PoisonUtils.createLeggings());
//                return true;
//
//            } else if (cmd.getName().equalsIgnoreCase("Poison")) {
//                p.getInventory().addItem(PoisonUtils.createBoots());
//                p.getInventory().addItem(PoisonUtils.createChestplate());
//                p.getInventory().addItem(PoisonUtils.createHelmet());
//                p.getInventory().addItem(PoisonUtils.createLeggings());
//                return true;

            } else if (cmd.getName().equalsIgnoreCase("Shop")) {
                MerchantInventory inv = (MerchantInventory) getServer().createInventory(p, InventoryType.MERCHANT);

                p.openInventory(inv);
            } else if (cmd.getName().equalsIgnoreCase("sea")) {
                // createSeaHouse(p.getLocation().getBlock());
            } else if (cmd.getName().equalsIgnoreCase("spawnTower")) {
                createTower(p.getLocation().getBlock());
            } else if (cmd.getName().equalsIgnoreCase("Fire")) {
                p.getInventory().addItem(FireUtils.createFireBoots());
                p.getInventory().addItem(FireUtils.createFireHelmet());
                p.getInventory().addItem(FireUtils.createFireChestplate());
                p.getInventory().addItem(FireUtils.createFireLeggings());
                return true;
            } else if (cmd.getName().equalsIgnoreCase("Air")) {
                p.getInventory().addItem(AirUtils.createAirBoots());
                p.getInventory().addItem(AirUtils.createAirHelmet());
                p.getInventory().addItem(AirUtils.createAirChestplate());
                p.getInventory().addItem(AirUtils.createAirLeggings());
                return true;
            } else if (cmd.getName().equalsIgnoreCase("Water")) {
                p.getInventory().addItem(WaterUtils.createWaterBoots());
                p.getInventory().addItem(WaterUtils.createWaterHelmet());
                p.getInventory().addItem(WaterUtils.createWaterChestplate());
                p.getInventory().addItem(WaterUtils.createWaterLeggings());
                return true;
            } else if (cmd.getName().equalsIgnoreCase("Ninja")) {
                p.getInventory().addItem(Ninja.createShurikenItemStack());
                p.getInventory().addItem(ArmourUtil.createWarpBow());
                return true;
            } else if (cmd.getName().equalsIgnoreCase("Bouncer")) {
                p.getInventory().addItem(ArmourUtil.createBouncerBoots());
                return true;
            } else if (cmd.getName().equalsIgnoreCase("setMonkeyHead")) {
                monkeySkull = new ItemStack(p.getItemInHand());
                saveAll(this);
                return true;
            } else if (cmd.getName().equalsIgnoreCase("giveColorArmour")) {
                if (args.length > 0 && args.length < 4) {
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

                    inv.addItem(head);
                    inv.addItem(body);
                    inv.addItem(legs);
                    inv.addItem(boots);

                    return true;
                } else {
                    return false;
                }
            }

        }

        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (!p.hasPlayedBefore()) {
            if (getServer().getOperators().isEmpty()) {
                p.setOp(true);
            }
        }
        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1f);
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : getServer().getOnlinePlayers()) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 0.5f);
                }

            }
        }, 20);
        Bukkit.getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : getServer().getOnlinePlayers()) {
                    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 0.5f);
                }
            }
        }, 40);

    }

    @EventHandler
    public void onProjectileLand(ProjectileHitEvent event) {
        if (event.getEntity().getType() == EntityType.ARROW) {
            Arrow a = (Arrow) event.getEntity();
            ProjectileSource shooter = a.getShooter();
            if (shooter instanceof Player) {
                Player p = (Player) shooter;
                if (ArmourUtil.checkHoldingWarpBow(p)) {
                    p.teleport(new Location(a.getWorld(), a.getLocation().getX(), a.getLocation().getY(), a.getLocation().getZ()));
                    a.remove();
                    p.getInventory().addItem(new ItemStack(Material.ARROW));

                }
            }
            if (shooter instanceof Skeleton) {
                Skeleton s = (Skeleton) shooter;
                if ("Bow Master".equals(s.getCustomName())) {
                    a.remove();
                }
            }
        }
    }

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {

        if (event.getEntity() instanceof Arrow) {
            Arrow a = (Arrow) event.getEntity();
            if (a.getShooter() instanceof Skeleton) {
                Skeleton s = (Skeleton) a.getShooter();
                if ("Bow Master".equals(s.getCustomName())) {

                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (p.getItemInHand().getType() == Material.FLINT_AND_STEEL && event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block bl = event.getClickedBlock();
            if (bl.getType() == Material.NETHERRACK) {
                getLogger().info("lit a block");
                List<Block> lapis = new ArrayList<Block>();
                lapis.add(bl.getLocation().add(2, 0, 0).getBlock());
                lapis.add(bl.getLocation().add(2, 0, 1).getBlock());
                lapis.add(bl.getLocation().add(2, 0, -1).getBlock());

                lapis.add(bl.getLocation().add(-1, 0, 2).getBlock());
                lapis.add(bl.getLocation().add(0, 0, 2).getBlock());
                lapis.add(bl.getLocation().add(1, 0, 2).getBlock());

                lapis.add(bl.getLocation().add(-1, 0, -2).getBlock());
                lapis.add(bl.getLocation().add(0, 0, -2).getBlock());
                lapis.add(bl.getLocation().add(-1, 0, -2).getBlock());

                lapis.add(bl.getLocation().add(-2, 0, 0).getBlock());
                lapis.add(bl.getLocation().add(-2, 0, 1).getBlock());
                lapis.add(bl.getLocation().add(-2, 0, -1).getBlock());

                if (areAllOneType(Material.LAPIS_BLOCK, lapis)) {
                    getLogger().info("has lapis!");
                    List<Block> glowstone = new ArrayList<Block>();
                    glowstone.add(bl.getLocation().add(-1, 0, 1).getBlock());
                    glowstone.add(bl.getLocation().add(0, 0, 1).getBlock());
                    glowstone.add(bl.getLocation().add(1, 0, 1).getBlock());

                    glowstone.add(bl.getLocation().add(-1, 0, 0).getBlock());
                    glowstone.add(bl.getLocation().add(1, 0, 0).getBlock());

                    glowstone.add(bl.getLocation().add(-1, 0, -1).getBlock());
                    glowstone.add(bl.getLocation().add(0, 0, -1).getBlock());
                    glowstone.add(bl.getLocation().add(1, 0, -1).getBlock());

                    if (areAllOneType(Material.GLOWSTONE, glowstone)) {
                        getLogger().info("has glowstone!");
                        List<Block> wood = new ArrayList<Block>();
                        wood.add(bl.getLocation().add(-2, 1, -2).getBlock());
                        wood.add(bl.getLocation().add(-2, 2, -2).getBlock());

                        wood.add(bl.getLocation().add(2, 1, -2).getBlock());
                        wood.add(bl.getLocation().add(2, 2, -2).getBlock());

                        wood.add(bl.getLocation().add(-2, 1, 2).getBlock());
                        wood.add(bl.getLocation().add(-2, 2, 2).getBlock());

                        wood.add(bl.getLocation().add(2, 1, 2).getBlock());
                        wood.add(bl.getLocation().add(2, 2, 2).getBlock());

                        if (areAllOneType(Material.LOG, wood)) {
                            getLogger().info("has wood!");
                            bl.getWorld().playSound(bl.getLocation(), Sound.ENTITY_FIREWORK_LARGE_BLAST, 2f, 1);
                            Block fire = bl.getLocation().add(0, 1, 0).getBlock();
                            fire.setType(Material.AIR);

                            Block crafter = bl.getLocation().getBlock();
                            crafter.setType(Material.DISPENSER);

                            // org.bukkit.material.Dispenser dispenserMat = (org.bukkit.material.Dispenser) crafter.getState();
                            // dispenserMat.setFacingDirection(BlockFace.UP);

                            Dispenser dispenser = (Dispenser) crafter.getState();
                            Inventory inventory = dispenser.getInventory();
                            dispenser.setCustomName("Crafter");
                            inventory.setItem(4, new ItemStack(Material.DIAMOND));

                            crafterLocations.add(LocationUtil.locationToString(crafter.getLocation()));
                            saveAll(this);

                            bl.getWorld().strikeLightningEffect(bl.getLocation());
                            bl.getWorld().spawnEntity(bl.getLocation().add(2, 2, 2), EntityType.THROWN_EXP_BOTTLE);
                            bl.getWorld().spawnEntity(bl.getLocation().add(-2, 2, 2), EntityType.THROWN_EXP_BOTTLE);
                            bl.getWorld().spawnEntity(bl.getLocation().add(2, 2, -2), EntityType.THROWN_EXP_BOTTLE);
                            bl.getWorld().spawnEntity(bl.getLocation().add(-2, 2, -2), EntityType.THROWN_EXP_BOTTLE);

                        }

                    }
                }

            }
        }

        if (p.getItemInHand().getType() == Material.AIR) {

            if ( !punchDelay.containsKey(p) || punchDelay.get(p) < 0 ) {
                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    EarthUtils.checkPunch(p);
                    WaterUtils.checkPunch(p);
                    FireUtils.checkPunch(p);
                    SkyUtils.checkPunch(p);
                    IceUtils.checkPunch(p);
                    LavaUtils.checkPunch(p);
                    PoisonUtils.checkPunch(p);


                    punchDelay.put(p, 5);

                }
                if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
                    MetalUtils.checkPunch(p);
                    punchDelay.put(p, 10);
                    LightningUtils.checkPunch(p);
                    AirUtils.checkPunch(p);


                }
            }

            if (EarthUtils.checkForHelmet(p)) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.STONE) {
                    Block block = event.getClickedBlock();
                    block.setType(Material.AIR);
                    Byte blockData = 0x0;
                    FallingBlock b = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 1.62, 0), Material.STONE, blockData);
                    holdingblock.put(p, b);
                    b.setGravity(false);

                }

                if (event.getAction() == Action.LEFT_CLICK_AIR) {
                    if (holdingblock.containsKey(p)) {
                        holdingblock.get(p).setGravity(true);
                        holdingblock.get(p).setVelocity(p.getLocation().getDirection().multiply(2));
                        holdingblock.remove(p);

                    }
                    p.sendMessage("Drop");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

    }

    @EventHandler
    public void onBlockFall(EntityChangeBlockEvent event) {
        Entity e = event.getEntity();
        if ((event.getEntityType() == EntityType.FALLING_BLOCK) && !holdingblock.keySet().contains(e) && e.getVelocity().length() > 1) {
            Byte blockData = 0x5;
            if (((FallingBlock) e).getBlockData() == blockData) {
                event.setCancelled(true);
                Location explosion = e.getLocation();
                World w = explosion.getWorld();
                w.createExplosion(explosion.getX(), explosion.getY(), explosion.getZ(), 5F, false, false);
                w.playSound(e.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, -4, 12);
            }
        }
    }

    public void doEveryTick() {

        CraftingUtil.crafterCheck(this);

        for (World w : Bukkit.getWorlds()) {
            for (LivingEntity e : w.getLivingEntities()) {

                for (EntityTypeController c : controllers) {
                    c.checkAndUpdate(e);
                }
                for (EntityTypeController c : nethercontrollers) {
                    c.checkAndUpdate(e);
                }

            }
        }

        for (Entry<Player, Integer> e : punchDelay.entrySet()) {
            punchDelay.put(e.getKey(), e.getValue() - 1);
        }

        for (Player p : getServer().getOnlinePlayers()) {
            if (holdingblock.get(p) != null) {
                holdingblock.get(p).teleport(p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(2)));
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


            if (ArmourUtil.checkForBouncerBoots(p)) {
                if (!p.isSneaking()) {
                    if (p.isOnGround()) {
                        if (p.isSprinting()) {
                            p.setVelocity(p.getLocation().getDirection().multiply(2.5f).setY(1f));
                        } else {
                            p.setVelocity(p.getLocation().getDirection().setY(1f));
                        }
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SLIME_ATTACK, 1, 1);
                        for (Entity n : p.getNearbyEntities(5, 5, 5)) {
                            if (!(n instanceof Player)) {
                                n.setFallDistance(10f);
                                if (n.getType() == EntityType.ARROW) {
                                    ((Arrow) n).setBounce(true);
                                }
                            }
                        }
                    }
                }
                p.setFallDistance(0f);
            }

            if (ArmourUtil.checkForRiderLeggings(p)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 64, 10));
                if (p.isOnGround() && p.isSprinting()) {
                    p.setVelocity(p.getLocation().getDirection().setY(0).multiply(8f));
                }
            }
            if (ArmourUtil.checkHoldingWarpBow(p)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 255));
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 40, 255));

                for (Entity e : p.getNearbyEntities(2, 2, 2)) {
                    if (e.getType() == EntityType.ARROW) {
                        e.setVelocity(e.getVelocity().multiply(1.5f));
                    }
                }
            }

        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getEntity().setMaxHealth(20D);
    }

    // @EventHandler
    // public void onDeath(EntityDeathEvent event) {
    // getLogger().info("got death event!");
    // LivingEntity e = event.getEntity();
    //
    // if (!(e instanceof Player)) {
    // if (e.getCustomName() != null) {
    // if (e.getLastDamageCause() != null) {
    // EntityDamageEvent ede = e.getLastDamageCause();
    //
    // DamageCause dc = ede.getCause();
    //
    // String d = ede.getCause().toString().toLowerCase().replaceAll("_", " ");
    // Bukkit.broadcastMessage("" + ChatColor.DARK_AQUA + e.getCustomName() + " died from " + d + " damage!!");
    // } else {
    // Bukkit.broadcastMessage("" + ChatColor.DARK_AQUA + e.getCustomName() + " was shot!");
    // }
    // }
    // }
    //
    // }

    @EventHandler
    public void onHurt(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (LightningUtils.checkForHelmet(p)) {
                if (event.getCause() == DamageCause.LIGHTNING) {
                    event.setCancelled(true);
                }
            }
        }
    }

    public static Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) {
                        radiusEntities.add(e);
                    }
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    // @EventHandler
    // public void onBlockPlaceEvent(BlockPlaceEvent event) {
    // Block bl = event.getBlock();
    // if (bl.getType() == )
    // }

    @EventHandler
    public void onSpawnEvent(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Monster && event.getEntity().getCustomName() == null && event.getSpawnReason() == SpawnReason.NATURAL) {

            Random random = new Random();
            if (event.getLocation().getBlock().getBiome() == Biome.SKY) {

            } else if (event.getLocation().getBlock().getBiome() == Biome.HELL) {
                if (random.nextBoolean() && event.getEntityType() == EntityType.PIG_ZOMBIE) {
                    int randomweight = random.nextInt(maxweight);
                    for (WeightedEntityController c : nethercontrollers) {
                        randomweight -= c.getWeight();
                        if (randomweight < 0) {
                            if (c.canSpawnThere(event.getLocation().getBlock())) {
                                c.spawn(event.getLocation());
                                event.setCancelled(true);
                                return;
                            }
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
                    } else if (entity == 1) {
                        event.setCancelled(true);

                        return;
                    } else {
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
                    if (randomweight < 0) {
                        if (c.canSpawnThere(event.getLocation().getBlock())) {
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

    public static void loadAll(JavaPlugin plugin) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("crafters.locations");
        if (configurationSection == null) {
            configurationSection = plugin.getConfig().createSection("crafters.locations");
        }

        for (Map.Entry<String, Object> entry : configurationSection.getValues(false).entrySet()) {
            crafterLocations.add(entry.getValue().toString());
        }

        ConfigurationSection configurationSection1 = plugin.getConfig().getConfigurationSection("skulls");
        if (configurationSection1 == null) {
            configurationSection1 = plugin.getConfig().createSection("skulls");
        }
        configurationSection1.getItemStack("MonkeySkull");

    }

    public static void saveAll(JavaPlugin plugin) {
        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection("crafters.locations");
        if (configurationSection == null) {
            configurationSection = plugin.getConfig().createSection("crafters.locations");
        }
        for (int i = 0; i < crafterLocations.size(); i++) {
            configurationSection.set(Integer.toString(i), crafterLocations.get(i));
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
    }

    public static MoonPhases getMoonPhase(World world) {
        long days = world.getFullTime() / 24000;
        long phase = days % 8;

        if (phase == 0) {
            return MoonPhases.FULL;
        } else if (phase == 1) {
            return MoonPhases.WAINING_GIBBUS;
        } else if (phase == 2) {
            return MoonPhases.WAINING_HALF;
        } else if (phase == 3) {
            return MoonPhases.WAINING_CRESCENT;
        } else if (phase == 4) {
            return MoonPhases.NEW;
        } else if (phase == 5) {
            return MoonPhases.WAXING_CRESCENT;
        } else if (phase == 6) {
            return MoonPhases.WAXING_HALF;
        } else if (phase == 7) {
            return MoonPhases.WAXING_GIBBUS;
        }
        return MoonPhases.UNKNOWN;

    }

    @EventHandler
    public void toggleGlideEvent(EntityToggleGlideEvent event) {

        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (SkyUtils.checkForBoots(p)) {
                if (p.isGliding()) {
                    // toggle off
                    if (!p.isOnGround()) {
                        event.setCancelled(true);
                        p.setGliding(true);
                    }
                } else {
                    // toggle on
                }
            }
        }
    }

    public static boolean day(World w) {

        long time = w.getTime();

        return time < 12300 || time > 23850;
    }

    // @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        Chunk chunk = event.getChunk();
        if (chunk.getBlock(0, 64, 0).getBiome() == Biome.DEEP_OCEAN || chunk.getBlock(0, 64, 0).getBiome() == Biome.OCEAN) {
            if (Math.random() > 0.95) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 32; y < 128; y++) {
                            Block current = chunk.getBlock(x, y, z);
                            if (current.getBiome() == Biome.DEEP_OCEAN || current.getBiome() == Biome.OCEAN) {
                                if (current.getType() == Material.GRAVEL) {
                                    createSeaHouse(current);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
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

    public void setSquidSpawner(Block block) {
        BlockState blockState = block.getState();
        CreatureSpawner spawner = ((CreatureSpawner) blockState);
        spawner.setSpawnedType(EntityType.SQUID);
        blockState.update();
    }

    public void createSeaHouse(Block current) {
        current.setType(Material.PRISMARINE);
        fill(current.getLocation().add(3, 0, 3), current.getLocation().add(-3, 0, -3), Material.STONE);
        fill(current.getLocation().add(3, 1, 3), current.getLocation().add(-3, 5, -3), Material.PRISMARINE);
        fill(current.getLocation().add(2, 1, 2), current.getLocation().add(-2, 4, -2), Material.WATER);
        fill(current.getLocation().add(3, 1, 0), current.getLocation().add(3, 2, 0), Material.WATER);
        fill(current.getLocation().add(0, 5, 0), current.getLocation().add(0, 8, 0), Material.SEA_LANTERN);

        fill(current.getLocation().add(3, 0, 3), current.getLocation().add(3, 6, 3), Material.GOLD_BLOCK);
        fill(current.getLocation().add(-3, 0, 3), current.getLocation().add(-3, 6, 3), Material.GOLD_BLOCK);
        fill(current.getLocation().add(3, 0, -3), current.getLocation().add(3, 6, -3), Material.GOLD_BLOCK);
        fill(current.getLocation().add(-3, 0, -3), current.getLocation().add(-3, 6, -3), Material.GOLD_BLOCK);

        current.setType(Material.MOB_SPAWNER);
        setSquidSpawner(current);

        Block b = current.getLocation().add(0, 1, 0).getBlock();
        b.setType(Material.CHEST);

        Chest chest = (Chest) b.getState();

        // get the inventory to edit it
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

        // From here do as you need. This is all pseudo code

        for (int i = 0; i < 4; i++) {

            Zombie zombie = (Zombie) current.getWorld().spawnEntity(current.getLocation(), EntityType.ZOMBIE);

            EntityEquipment zomb = zombie.getEquipment();
            zombie.setMaxHealth(60D);
            zombie.setHealth(60D);

            zombie.setVillager(false);
            zombie.setBaby(false);

            zomb.setBoots(ArmourUtil.createAquaBoots());
            zomb.setBootsDropChance(1f);
            zomb.setLeggings(ArmourUtil.createAquaPants());
            zomb.setChestplate(ArmourUtil.createAquaShirt());
            zomb.setHelmet(new ItemStack(Material.SEA_LANTERN));
            // zomb.setItemInHand(new ItemStack(Material.STONE_HOE));
            zombie.setCustomName("Sea Monster");
            zombie.teleport(current.getLocation());
        }
        for (int i = 0; i < 5; i++) {

            Zombie zombie = (Zombie) current.getWorld().spawnEntity(current.getLocation().add(0, 1, 0), EntityType.ZOMBIE);

            EntityEquipment zomb = zombie.getEquipment();
            zombie.setMaxHealth(60D);
            zombie.setHealth(60D);

            zombie.setVillager(false);
            zombie.setBaby(true);

            zomb.setBoots(ArmourUtil.createAquaBoots());
            zomb.setBootsDropChance(1f);
            zomb.setLeggings(ArmourUtil.createAquaPants());
            zomb.setChestplate(ArmourUtil.createAquaShirt());
            zomb.setHelmet(new ItemStack(Material.SEA_LANTERN));
            // zomb.setItemInHand(new ItemStack(Material.STONE_HOE));
            zombie.setCustomName("Sea Monster");
            zombie.teleport(current.getLocation());
        }
    }

    public void createTower(Block current) {
        current.setType(Material.STONE);
        fill(current.getLocation().add(3, 0, 3), current.getLocation().add(-3, -20, -3), Material.COBBLESTONE);
        fill(current.getLocation().add(3, 10, 3), current.getLocation().add(-3, 0, -3), Material.STONE);
        fill(current.getLocation().add(2, 10, 2), current.getLocation().add(-2, 1, -2), Material.AIR);

        fill(current.getLocation().add(0, 1, 3), current.getLocation().add(0, 11, 3), Material.COBBLESTONE);
        fill(current.getLocation().add(0, 1, -3), current.getLocation().add(0, 11, -3), Material.COBBLESTONE);
        fill(current.getLocation().add(3, 1, 0), current.getLocation().add(3, 11, 0), Material.COBBLESTONE);
        fill(current.getLocation().add(-3, 1, 0), current.getLocation().add(-3, 11, 0), Material.COBBLESTONE);

        fill(current.getLocation().add(3, 10, 3), current.getLocation().add(3, 0, 3), Material.AIR);
        fill(current.getLocation().add(-3, 10, -3), current.getLocation().add(-3, 0, -3), Material.AIR);
        fill(current.getLocation().add(3, 10, -3), current.getLocation().add(3, 0, -3), Material.AIR);
        fill(current.getLocation().add(-3, 10, 3), current.getLocation().add(-3, 0, 3), Material.AIR);

        fill(current.getLocation().add(0, 1, 3), current.getLocation().add(0, 2, 3), Material.AIR);
        fill(current.getLocation().add(0, 1, -3), current.getLocation().add(0, 2, -3), Material.AIR);
        fill(current.getLocation().add(3, 1, 0), current.getLocation().add(3, 2, 0), Material.AIR);
        fill(current.getLocation().add(-3, 1, 0), current.getLocation().add(-3, 2, 0), Material.AIR);

        fill(current.getLocation().add(3, 1, -1), current.getLocation().add(3, 3, 1), Material.AIR);

        fill(current.getLocation().add(0, 5, 3), current.getLocation().add(0, 6, 3), Material.AIR);
        fill(current.getLocation().add(0, 5, -3), current.getLocation().add(0, 6, -3), Material.AIR);
        fill(current.getLocation().add(3, 5, 0), current.getLocation().add(3, 6, 0), Material.AIR);
        fill(current.getLocation().add(-3, 5, 0), current.getLocation().add(-3, 6, 0), Material.AIR);

        fill(current.getLocation().add(2, 4, 2), current.getLocation().add(-2, 4, -2), Material.COBBLESTONE);
        fill(current.getLocation().add(1, 4, 1), current.getLocation().add(-1, 4, -1), Material.AIR);

        fill(current.getLocation().add(2, 9, 2), current.getLocation().add(-2, 9, -2), Material.COBBLESTONE);
        fill(current.getLocation().add(1, 9, 1), current.getLocation().add(-1, 9, -1), Material.AIR);

        fill(current.getLocation().add(2, 11, 3), current.getLocation().add(2, 0, 3), Material.COBBLESTONE);

        fill(current.getLocation().add(-2, 11, -3), current.getLocation().add(-2, 0, -3), Material.COBBLESTONE);
        fill(current.getLocation().add(2, 11, -3), current.getLocation().add(2, 0, -3), Material.COBBLESTONE);
        fill(current.getLocation().add(-2, 11, 3), current.getLocation().add(-2, 0, 3), Material.COBBLESTONE);
        fill(current.getLocation().add(3, 11, 2), current.getLocation().add(3, 0, 2), Material.COBBLESTONE);
        fill(current.getLocation().add(-3, 11, -2), current.getLocation().add(-3, 0, -2), Material.COBBLESTONE);
        fill(current.getLocation().add(3, 11, -2), current.getLocation().add(3, 0, -2), Material.COBBLESTONE);
        fill(current.getLocation().add(-3, 11, 2), current.getLocation().add(-3, 0, 2), Material.COBBLESTONE);

        fill(current.getLocation().add(0, 11, 0), current.getLocation().add(0, 0, 0), Material.COBBLESTONE);
        fill(current.getLocation().add(0, 11, 0), current.getLocation().add(0, 11, 0), Material.GLOWSTONE);
        fill(current.getLocation().add(0, 1, 0), current.getLocation().add(0, 1, 0), Material.GLOWSTONE);

        Block b = current.getLocation().add(0, 12, 0).getBlock();
        b.setType(Material.CHEST);

        Chest chest = (Chest) b.getState();

        // get the inventory to edit it
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
        if ("Eyeball".equals(event.getEntity().getCustomName())) {
            if (event.getDamage() < 7) {
                EyeController c = new EyeController(0);
                c.spawn(event.getEntity().getLocation());
            }
        }
        if ("Bandit".equals(event.getDamager().getCustomName()) && event.getDamager() instanceof Zombie) {
            Zombie z = (Zombie) event.getDamager();
            if (event.getEntity() instanceof Player) {
                Player p = (Player) event.getEntity();
                if (z.getEquipment().getItemInMainHand().getType() == Material.WOOD_SWORD && p.getEquipment().getItemInMainHand().getType() != Material.WOOD_SWORD
                        && p.getEquipment().getItemInMainHand().getType().toString().contains("SWORD")) {
                    z.getEquipment().setItemInMainHand(p.getItemInHand());
                    z.getEquipment().setItemInMainHandDropChance(1f);
                    p.getItemInHand().setType(Material.AIR);
                    p.getItemInHand().setType(Material.WOOD_SWORD);
                    p.getItemInHand().setDurability((short) 0);
                    ;

                }

            }
        }
    }

}