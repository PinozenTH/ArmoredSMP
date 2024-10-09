package me.armored.core.command;

import me.armored.core.Armored;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class minigame implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            WorldBorder border = Objects.requireNonNull(Bukkit.getWorld("firstevent")).getWorldBorder();
            border.setSize(300, 60);
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.addPotionEffect(PotionEffectType.CONDUIT_POWER.createEffect(PotionEffect.INFINITE_DURATION, 1));
            }

            // delay 30 minutes -> broadcast "30 minutes left"
            // delay 45 minutes -> broadcast "15 minutes left"
            // delay 55 minutes -> broadcast "5 minutes left"
            // delay 59 minutes -> broadcast "1 minute left"
            // delay 60 minutes -> broadcast "Game Over"

            Bukkit.getScheduler().runTaskLater(Armored.plugin, () -> {
                border.setSize(275, 360);
                broadcast(ChatColor.GREEN + "30 Minutes left", "border: 300 -> 275");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "30 minutes left");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "Next border: 250");
            }, 36000L); // 36000L = 30 minutes

            Bukkit.getScheduler().runTaskLater(Armored.plugin, () -> {
                border.setSize(250, 360);
                broadcast(ChatColor.GREEN + "15 Minutes left", "border: 275 -> 250");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "15 minutes left");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "Next border: 200");
            }, 54000L); // 54000L = 45 minutes

            Bukkit.getScheduler().runTaskLater(Armored.plugin, () -> {
                border.setSize(200, 360);
                broadcast(ChatColor.GREEN + "5 Minutes left", "border: 250 -> 200");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "5 minutes left");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "Next border: 150");
            }, 66000L); // 66000L = 55 minutes

            Bukkit.getScheduler().runTaskLater(Armored.plugin, () -> {
                border.setSize(150, 360);
                broadcast(ChatColor.GREEN + "1 Minute left", "border: 200 -> 150");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "1 minute left");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "Next border: 100");
            }, 70800L); // 70800L = 59 minutes

            Bukkit.getScheduler().runTaskLater(Armored.plugin, () -> {
                border.setSize(100, 360);
                broadcast(ChatColor.GREEN + "Game Over", "border: 150 -> 100");
                Bukkit.broadcastMessage(ChatColor.BOLD + "ARMORED SMP" + ChatColor.RESET + "" + ChatColor.YELLOW + ">>" + ChatColor.GREEN + "Game Over");
            }, 72000L); // 72000L = 60 minutes

            return true;
        }
        return false;
    }

    private void broadcast(String title, String subtitle) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle(title, subtitle, 10, 70, 20);
        }
    }
}
