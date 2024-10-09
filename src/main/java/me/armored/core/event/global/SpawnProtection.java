package me.armored.core.event.global;

import me.armored.core.Armored;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SpawnProtection implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e){ // edited by Pinont_
        Player p = e.getPlayer();
        if (!(Armored.build.contains(p)) && p.getWorld().equals("world")) {
            if (Armored.bamboo.contains(e.getBlock().getLocation()) || Armored.conduit.contains(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        if (!(Armored.build.contains(p)) && p.getWorld().equals("world")) {
            if (Armored.bamboo.contains(e.getBlockPlaced().getLocation()) || Armored.conduit.contains(e.getBlockPlaced().getLocation())) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onCommandWE(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage();
        if(cmd.contains("//") || cmd.toLowerCase().contains("/br") || cmd.toLowerCase().contains("/wea")) {
            if (!(Armored.build.contains(p)) && p.getWorld().equals("world")) {
                p.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                e.setCancelled(true);
            }
        }
    }

}
