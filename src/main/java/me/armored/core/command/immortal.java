package me.armored.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class immortal implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("ranks.admin")) {
                if (strings.length == 0) {
                    if (player.isInvulnerable()) {
                        player.setInvulnerable(false);
                        player.sendMessage("§aImmortality removed");
                        return true;
                    } else {
                        player.setInvulnerable(true);
                        player.sendMessage("§aImmortality added");
                        return true;
                    }
                } else if (strings.length == 1) {
                    Player target = player.getServer().getPlayer(strings[0]);
                    if (target == null) {
                        player.sendMessage("§cPlayer not found");
                        return true;
                    } else if (target.isInvulnerable()) {
                        target.setInvulnerable(false);
                        player.sendMessage("§aImmortality removed");
                        return true;
                    } else {
                        target.setInvulnerable(true);
                        player.sendMessage("§aImmortality added");
                        return true;
                    }
                } else {
                    player.sendMessage("§cYou don't have permission to use this command");
                }
            } else {
                commandSender.sendMessage("§cYou must be a player to use this command");
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            for (Player player : commandSender.getServer().getOnlinePlayers()) {
                return List.of(player.getName());
            }
        }
        return null;
    }
}
