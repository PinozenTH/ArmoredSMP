package me.armored.core;

import me.armored.core.event.JoinEvent;
import me.armored.core.event.RespawnEvent;
import me.armored.core.utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.signature.qual.ClassGetName;

import java.sql.SQLException;

public final class Armored extends JavaPlugin {


    @Override
    public void onEnable() {

        Bukkit.getLogger().info("Armored plugin is starting");
        loadDatabase();
        registerEvents();
        Bukkit.getLogger().info("Armored plugin has been started");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(), this);
    }

    public void loadDatabase() {
        Bukkit.getLogger().info("Armored connecting to database..");
        try {
            Database database = new Database();
            database.initializeDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Armored could not initialize database.");
        }
        Bukkit.getLogger().info("Armored connected to database!");
    }

    // ban player
    // check respawn event if full netherite -> ban 30 minute
    // unbanned -> check if been banned -> clear all item, advancement, xp

    @Override
    public void onDisable() {
        Bukkit.getLogger().info( "Armored plugin is disabled");
    }
}
