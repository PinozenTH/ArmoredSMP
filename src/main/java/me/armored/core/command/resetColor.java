package me.armored.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class resetColor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            player.setCustomName(player.getName());
            player.setCustomNameVisible(true);
        }
        return true;
    }
}
