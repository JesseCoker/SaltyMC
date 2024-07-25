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

    private static final Random random = new Random();

    private static final double PROJECTILE_SPEED = 1.4;
    private static final int MAX_PROJECTILES = 18;
    private static final long INITIAL_THROW_DELAY = 30L;
    private static final long DELAY_BETWEEN_THROWS = 2L;

    @Override
    public void start()
    {
        Player nearestPlayer = customBoss.getNearestPlayer();

        boolean isSnowball = random.nextBoolean();
        if (isSnowball)
        {
            customBoss.equipItemInOffHand(new ItemStack(Material.SNOWBALL));
            if (isInSight(nearestPlayer))
            {
                scheduleThrows(Snowball.class, nearestPlayer, Sound.ENTITY_SNOWBALL_THROW);
            }
        }
        else
        {
            customBoss.equipItemInOffHand(new ItemStack(Material.EGG));
            if (isInSight(nearestPlayer))
            {
                scheduleThrows(Egg.class, nearestPlayer, Sound.ENTITY_EGG_THROW);
            }
        }

    }

    private boolean isInSight(Player player)
    {
        return player != null && customBoss.getEntity().hasLineOfSight(player);
    }

    private void scheduleThrows(Class<? extends Projectile> projectileClass, Player targetPlayer, Sound sound)
    {
        int numberOfThrows = random.nextInt(MAX_PROJECTILES);
        for (int index = 0; index < numberOfThrows; ++index)
        {
            Bukkit.getScheduler().runTaskLater(plugin, ()->throwProjectile(projectileClass, targetPlayer, sound), INITIAL_THROW_DELAY + DELAY_BETWEEN_THROWS * index);
        }
    }

    private void throwProjectile(Class<? extends Projectile> projectileClass, Player targetPlayer, Sound sound)
    {
        if (!customBoss.isAlive()) return;
        Vector targetVector = targetPlayer.getLocation().toVector();
        Vector bossVector = customBoss.getEntity().getLocation().toVector();
        Vector projectileVelocity = targetVector.subtract(bossVector).normalize().multiply(PROJECTILE_SPEED);
        customBoss.getEntity().launchProjectile(projectileClass, projectileVelocity);
        customBoss.playSound(customBoss.getEntity().getLocation(), sound);
    }
}
