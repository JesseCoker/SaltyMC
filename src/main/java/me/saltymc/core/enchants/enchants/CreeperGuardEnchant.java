package me.saltymc.core.enchants.enchants;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.CustomEnchant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CreeperGuardEnchant extends CustomEnchant
{
    public CreeperGuardEnchant(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Creeper Guard").color(NamedTextColor.DARK_GREEN);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Protects the area around you").color(NamedTextColor.YELLOW));
        lore.add(Component.text("from creeper explosions.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    public String getItemId()
    {
        return "CREEPER_GUARD_ENCHANTMENT";
    }

    @Override
    protected ItemStack applyEnchantment(ItemStack itemStack)
    {
        ItemStack result = new ItemStack(itemStack);
        ItemMeta itemMeta = result.getItemMeta();
        List<Component> lore = new ArrayList<>();
        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) lore.addAll(itemLore);
        lore.add(Component.text("Creeper Guard").color(NamedTextColor.GREEN));
        itemMeta.lore(lore);

        NamespacedKey namespacedKey = new NamespacedKey(plugin, getItemId());
        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, 1);

        result.setItemMeta(itemMeta);
        return result;
    }

    private boolean nearbyPlayerHasEnchant(Location location)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            double effectiveDistance = 24.0;
            if (player.getLocation().distance(location) <= effectiveDistance && playerHasEnchant(player)) return true;
        }
        return false;
    }

    @EventHandler
    public void onCreeperExplode(EntityExplodeEvent event)
    {
        Entity entity = event.getEntity();
        Location location = event.getLocation();
        if (entity instanceof Creeper && nearbyPlayerHasEnchant(location))
        {
            event.setCancelled(true);
        }
    }
}
