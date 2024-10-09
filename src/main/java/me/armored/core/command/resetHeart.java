package me.armored.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class resetHeart implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("ranks.admin")) {
            if (strings.length == 0) {
                commandSender.sendMessage("§c/resetHeart <player>");
                return true;
            }
            Player player = commandSender.getServer().getPlayer(strings[0]);
            if (player == null) {
                commandSender.sendMessage("§cPlayer not found");
                return true;
            }
            player.setHealth(20);
            commandSender.sendMessage("§aHeart reset for " + strings[0]);
            return true;
        } else {
            commandSender.sendMessage("§cYou don't have permission to use this command");
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (Player player : commandSender.getServer().getOnlinePlayers()) {
            return List.of(player.getName());
        }
        return null;
    }
}
