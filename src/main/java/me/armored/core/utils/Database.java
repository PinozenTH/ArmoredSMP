package me.armored.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Objects;

import static me.armored.core.Armored.plugin;

public class Database {
    public static Connection connection;

    public Connection getConnection() throws SQLException {
        String host = "localhost";
        String database = "db";
        String username = "user";
        String password = "minecraft";
        Connection con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?useSSL=true", username, password);
        Database.connection = con;
        return con;
    }

    public void initializeDatabase() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS plugin (name varchar(16), version varchar(16))");
        statement.execute("CREATE TABLE IF NOT EXISTS ban (uuid varchar(36), username varchar(16), reason varchar(128), duration timestamp)");
        statement.execute("CREATE TABLE IF NOT EXISTS whitelist (name varchar(16))");
        statement.execute("CREATE TABLE IF NOT EXISTS cooldown (uuid varchar(36), name varchar(16), action varchar(16), time timestamp)");
        statement.close();
    }

    public static void loadCooldownToHashMap() {
        checkConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM cooldown");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                String username = rs.getString("username");
                String action = rs.getString("action");
                Timestamp time = rs.getTimestamp("time");
                if (Objects.equals(action, "cake")) {
                    CooldownManager.cakeCooldown.put(uuid, time);
                } else if (Objects.equals(action, "milk")) {
                    CooldownManager.milkCooldown.put(uuid, time);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void uploadHashMapToCooldown() {
        checkConnection();
        try { // cooldown is a hashmap, so we need to iterate through the hashmap and insert each key-value pair into the database
            PreparedStatement ps = connection.prepareStatement("INSERT INTO cooldown(uuid, name, action, time) VALUES (?,?,?,?)");
            for (String key : CooldownManager.cakeCooldown.keySet()) {
                ps.setString(1, key);
                ps.setString(2, Bukkit.getPlayer(key).getName());
                ps.setString(3, "cake");
                ps.setTimestamp(4, CooldownManager.cakeCooldown.get(key));
                ps.executeUpdate();
            }
            for (String key : CooldownManager.milkCooldown.keySet()) {
                ps.setString(1, key);
                ps.setString(2, Bukkit.getPlayer(key).getName());
                ps.setString(3, "milk");
                ps.setTimestamp(4, CooldownManager.milkCooldown.get(key));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = new Database().getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Boolean isWhitelist(String name) {
        checkConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM whitelist WHERE name = ?");
            ps.setString(1, name.toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void Unban(Player player) throws SQLException {
        checkConnection();
        PreparedStatement ps = connection.prepareStatement("DELETE FROM ban WHERE uuid = ?");
        ps.setString(1, String.valueOf(player.getUniqueId()));
        ps.executeUpdate();
        ps.close();
    }

    public static void Ban(Player player, String reason, int durationHR) throws SQLException {
        checkConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT IGNORE INTO ban(uuid, username, reason, duration) VALUES (?,?,?, NOW() + INTERVAL ? HOUR)"); // what is NOW() + INTERVAL ? HOUR doing?
        ps.setString(1, player.getUniqueId().toString());
        ps.setString(2, player.getName());
        ps.setString(3, reason);
        ps.setInt(4, durationHR);
        ps.executeUpdate();
        ps.close();
    }

    public static void checkPluginVersion() throws SQLException {
        checkConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM plugin WHERE name = ?");
        ps.setString(1, "Armored");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String version = rs.getString("version");
            if (!version.equals(plugin.getDescription().getVersion())) {
                Bukkit.getLogger().info("Plugin is not up to date, please update it.");
                Bukkit.getServer().shutdown();
            }
        } else {
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO plugin(name, version) VALUES (?,?)");
            ps2.setString(1, "Armored");
            ps2.setString(2, plugin.getDescription().getVersion());
            ps2.executeUpdate();
            ps2.close();
        }
    }

    public static void updateVersion() throws SQLException {
        checkConnection();
        PreparedStatement ps = connection.prepareStatement("INSERT INTO plugin(name, version) VALUES (?,?)");
        ps.setString(1, "Armored");
        ps.setString(2, plugin.getDescription().getVersion());
        ps.executeUpdate();
        ps.close();
    }
}
