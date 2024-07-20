package me.saltymc.core.runnables;

import me.saltymc.core.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class DrownedStorm
{
    private final Main plugin;
    private final World world;
    private final Random random = new Random();
    private BukkitTask task;

    public DrownedStorm(Main plugin, World world)
    {
        this.plugin = plugin;
        this.world = world;
    }

    public void start()
    {
        world.setStorm(true);
        int delay = 0;
        int period = 8;
        task = Bukkit.getScheduler().runTaskTimer(plugin, this::update, delay, period);
        Bukkit.broadcast(Component.text("The sky suddenly goes dark...").color(NamedTextColor.DARK_GRAY));
    }

    private void stop()
    {
        task.cancel();
    }

    private void update()
    {
        int shouldStrike = random.nextInt(4);
        if (shouldStrike != 0) return;

        Player randomPlayer = getRandomPlayer();
        Location playerLocation = randomPlayer.getLocation();

        double randomDistance = random.nextDouble() * 50;
        double randomAngle = Math.toRadians(random.nextInt(360));
        double xOffset = randomDistance * Math.cos(randomAngle);
        double zOffset = randomDistance * Math.sin(randomAngle);

        double newX = playerLocation.getX() + xOffset;
        double newZ = playerLocation.getZ() + zOffset;
        Block highestBlock = world.getHighestBlockAt((int)newX, (int)newZ);

        world.strikeLightning(new Location(world, newX, highestBlock.getY(), newZ));

        if (world.isClearWeather())
        {
            stop();
        }
    }

    private Player getRandomPlayer()
    {
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int randomPlayer = random.nextInt(players.size());
        return players.get(randomPlayer);
    }
}
