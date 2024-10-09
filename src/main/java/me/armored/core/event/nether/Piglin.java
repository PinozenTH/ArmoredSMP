package me.armored.core.event.nether;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.inventory.ItemStack;

public class Piglin implements Listener {
    @EventHandler
    public void onPiglinTrade(PiglinBarterEvent event) {
        ItemStack item = event.getInput();
        if (item.getType().isAir()) return;
        if (item.getType().equals(Material.ELYTRA)) {
            event.getOutcome().set(0, new ItemStack(Material.DRAGON_HEAD));
            event.getOutcome().set(1, new ItemStack(Material.DRAGON_EGG));
        }
    }
}
