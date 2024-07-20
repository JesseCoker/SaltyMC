package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.food.GoldenHead;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class GoldenHeadRecipe extends CustomRecipe
{
    public GoldenHeadRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "golden-head-recipe";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new GoldenHead(plugin).getItem(1);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("GGG", "GHG", "GGG");
        recipe.setIngredient('H', Material.PLAYER_HEAD);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        return recipe;
    }
}
