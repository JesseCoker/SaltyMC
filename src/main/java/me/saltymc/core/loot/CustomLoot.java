package me.saltymc.core.loot;

import me.saltymc.core.Main;
import me.saltymc.core.items.item.NetherCrossguardPiece;
import me.saltymc.core.items.item.SkulkCrossguardPiece;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class CustomLoot implements Listener
{
    private static Main plugin;
    private static final Random random = new Random();

    public CustomLoot(Main plugin)
    {
        CustomLoot.plugin = plugin;
    }

    @EventHandler
    public void onLootGenerate(LootGenerateEvent event)
    {
        World world = event.getWorld();
        InventoryHolder inventoryHolder = event.getInventoryHolder();
        if (world.getEnvironment() == World.Environment.NETHER && random.nextInt(5) == 0)
        {
            ArrayList<ItemStack> loot = new ArrayList<>(event.getLoot());
            loot.add(new NetherCrossguardPiece(plugin).getItem(1));
            event.setLoot(loot);
        }
        if (inventoryHolder instanceof Chest && world.getEnvironment() == World.Environment.NORMAL && random.nextInt(3) == 0)
        {
            Chest chest = (Chest) inventoryHolder;
            if (world.getBiome(chest.getLocation()) == Biome.DEEP_DARK)
            {
                ArrayList<ItemStack> loot = new ArrayList<>(event.getLoot());
                loot.add(new SkulkCrossguardPiece(plugin).getItem(1));
                event.setLoot(loot);
            }
        }
    }

    @EventHandler
    public void onPiglinTrade(EntityDropItemEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof Piglin && random.nextInt(40) == 0)
        {
            Item item = event.getItemDrop();
            item.remove();

            ItemStack itemstack = new NetherCrossguardPiece(plugin).getItem(1);
            entity.getWorld().dropItemNaturally(entity.getLocation(), itemstack);
        }
    }

    private boolean isPiglin(Entity entity)
    {
        return entity instanceof Piglin || entity instanceof PiglinBrute;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        if (isPiglin(entity) && !entity.fromMobSpawner() && random.nextInt(40) == 0)
        {
            ItemStack netherCrossguardPiece = new NetherCrossguardPiece(plugin).getItem(1);
            entity.getWorld().dropItemNaturally(entity.getLocation(), netherCrossguardPiece);
        }
    }
}
