package me.saltymc.core.entities.helpers;

import org.bukkit.Location;

import java.util.Random;

public class PositionHelper
{
    private static final Random random = new Random();

    public static Location getRandomLocationInCircle(Location targetLocation, int radius)
    {
        double randomDistance = random.nextInt(radius);
        double randomAngle = Math.toRadians(random.nextInt(360));
        double xOffset = randomDistance * Math.cos(randomAngle);
        double zOffset = randomDistance * Math.sin(randomAngle);

        double newX = targetLocation.getX() + xOffset;
        double newZ = targetLocation.getZ() + zOffset;
        return new Location(targetLocation.getWorld(), newX, targetLocation.getY(), newZ);
    }
}
