package me.saltymc.core.entities.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.entities.bosses.herobrine.HerobrineSettings;
import me.saltymc.core.entities.helpers.PositionHelper;
import org.bukkit.*;
import org.bukkit.entity.EntityType;

import java.util.Random;

public class TNTRainAbility extends BossAbility
{
    private static final Random random = new Random();

    public TNTRainAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    @Override
    public void start()
    {
        for (int index = 0; index < HerobrineSettings.Ability.TNT_PLACE_COUNT; ++index)
        {
            Bukkit.getScheduler().runTaskLater(plugin, this::placeTNT, random.nextInt(HerobrineSettings.Ability.MAX_TNT_PLACE_DELAY));
        }
    }

    private void placeTNT()
    {
        if (!customBoss.isAlive()) return;

        Location bossLocation = customBoss.getEntity().getLocation();
        Location newLocation = PositionHelper.getRandomLocationInCircle(bossLocation, HerobrineSettings.Ability.TNT_PLACE_RANGE);
        int placeHeight = random.nextInt( HerobrineSettings.Ability.MAX_TNT_PLACE_HEIGHT - HerobrineSettings.Ability.MIN_TNT_PLACE_HEIGHT + 1)
                + HerobrineSettings.Ability.MIN_TNT_PLACE_HEIGHT;
        double newY = bossLocation.getWorld().getHighestBlockAt(newLocation).getY() + placeHeight;
        newLocation.setY(newY);

        newLocation.getWorld().spawnEntity(newLocation, EntityType.PRIMED_TNT);
        newLocation.getWorld().playSound(newLocation, Sound.ENTITY_TNT_PRIMED, 1.0f, 1.0f);
    }
}
