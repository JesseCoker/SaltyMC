package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.item.AncientBlade;
import me.saltymc.core.items.item.AncientCrossguard;
import me.saltymc.core.items.item.AncientHandle;
import me.saltymc.core.items.weapon.AncientSpell;
import me.saltymc.core.items.weapon.AncientSword;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class AncientSwordRecipe extends CustomRecipe
{
    public AncientSwordRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "ANCIENT_SWORD_RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new AncientSword(plugin).getItem(1);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("  B", " CS", "H  ");
        recipe.setIngredient('H', new AncientHandle(plugin).getItem(1));
        recipe.setIngredient('C', new AncientCrossguard(plugin).getItem(1));
        recipe.setIngredient('B', new AncientBlade(plugin).getItem(1));
        recipe.setIngredient('S', new AncientSpell(plugin).getItem(1));
        return recipe;
    }
}
