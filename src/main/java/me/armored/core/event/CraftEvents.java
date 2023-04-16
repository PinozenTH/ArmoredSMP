package me.armored.core.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftEvents implements Listener {

    @EventHandler
    public void onPlayerCrafting(CraftItemEvent event) {
        Material craftResult = event.getRecipe().getResult().getType();

        if (craftResult.getEquipmentSlot().isArmor()) {
            event.setCancelled(true);
        }
    }

}
