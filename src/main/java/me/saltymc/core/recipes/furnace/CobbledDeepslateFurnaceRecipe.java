package me.saltymc.core.recipes.furnace;

import me.saltymc.core.Main;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

public class CobbledDeepslateFurnaceRecipe extends CustomRecipe
{
    public CobbledDeepslateFurnaceRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "COBBLED-DEEPSLATE-FURNACE-RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack ingredient = new ItemStack(Material.DEEPSLATE);
        ItemStack result = new ItemStack(Material.COBBLED_DEEPSLATE);

        return new FurnaceRecipe(recipeKey, result, new RecipeChoice.ExactChoice(ingredient), 0, 200);
    }
}