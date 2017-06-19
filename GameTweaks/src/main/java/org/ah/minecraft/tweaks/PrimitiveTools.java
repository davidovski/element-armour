package org.ah.minecraft.tweaks;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PrimitiveTools extends Tweak {

    @EventHandler
    public void onHit (EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity)event.getDamager();
            if (e.getEquipment() != null) {
                ItemStack hand = e.getEquipment().getItemInMainHand();
                if (hand.getType() == Material.FLINT) {
                    event.setDamage(event.getDamage() + 1);
                    return;
                }
                if (hand.getType() == Material.STICK) {
                    event.setDamage(event.getDamage() + 1);
                    return;
                }
                if (hand.getType() == Material.BLAZE_ROD) {
                    event.setDamage(event.getDamage() + 1);
                    event.getEntity().setFireTicks(5);
                    return;
                }
                if (hand.getType() == Material.BONE) {
                    event.setDamage(event.getDamage() + 1);
                    return;
                }
            }
        }
    }

}
