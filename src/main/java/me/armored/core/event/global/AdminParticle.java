package me.armored.core.event.global;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

import static me.armored.core.Armored.isVanished;

public class AdminParticle implements Listener {
    @EventHandler
    public void move(PlayerMoveEvent event) {
        if (event.getPlayer().getName().equals("Pinont_")) {
            if (isVanished(event.getPlayer())
                    || event.getPlayer().getGameMode().equals(GameMode.SPECTATOR))
                return;
            Random random = new Random();
            int x = random.nextInt(255);
            random = new Random();
            int y = random.nextInt(255);
            random = new Random();
            int z = random.nextInt(255);
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(x, y, z), 1);
            Particle particle = Particle.REDSTONE;

            event.getPlayer().getWorld().spawnParticle(particle, event.getPlayer().getLocation(), 1, dustOptions);
        }
    }

    @EventHandler
    public void onChangeGameMode(PlayerGameModeChangeEvent event) {
        if (event.getPlayer().getName().equals("WitheredBouquet")) {
            if (isVanished(event.getPlayer())) return;
            Particle particle = Particle.EXPLOSION_HUGE;
            event.getPlayer().getWorld().spawnParticle(particle, event.getPlayer().getLocation(), 2);
        } else if (event.getPlayer().getName().equals("Pinont_")) {
            if (isVanished(event.getPlayer())) return;
            Particle particle = Particle.FIREWORKS_SPARK;
            event.getPlayer().getWorld().spawnParticle(particle, event.getPlayer().getLocation(), 5);
        }
    }
}
