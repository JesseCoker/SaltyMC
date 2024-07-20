package me.saltymc.core.entities.bosses.oldherobrine;

import org.bukkit.Material;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Random;

public class OldHerobrineEggHelper
{
    private static final Random random = new Random();
    private static final ArrayList<SpawnEggPair> spawnEggPairs = new ArrayList<>();

    static
    {
        spawnEggPairs.add(new SpawnEggPair(Material.CREEPER_SPAWN_EGG, EntityType.CREEPER));
        spawnEggPairs.add(new SpawnEggPair(Material.GHAST_SPAWN_EGG, EntityType.GHAST));
        spawnEggPairs.add(new SpawnEggPair(Material.ZOMBIE_SPAWN_EGG, EntityType.ZOMBIE));
        spawnEggPairs.add(new SpawnEggPair(Material.SKELETON_SPAWN_EGG, EntityType.SKELETON));
        spawnEggPairs.add(new SpawnEggPair(Material.WITCH_SPAWN_EGG, EntityType.WITCH));
        spawnEggPairs.add(new SpawnEggPair(Material.SLIME_SPAWN_EGG, EntityType.SLIME));
        spawnEggPairs.add(new SpawnEggPair(Material.CAVE_SPIDER_SPAWN_EGG, EntityType.CAVE_SPIDER));
        spawnEggPairs.add(new SpawnEggPair(Material.MAGMA_CUBE_SPAWN_EGG, EntityType.MAGMA_CUBE));
        spawnEggPairs.add(new SpawnEggPair(Material.WITHER_SKELETON_SPAWN_EGG, EntityType.WITHER_SKELETON));
        spawnEggPairs.add(new SpawnEggPair(Material.BLAZE_SPAWN_EGG, EntityType.BLAZE));
        spawnEggPairs.add(new SpawnEggPair(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, EntityType.ZOMBIFIED_PIGLIN));
        spawnEggPairs.add(new SpawnEggPair(Material.ENDERMAN_SPAWN_EGG, EntityType.ENDERMAN));
        spawnEggPairs.add(new SpawnEggPair(Material.PHANTOM_SPAWN_EGG, EntityType.PHANTOM));
    }

    static class SpawnEggPair
    {
        private final Material spawnEggMaterial;
        private final EntityType entityType;

        public SpawnEggPair(Material spawnEggMaterial, EntityType entityType)
        {
            this.spawnEggMaterial = spawnEggMaterial;
            this.entityType = entityType;
        }

        public Material getSpawnEggMaterial()
        {
            return spawnEggMaterial;
        }

        public EntityType getEntityType()
        {
            return entityType;
        }
    }

    public Material useSpawnEgg(Zombie herobrine)
    {
        int randomSpawnEgg = random.nextInt(spawnEggPairs.size());

        Entity entity = herobrine.getWorld().spawnEntity(herobrine.getLocation(), spawnEggPairs.get(randomSpawnEgg).getEntityType());

        if (entity instanceof PigZombie)
        {
            PigZombie pigZombie = (PigZombie) entity;
            pigZombie.setAngry(true);
        }
        else if (entity instanceof Enderman)
        {
            Enderman enderman = (Enderman) entity;
            enderman.setScreaming(true);
        }
        else if (entity instanceof Slime)
        {
            Slime slime = (Slime) entity;
            slime.setSize(random.nextInt(15));
        }
        else if (entity instanceof Phantom)
        {
            Phantom phantom = (Phantom) entity;
            phantom.setSize(random.nextInt(15));
            phantom.setShouldBurnInDay(false);
        }

        return spawnEggPairs.get(randomSpawnEgg).getSpawnEggMaterial();
    }
}
