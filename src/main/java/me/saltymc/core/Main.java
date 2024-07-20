package me.saltymc.core;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Register.registerEvents(this);
        Register.registerCommands(this);
        Register.registerItems(this);
        Register.registerRecipes(this);
        Register.registerEnchantments(this);
    }
}