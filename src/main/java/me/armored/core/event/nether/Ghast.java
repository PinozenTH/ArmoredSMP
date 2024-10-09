package me.armored.core.event.nether;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Random;

public class Ghast implements Listener {

    @EventHandler
    public void onGhastFireball(ProjectileLaunchEvent event) {
        if (event.getEntity().getType().equals(EntityType.FIREBALL) && event.getEntity().getShooter().equals(EntityType.GHAST)) {
            Random random = new Random();
            if (random.nextInt(100) > 90) {
                Fireball fireball = (Fireball) event.getEntity();
                Location location = fireball.getLocation();
                // summon 5 fireball around fireball
                for (int i = 0; i < 5; i++) {
                    Fireball newFireball = location.getWorld().spawn(location, Fireball.class);
                    newFireball.setDirection(location.getDirection());
                }
            }
        }
    }

}
