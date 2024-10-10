package com.pinont.armoredsmp.events;

import com.pinont.armoredsmp.scoreboard.Health;
import com.pinont.piXLib.api.utils.enums.MessageType;
import com.pinont.piXLib.api.utils.texts.Message;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class ConnectionEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Health.setHealthScore(event.getPlayer());
        if (event.getPlayer().hasPlayedBefore()) {
            event.setJoinMessage(ChatColor.AQUA + "Welcome back to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName());
            new Message(ChatColor.AQUA + "Welcome back to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName()).setMessageType(MessageType.BOTH).send();
            return;
        }
        event.setJoinMessage(ChatColor.AQUA + "Welcome to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName());
        new Message(ChatColor.AQUA + "Welcome back to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName()).setMessageType(MessageType.BOTH).send();
    }

}
