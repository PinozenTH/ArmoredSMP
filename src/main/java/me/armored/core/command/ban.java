package me.armored.core.command;

import me.armored.core.utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ban implements CommandExecutor, TabExecutor {


    @Override
    public boolean onCommand( CommandSender sender, Command cmd, String string, String[] args) {
        if (!sender.isOp()) {
            if (args.length > 0) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {

                } else {
                    try {
                        Database.Ban(target, ChatColor.YELLOW + "Administrator banned", 99999);
                        if (sender instanceof Player) {
                            sender.sendMessage(ChatColor.RED + target.getName() + " has been Banned!");
                        } else {
                            Bukkit.getLogger().info(ChatColor.RED + target.getName() + " has been Banned!");
                        }
                        target.kickPlayer("You have been banned from the server");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                if (sender instanceof Player) {
                    sender.sendMessage("Usage: /ban <player>");
                } else {
                    Bukkit.getLogger().info("Usage: /ban <player>");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete( CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (sender.isOp()) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("setChaser")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        commands.add(player.getName());
                    }
                }
                StringUtil.copyPartialMatches(args[0], commands, completions);
            }

            Collections.sort(completions);

            return completions;
        }
        return null;
    }
}
