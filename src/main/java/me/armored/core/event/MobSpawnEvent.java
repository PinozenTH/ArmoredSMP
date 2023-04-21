package me.armored.core.event;

import me.armored.core.Armored;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class MobSpawnEvent implements Listener {

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Mob) {
            Mob mob = (Mob) event.getEntity();
            if (Armored.spawn.contains(mob.getLocation())) {
                event.setCancelled(true);
            }
        }
    }
}
