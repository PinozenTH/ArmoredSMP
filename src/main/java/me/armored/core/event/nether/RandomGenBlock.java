package me.armored.core.event.nether;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static me.armored.core.Armored.bypass;
import static me.armored.core.utils.RandomizeUtil.randomFloat;
import static me.armored.core.utils.RandomizeUtil.randomInt;

public class RandomGenBlock implements Listener {
    Player player;

    @EventHandler
    public void randomSetBlock(BlockBreakEvent event) {
        Block block = event.getBlock();
        player = event.getPlayer();
        if (block.getType().equals(Material.NETHERRACK)) {
            randomSetNetheriteBlock(block);
        }
    }

    private void randomSetNetheriteBlock(Block block) {
        if (randomFloat(100)/0.01 == 0 || bypass.contains(player)) {
            int x = randomInt(10);
            int y = randomInt(10);
            int z = randomInt(10);
            Location location = block.getLocation();
            if (location.add(x, y, z).getBlock().equals(Material.NETHERRACK)) {
                location.add(x, y, z).getBlock().setType(Material.ANCIENT_DEBRIS);
                player.sendMessage("Ancient Debris spawned at " + location.add(x, y, z).getBlock().getLocation() + "!");
                player.playSound(player.getLocation(), "block.note_block.harp", 1, 1);
            }
        }
    }

}
