package me.armored.core.event.global;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

import static org.bukkit.Material.*;

public class ArmorRemover implements Listener {
    @EventHandler
    public void onArmorChange(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!checkPlayerArmor(player)) return;
        if (event.getItem() == null) return;
        if (!event.getItem().getItemMeta().hasCustomModelData()) return;
        if ((event.getItem().getItemMeta().getCustomModelData() == 202) && event.getItem().getType().equals(Material.TOTEM_OF_UNDYING)) { // reset all armor
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            event.getItem().setAmount(0);
            new RespawnEvent().reset();
        } else if (event.getItem().getType().equals(Material.JACK_O_LANTERN) && event.getItem().getItemMeta().hasCustomModelData() && isTargetArmor(player)) { // remove armor randomly (iron -> diamond)
            removeArmor(player);
            event.getItem().setAmount(0);
            new RespawnEvent().reset();
        }
    }

    private void removeArmor(Player player) {
        Random random = new Random();
        int i = random.nextInt(4);
        switch (i) {
            case 0:
                player.getInventory().setHelmet(getArmor(player, player.getInventory().getHelmet()));
                break;
            case 1:
                player.getInventory().setChestplate(getArmor(player, player.getInventory().getChestplate()));
                break;
            case 2:
                player.getInventory().setLeggings(getArmor(player, player.getInventory().getLeggings()));
                break;
            case 3:
                player.getInventory().setBoots(getArmor(player, player.getInventory().getBoots()));
                break;
        }
    }

    private Boolean isTargetArmor(Player player) {
        // check if armor of players is iron or gold or diamond (full check [helmet, chestplate, leggings, boots])
        Material helmet = player.getInventory().getHelmet() != null ? player.getInventory().getHelmet().getType() : null;
        Material chestplate = player.getInventory().getChestplate() != null ? player.getInventory().getChestplate().getType() : null;
        Material leggings = player.getInventory().getLeggings() != null ? player.getInventory().getLeggings().getType() : null;
        Material boots = player.getInventory().getBoots() != null ? player.getInventory().getBoots().getType() : null;
        return isTargetMaterial(helmet) || isTargetMaterial(chestplate) || isTargetMaterial(leggings) || isTargetMaterial(boots);
    }

    private Boolean isTargetMaterial(Material material) {
        return material != null && (material.name().toLowerCase().contains("iron") || material.name().toLowerCase().contains("gold") || material.name().toLowerCase().contains("diamond"));
    }

    private ItemStack getArmor(Player player, ItemStack armor) {
        if (!isTargetArmor(player)) removeArmor(player);
        return switch (Objects.requireNonNull(armor).getType()) {
            case DIAMOND_HELMET -> new ItemStack(Material.GOLDEN_HELMET);
            case DIAMOND_CHESTPLATE -> new ItemStack(Material.GOLDEN_CHESTPLATE);
            case DIAMOND_LEGGINGS -> new ItemStack(Material.GOLDEN_LEGGINGS);
            case DIAMOND_BOOTS -> new ItemStack(Material.GOLDEN_BOOTS);

            case GOLDEN_HELMET -> new ItemStack(IRON_HELMET);
            case GOLDEN_CHESTPLATE -> new ItemStack(Material.IRON_CHESTPLATE);
            case GOLDEN_LEGGINGS -> new ItemStack(IRON_LEGGINGS);
            case GOLDEN_BOOTS -> new ItemStack(IRON_BOOTS);

            case IRON_HELMET -> new ItemStack(Material.CHAINMAIL_HELMET);
            case IRON_CHESTPLATE -> new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            case IRON_LEGGINGS -> new ItemStack(Material.CHAINMAIL_LEGGINGS);
            case IRON_BOOTS -> new ItemStack(Material.CHAINMAIL_BOOTS);
            default -> armor;
        };
    }

//    private Boolean armorTrim(Material item) {
//        ArrayList<Material> material = new ArrayList<>();
//        material.add(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE);
//        material.add(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
//        return material.contains(item);
//    }

    private boolean checkPlayerArmor(Player player) {
        return player.getInventory().getHelmet() != null || player.getInventory().getChestplate() != null || player.getInventory().getLeggings() != null || player.getInventory().getBoots() != null;
    }
}
