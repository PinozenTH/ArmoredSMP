package me.armored.core.event.overworld;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static me.armored.core.Armored.bypass;
import static me.armored.core.utils.RandomizeUtil.*;

public class FortuneSpawn implements Listener {

    @EventHandler
    public void mine(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode().equals(org.bukkit.GameMode.CREATIVE)) return;
        Block block = event.getBlock();
        if (isNetherStuff(block)) {
            if (randomFloat(100) < 0.02) {
                summonQuartz(block);
                event.setExpToDrop(randomInt(1, 3));
            } else if (bypass.contains(event.getPlayer())) {
                summonQuartz(block);
                event.setExpToDrop(randomInt(1, 3));
            }
        } else if (isStoneStuff(block)) {
            // summon iron
            if (randomFloat(100) < 0.5) {
                summonIron(block);
            } else if (bypass.contains(event.getPlayer())) {
                summonIron(block);
            }
        } else if (isLogsStuff(block)) {
            if (!event.getPlayer().getInventory().getItemInMainHand().getType().isAir()) {
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int enchantLevel = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
                    Material item = block.getType();
                    if (enchantLevel == 1) {
                        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(item, randomInt(1, 2)));
                    } else if (enchantLevel == 2) {
                        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(item, randomInt(2, 3)));
                    } else if (enchantLevel == 3) {
                        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(item, randomInt(2, 4)));
                    }
                    block.setType(Material.AIR);
                }
            }
        }
    }

    private void dropLogs(Block block) {
        int amount = randomInt(1, 3);
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType(), amount));
        block.setType(Material.AIR);
    }

    private boolean isLogsStuff(Block block) {
        return block.getType().equals(Material.OAK_LOG) || block.getType().equals(Material.SPRUCE_LOG) || block.getType().equals(Material.BIRCH_LOG) || block.getType().equals(Material.JUNGLE_LOG) || block.getType().equals(Material.ACACIA_LOG) || block.getType().equals(Material.DARK_OAK_LOG) || block.getType().equals(Material.CRIMSON_STEM) || block.getType().equals(Material.WARPED_STEM);
    }

    private boolean isStoneStuff(Block block) {
        return block.getType().equals(Material.STONE) || block.getType().equals(Material.DEEPSLATE) || block.getType().equals(Material.GRAVEL);
    }

    private void summonIron(Block block) {
        int amount = randomInt(1, 3);
        block.getWorld().dropItemNaturally(block.getLocation(), ore(amount));
        block.setType(Material.AIR);
    }

    private ItemStack ore(int amount) {
        ArrayList<ItemStack> ore = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            ore.add(new ItemStack(Material.COAL, amount));
        }
        ore.add(new ItemStack(Material.RAW_IRON, amount));
        ore.add(new ItemStack(Material.RAW_GOLD, amount));
        return (ItemStack) randomObject(ore);
    }

    private void summonQuartz(Block block) {
        int amount = randomInt(1, 3); // 1-3 (inclusive
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.QUARTZ, amount));
        block.setType(Material.AIR);
    }

    private boolean isNetherStuff(Block block) {
        return block.getType().equals(Material.NETHERRACK) || block.getType().equals(Material.NETHER_GOLD_ORE) || block.getType().equals(Material.MAGMA_BLOCK) || block.getType().equals(Material.OBSIDIAN);
    }
}
