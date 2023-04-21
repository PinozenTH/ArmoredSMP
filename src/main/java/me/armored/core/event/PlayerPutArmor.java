package me.armored.core.event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPutArmor implements Listener {

    @EventHandler
    public void onPlayerPutArmorOn(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("Armor Change");
    }

}
