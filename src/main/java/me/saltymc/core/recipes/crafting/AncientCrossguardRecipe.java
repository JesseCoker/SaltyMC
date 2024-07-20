package me.saltymc.core.recipes.crafting;

import me.saltymc.core.Main;
import me.saltymc.core.items.item.AncientCrossguard;
import me.saltymc.core.items.item.NetherCrossguardPiece;
import me.saltymc.core.items.item.SkulkCrossguardPiece;
import me.saltymc.core.recipes.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class AncientCrossguardRecipe extends CustomRecipe
{
    public AncientCrossguardRecipe(Main plugin)
    {
        super(plugin);
    }

    @Override
    public String getKey()
    {
        return "ANCIENT_CROSSGUARD_RECIPE";
    }

    @Override
    public Recipe getRecipe()
    {
        NamespacedKey recipeKey = new NamespacedKey(plugin, getKey());
        ItemStack result = new AncientCrossguard(plugin).getItem(1);

        ItemStack netherCrossguardPiece = new NetherCrossguardPiece(plugin).getItem(1);
        ItemStack skulkCrossguardPiece = new SkulkCrossguardPiece(plugin).getItem(1);

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, result);
        recipe.shape("   ", "NGS", "   ");
        recipe.setIngredient('N', netherCrossguardPiece);
        recipe.setIngredient('G', Material.GLOW_INK_SAC);
        recipe.setIngredient('S', skulkCrossguardPiece);
        return recipe;
    }
}
