package me.armored.core.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class nick implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            if (strings.length < 1) {
                player.sendMessage(ChatColor.RED + "You need to specify a name!");
                return false;
            } else {
                StringBuilder name = new StringBuilder();
                if (strings.length > 1) {
                    for (String string : strings) {
                        name.append(string).append(" ");
                    }
                } else {
                    name.append(strings[0]);
                }
                player.setDisplayName(name.toString());
                player.setPlayerListName(player.getDisplayName());
                player.setCustomName(player.getDisplayName());
                player.setCustomNameVisible(true);
                player.sendMessage("Your name has been set to " + player.getDisplayName());
                return true;
            }
        }
        return false;
    }
}
