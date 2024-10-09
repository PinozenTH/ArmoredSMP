package me.armored.core;

import me.armored.core.command.*;
import me.armored.core.event.global.*;
import me.armored.core.event.nether.Ghast;
import me.armored.core.event.nether.Piglin;
import me.armored.core.event.nether.RandomGenBlock;
import me.armored.core.event.overworld.Fishing;
import me.armored.core.event.overworld.FortuneSpawn;
import me.armored.core.event.overworld.HeartCow;
import me.armored.core.utils.Cuboid;
import me.armored.core.utils.Database;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.ArrayList;

import static me.armored.core.utils.CooldownManager.checkCakeCooldown;
import static me.armored.core.utils.Database.*;

public final class Armored extends JavaPlugin {

    public static World world = Bukkit.getWorld("world");
    public static World harvestWorld = Bukkit.getWorld("harvestFest");
    public static ArrayList<Player> build = new ArrayList<>();
    public static ArrayList<Player> bypass = new ArrayList<>();
    public static ArrayList<Player> vanish = new ArrayList<>();
    public static boolean enable = true;
    public static Cuboid harvestFest = new Cuboid(harvestWorld, 50, 65, 50, -51, 88, -51);
    public static Cuboid conduit = new Cuboid(world, 197, -4, 187, 193, -2, 191);
    public static Cuboid bamboo = new Cuboid(world, 188, 62, 228, 183, 63, 232);
    public static Plugin plugin;

    public Armored() {
        plugin = this;
    }

    public static void reload() {

//        new Recipes().LoadRecipes();
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Armored plugin is starting");
        loadDatabase();
        loadEvents();
        loadCommand();
        checkPluginVersion();
        reload();
        checkDependencies();
        loadCooldownToHashMap();
        clearCooldown();
        Bukkit.getLogger().info("Armored plugin has been started");
    }

    @Override
    public void onDisable() {
        uploadHashMapToCooldown();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getLogger().info("Armored plugin is disabled");
    }

    public static NamespacedKey key(String key) {
        return new NamespacedKey(plugin, key);
    }

    public void loadEvents() {
        getServer().getPluginManager().registerEvents(new LoggedEvent(), this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(), this);
        getServer().getPluginManager().registerEvents(new CraftEvents(), this);
        getServer().getPluginManager().registerEvents(new SpawnProtection(), this);
        getServer().getPluginManager().registerEvents(new ArmorChangeEvent(), this);
        getServer().getPluginManager().registerEvents(new CommandByConsole(), this);
        getServer().getPluginManager().registerEvents(new FortuneSpawn(), this);
        getServer().getPluginManager().registerEvents(new ArmorRemover(), this);
        getServer().getPluginManager().registerEvents(new AdminParticle(), this);
        getServer().getPluginManager().registerEvents(new Ghast(), this);
        getServer().getPluginManager().registerEvents(new Piglin(), this);
        getServer().getPluginManager().registerEvents(new RandomGenBlock(), this);
        getServer().getPluginManager().registerEvents(new Fishing(), this);
        getServer().getPluginManager().registerEvents(new HeartCake(), this);
        getServer().getPluginManager().registerEvents(new HeartCow(), this);
        getServer().getPluginManager().registerEvents(new CustomItemsAndArtifact(), this);
    }


    private void checkDependencies() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();
            api.runUpdateTask();
        }
    }

    private void checkPluginVersion() {
        Database database = new Database();
        try {
            database.checkPluginVersion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
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
                }
            }
        }.runTaskTimer(this, 0L, 2400L);
    }

    public void loadCommand() {
        getCommand("ban").setExecutor(new ban());
        getCommand("ban").setTabCompleter(new ban());

        getCommand("unban").setExecutor(new unban());
        getCommand("unban").setTabCompleter(new unban());

        getCommand("b").setExecutor(new build());
        getCommand("b").setTabCompleter(new build());

        getCommand("bypass").setExecutor(new bypass());
        getCommand("bypass").setTabCompleter(new bypass());

        getCommand("start").setExecutor(new start());
        getCommand("nick").setExecutor(new nick());

        getCommand("color").setExecutor(new color());
        getCommand("color").setTabCompleter(new color());
        getCommand("resetColor").setExecutor(new resetColor());

        getCommand("minigame").setExecutor(new minigame());

        getCommand("fly").setExecutor(new fly());

        getCommand("gmc").setExecutor(new gamemode());
        getCommand("gma").setExecutor(new gamemode());
        getCommand("gms").setExecutor(new gamemode());
        getCommand("gmsp").setExecutor(new gamemode());

        getCommand("heartreset").setExecutor(new resetHeart());
        getCommand("heartreset").setTabCompleter(new resetHeart());

        getCommand("challenge").setExecutor(new challenge());
        getCommand("challenge").setTabCompleter(new challenge());

//        getCommand("immortal").setExecutor(new immortal());
//        getCommand("immortal").setTabCompleter(new immortal());

//        getCommand("harvest").setExecutor(new fest());
    }

    public void discord() {
        new BukkitRunnable() {
            @Override
            public void run() { // delay 60 Minutes
                for (Player player : Bukkit.getOnlinePlayers()) {
                    TextComponent message = new TextComponent("Join our discord server! to get news and updates");
                    message.setFont("minecraft:uniform");

                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Click to join")));
                    message.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, "https://discord.gg/wQ748MmdWn"));
                    player.spigot().sendMessage(message);
                }
            }
        }.runTaskTimer(this, 0L, 72000L);
    }

    public void clearCooldown() {
        new BukkitRunnable() {
            @Override
            public void run() {
                checkCakeCooldown();
            }
        }.runTaskTimer(this, 0L, 2400L); // 30 minutes
    }
}
