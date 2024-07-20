package me.saltymc.core.recipes;

import me.saltymc.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

public abstract class CustomRecipe
{
    protected final Main plugin;

    public CustomRecipe(Main plugin)
    {
        this.plugin = plugin;
    }

    abstract public String getKey();
    abstract public Recipe getRecipe();

    public static void register(CustomRecipe customRecipe)
    {
        Bukkit.addRecipe(customRecipe.getRecipe());
    }
}
