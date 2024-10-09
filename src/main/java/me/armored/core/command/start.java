package me.armored.core.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static me.armored.core.Armored.plugin;

public class start implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Bukkit.broadcastMessage(ChatColor.BOLD + "Armored SMP" + ChatColor.RESET + "" + ChatColor.GREEN + " has started" + ChatColor.RED + " PVP is off for 2 hour!");
        WorldBorder border = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(commandSender.getName())) {
                Location location = new Location(Bukkit.getWorld("world"), 186, 62, 230);
                player.teleport(location);
            }
        }
        border.setSize(4.5);
        border.setSize(200000, TimeUnit.HOURS, 2);
        int delayTicks = 2 * 60 * 60 * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(ChatColor.RED + "PVP has been enabled!");
            }
        }.runTaskLaterAsynchronously(plugin, delayTicks);
        return true;
    }
}
