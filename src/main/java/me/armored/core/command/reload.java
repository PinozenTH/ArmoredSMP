package me.armored.core.command;

import me.armored.core.Recipes.Recipes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class reload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("armored.admin")) {
            new Recipes().LoadRecipes();
            commandSender.sendMessage("Plugin reloaded!");
            return true;
        }
        return true;
    }
}
