package me.armored.core.event.global;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemsAndArtifact implements Listener {
    @EventHandler
    public void onPlaceArmorRemover(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType().equals(Material.JACK_O_LANTERN)) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInMainHand().getType().equals(Material.JACK_O_LANTERN) && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getFoodLevel() < 8) {
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && isSweetArtifact(item)) {
                        event.setFoodLevel(8);
                    }
                }
            }
        }
    }

    private Boolean isSweetArtifact(ItemStack item) {
        return item.getType().equals(Material.HONEY_BOTTLE) && item.getItemMeta().hasCustomModelData();
    }
}
