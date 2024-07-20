package me.saltymc.core.loot;

import me.saltymc.core.Main;
import me.saltymc.core.items.weapon.AncientSpell;
import org.bukkit.*;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ShulkerSpell implements Listener
{
    private static Main plugin;

    public ShulkerSpell(Main plugin)
    {
        ShulkerSpell.plugin = plugin;
    }

    private boolean isSpell(Inventory shulkerBox)
    {
        ItemStack[] contents = shulkerBox.getContents();
        if
        (contents[0] != null && contents[0].getType() == Material.SCUTE && contents[0].getAmount() == 1 &&
                contents[1] != null && contents[1].getType() == Material.LAPIS_LAZULI && contents[1].getAmount() == 1 &&
                contents[2] != null && contents[2].getType() == Material.DRAGON_BREATH && contents[2].getAmount() == 1)
        {
            for (int index = 3; index < contents.length; ++index)
            {
                if (contents[index] != null) return false;
            }
            return true;
        }
        return false;
    }

    static class SpawnSpellRunnable implements Runnable
    {
        private static Main plugin;
        private final World world;
        private final Location location;

        SpawnSpellRunnable(Main plugin, World world, Location location)
        {
            SpawnSpellRunnable.plugin = plugin;
            this.world = world;
            this.location = location;
        }

        @Override
        public void run()
        {
            world.playSound(location, Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
            world.dropItemNaturally(location, new AncientSpell(plugin).getItem(1));
        }
    }

    @EventHandler
    public void onCloseShulker(InventoryCloseEvent event)
    {
        World world = event.getPlayer().getWorld();
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder instanceof ShulkerBox && isSpell(event.getInventory()))
        {
            ShulkerBox shulkerBox = (ShulkerBox) inventoryHolder;
            world.getBlockAt(shulkerBox.getLocation()).setType(Material.AIR);
            world.playSound(shulkerBox.getLocation(), Sound.ENTITY_SHULKER_BULLET_HURT, 1.0f, 1.0f);
            world.playSound(shulkerBox.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1.0f, 2.0f);
            world.spawnParticle(Particle.PORTAL, shulkerBox.getLocation(), 120, null);
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new SpawnSpellRunnable(plugin, world, shulkerBox.getLocation()), 50L);
        }
    }
}
