package com.pinont.armoredsmp;

import com.pinont.armoredsmp.events.ConnectionEvents;
import com.pinont.armoredsmp.events.HealthEvents;
import com.pinont.armoredsmp.scoreboard.Health;
import com.pinont.armoredsmp.task.HealthTask;
import com.pinont.piXLib.PiXPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        PiXPlugin.listeners.addAll(List.of(
                new ConnectionEvents(),
                new HealthEvents()
        ));

        PiXPlugin.setPlugin(this);

        Health.createHealth();
        HealthTask.start();
    }

    @Override
    public void onDisable() {
        PiXPlugin.unregister(this);
    }

    public static Core getInstance() {
        return (Core) PiXPlugin.getPlugin();
    }
}
