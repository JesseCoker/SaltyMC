package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.item.AncientBlade;
import me.saltymc.core.items.item.PureSoul;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class AncientBladeRecipe extends CustomRecipe
{
    public AncientBladeRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "ANCIENT_BLADE_RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new AncientBlade(plugin).getItem(1);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("WEN", "ENE", "NEP");
        recipe.setIngredient('W', Material.WARDEN_SPAWN_EGG);
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('P', new PureSoul(plugin).getItem(1));
        return recipe;
    }
}
