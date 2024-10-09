package me.armored.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            if (player.hasPermission("ranks.admin")) {
                if (strings.length == 0) {
                    if (player.isFlying()) {
                        player.setFlying(false);
                        player.sendMessage("You are now not flying!");
                    } else {
                        player.setFlying(true);
                        player.sendMessage("You are now flying!");
                    }
                } else {
                    player.sendMessage("Usage: /fly");
                }
            } else {
                player.sendMessage("You don't have permission to do that!");
            }
        }

        return false;
    }
}
