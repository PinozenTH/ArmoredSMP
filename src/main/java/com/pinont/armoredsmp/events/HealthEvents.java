package com.pinont.armoredsmp.events;

import com.pinont.armoredsmp.scoreboard.Health;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;


public class HealthEvents implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player)
        {
            Health.updateHealth(player, player.getHealth());
            player.setHealth(player.getScoreboard().getObjective("asmp_health").getScore(player.getName()).getScore());
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player player)
        {
            Health.updateHealth(player, player.getHealth());
        }
    }
}
