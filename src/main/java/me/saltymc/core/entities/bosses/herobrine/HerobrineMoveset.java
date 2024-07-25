package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.entities.bosses.abilities.*;
import me.saltymc.core.items.weapon.HerobrineBow;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class HerobrineMoveset
{
    private final Main plugin;
    private final CustomBoss customBoss;

    private static final Random random = new Random();

    public HerobrineMoveset(Main plugin, CustomBoss customBoss)
    {
        this.plugin = plugin;
        this.customBoss = customBoss;
    }

    private static final HashMap<EntityType, Material> spawnables = new HashMap<>();
    static
    {
        spawnables.put(EntityType.GHAST, Material.GHAST_SPAWN_EGG);
        spawnables.put(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG);
        spawnables.put(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG);
        spawnables.put(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG);
    }

    public void nextMove()
    {
        int nextMove = random.nextInt(HerobrineSettings.Ability.USE_ABILITY_CHANCE);

        if (nextMove == 0) new LavaRainAbility(plugin, customBoss).start();
        else if (nextMove == 1)  new TNTRainAbility(plugin, customBoss).start();
        else if (nextMove == 2)  new SpawnEggAbility(plugin, customBoss, spawnables).start();
        else if (nextMove == 3)  new LightningAbility(plugin, customBoss).start();
        else if (nextMove == 4)  new FireItemAbility(plugin, customBoss).start();
        else if (nextMove == 5)
        {
            ItemStack herobrineBow = new HerobrineBow(plugin).getItem(1);
            new BowAbility(plugin, customBoss, herobrineBow, HerobrineSettings.Ability.BOW_POWER).start();
        }
    }
}