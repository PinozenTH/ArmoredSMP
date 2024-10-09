package me.armored.core.event.overworld;

import io.papermc.paper.world.MoonPhase;
import me.armored.core.utils.CooldownManager;
import me.armored.core.utils.RandomizeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

import static me.armored.core.Armored.bypass;

public class HeartCow implements Listener {

    @EventHandler
    public void onMilking(PlayerInteractEntityEvent event) {
        World world = Bukkit.getWorld(event.getPlayer().getWorld().getName());
        assert world != null;
        if (!world.hasCeiling() && Objects.requireNonNull(world).getMoonPhase().equals(MoonPhase.FULL_MOON) || bypass.contains(event.getPlayer())) {
            Player player = event.getPlayer();
            if (!event.getRightClicked().getType().equals(EntityType.COW)) return;
            if (!player.getInventory().getItemInMainHand().getType().equals(Material.BUCKET)) return;
            Cow cow = (Cow) event.getRightClicked();
            if (cow.isDead() || !cow.isAdult()) return;
            float random = RandomizeUtil.random3Float(100);
            if (random <= 0.1f && !CooldownManager.isCooldown(player, "heart_cow")) {
                ItemStack item = new ItemStack(Material.MILK_BUCKET);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName("§eSpecial §lMilk Bucket");
                itemMeta.setCustomModelData(204);
                itemMeta.setLore(List.of("§7Drink to get 1 extra heart"));
                item.setItemMeta(itemMeta);
                player.getInventory().setItemInMainHand(item);
                // cooldown 1 day
                CooldownManager.milkCooldown.put(player.getUniqueId().toString(), CooldownManager.getCooldown(1));
            } else if ((random > 50f && random < 70f)) {
                player.sendMessage("§cThe cow is not in the mood to be milked.");
                event.setCancelled(true);
            }
        }
    }

}
