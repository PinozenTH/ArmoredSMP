package me.armored.core.event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorChangeEvent implements Listener {

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();
        ItemStack newArmor = event.getNewItem();
        Material newArmorType = newArmor.getType();

        if (!isAllowPutOff(newArmorType)) {
            setMeta(newArmor);
        }

    }

    private boolean isAllowPutOff(Material material) {

        if (material == Material.ELYTRA) {
            return true;
        } else {
            return false;
        }

    }

    private ItemMeta setMeta(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null) {
            return null;
        } else if (itemMeta.hasEnchant(Enchantment.BINDING_CURSE)) {
            return null;
        } else if (item.getType().equals(Material.ELYTRA)) {
            itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1,  true);
        } else {
            itemMeta.setUnbreakable(true);
            itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        }

        return itemMeta;
    }

}
