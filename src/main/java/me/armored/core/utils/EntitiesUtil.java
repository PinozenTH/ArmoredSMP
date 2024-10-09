package me.armored.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntitiesUtil {

    public static Entity createEntity(Location location, Class<? extends Entity> entityClass) {
        Entity entity = Bukkit.getWorld("world").createEntity(location, entityClass);
        return entity;
    }

}
