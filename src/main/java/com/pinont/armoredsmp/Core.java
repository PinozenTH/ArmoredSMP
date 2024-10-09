package com.pinont.armoredsmp;

import com.pinont.armoredsmp.events.ConnectionEvents;
import com.pinont.piXLib.PiXPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        PiXPlugin.listeners.addAll(List.of(
                new ConnectionEvents()
        ));

        PiXPlugin.setPlugin(this);
    }
}
