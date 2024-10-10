package com.pinont.armoredsmp.task;

import com.pinont.armoredsmp.Core;
import com.pinont.armoredsmp.scoreboard.Health;
import com.pinont.piXLib.PiXPlugin;
import com.pinont.piXLib.api.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class HealthTask {

    public static void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getScoreboard().getObjective("asmp_health") == null) {
                        Health.setHealthScore(player);
                    } else {
                        Health.updateHealth(player);
                    }
                }
            }
        }.runTaskTimerAsynchronously(Core.getInstance(), 0, 10);
    }

}
