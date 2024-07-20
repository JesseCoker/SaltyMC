package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.entities.bosses.herobrine.HerobrineSettings;
import me.saltymc.core.helpers.PositionHelper;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LavaRainAbility extends BossAbility
{
    private static final Random random = new Random();

    public LavaRainAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    @Override
    public void start()
    {
        customBoss.equipItemInMainHand(new ItemStack(Material.LAVA_BUCKET));
        disperseLava();
    }

    private void disperseLava()
    {
        for (int index = 0; index < HerobrineSettings.Ability.LAVA_PLACE_COUNT; ++index)
        {
            Bukkit.getScheduler().runTaskLater(plugin, this::placeLava, random.nextInt(HerobrineSettings.Ability.MAX_LAVA_PLACE_DELAY));
        }
    }

    private void placeLava()
    {
        if (!customBoss.isAlive()) return;

        Location bossLocation = customBoss.getEntity().getLocation();
        Location newLocation = PositionHelper.getRandomLocationInCircle(bossLocation, HerobrineSettings.Ability.LAVA_PLACE_RANGE);
        int placeHeight = random.nextInt( HerobrineSettings.Ability.MAX_LAVA_PLACE_HEIGHT - HerobrineSettings.Ability.MIN_LAVA_PLACE_HEIGHT + 1)
                + HerobrineSettings.Ability.MIN_LAVA_PLACE_HEIGHT;
        double newY = bossLocation.getWorld().getHighestBlockAt(newLocation).getY() + placeHeight;
        newLocation.setY(newY);

        newLocation.getWorld().getBlockAt(newLocation).setType(Material.LAVA);
        newLocation.getWorld().spawnParticle(Particle.LANDING_LAVA, newLocation, 30);
        newLocation.getWorld().playSound(newLocation, Sound.ITEM_BUCKET_FILL_LAVA, 1.0f, 1.0f);
    }
}
