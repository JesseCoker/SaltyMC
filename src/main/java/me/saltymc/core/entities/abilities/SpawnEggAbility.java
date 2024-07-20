package me.saltymc.core.entities.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SpawnEggAbility extends BossAbility {

    private final HashMap<EntityType, Material> spawnables;

    private static final Random random = new Random();

    public SpawnEggAbility(Main plugin, CustomBoss customBoss, HashMap<EntityType, Material> spawnables)
    {
        super(plugin, customBoss);
        this.spawnables = new HashMap<>(spawnables);
    }

    @Override
    public void start() {
        EntityType entityType = getRandomEntityType();
        ItemStack spawnEgg = getSpawnEggFromEntityType(entityType);

        customBoss.equipItemInOffHand(spawnEgg);
        spawnEntity(entityType);
    }

    private EntityType getRandomEntityType()
    {
        ArrayList<EntityType> keys = new ArrayList<>(spawnables.keySet());
        return keys.get(random.nextInt(keys.size()));
    }

    private ItemStack getSpawnEggFromEntityType(EntityType entityType)
    {
        return new ItemStack(spawnables.get(entityType));
    }

    private void spawnEntity(EntityType entityType)
    {
        Location location = customBoss.getEntity().getLocation();
        location.getWorld().spawnEntity(location, entityType);
    }
}
