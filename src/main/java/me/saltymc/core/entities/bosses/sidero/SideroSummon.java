package me.saltymc.core.entities.bosses.sidero;

import me.saltymc.core.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class SideroSummon implements Listener
{
    private static Main plugin;

    private static final Random random = new Random();

    public SideroSummon(Main plugin)
    {
        SideroSummon.plugin = plugin;
    }

    @EventHandler
    public void onIronGolemKill(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        if (entity instanceof IronGolem && !entity.fromMobSpawner())
        {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, SideroSettings.IRON_GOLEM_KEY);
            if (!entity.getPersistentDataContainer().has(namespacedKey) && random.nextInt(SideroSettings.SPAWN_CHANCE) == 0)
            {
                Sidero sidero = new Sidero(plugin);
                sidero.summon(entity.getLocation());
            }
        }
    }
}
