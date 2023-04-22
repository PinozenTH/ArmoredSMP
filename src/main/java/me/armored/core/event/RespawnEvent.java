package me.armored.core.event;

import me.armored.core.utils.Database;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Material.*;

public class RespawnEvent implements Listener {
    // player death -> run task -> put armor

    private final ItemStack air = new ItemStack(AIR);

    private final Map<String, Integer> playerItem = new HashMap<>();

    public RespawnEvent() {
        this.playerItem.put("helmet", 0);
        this.playerItem.put("chestplate", 0);
        this.playerItem.put("leggings", 0);
        this.playerItem.put("boots", 0);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) throws SQLException {

        Player player = event.getPlayer();

        Map<String, Integer> resultPlayerItem = checkItemLevel(player.getInventory());
        System.out.println(resultPlayerItem.toString());
        if (
                resultPlayerItem.get("helmet") == 6 &&
                        resultPlayerItem.get("chestplate") == 6 &&
                        resultPlayerItem.get("leggings") == 6 &&
                        resultPlayerItem.get("boots") == 6
        ) {

            player.getInventory().clear(); // reset inventory
            player.setLevel(0); // reset level
            resultPlayerItem = this.playerItem;
        } else {
            setItemPlayer(player.getInventory(), resultPlayerItem);
        }
        player.sendMessage(Component.text("Respawn" + resultPlayerItem));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) throws SQLException {
        Player player = event.getPlayer();
        Map<String, Integer> resultPlayerItem = checkItemLevel(player.getInventory());
        player.sendMessage(Component.text("Dead = " + resultPlayerItem.toString()));

        if (
                resultPlayerItem.get("helmet") == 6 &&
                        resultPlayerItem.get("chestplate") == 6 &&
                        resultPlayerItem.get("leggings") == 6 &&
                        resultPlayerItem.get("boots") == 6
        ) {
            if (player.getBedSpawnLocation() != null) {

                Location bedSpawnLocation = player.getBedSpawnLocation();
                World bedSpawnWorld = player.getWorld();
                Entity tnt = bedSpawnWorld.spawn(bedSpawnLocation, TNTPrimed.class);
                ((TNTPrimed) tnt).setFuseTicks(200);

            }
//            player.sendMessage("Ban");
            Database.Ban(player, ChatColor.YELLOW + "Out of life", 12);
            player.kickPlayer("You cannot respawn right now!");
        } else if ((resultPlayerItem.get("helmet") >= 1 ||
                resultPlayerItem.get("chestplate") >= 1 ||
                resultPlayerItem.get("leggings") >= 1 ||
                resultPlayerItem.get("boots") >= 1) && (resultPlayerItem.get("helmet") <= 6 ||
                resultPlayerItem.get("chestplate") <= 6 ||
                resultPlayerItem.get("leggings") <= 6 ||
                resultPlayerItem.get("boots") <= 6)) {

            PlayerInventory playerInventory = player.getInventory();

            if (!Optional.ofNullable(playerInventory.getHelmet()).isEmpty()) {
                setItemHelmet(playerInventory, true, setItemByLevelHelmet(resultPlayerItem.get("helmet")));
            }

            if (!Optional.ofNullable(playerInventory.getChestplate()).isEmpty()) {
                setItemChestplate(playerInventory, true, setItemLevelChestplate(resultPlayerItem.get("chestplate")));
            }

            if (!Optional.ofNullable(playerInventory.getLeggings()).isEmpty()) {
                setItemLeggings(playerInventory, true, setItemLevelLeggings(resultPlayerItem.get("leggings")));
            }

            if (!Optional.ofNullable(playerInventory.getBoots()).isEmpty()) {
                setItemBoots(playerInventory, true, setItemLevelBoots(resultPlayerItem.get("boots")));
            }
        }
    }

    private Map<String, Integer> checkItemLevel(PlayerInventory playerInventory) {
        Map<String, Integer> playerItem = this.playerItem;

        if (!Optional.ofNullable(playerInventory.getHelmet()).isEmpty()) {
            playerItem.put("helmet", getLevelItem(playerInventory.getHelmet()));
        }

        if (!Optional.ofNullable(playerInventory.getChestplate()).isEmpty()) {
            playerItem.put("chestplate", getLevelItem(playerInventory.getChestplate()));
        }

        if (!Optional.ofNullable(playerInventory.getLeggings()).isEmpty()) {

            playerItem.put("leggings", getLevelItem(playerInventory.getLeggings()));
        }

        if (!Optional.ofNullable(playerInventory.getBoots()).isEmpty()) {
            playerItem.put("boots", getLevelItem(playerInventory.getBoots()));
        }

        return playerItem;
    }

    private void setItemPlayer(PlayerInventory playerInventory, Map<String, Integer> playerItem) {
        Random random = new Random();
        int maxLevelItem = playerItem.values().stream().max(Integer::compare).get();
        int finalMaxLevelItem = maxLevelItem;
        int maxRandom = (int) playerItem.values().stream().filter(it -> it != finalMaxLevelItem).count();//[1,1,1,1] = 4 : [0,1,1,1] = 3
        List<String> playerItemList = playerItem.keySet().stream().collect(Collectors.toList());
        List<String> result = new ArrayList<>();

        for (String name : playerItem.keySet()) {// [0,0,0,0] mx = 0
            if (playerItem.get(name) < maxLevelItem) {
                result.add(name);
            }
        }

        if (maxRandom == 0) {
            maxRandom = 4;
        }
        if (result.isEmpty()) {
            result = playerItemList;
        }

        int numPlayerItem = random.nextInt(maxRandom);

        switch (result.get(numPlayerItem)) {
            case "helmet" -> {
                setItemHelmet(playerInventory, false, null);
            }
            case "chestplate" -> {
                setItemChestplate(playerInventory, false, null);
            }
            case "leggings" -> {
                setItemLeggings(playerInventory, false, null);
            }
            case "boots" -> {
                setItemBoots(playerInventory, false, null);
            }
        }
    }


    private void setItemHelmet(PlayerInventory playerInventory, boolean isKeep, Material itemSet) {
        ItemStack helmet = playerInventory.getHelmet();

        if (isKeep) {
            helmet.setType(itemSet);
            helmet.setItemMeta(setItemMetaHelmet(helmet));
        } else {
            if (Optional.ofNullable(helmet).isEmpty()) {
                helmet = new ItemStack(setItemLevelHelmet(air));
                helmet.setItemMeta(setItemMetaHelmet(helmet));
                playerInventory.setHelmet(helmet);
            } else {
                helmet.setType(setItemLevelHelmet(Optional.of(helmet).get()));
                helmet.setItemMeta(setItemMetaHelmet(helmet));
            }
        }
    }

    private void setItemChestplate(PlayerInventory playerInventory, boolean isKeep, Material itemSet) {
        ItemStack chestplate = playerInventory.getChestplate();

        if (isKeep) {
            chestplate.setType(itemSet);
            chestplate.setItemMeta(setItemMetaChestplate(chestplate));
        } else {
            if (Optional.ofNullable(chestplate).isEmpty()) {
                chestplate = new ItemStack(setItemLevelChestplate(air));
                chestplate.setItemMeta(setItemMetaChestplate(chestplate));
                playerInventory.setChestplate(chestplate);
            } else {
                chestplate.setType(setItemLevelChestplate(Optional.of(chestplate).get()));
                chestplate.setItemMeta(setItemMetaChestplate(chestplate));
            }

        }
    }

    private void setItemLeggings(PlayerInventory playerInventory, boolean isKeep, Material itemSet) {
        ItemStack leggings = playerInventory.getLeggings();

        if (isKeep) {
            leggings.setType(itemSet);
            leggings.setItemMeta(setItemMetaLeggings(leggings));
        } else {
            if (Optional.ofNullable(leggings).isEmpty()) {
                leggings = new ItemStack(setItemLevelLeggings(air));
                leggings.setItemMeta(setItemMetaLeggings(leggings));
                playerInventory.setLeggings(leggings);
            } else {
                leggings.setType(setItemLevelLeggings(Optional.of(leggings).get()));
                leggings.setItemMeta(setItemMetaLeggings(leggings));
            }
        }

    }

    private void setItemBoots(PlayerInventory playerInventory, boolean isKeep, Material itemSet) {
        ItemStack boots = playerInventory.getBoots();

        if (isKeep) {
            boots.setType(itemSet);
            boots.setItemMeta(setItemMetaBoots(boots));
        } else {
            if (Optional.ofNullable(boots).isEmpty()) {
                boots = new ItemStack(setItemLevelBoots(air));
                boots.setItemMeta(setItemMetaBoots(boots));
                playerInventory.setBoots(boots);
            } else {
                boots.setType(setItemLevelBoots(Optional.of(boots).get()));
                boots.setItemMeta(setItemMetaBoots(boots));
            }
        }
    }

    private ItemMeta setItemMetaHelmet(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.setUnbreakable(true);
        return itemMeta;
    }

    private ItemMeta setItemMetaChestplate(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.setUnbreakable(true);
        return itemMeta;
    }

    private ItemMeta setItemMetaLeggings(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.setUnbreakable(true);
        return itemMeta;
    }

    private ItemMeta setItemMetaBoots(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        itemMeta.setUnbreakable(true);
        return itemMeta;
    }

    private Material setItemLevelHelmet(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_HELMET -> CHAINMAIL_HELMET;
            case CHAINMAIL_HELMET -> IRON_HELMET;
            case IRON_HELMET -> GOLDEN_HELMET;
            case GOLDEN_HELMET -> DIAMOND_HELMET;
            case DIAMOND_HELMET, NETHERITE_HELMET -> NETHERITE_HELMET;
            default -> LEATHER_HELMET;
        };
    }

    private Material setItemLevelChestplate(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_CHESTPLATE -> CHAINMAIL_CHESTPLATE;
            case CHAINMAIL_CHESTPLATE -> IRON_CHESTPLATE;
            case IRON_CHESTPLATE -> GOLDEN_CHESTPLATE;
            case GOLDEN_CHESTPLATE -> DIAMOND_CHESTPLATE;
            case DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE -> NETHERITE_CHESTPLATE;
            default -> LEATHER_CHESTPLATE;
        };
    }

    private Material setItemLevelLeggings(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_LEGGINGS -> CHAINMAIL_LEGGINGS;
            case CHAINMAIL_LEGGINGS -> IRON_LEGGINGS;
            case IRON_LEGGINGS -> GOLDEN_LEGGINGS;
            case GOLDEN_LEGGINGS -> DIAMOND_LEGGINGS;
            case DIAMOND_LEGGINGS, NETHERITE_LEGGINGS -> NETHERITE_LEGGINGS;
            default -> LEATHER_LEGGINGS;
        };
    }

    private Material setItemLevelBoots(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_BOOTS -> CHAINMAIL_BOOTS;
            case CHAINMAIL_BOOTS -> IRON_BOOTS;
            case IRON_BOOTS -> GOLDEN_BOOTS;
            case GOLDEN_BOOTS -> DIAMOND_BOOTS;
            case DIAMOND_BOOTS, NETHERITE_BOOTS -> NETHERITE_BOOTS;
            default -> LEATHER_BOOTS;
        };
    }

    private Integer getLevelItem(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS -> 1;
            case CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_LEGGINGS, CHAINMAIL_BOOTS -> 2;
            case IRON_HELMET, IRON_CHESTPLATE, IRON_LEGGINGS, IRON_BOOTS -> 3;
            case GOLDEN_HELMET, GOLDEN_CHESTPLATE, GOLDEN_LEGGINGS, GOLDEN_BOOTS -> 4;
            case DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS -> 5;
            case NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS -> 6;
            default -> 0;
        };
    }

    private Material setItemByLevelHelmet(int level) {
        return switch (level) {
            case 1 -> LEATHER_HELMET;
            case 2 -> CHAINMAIL_HELMET;
            case 3 -> IRON_HELMET;
            case 4 -> GOLDEN_HELMET;
            case 5 -> DIAMOND_HELMET;
            case 6 -> NETHERITE_HELMET;
            default -> AIR;
        };
    }

}
