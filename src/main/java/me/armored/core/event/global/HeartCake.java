package me.armored.core.event.global;

import me.armored.core.Armored;
import me.armored.core.utils.CooldownManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Cake;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class HeartCake implements Listener {

    public static HashMap<String, Integer> cakeEaten = new HashMap<>();

    @EventHandler
    public void onEatCake(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null
                && event.getClickedBlock().getType().equals(Material.CAKE)
                && event.getPlayer().getFoodLevel() < 20
                && event.getClickedBlock().hasMetadata("health_cake")) {
            Player player = event.getPlayer();
            if (player.getMaxHealth() >= 60) {
                player.sendMessage("§cYou have reached the max health!");
                return;
            }
//            if (CooldownManager.isCooldown(player, "cake")) {
//                player.sendMessage("§cYou have eaten too many heart cakes! Wait for 1 day to eat again.");
//                return;
//            }
            Block block = event.getClickedBlock();
            Cake cake = (Cake) block.getBlockData();
            cake.setBites(cake.getBites() - 1);
            if (cake.getBites() >= 6) {
                return;
            }
            player.setMaxHealth(player.getMaxHealth() + 1);
//            player.sendMessage("§aYou have eaten a heart cake and gained 1 max health!");
//            if (cakeEaten.containsKey(player) && cakeEaten.get(player) >= 5) {
//                player.sendMessage("§cYou have eaten too many heart cakes! Wait for 1 day to eat again.");
//                CooldownManager.cakeCooldown.put(player.getUniqueId().toString(), CooldownManager.getCooldown(1));
//                return;
//            } else if (cakeEaten.containsKey(player)) {
//                cakeEaten.replace(player.getName(), cakeEaten.get(player) + 1);
//            } else {
//                cakeEaten.put(player.getName(), 1);
//            }
            block.setBlockData(cake);
        }
    }

    @EventHandler
    public void onPlaceCake(BlockPlaceEvent event) {
        if (event.getBlock().getType().equals(Material.CAKE) && event.getItemInHand().getItemMeta().hasLore()) {
            Block block = event.getBlock();
            block.setMetadata("health_cake", new FixedMetadataValue(Armored.plugin, true));
            Cake cake = (Cake) event.getBlock().getBlockData();
            cake.setBites(6);
            event.getBlock().setBlockData(cake);
        }
    }

}
