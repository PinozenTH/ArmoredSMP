package me.armored.core.event.overworld;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import static me.armored.core.Armored.bypass;
import static me.armored.core.utils.RandomizeUtil.random3Float;

public class Fishing implements Listener {

    @EventHandler
    public void onHook(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            if (event.getHook().getShooter() instanceof Player player) {
                if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.LUCK)) {
                    int enchantLevel = player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(Enchantment.LUCK);
                    if (random3Float(100) <= 0.01 * enchantLevel || bypass.contains(player)) {
                        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
                        ItemMeta meta = totem.getItemMeta();
                        meta.setCustomModelData(202);
                        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        meta.setDisplayName("§c§lArmor Reset");
                        totem.setItemMeta(meta);
                        Item item = event.getHook().getWorld().dropItemNaturally(event.getHook().getLocation(), totem);
                        Player shooter = (Player) event.getHook().getShooter();
                        item.setVelocity(new Vector(shooter.getLocation().getDirection().getX() * -1, 0.5, shooter.getLocation().getDirection().getZ() * -1));
                        event.getHook().setHookedEntity(item);
                        Bukkit.broadcast("§a" + shooter.getName() + " has fished an" + ChatColor.GOLD + " Armor Reset totem!", "");
                    }
                }
            }
        }
    }


}
