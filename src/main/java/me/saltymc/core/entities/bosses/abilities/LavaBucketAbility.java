package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LavaBucketAbility extends BossAbility
{
    public LavaBucketAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    private static final int PLACE_RANGE = 8;

    @Override
    public void start()
    {
        attemptPlaceLavaBucket();
        customBoss.equipItemInOffHand(new ItemStack(Material.LAVA_BUCKET));
    }

    private void attemptPlaceLavaBucket()
    {
        Player nearestPlayer = customBoss.getNearestPlayer();
        if (nearestPlayer != null && customBoss.getDistanceToPlayer(nearestPlayer) <= PLACE_RANGE)
        {
            placeLavaBucket(nearestPlayer);
        }
    }

    private void placeLavaBucket(Player player)
    {
        Location location = player.getLocation();
        Block block = location.getBlock();
        if (block.getType() == Material.AIR)
        {
            block.setType(Material.LAVA);
        }
    }
}
