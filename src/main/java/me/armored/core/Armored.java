package me.armored.core;

import me.armored.core.command.ban;
import me.armored.core.command.unban;
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
        loadCommand();
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
            Bukkit.getLogger().info("Armored connected to database!");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Armored could not initialize database.");
        }
    }

    // ban player
    // check respawn event if full netherite -> ban 30 minute
    // unbanned -> check if been banned -> clear all item, advancement, xp

    public void loadCommand() {
        getCommand("ban").setExecutor(new ban());
        getCommand("ban").setTabCompleter(new ban());

        getCommand("unban").setExecutor(new unban());
        getCommand("unban").setTabCompleter(new unban());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info( "Armored plugin is disabled");
    }
}
