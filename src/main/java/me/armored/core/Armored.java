package me.armored.core;

import me.armored.core.command.*;
import me.armored.core.event.*;
import me.armored.core.utils.Cuboid;
import me.armored.core.utils.Database;
import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;

public final class Armored extends JavaPlugin {

    public static World world = Bukkit.getWorld("world");
    public static ArrayList<String> build = new ArrayList<String>();
    public static Cuboid spawn = new Cuboid(world, 33, 81, 395, 92, 44, 458);
    public static Cuboid farm = new Cuboid(world, 81, 64, 449, 45, 65, 454);
    public static Cuboid barn = new Cuboid(world, 57, 72, 438, 47, 73, 415);
    public static Plugin plugin;
    public Armored() {
        plugin = this;
    }

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
        getServer().getPluginManager().registerEvents(new CraftEvents(), this);
        getServer().getPluginManager().registerEvents(new SpawnProtection(), this);
        getServer().getPluginManager().registerEvents(new ArmorChangeEvent(), this);
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

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Database database = new Database();
                    database.initializeDatabase();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Bukkit.getLogger().info("Armored could not connect to database.");
                    return;
                }
            }
        }.runTaskTimer(this, 0L, 2400L);

    }

    // ban player
    // check respawn event if full netherite -> ban 30 minute
    // unbanned -> check if been banned -> clear all item, advancement, xp

    public void loadCommand() {
        getCommand("ban").setExecutor(new ban());
        getCommand("ban").setTabCompleter(new ban());

        getCommand("unban").setExecutor(new unban());
        getCommand("unban").setTabCompleter(new unban());

        getCommand("b").setExecutor(new build());
        getCommand("b").setTabCompleter(new build());

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info( "Armored plugin is disabled");
    }
}
