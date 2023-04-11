package me.armored.core.command;

import me.armored.core.utils.Database;
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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.armored.core.utils.Database.connection;

public class unban implements CommandExecutor, TabExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.isOp()) {
            if (args.length == 1) {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM ban WHERE uuid=?");
                    ps.setString(1, Bukkit.getPlayerUniqueId(args[0]).toString());
                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        sender.sendMessage(args[0] + " has been unbanned.");
                    } else {
                        sender.sendMessage(args[0] + " is not currently banned.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage("An error occurred while attempting to unban " + args[0] + ".");
                }
            } else {
                sender.sendMessage("Usage: /unban <player>");
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
