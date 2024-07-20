package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.weapon.EmeraldSword;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class EmeraldSwordRecipe extends CustomRecipe
{
    public EmeraldSwordRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "emerald-sword-recipe";
    }

    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new EmeraldSword(plugin).getItem(1);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape(" E ", " E ", " S ");
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('S', Material.STICK);
        return recipe;
    }
}
