package me.armored.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class color implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You need to specify a color and player");
            } else if (args.length == 1) {
                player.sendMessage(ChatColor.RED + "You need to specify a player");
            } else if (args.length == 2 && player.hasPermission("ranks.team")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    HashMap<String, String> color = new HashMap<>();
                    color.put("RED", ChatColor.RED + "" + ChatColor.BOLD + "R " + ChatColor.RESET);
                    color.put("GREEN", ChatColor.GREEN + "" + ChatColor.BOLD + "G " + ChatColor.RESET);
                    color.put("BLUE", ChatColor.AQUA + "" + ChatColor.BOLD + "B " + ChatColor.RESET);
                    color.put("YELLOW", ChatColor.YELLOW + "" + ChatColor.BOLD + "Y " + ChatColor.RESET);

                    if (color.containsKey(args[0].toUpperCase())) {
                        target.setDisplayName(color.get(args[0].toUpperCase()) + target.getDisplayName());
                        target.setPlayerListName(target.getDisplayName());
                        target.setCustomName(target.getDisplayName());
                        target.setCustomNameVisible(true);
                        player.sendMessage(ChatColor.GREEN + "That player has been set to " + target.getName());
                    } else {
                        player.sendMessage(ChatColor.RED + "That color is not specific to what we have!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "That player is not online!");
                }

            }
            return true;
        }
        return false;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        List<String> ret = new ArrayList<>();

        switch (args.length) {
            case 1:
                ret.add("RED");
                ret.add("GREEN");
                ret.add("BLUE");
                ret.add("YELLOW");
                return StringUtil.copyPartialMatches(args[0].toLowerCase(), ret, new ArrayList<>());
            case 2:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ret.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[1].toLowerCase(), ret, new ArrayList<>());
        }

        return Collections.emptyList();
    }
}
