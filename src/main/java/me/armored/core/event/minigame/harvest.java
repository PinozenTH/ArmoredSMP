package me.armored.core.event.minigame;

import org.bukkit.entity.Ageable;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class harvest implements Listener {

    public void harvest(BlockBreakEvent event) {
        if (event.getBlock() instanceof Ageable ageable) {
            if (ageable.isAdult()) {
                ageable.setAge(0);
            }
        }
    }

}
