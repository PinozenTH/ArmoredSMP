package me.armored.core.Recipes;

import me.armored.core.Armored;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Recipes {
    public static void LoadRecipes() {
        resetArmor();
    }

    public static ItemStack resetArmor() {
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = totem.getItemMeta();
        meta.setCustomModelData(202);
        meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName("§c§lArmor Reset");
        totem.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(Armored.key("armor_reset"), totem);
        recipe.shape("AAA", "ABC", "CCC"); // Netherite Block , Totem of Undying, Nether Star
        recipe.setIngredient('A', Material.NETHERITE_BLOCK);
        recipe.setIngredient('B', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('C', Material.NETHER_STAR);
        Bukkit.addRecipe(recipe);
        return totem;
    }

    public static ItemStack healthCake() {
        ItemStack cake = new ItemStack(Material.CAKE);
        cake.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemMeta meta = cake.getItemMeta();
        meta.setCustomModelData(203);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName("§c§lHealth Boost Cake");
        meta.setLore(List.of("§7Eat this cake to gain 1 extra heart!"));
        cake.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(Armored.key("increase_health"), cake);
        recipe.shape("CCC", "GEG", "HHH"); // Cake, Gold Block, Emerald Ore, Honey Block
        recipe.setIngredient('C', Material.CAKE);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('E', Material.EMERALD_BLOCK);
        recipe.setIngredient('H', Material.HONEY_BLOCK);
        Bukkit.addRecipe(recipe);
        return cake;
    } // Cake that will give player 1 extra heart per eaten piece


    public static ItemStack featherOfRevernant() {
        return null;
    } // Feather that will keep player inventory when dead limit (5 per day)

    public static ItemStack voidTotem() {
        return null;
    } // Totem that will not change player armor when dead and player inventory will not drop (limit 1 per day)

    public static ItemStack armoredStar() {
        return null;
    } // Totem for play who participated Armored SMP SS1
}
