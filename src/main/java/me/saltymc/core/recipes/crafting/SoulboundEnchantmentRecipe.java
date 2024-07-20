package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.enchants.SoulboundEnchant;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class SoulboundEnchantmentRecipe extends CustomRecipe
{
    public SoulboundEnchantmentRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "SOULBOUND_ENCHANTMENT_RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new SoulboundEnchant(plugin).getItem();

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("TTT", "TBT", "TTT");
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('B', Material.BOOK);
        return recipe;
    }
}
