package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.helpers.PositionHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class HerobrineTeleport
{
    private final CustomBoss customBoss;

    private long lastTeleportTick = 0;

    private static final Random random = new Random();

    public HerobrineTeleport(CustomBoss customBoss)
    {
        this.customBoss = customBoss;
    }

    public void attemptTeleport()
    {
        if (customBoss.getTicksBetween(lastTeleportTick) >= HerobrineSettings.Teleport.TICKS_BETWEEN_RANDOM_TELEPORTS)
        {
            randomTeleport();
            lastTeleportTick = customBoss.getTotalTicks();
        }
    }

    private Player getRandomPlayer()
    {
        ArrayList<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        return playerList.get(random.nextInt(playerList.size()));
    }

    private boolean isPlayerInRange(Player player)
    {
        return customBoss.getDistanceToPlayer(player) <= customBoss.getBossRange();
    }

    public void randomTeleport()
    {
        int randomTeleport = random.nextInt(3);
        if (randomTeleport == 0) teleportToNearestPlayer();
        else if (randomTeleport == 1) teleportToRandomPlayer();
        else teleportAroundRandomPlayer();
    }

    public void teleportToNearestPlayer() {
        Player nearestPlayer = customBoss.getNearestPlayer();
        if (nearestPlayer != null && isPlayerInRange(nearestPlayer)) teleportTo(nearestPlayer.getLocation());
    }

    public void teleportToRandomPlayer()
    {
        Player randomPlayer = getRandomPlayer();
        if (isPlayerInRange(randomPlayer)) teleportTo(randomPlayer.getLocation());
    }

    public void teleportAroundRandomPlayer()
    {
        Player randomPlayer = getRandomPlayer();
        if (isPlayerInRange(randomPlayer))
        {
            Location playerLocation = randomPlayer.getLocation();
            Location newLocation = PositionHelper.getRandomLocationInCircle(
                    playerLocation, HerobrineSettings.Teleport.TELEPORT_AROUND_RADIUS);
            newLocation.setY(newLocation.getWorld().getHighestBlockAt(newLocation).getY());
            if (Math.abs(playerLocation.getY() - newLocation.getY()) < HerobrineSettings.Teleport.MAX_HEIGHT_DIFFERENCE)
            {
                teleportTo(newLocation);
            }
        }
    }

    public void teleportTo(Location location)
    {
        Location oldLocation = customBoss.getEntity().getLocation();
        customBoss.playSound(oldLocation, Sound.ENTITY_ENDERMAN_TELEPORT);
        customBoss.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT);
        customBoss.spawnParticles(Particle.PORTAL, oldLocation);
        customBoss.spawnParticles(Particle.PORTAL, location);
        customBoss.getEntity().teleport(location.add(new Vector(0, 1, 0)));
    }
}
