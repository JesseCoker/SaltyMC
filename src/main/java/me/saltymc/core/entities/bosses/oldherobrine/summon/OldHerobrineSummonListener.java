package me.saltymc.core.entities.bosses.oldherobrine.summon;

import me.saltymc.core.Main;
import me.saltymc.core.entities.bosses.oldherobrine.OldHerobrine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class OldHerobrineSummonListener implements Listener
{
    private static Main plugin;

    public OldHerobrineSummonListener(Main plugin)
    {
        OldHerobrineSummonListener.plugin = plugin;
    }

    private static final Vector[] relativeGoldBlockPositions = {
            new Vector(-1, -1, 0),
            new Vector(1, -1, 0),
            new Vector(0, -1, -1),
            new Vector(0, -1, 1),
            new Vector(-1, -1, -1),
            new Vector(1, -1, 1),
            new Vector(-1, -1, 1),
            new Vector(1, -1, -1)
    };

    private static final Vector[] relativeRedstoneTorchPositions = {
            new Vector(-1, 0, 0),
            new Vector(1, 0, 0),
            new Vector(0, 0, -1),
            new Vector(0, 0, 1)
    };

    private boolean isShrine(Block netherrackBlock)
    {
        World world = netherrackBlock.getWorld();

        if (netherrackBlock.getType() != Material.NETHERRACK) return false;

        for (Vector relativePosition : relativeGoldBlockPositions)
        {
            Location blockLocation = netherrackBlock.getLocation();
            if (world.getBlockAt(blockLocation.add(relativePosition)).getType() != Material.GOLD_BLOCK)
            {
                return false;
            }
        }

        for (Vector relativePosition : relativeRedstoneTorchPositions)
        {
            Location blockLocation = netherrackBlock.getLocation();
            if (world.getBlockAt(blockLocation.add(relativePosition)).getType() != Material.REDSTONE_TORCH)
            {
                return false;
            }
        }

        return true;
    }

    @EventHandler
    public void onHerobrineShrineIgnite(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && itemInMainHand.getType() == Material.FLINT_AND_STEEL)
        {
            Block netherrackBlock = event.getClickedBlock();
            if (netherrackBlock != null && isShrine(netherrackBlock))
            {
                netherrackBlock.getWorld().spawnParticle(Particle.DUST_PLUME, netherrackBlock.getLocation(), 120);
                netherrackBlock.getWorld().playSound(netherrackBlock.getLocation(), Sound.AMBIENT_CAVE, 1.0f, 1.0f);
                TextComponent part1 = Component.text("In the fire, sacrafice to me a ").color(NamedTextColor.GRAY);
                TextComponent part2 = Component.text("shiny gem").color(NamedTextColor.LIGHT_PURPLE);
                TextComponent part3 = Component.text(", and a ").color(NamedTextColor.GRAY);
                TextComponent part4 = Component.text("block infused with the air of the new world.").color(TextColor.color(43, 181, 135));
                player.sendMessage(part1.append(part2).append(part3).append(part4));
            }
        }
    }

    private boolean isCopperItem(Entity entity)
    {
        if (entity instanceof Item)
        {
            Item item = (Item) entity;
            return item.getItemStack().getType() == Material.OXIDIZED_COPPER;
        }
        return false;
    }

    private boolean isAmethystItem(Entity entity)
    {
        if (entity instanceof Item)
        {
            Item item = (Item) entity;
            return item.getItemStack().getType() == Material.AMETHYST_SHARD;
        }
        return false;
    }

    private void messageNearbyPlayers(Block block, TextComponent message)
    {
        Location location = block.getLocation();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getLocation().distance(location) <= 50)
            {
                player.sendMessage(message);
            }
        }
    }

    private void knockbackNearbyPlayers(Block combuster)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getLocation().distance(combuster.getLocation()) < 20)
            {
                Vector playerVector = player.getLocation().toVector();
                Vector combusterVector = combuster.getLocation().toVector();
                Vector directionVector = playerVector.subtract(combusterVector).normalize().multiply(4.0f).setY(0.0f).add(new Vector(0.0f, 1.2f, 0.0f));
                player.setVelocity(directionVector);
            }
        }
    }

    private static Location copperSacraficeLocation = null;
    private static Location amethystSacraficeLocation = null;

    private void updateSacrafices(Block combuster, Entity entity)
    {
        if (isCopperItem(entity))
        {
            copperSacraficeLocation = combuster.getLocation();
            messageNearbyPlayers(combuster, Component.text("I will accept this oxidized copper."));
            entity.remove();
        }
        else if (isAmethystItem(entity))
        {
            amethystSacraficeLocation = combuster.getLocation();
            messageNearbyPlayers(combuster, Component.text("I will accept this amethyst shard."));
            entity.remove();
        }

        if (copperSacraficeLocation != null && copperSacraficeLocation.equals(amethystSacraficeLocation))
        {
            messageNearbyPlayers(combuster, Component.text("FOOL!").color(NamedTextColor.RED));
            knockbackNearbyPlayers(combuster);
            copperSacraficeLocation = null;
            amethystSacraficeLocation = null;
            combuster.getWorld().strikeLightning(combuster.getLocation());
            OldHerobrine herobrine = new OldHerobrine(plugin);
            herobrine.summonHerobrine(combuster.getLocation());
        }
    }

    @EventHandler
    public void onItemSacrafice(EntityCombustByBlockEvent event)
    {
        Block combuster = event.getCombuster();
        if (combuster != null && combuster.getType() == Material.FIRE)
        {
            World world = combuster.getWorld();
            Block netherrackBlock = world.getBlockAt(combuster.getLocation().subtract(new Vector(0, 1, 0)));
            if (isShrine(netherrackBlock))
            {
                updateSacrafices(combuster, event.getEntity());
            }
        }
    }
}
