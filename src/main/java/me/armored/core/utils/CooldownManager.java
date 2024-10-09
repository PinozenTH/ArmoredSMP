package me.armored.core.utils;

import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.util.HashMap;

import static me.armored.core.event.global.HeartCake.cakeEaten;

public class CooldownManager {
    public static HashMap<String, Timestamp> cakeCooldown = new HashMap<>();
    public static HashMap<String, Timestamp> milkCooldown = new HashMap<>();

    public static boolean isCooldown(Player player, String action) {
        if (action.equals("cake")) {
            if (cakeCooldown.containsKey(player.getUniqueId().toString())) {
                Timestamp time = cakeCooldown.get(player.getUniqueId().toString());
                return time.after(new Timestamp(System.currentTimeMillis()));
            }
        } else if (action.equals("milk")) {
            if (milkCooldown.containsKey(player.getUniqueId().toString())) {
                Timestamp time = milkCooldown.get(player.getUniqueId().toString());
                return time.after(new Timestamp(System.currentTimeMillis()));
            }
        }
        return false;
    }

    public static Timestamp getCooldown(int i) {
        return new Timestamp(System.currentTimeMillis() + (long) i * 24 * 60 * 60 * 1000);
    }

    public static boolean hasCooldownHash(Player player) {
        return cakeCooldown.containsKey(player.getUniqueId().toString()) && milkCooldown.containsKey(player.getUniqueId().toString());
    }

    public static void putsAll(Player player) {
        cakeCooldown.put(player.getUniqueId().toString(), new Timestamp(System.currentTimeMillis()));
        milkCooldown.put(player.getUniqueId().toString(), new Timestamp(System.currentTimeMillis()));
    }

    public static void checkCakeCooldown() {
        for (String key : cakeCooldown.keySet()) {
            if (cakeCooldown.get(key).before(new Timestamp(System.currentTimeMillis()))) {
                cakeEaten.remove(key);
            }
        }
    }
}
