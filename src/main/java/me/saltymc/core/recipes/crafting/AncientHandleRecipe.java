package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.item.AncientHandle;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class AncientHandleRecipe extends CustomRecipe
{
    public AncientHandleRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "ANCIENT_HANDLE_RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new AncientHandle(plugin).getItem(1);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("OOO", "OGO", "OOO");
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        return recipe;
    }
}
