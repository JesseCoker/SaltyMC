package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.item.Curse;
import me.saltymc.core.items.item.SpawnerCage;
import me.saltymc.core.items.item.SpawnerCore;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class SpawnerRecipe extends CustomRecipe
{
    public SpawnerRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "SPAWNER_RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new ItemStack(Material.SPAWNER);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("GGG", "GSG", "GCG");
        recipe.setIngredient('G', new SpawnerCage(plugin).getItem(1));
        recipe.setIngredient('S', new SpawnerCore(plugin).getItem(1));
        recipe.setIngredient('C', new Curse(plugin).getItem(1));
        return recipe;
    }
}
