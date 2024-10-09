package me.armored.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class challenge implements CommandExecutor, TabCompleter {

    public static HashMap<Player, Boolean> challenge = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (player.hasPermission("ranks.admin")) {
                if (strings.length == 0) {
                    player.sendMessage("§c/challenge <player>");
                    return true;
                }
                Player target = player.getServer().getPlayer(strings[0]);
                if (target == null) {
                    player.sendMessage("§cPlayer not found");
                    return true;
                } else if (challenge.containsKey(target)) {
                    challenge.remove(target);
                    player.sendMessage("§aChallenge removed");
                    return true;
                } else {
                    challenge.put(target, true);
                    player.sendMessage("§aChallenge added");
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            return List.of("add", "remove");
        } else if (strings[0].isEmpty() || strings[0].equals("add")) {
            for (Player player : getServer().getOnlinePlayers()) {
                return List.of(player.getName());
            }
        } else if (strings[0].equals("remove")) {
            for (Player player : challenge.keySet()) {
                if (challenge.get(player)) {
                    return List.of(player.getName());
                }
            }
        }
        return null;
    }
}
