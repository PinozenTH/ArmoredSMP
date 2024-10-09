package me.armored.core.command;

import me.armored.core.Armored;
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
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class bypass implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bypass")) {
            // Check for arguments
            if (args.length == 0) {
                if (!(sender instanceof Player p)) {
                    sender.sendMessage(ChatColor.RED+"Can't use this command with console.");
                    return true;
                }else{
                    if (!(sender.hasPermission("rank.admin"))) {
                        sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                        return true;
                    } else {
                        if (!(Armored.bypass.contains(p))) {
                            Armored.bypass.add(p);
                            p.sendMessage("Bypass has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+".");
                            return true;
                        } else if (Armored.bypass.contains(p)) {
                            Armored.bypass.remove(p);
                            p.sendMessage("Bypass has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+".");
                            return true;
                        }
                    }
                }
            }else if (args.length == 1) {
                if (!(sender.hasPermission("rank.admin"))) {
                    sender.sendMessage(ChatColor.RED+"You don't have permission to do that!");
                    return true;
                }else {
                    Player target = getServer().getPlayer(args[0]);
                    if (target == null){
                        sender.sendMessage("Player offline");
                        return true;
                    }
                    if (!(Armored.bypass.contains(target))) {
                        Armored.bypass.add(target);
                        target.sendMessage("Build has been "+ChatColor.GREEN+"enabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                        sender.sendMessage(ChatColor.GREEN+"Enabled build for "+ChatColor.YELLOW+target.getName());
                        return true;
                    } else if (Armored.bypass.contains(target)) {
                        Armored.bypass.remove(target);
                        target.sendMessage("Build has been "+ChatColor.RED+"disabled"+ChatColor.GRAY+" by "+ChatColor.YELLOW+sender.getName()+ChatColor.GRAY+".");
                        sender.sendMessage(ChatColor.RED+"Disabled build for "+ChatColor.YELLOW+target.getName());
                        return true;
                    }
                }
            } else {
                sender.sendMessage("/bypass" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable build mode.");
                sender.sendMessage("/bypass <player>" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Enable/Disable build mode player you want.");
                return true;
            }

        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (sender.isOp()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                commands.add(player.getName());
            }
            StringUtil.copyPartialMatches(args[0], commands, completions);

            Collections.sort(completions);

            return completions;
        }
        return null;
    }

}
