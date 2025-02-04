package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;

public abstract class BossAbility
{
    protected final Main plugin;
    protected final CustomBoss customBoss;

    protected static final long ITEM_HOLD_DELAY = 30L;

    public BossAbility(Main plugin, CustomBoss customBoss)
    {
        this.plugin = plugin;
        this.customBoss = customBoss;
    }

    abstract public void start();
}
