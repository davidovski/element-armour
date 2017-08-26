package org.ah.minecraft.pigs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.ah.minecraft.pigs.structure.Structure;
import org.ah.minecraft.pigs.structure.StructureAPI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftCreature;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EntityCreature;

public class Plugin extends JavaPlugin implements Listener {

    private static final ChatColor PINK = ChatColor.LIGHT_PURPLE;
    private HashMap<Player, Pig> holding;
    public long alpha = 0;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        holding = new HashMap<Player, Pig>();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                doEveryTick();
            }
        }, 1, 1);
    }

    public void moveTo(LivingEntity entity, Location to, float speed) {
        EntityCreature ec = ((CraftCreature) entity).getHandle();
        // PathEntity pf = ((CraftWorld) to.getWorld()).getHandle().a(ec, to.getX(), to.getY(), to.getZ(), 16.0f, true, false, false, true);
        // ec.setPathEntity(pf);
    }

    public void doEveryTick() {
        alpha++;
        for (Player p : getServer().getOnlinePlayers()) {
            if (holding.get(p) != null) {
                Location targetLoc = p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(3));
                holding.get(p).setVelocity(targetLoc.subtract(holding.get(p).getLocation()).toVector());
            }
        }

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity le : world.getLivingEntities()) {
                if (le instanceof Pig) {
                    if ("SoldierPig".equals(le.getCustomName())) {
                        le.setFallDistance(0);
                        le.setCustomNameVisible(false);
                        for (Entity e : le.getNearbyEntities(10, 10, 10)) {
                            if (e instanceof Player) {
                                Player p = (Player) e;
                                if (le.getTicksLived() % 10 == 0) {
                                    if (le.hasLineOfSight(p) && le.isOnGround() && (p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)) {
                                        Vector normalize = p.getEyeLocation().subtract(le.getLocation()).toVector().normalize().multiply(1);
                                        le.setVelocity(normalize);
                                        ((Pig) le).setTarget(p);
                                    }
                                }
                                if (p.getLocation().distanceSquared(le.getLocation()) < 2) {
                                    if (!p.isBlocking()) {
                                        p.damage(0.5D, le);
                                    } else {
                                        p.setVelocity(new Vector(0, 0, 0));
                                    }
                                }
                            }
                        }
                    }
                }
                if (le.getCustomName() != null) {
                    if (le.getCustomName().startsWith("Bomb")) {

                        if (le.isOnGround()) {
                            le.remove();
                            createPorkExplosion(16, le.getLocation());
                        }
                    }

                    if (le.getCustomName().equals(ChatColor.BLACK + "•")) {
                        le.setGravity(true);
                        le.playEffect(EntityEffect.HURT);
                        for (Entity e : le.getNearbyEntities(1, 1, 1)) {
                            if (e instanceof LivingEntity) {
                                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                                    if (player.getLocation().distanceSquared(e.getLocation()) < 64 * 64) {
                                        ((LivingEntity) e).damage(2D, player);
                                        break;
                                    }
                                }

                                le.remove();

                            }
                        }
                        if (le.isOnGround() && le.getTicksLived() > 20) {
                            le.remove();
                        }

                    }
                }
            }
        }
    }

    public static ItemStack getPigStaff() {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(PINK + "The Pork Staff");
        List<String> lore = new ArrayList<String>();
        lore.add(PINK + "Control the pigs!");
        lore.add(ChatColor.WHITE + "Right-click them to pick them up!");
        lore.add(ChatColor.WHITE + "Machine Gun them by holding right-click, while sneaking!");

        lore.add(ChatColor.GRAY + "Butcher V");
        lore.add(ChatColor.GRAY + "Launch V");
        lore.add(ChatColor.GRAY + "Durablitity V");

        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
        item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        item.addUnsafeEnchantment(Enchantment.THORNS, 5);
        return item;
    }

    public static void createPorkExplosion(int ammount, Location loc) {
        Random random = new Random();
        loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 4, false, false);
        for (int i = 0; i < ammount; i++) {
            Item item = loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.PORK));
            Vector randomv = new Vector((random.nextInt(200) - 100f) / 100f, (random.nextInt(200)) / 200f, (random.nextInt(200) - 100f) / 100f);
            randomv = randomv.multiply(0.5);
            item.setVelocity(randomv);
            item.teleport(loc.add(randomv));
            item.setPickupDelay(20);
        }
    }

    @EventHandler
    public void onMobClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity rightClicked = event.getRightClicked();
        if (player.getEquipment().getItemInMainHand().equals(getPigStaff()) && !player.isSneaking()) {
            if (rightClicked instanceof Pig) {
                if (rightClicked.getCustomName() == null) {
                    rightClicked.setCustomName("Right-click me");
                } else if (rightClicked.getCustomName().equals("Right-click me")) {
                    holding.put(player, (Pig) rightClicked);
                    rightClicked.setCustomName(
                            ChatColor.RED + "LEFT_CLICK" + ChatColor.YELLOW + " shoot" + ChatColor.GREEN + " | " + ChatColor.RED + "RIGHT_CLICK" + ChatColor.YELLOW + " drop");
                } else if (rightClicked.getCustomName().equals("and again...")) {
                    rightClicked.setCustomName("");
                } else {
                    rightClicked.setCustomName("and again...");
                    holding.remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onclick(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (p.isSneaking() && (p.getEquipment().getItemInMainHand().equals(getPigStaff()) || p.getEquipment().getItemInOffHand().equals(getPigStaff()))) {
            Pig pig = (Pig) p.getWorld().spawnEntity(p.getLocation().add(0, 0.5, 0).add(p.getLocation().getDirection().multiply(2)), EntityType.PIG);
            pig.setBaby();
            pig.setInvulnerable(true);
            pig.setAgeLock(true);
            pig.setVelocity(p.getLocation().getDirection().multiply(3));
            pig.setCustomName(ChatColor.BLACK + "•");
            pig.setCustomNameVisible(false);
        }
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            if (holding.containsKey(p)) {
                holding.get(p).setGravity(true);
                holding.get(p).setVelocity(p.getLocation().getDirection().multiply(2));
                holding.get(p).setCustomName("Bomb");
                holding.remove(p);

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
            if (cmd.getName().equalsIgnoreCase("staff")) {
                p.getInventory().addItem(getPigStaff());
                return true;
            } else if (cmd.getName().equalsIgnoreCase("saveChunk")) {
                if (args.length > 0) {
                    try {
                        StructureAPI structureAPI = new StructureAPI(this);
                        Block block1 = p.getWorld().getBlockAt(p.getLocation().getChunk().getX() * 16, 0, p.getLocation().getChunk().getZ() * 16);
                        Block block2 = p.getWorld().getBlockAt(p.getLocation().getChunk().getX() * 16 + 15, 255, p.getLocation().getChunk().getZ() * 16 + 15);

                        Structure structure = structureAPI.getStructure(block1, block2);
                        structureAPI.save(args[0], structure);
                    } catch (Exception e) {
                        p.sendMessage(ChatColor.RED + "OOpS! SoMEThing WenT WerY WronG!");
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return false;
                }
            } else if (cmd.getName().equalsIgnoreCase("pasteChunk")) {
                if (args.length > 0) {
                    try {
                        loadCastlePart(args[0], p.getLocation().getChunk());
                    } catch (Exception e) {
                        p.sendMessage(ChatColor.RED + "OOpS! SoMEThing WenT WerY WronG!");
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    private void loadCastlePart(String name, Chunk chunk) {
        StructureAPI structureAPI = new StructureAPI(this);
        Block block1 = chunk.getWorld().getBlockAt(chunk.getX() * 16, 0, chunk.getZ() * 16);

        Structure structure = structureAPI.load(name);
        structureAPI.pasteWithPigs(structure, block1.getLocation());

    }

    @EventHandler
    public void onChunkCreate(ChunkPopulateEvent event) {
        Chunk chunk = event.getChunk();
        //
        if (chunk.getX() == 0 && chunk.getZ() == 102) {
            loadCastlePart("EDGEsc", chunk);
        }
        if (chunk.getX() == 0 && chunk.getZ() == 101) {
            loadCastlePart("MIDDLEs", chunk);
        }
        if (chunk.getX() == 0 && chunk.getZ() == 100) {
            loadCastlePart("CENTRE", chunk);
        }
        if (chunk.getX() == 0 && chunk.getZ() == 99) {
            loadCastlePart("MIDDLEn", chunk);
        }
        if (chunk.getX() == 0 && chunk.getZ() == 98) {
            loadCastlePart("EDGEnc", chunk);
        }
        //
        if (chunk.getX() == 1 && chunk.getZ() == 102) {
            loadCastlePart("EDGEsl", chunk);
        }
        if (chunk.getX() == 1 && chunk.getZ() == 101) {
            loadCastlePart("MIDDLEse", chunk);
        }
        if (chunk.getX() == 1 && chunk.getZ() == 100) {
            loadCastlePart("MIDDLEe", chunk);
        }
        if (chunk.getX() == 1 && chunk.getZ() == 99) {
            loadCastlePart("MIDDLEne", chunk);
        }
        if (chunk.getX() == 1 && chunk.getZ() == 98) {
            loadCastlePart("EDGEnr", chunk);
        }

        //
        if (chunk.getX() == -1 && chunk.getZ() == 102) {
            loadCastlePart("EDGEsr", chunk);
        }
        if (chunk.getX() == -1 && chunk.getZ() == 101) {
            loadCastlePart("MIDDLEsw", chunk);
        }
        if (chunk.getX() == -1 && chunk.getZ() == 100) {
            loadCastlePart("MIDDLEw", chunk);
        }
        if (chunk.getX() == -1 && chunk.getZ() == 99) {
            loadCastlePart("MIDDLEnw", chunk);
        }
        if (chunk.getX() == -1 && chunk.getZ() == 98) {
            loadCastlePart("EDGEnl", chunk);
        }

        //
        if (chunk.getX() == -2 && chunk.getZ() == 102) {
            loadCastlePart("CORNERsw", chunk);
        }
        if (chunk.getX() == -2 && chunk.getZ() == 101) {
            loadCastlePart("EDGEwr", chunk);
        }
        if (chunk.getX() == -2 && chunk.getZ() == 100) {
            loadCastlePart("EDGEwc", chunk);
        }
        if (chunk.getX() == -2 && chunk.getZ() == 99) {
            loadCastlePart("EDGEwl", chunk);
        }
        if (chunk.getX() == -2 && chunk.getZ() == 98) {
            loadCastlePart("CORNERnw", chunk);
        }

        //
        if (chunk.getX() == 2 && chunk.getZ() == 102) {
            loadCastlePart("CORNERse", chunk);
        }
        if (chunk.getX() == 2 && chunk.getZ() == 101) {
            loadCastlePart("EDGEer", chunk);
        }
        if (chunk.getX() == 2 && chunk.getZ() == 100) {
            loadCastlePart("EDGEec", chunk);
        }
        if (chunk.getX() == 2 && chunk.getZ() == 99) {
            loadCastlePart("EDGEel", chunk);
        }
        if (chunk.getX() == 2 && chunk.getZ() == 98) {
            loadCastlePart("CORNERne", chunk);
        }
    }
}
