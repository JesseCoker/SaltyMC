package me.saltymc.core.entities.bosses.oldherobrine;

import me.saltymc.core.Main;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class OldHerobrineReward implements Runnable
{
    private int taskId;

    private final Main plugin;
    private final Location rewardLocation;

    private int count = 0;
    private final int rewardCount;

    private static final int MIN_REWARD = 48;
    private static final int MAX_REWARD = 72;
    private static final int REWARD_HEIGHT = 20;

    private static final Random random = new Random();

    public OldHerobrineReward(Main plugin, Location altarLocation)
    {
        this.plugin = plugin;
        this.rewardLocation = altarLocation.add(new Vector(0, REWARD_HEIGHT, 0));
        rewardCount = random.nextInt(MAX_REWARD - MIN_REWARD) + MIN_REWARD;
        rewardLocation.getWorld().spawnParticle(Particle.CHERRY_LEAVES, rewardLocation, 120);
        altarLocation.getWorld().setStorm(false);
    }

    @Override
    public void run()
    {
        rewardLocation.getWorld().dropItemNaturally(rewardLocation, new ItemStack(Material.DIAMOND));
        ++count;

        if (count >= rewardCount) plugin.getServer().getScheduler().cancelTask(taskId);
    }

    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }
}
