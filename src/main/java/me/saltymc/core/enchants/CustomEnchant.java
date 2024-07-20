package me.saltymc.core.enchants;

import me.saltymc.core.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomEnchant implements Listener
{
    protected static Main plugin;

    public static final ArrayList<CustomEnchant> registeredItems = new ArrayList<>();

    public CustomEnchant(Main plugin)
    {
        CustomEnchant.plugin = plugin;
    }

    public static void register(CustomEnchant customEnchant)
    {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(customEnchant, plugin);
        registeredItems.add(customEnchant);
    }

    public boolean matchesItem(ItemStack itemStack)
    {
        if (itemStack != null && itemStack.hasItemMeta())
        {
            return itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, getItemId()));
        }
        return false;
    }

    public ItemStack getItem()
    {
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(getDisplayName());
        itemMeta.lore(getLore());
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, getItemId()), PersistentDataType.BOOLEAN, true);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public boolean hasEnchantment(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null)
        {
            NamespacedKey namespacedKey = new NamespacedKey(plugin, getItemId());
            return itemMeta.getPersistentDataContainer().has(namespacedKey);
        }
        return false;
    }

    protected boolean playerHasEnchant(Player player)
    {
        PlayerInventory playerInventory = player.getInventory();
        return hasEnchantment(playerInventory.getItem(EquipmentSlot.HAND)) ||
                hasEnchantment(playerInventory.getItem(EquipmentSlot.OFF_HAND)) ||
                hasEnchantment(playerInventory.getItem(EquipmentSlot.HEAD)) ||
                hasEnchantment(playerInventory.getItem(EquipmentSlot.CHEST)) ||
                hasEnchantment(playerInventory.getItem(EquipmentSlot.LEGS)) ||
                hasEnchantment(playerInventory.getItem(EquipmentSlot.FEET));
    }

    abstract protected TextComponent getDisplayName();
    abstract protected List<Component> getLore();
    abstract public String getItemId();
    abstract protected ItemStack applyEnchantment(ItemStack itemStack);

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event)
    {
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack firstItem = anvilInventory.getFirstItem();
        ItemStack secondItem = anvilInventory.getSecondItem();
        if (firstItem != null && secondItem != null && matchesItem(secondItem))
        {
            anvilInventory.setRepairCost(30);
            ItemStack result = applyEnchantment(firstItem);
            event.setResult(result);
        }
    }
}
