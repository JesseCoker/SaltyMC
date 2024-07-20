package me.saltymc.core.entities.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;

public abstract class BossAbility
{
    protected final Main plugin;
    protected final CustomBoss customBoss;

    public BossAbility(Main plugin, CustomBoss customBoss)
    {
        this.plugin = plugin;
        this.customBoss = customBoss;
    }

    abstract public void start();
}
