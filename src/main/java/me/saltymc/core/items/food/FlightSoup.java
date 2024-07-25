package me.saltymc.core.items.food;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FlightSoup extends CustomItem
{
    public FlightSoup(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.MUSHROOM_STEW;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Flight Soup").color(NamedTextColor.YELLOW);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Consumption allows you to fly"));
        lore.add(Component.text("for up to 30 minutes."));
        lore.add(Component.text("Flight is lost if you take"));
        lore.add(Component.text("any damage."));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 0;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "FLIGHT_SOUP";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private void applyFlight(Player player)
    {
        player.setAllowFlight(true);

        int flightTimeInMinutes = 30;
        int warningTimeInSeconds = 30;

        int flightTimeInTicks = flightTimeInMinutes * 60 * 20;
        int warningTimeInTicks = warningTimeInSeconds * 20;

        Runnable warnFlight = () ->
        {
            player.sendMessage(Component.text("Your flight will run out in " + warningTimeInSeconds + " seconds!").color(NamedTextColor.RED));
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
        };
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, warnFlight, flightTimeInTicks - warningTimeInTicks);

        Runnable disableFlight = () ->
        {
            player.setAllowFlight(false);
            player.sendMessage(Component.text("Your flight has run out!").color(NamedTextColor.RED));
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
        };
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, disableFlight, flightTimeInTicks);
    }

    @EventHandler
    public void onConsume(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand))
        {
            itemInMainHand.setAmount(0);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 1.0f);
            applyFlight(player);
        }
    }

    @EventHandler
    public void onTakeDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if (player.isFlying() && player.getGameMode() != GameMode.CREATIVE)
            {
                player.setAllowFlight(false);
            }
        }
    }
}
