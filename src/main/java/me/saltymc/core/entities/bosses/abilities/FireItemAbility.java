package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FireItemAbility extends BossAbility
{
    public FireItemAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    private static final Random random = new Random();

    private static final int LAVA_BUCKET_PLACE_RANGE = 6;
    private static final int FLINT_AND_STEEL_PLACE_RANGE = 7;

    @Override
    public void start()
    {
        Player nearestPlayer = customBoss.getNearestPlayer();

        boolean isLava = random.nextBoolean();
        if (isLava)
        {
            customBoss.equipItemInOffHand(new ItemStack(Material.LAVA_BUCKET));
            boolean isInRange = playerIsInRange(nearestPlayer, LAVA_BUCKET_PLACE_RANGE);
            if (isInRange) useItem(nearestPlayer.getLocation(), Material.LAVA);
        }
        else
        {
            customBoss.equipItemInOffHand(new ItemStack(Material.FLINT_AND_STEEL));
            boolean isInRange = playerIsInRange(nearestPlayer, FLINT_AND_STEEL_PLACE_RANGE);
            if (isInRange) useItem(nearestPlayer.getLocation(), Material.FIRE);
        }
    }

    private boolean playerIsInRange(Player player, int placeRange)
    {
        return player != null && customBoss.getDistanceToPlayer(player) <= placeRange;
    }

    private void useItem(Location location, Material blockType)
    {
        Block block = location.getBlock();
        if (block.getType() == Material.AIR || block.getType() == Material.SHORT_GRASS)
        {
            block.setType(blockType);
        }
    }
}
