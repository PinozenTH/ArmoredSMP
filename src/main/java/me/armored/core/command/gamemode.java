package me.armored.core.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        if (command.getName().equalsIgnoreCase("gmc")) {
            commandSender.sendMessage("You are now in creative mode!");
            ((Player) commandSender).setGameMode(GameMode.CREATIVE);
        } else if (command.getName().equalsIgnoreCase("gma")) {
            commandSender.sendMessage("You are now in adventure mode!");
            ((Player) commandSender).setGameMode(GameMode.ADVENTURE);
        } else if (command.getName().equalsIgnoreCase("gms")) {
            commandSender.sendMessage("You are now in survival mode!");
            ((Player) commandSender).setGameMode(GameMode.SURVIVAL);
        } else if (command.getName().equalsIgnoreCase("gmsp")) {
            commandSender.sendMessage("You are now in spectator mode!");
            ((Player) commandSender).setGameMode(GameMode.SPECTATOR);
        } else {
            commandSender.sendMessage("Usage: /gmc, /gma, /gms, /gmsp");
        }
        return true;
    }
}
