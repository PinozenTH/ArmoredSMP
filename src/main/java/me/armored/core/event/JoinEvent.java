package me.armored.core.event;

import me.armored.core.utils.Database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        try {
            PreparedStatement ps = Database.connection.prepareStatement("SELECT * FROM ban WHERE uuid=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // player is banned, kick out and return ban reason
                String reason = rs.getString("reason");
                Timestamp duration = rs.getTimestamp("duration");
                String message = ChatColor.RED + "You are banned from the Server" + "\n" + ChatColor.YELLOW + "Reason: " +reason;
                if (duration != null) {
                    long millisLeft = duration.getTime() - System.currentTimeMillis();
                    if (millisLeft > 0) {
                        message += "\nexpires: " + TimeUnit.MICROSECONDS.toMinutes(millisLeft) + " minutes.";
                    } else {
                        // ban expired, remove from database
                        PreparedStatement ps2 = Database.connection.prepareStatement("DELETE FROM ban WHERE uuid=?");
                        ps2.setString(1, player.getUniqueId().toString());
                        ps2.executeUpdate();
                        ps2.close();
                    }
                }
                e.setJoinMessage(null);
                player.kickPlayer(message);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
