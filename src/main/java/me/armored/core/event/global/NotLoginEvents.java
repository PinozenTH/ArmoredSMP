package me.armored.core.event.global;

import me.armored.core.utils.PermissionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class NotLoginEvents implements Listener {

    private void setWarning(Player player) {
        player.sendActionBar("You are not logged in, please login first!");
        player.playSound(player.getLocation(), "minecraft:block.note_block.bass", 1, 1);
    }

    @EventHandler
    public void onWalking(PlayerMoveEvent e) {
        if (PermissionUtils.hasPermission(e.getPlayer(), "user")) {
            setWarning(e.getPlayer());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (PermissionUtils.hasPermission(player, "user")) {
            setWarning(player);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (PermissionUtils.hasPermission(player, "user")) {
            setWarning(player);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void openInventory(InventoryEvent e) {
        if (e.getView().getPlayer() instanceof Player player) {
            if (PermissionUtils.hasPermission(player, "user")) {
                setWarning(player);
                e.getInventory().close();
            }
        }
    }

    @EventHandler
    public void DropItemEvent(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (PermissionUtils.hasPermission(player, "user")) {
            setWarning(player);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (PermissionUtils.hasPermission(player, "user")) {
            setWarning(player);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        if (PermissionUtils.hasPermission(player, "user")) {
            setWarning(player);
            e.setCancelled(true);
        }
    }
}
