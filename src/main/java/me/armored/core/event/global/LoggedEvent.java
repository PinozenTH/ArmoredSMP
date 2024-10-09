package me.armored.core.event.global;

import me.armored.core.utils.CooldownManager;
import me.armored.core.utils.Database;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;

import static me.armored.core.Armored.plugin;
import static me.armored.core.command.challenge.challenge;
import static me.armored.core.utils.Database.isWhitelist;

public class LoggedEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (!isWhitelist(player.getName())) {
            player.kickPlayer(ChatColor.RED + "Please register a whitelist on discord.armored.pinont.com");
            e.joinMessage(null);
        }

        try {
            PreparedStatement ps = Database.connection.prepareStatement("SELECT * FROM ban WHERE uuid=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // player is banned, kick out and return ban reason
                String reason = rs.getString("reason");
                Timestamp duration = rs.getTimestamp("duration");
                String message = ChatColor.RED + "You are banned from the Server" + "\n" + ChatColor.YELLOW + "Reason: " + reason;
                if (duration != null) {
                    long millisLeft = duration.getTime() - System.currentTimeMillis(); // .getTime -> milli - current time (milli)
                    if (millisLeft > 0) {
                        message += "\nexpires: " + duration;
                        e.setJoinMessage(null);
                        player.kickPlayer(message);
                    } else {
                        // ban expired, remove from database
                        PreparedStatement ps2 = Database.connection.prepareStatement("DELETE FROM ban WHERE uuid=?");
                        ps2.setString(1, player.getUniqueId().toString());
                        ps2.executeUpdate();
                        ps2.closeOnCompletion();
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        challenge.remove(player);
    }
}
