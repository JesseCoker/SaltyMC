package me.saltymc.core.enchants.enchants;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.CustomEnchant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

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

    private boolean isKeepInventory(World world)
    {
        return Boolean.TRUE.equals(world.getGameRuleValue(GameRule.KEEP_INVENTORY));
    }

    private final HashMap<UUID, List<ItemStack>> soulboundItems = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();

        if (isKeepInventory(player.getWorld())) return;

        List<ItemStack> drops = event.getDrops();
        ListIterator<ItemStack> iterator = drops.listIterator();

        List<ItemStack> itemsToSave = new ArrayList<>();

        while(iterator.hasNext()){

            ItemStack itemStack = iterator.next();
            if (hasEnchantment(itemStack)) {
                itemsToSave.add(itemStack);
                iterator.remove();
            }
        }

        soulboundItems.put(player.getUniqueId(), itemsToSave);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        List<ItemStack> itemsToGive = soulboundItems.get(player.getUniqueId());
        if (itemsToGive != null)
        {
            for (ItemStack itemStack : itemsToGive)
            {
                inventory.addItem(itemStack);
            }
            soulboundItems.remove(player.getUniqueId());
        }
    }
}
