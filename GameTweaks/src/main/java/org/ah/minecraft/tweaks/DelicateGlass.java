package org.ah.minecraft.tweaks;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class DelicateGlass extends Tweak {

    @EventHandler
    public void onFall(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.getFallDistance() > 5) {
            Block b = event.getTo().getBlock().getRelative(BlockFace.DOWN);
            if (b.getType() == Material.GLASS) {
                p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.GLASS, 10);
                b.breakNaturally();
            }
            if (b.getType() == Material.ICE) {
                p.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.ICE, 10);
                b.breakNaturally();
            }
        }
    }
}
