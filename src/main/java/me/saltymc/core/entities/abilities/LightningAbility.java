package me.saltymc.core.entities.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.entities.bosses.herobrine.HerobrineSettings;
import me.saltymc.core.entities.helpers.PositionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class LightningAbility extends BossAbility {

    private static final Random random = new Random();

    public LightningAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    @Override
    public void start()
    {
        randomLightningStrikes();
    }

    private void randomLightningStrikes()
    {
        Location bossLocation = customBoss.getEntity().getLocation();
        for (int index = 0; index < HerobrineSettings.Ability.LIGHTNING_STRIKE_COUNT; ++index)
        {
            int strikeDelay = random.nextInt(HerobrineSettings.Ability.MAX_LIGHTNING_STRIKE_DELAY);
            int strikeRadius = random.nextInt(HerobrineSettings.Ability.MAX_LIGHTNING_STRIKE_RADIUS);
            Location strikeLocation = PositionHelper.getRandomLocationInCircle(bossLocation, strikeRadius);
            strikeLocation.setY(strikeLocation.getWorld().getHighestBlockAt(strikeLocation).getY());
            Bukkit.getScheduler().runTaskLater(plugin, ()->strikeLightning(strikeLocation), strikeDelay);
            strikeLightning(strikeLocation);
        }
    }

    private void strikeLightning(Location location)
    {
        World world = location.getWorld();
        world.strikeLightning(location);
    }
}
