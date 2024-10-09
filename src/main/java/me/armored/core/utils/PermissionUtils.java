package me.armored.core.utils;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PermissionUtils {

    private static final LuckPerms luckPerms = LuckPermsProvider.get();

    public static Boolean hasPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

    public static Boolean isLogined(Player player) {
        return player.hasPermission("ranks.user");
    }

    public static void addPermission(User user, String permission) {
        // Add the permission
        user.data().add(Node.builder(permission).build());

        // Now we need to save changes.
        luckPerms.getUserManager().saveUser(user);
    }

    public static void addPermission(UUID userUuid, String permission) {
        // Load, modify, then save
        luckPerms.getUserManager().modifyUser(userUuid, user -> {
            // Add the permission
            user.data().add(Node.builder(permission).build());
        });
    }

    public static void removePermission(User user, String permission) {
        // Remove the permission
        user.data().remove(Node.builder(permission).build());

        // Now we need to save changes.
        luckPerms.getUserManager().saveUser(user);
    }

    public static void removePermission(UUID userUuid, String permission) {
        // Load, modify, then save
        luckPerms.getUserManager().modifyUser(userUuid, user -> {
            // Remove the permission
            user.data().remove(Node.builder(permission).build());
        });
    }
}
