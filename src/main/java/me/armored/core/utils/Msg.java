package me.armored.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Msg {

    public static void console(String message) {
        Bukkit.getLogger().info(message);
    }

    public static void player(Player player, String message) {
        player.sendMessage(message);
    }

    public static void actionbar(Player player, String message) {
        player.sendActionBar(message);
    }

}
