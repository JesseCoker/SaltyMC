package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class EggAndSnowballAbility extends BossAbility
{
    public EggAndSnowballAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    private static final double PROJECTILE_SPEED = 1.4;
    private static final long THROW_DELAY = 30L;

    @Override
    public void start()
    {
        Player nearestPlayer = customBoss.getNearestPlayer();

        Random random = new Random();
        boolean isSnowball = random.nextBoolean();
        if (isSnowball)
        {
            customBoss.equipItemInOffHand(new ItemStack(Material.SNOWBALL));
            if (isInSight(nearestPlayer))
            {
                Bukkit.getScheduler().runTaskLater(plugin,
                        ()->throwProjectile(Snowball.class, nearestPlayer.getLocation(), Sound.ENTITY_SNOWBALL_THROW), THROW_DELAY);
            }
        }
        else
        {
            customBoss.equipItemInOffHand(new ItemStack(Material.EGG));
            if (isInSight(nearestPlayer))
            {
                Bukkit.getScheduler().runTaskLater(plugin,
                        ()->throwProjectile(Egg.class, nearestPlayer.getLocation(), Sound.ENTITY_EGG_THROW), THROW_DELAY);
            }
        }

    }

    private boolean isInSight(Player player)
    {
        return customBoss.getEntity().hasLineOfSight(player);
    }

    private void throwProjectile(Class<? extends Projectile> projectileClass, Location location, Sound sound)
    {
        if (!customBoss.isAlive()) return;
        Vector targetVector = location.toVector();
        Vector bossVector = customBoss.getEntity().getLocation().toVector();
        Vector projectileVelocity = targetVector.subtract(bossVector).normalize().multiply(PROJECTILE_SPEED);
        customBoss.getEntity().launchProjectile(projectileClass, projectileVelocity);
        customBoss.playSound(customBoss.getEntity().getLocation(), sound);
    }
}
