package me.armored.core.command;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class fest implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (!player.getWorld().getName().equals("harvestFest")) {
                World harvest = player.getServer().getWorld("harvestFest");
                player.teleport(harvest.getSpawnLocation());
            } else {
                World smp = player.getServer().getWorld("world");
                if (player.getBedSpawnLocation() != null) {
                    Location spawnLocation = player.getBedSpawnLocation();
                    Location newLocation = new Location(smp, spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
                    player.teleport(newLocation);
                } else {
                    Location spawnLocation = player.getWorld().getSpawnLocation();
                    Location newLocation = new Location(smp, spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ());
                    player.teleport(newLocation);
                }
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command.");
        }
        return true;
    }

}
