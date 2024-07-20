package me.saltymc.core.enchants.enchants;

import me.saltymc.core.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class UnsafeEnchanting implements Listener
{
    private static Main plugin;

    public UnsafeEnchanting(Main plugin)
    {
        UnsafeEnchanting.plugin = plugin;
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event)
    {
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack firstItem = anvilInventory.getFirstItem();
        ItemStack secondItem = anvilInventory.getSecondItem();
        if (firstItem != null && secondItem != null && firstItem.getType() != Material.ENCHANTED_BOOK && secondItem.getType() == Material.ENCHANTED_BOOK)
        {
            ItemStack result = new ItemStack(firstItem);
            EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) secondItem.getItemMeta();
            for (Map.Entry<Enchantment, Integer> set : enchantmentStorageMeta.getStoredEnchants().entrySet())
            {
                result.addUnsafeEnchantment(set.getKey(), set.getValue());
            }
            event.setResult(result);
        }
    }

    public static void addBookEnchant(ItemStack itemStack, Enchantment enchantment, int level)
    {
        EnchantmentStorageMeta enchantmentStorageMeta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        enchantmentStorageMeta.addStoredEnchant(enchantment, level, true);
        itemStack.setItemMeta(enchantmentStorageMeta);
    }
}
