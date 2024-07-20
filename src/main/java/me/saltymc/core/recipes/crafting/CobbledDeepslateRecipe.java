package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CobbledDeepslateRecipe extends CustomRecipe
{
    public CobbledDeepslateRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "COBBLED-DEEPSLATE-RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new ItemStack(Material.COBBLED_DEEPSLATE, 4);

        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, result);
        recipe.addIngredient(4, Material.DEEPSLATE);
        return recipe;
    }
}
