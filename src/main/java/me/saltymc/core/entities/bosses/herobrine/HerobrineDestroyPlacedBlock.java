package me.saltymc.core.entities.bosses.herobrine;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Random;

public class HerobrineDestroyPlacedBlock
{
    private final Herobrine herobrine;

    private static final Random random = new Random();

    public HerobrineDestroyPlacedBlock(Herobrine herobrine)
    {
        this.herobrine = herobrine;
    }

    public void punish(BlockPlaceEvent event)
    {
        int chance = random.nextInt(HerobrineSettings.BlockPunish.BLOCK_PUNISH_CHANCE);
        if (chance == 0)
        {
            Player player = event.getPlayer();
            Block block = event.getBlockPlaced();

            herobrine.getHerobrineTeleport().teleportTo(block.getLocation());

            player.playSound(block.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0f, 1.0f);
            player.playSound(block.getLocation(), Sound.AMBIENT_CAVE, 1.0f, 1.0f);
            block.getWorld().spawnParticle(Particle.DUST_PLUME, block.getLocation(), 20);
            block.setType(Material.AIR);
        }
    }
}
