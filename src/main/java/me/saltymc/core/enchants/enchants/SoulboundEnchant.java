package me.saltymc.core.enchants.enchants;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.CustomEnchant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SoulboundEnchant extends CustomEnchant
{
    public SoulboundEnchant(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Soulbound").color(NamedTextColor.DARK_PURPLE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("You will never lose an item again.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    public String getItemId()
    {
        return "SOULBOUND_ENCHANTMENT";
    }

    @Override
    protected ItemStack applyEnchantment(ItemStack itemStack)
    {
        ItemStack result = new ItemStack(itemStack);
        ItemMeta itemMeta = result.getItemMeta();
        List<Component> lore = new ArrayList<>();
        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) lore.addAll(itemLore);
        lore.add(Component.text("Soulbound").color(NamedTextColor.DARK_PURPLE));
        itemMeta.lore(lore);

        NamespacedKey namespacedKey = new NamespacedKey(plugin, getItemId());
        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, 1);

        result.setItemMeta(itemMeta);
        return result;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();

        if (Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))) return;

        Inventory inventory = player.getInventory();
        ItemStack[] inventoryContents = inventory.getContents();
        for (ItemStack itemStack : inventoryContents)
        {
            if (itemStack != null && !hasEnchantment(itemStack))
            {
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                inventory.remove(itemStack);
            }
        }

        event.getDrops().clear();
        event.setKeepInventory(true);
    }
}
