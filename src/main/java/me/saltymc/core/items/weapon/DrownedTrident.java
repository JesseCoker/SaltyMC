package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DrownedTrident extends CustomItem
{
    public DrownedTrident(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.TRIDENT;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("The Maelstrom's Fang").color(NamedTextColor.DARK_BLUE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Can create massive storms.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Use /startstorm while holding.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Eliminates all fall damage when.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("held while raining.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 98;
    }

    @Override
    protected boolean doesDespawn()
    {
        return false;
    }

    @Override
    public String getItemId()
    {
        return "DROWNED_TRIDENT";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (matchesItem(itemInMainHand) && event.getCause() == EntityDamageEvent.DamageCause.FALL && !(player.getWorld().isClearWeather()))
            {
                event.setCancelled(true);
            }
        }
    }
}
