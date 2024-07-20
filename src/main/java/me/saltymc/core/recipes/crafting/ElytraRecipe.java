package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class ElytraRecipe extends CustomRecipe
{
    public ElytraRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "elytra-recipe";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new ItemStack(Material.ELYTRA);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("MMM", "MMM", "M M");
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);
        return recipe;
    }
}
