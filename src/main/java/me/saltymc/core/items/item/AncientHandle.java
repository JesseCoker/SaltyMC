package me.saltymc.core.items.item;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AncientHandle extends CustomItem
{

    public AncientHandle(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.GOLD_NUGGET;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Ancient Handle").color(NamedTextColor.DARK_GRAY);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Crafting Ingredient").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Can only be held by").color(NamedTextColor.YELLOW));
        lore.add(Component.text("those who are worthy.").color(NamedTextColor.YELLOW));
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
        return true;
    }

    @Override
    public String getItemId()
    {
        return "ANCIENT_HANDLE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private boolean isWorthy(Entity entity)
    {
        if (entity instanceof Player)
        {
            return entity.getName().equals("devocalized");
        }
        return false;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event)
    {
        ItemStack itemStack = event.getItem().getItemStack();
        if (itemStack.getType() == getMaterial() && matchesItem(itemStack) && !isWorthy(event.getEntity()))
        {
            event.setCancelled(true);
        }
    }
}
