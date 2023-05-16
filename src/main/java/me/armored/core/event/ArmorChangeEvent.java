package me.armored.core.event;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class ArmorChangeEvent implements Listener {

    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent event) {

        Player player = event.getPlayer();

        ItemStack newArmor = event.getNewItem();
        ItemStack oldArmor = event.getOldItem();

        if (!newArmor.getType().isAir()) {
            if (oldArmor.equals(newArmor) || event.getNewItem().getItemMeta().getEnchants().containsKey(Enchantment.BINDING_CURSE)) return;
            else {
                ItemStack item = newArmor;
                item.setItemMeta((ItemMeta) item(item));
                player.getInventory().setItem(newArmor.getType().getEquipmentSlot(), newArmor);
                if (player.getFoodLevel() >= 4 && player.getGameMode() != player.getGameMode().CREATIVE) {
                    player.setFoodLevel(player.getFoodLevel() - 4);
                } else {
                    player.damage(3.5);
                }
            }
            
        }
    }

    @EventHandler
    public void onPlayerDieDueArmorChange(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CUSTOM && player.getLastDamage() == 3.5) {
            event.setDeathMessage(player.getName() + " died while changing armor.");
        }

    }

    private boolean isAllowPutOff(Material material) {

        if (material == Material.ELYTRA) {
            return true;
        } else {
            return false;
        }
    }

    private ItemMeta item(ItemStack item) {

        if (!isAllowPutOff(item.getType())) {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setUnbreakable(true);
            itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
            itemMeta.removeEnchant(Enchantment.VANISHING_CURSE);
            if (itemMeta.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
                itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "Armor", -8, AttributeModifier.Operation.ADD_NUMBER));
                itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "Toughness", -3, AttributeModifier.Operation.ADD_NUMBER));
                itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "KnockbackResistance", -1, AttributeModifier.Operation.ADD_NUMBER));
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            return itemMeta;
        } else if (isAllowPutOff(item.getType())) {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
            if (itemMeta.getItemFlags().contains(ItemFlag.HIDE_ATTRIBUTES)) {
                itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "Armor", -8, AttributeModifier.Operation.ADD_NUMBER));
                itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "Toughness", -3, AttributeModifier.Operation.ADD_NUMBER));
                itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "KnockbackResistance", -1, AttributeModifier.Operation.ADD_NUMBER));
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            }
            return itemMeta;
        } else {
            return null;
        }
    }

}
