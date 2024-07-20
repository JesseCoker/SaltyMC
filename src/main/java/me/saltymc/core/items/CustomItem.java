package me.saltymc.core.items;

import me.saltymc.core.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public abstract class CustomItem implements Listener
{
    protected static Main plugin;

    public static final ArrayList<CustomItem> registeredItems = new ArrayList<>();

    public CustomItem(Main plugin)
    {
        CustomItem.plugin = plugin;
    }

    public static void register(CustomItem customItem)
    {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(customItem, plugin);
        registeredItems.add(customItem);
    }

    public boolean matchesItem(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.hasItemMeta())
        {
            return itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, getItemId()));
        }
        return false;
    }

    public ItemStack getItem(int amount)
    {
        ItemStack item = new ItemStack(getMaterial());

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(getDisplayName());
        itemMeta.lore(getLore());
        itemMeta.setCustomModelData(getModelId());
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, getItemId()), PersistentDataType.BOOLEAN, true);
        item.setItemMeta(itemMeta);

        modifyItem(item);

        item.setAmount(amount);

        return item;
    }

    abstract protected Material getMaterial();
    abstract protected TextComponent getDisplayName();
    abstract protected List<Component> getLore();
    abstract protected int getModelId();
    abstract protected boolean doesDespawn();
    abstract public String getItemId();
    abstract protected void modifyItem(ItemStack itemStack);

    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event)
    {
        ItemStack itemStack = event.getEntity().getItemStack();
        if (matchesItem(itemStack)) event.setCancelled(!doesDespawn());
    }

    @EventHandler
    public void onMobPickup(EntityPickupItemEvent event)
    {
        if (matchesItem(event.getItem().getItemStack()) && !(event.getEntity() instanceof Player)) event.setCancelled(true);
    }
}
