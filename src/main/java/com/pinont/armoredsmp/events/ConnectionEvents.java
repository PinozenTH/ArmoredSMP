package com.pinont.armoredsmp.events;

import com.pinont.piXLib.utils.enums.MessageType;
import com.pinont.piXLib.utils.texts.Message;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (event.getPlayer().hasPlayedBefore()) {
            event.setJoinMessage(ChatColor.AQUA + "Welcome back to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName());
            new Message(MessageType.BOTH, ChatColor.AQUA + "Welcome back to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName());
        } else {
            event.setJoinMessage(ChatColor.AQUA + "Welcome to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName());
            new Message(MessageType.BOTH, ChatColor.AQUA + "Welcome back to Armored SMP! " + ChatColor.YELLOW + ChatColor.BOLD + event.getPlayer().getName());
//            event.getPlayer().getInventory().addItem(QuestBooks.getQuestBook());
        }
    }

}
