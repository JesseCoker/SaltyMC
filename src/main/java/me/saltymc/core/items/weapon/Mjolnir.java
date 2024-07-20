package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Mjolnir extends CustomItem
{

    public Mjolnir(Main plugin)
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
        return Component.text("Mjolnir").color(NamedTextColor.DARK_GRAY);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Can only be wielded by those").color(NamedTextColor.YELLOW));
        lore.add(Component.text("who are worthy (20K Mob Kills).").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Left click up to summon").color(NamedTextColor.YELLOW));
        lore.add(Component.text("lightning.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 105;
    }

    @Override
    protected boolean doesDespawn()
    {
        return false;
    }

    @Override
    public String getItemId()
    {
        return "MJOLNIR";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        itemStack.addEnchantment(Enchantment.LOYALTY, 3);
    }

    private static final int requiredMobKills = 20000;

    public boolean isNotWorthy(Entity entity)
    {
        if (entity instanceof Player)
        {
            Player player = (Player) entity;
            return player.getStatistic(Statistic.MOB_KILLS) < requiredMobKills;
        }
        return true;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event)
    {
        ItemStack itemStack = event.getItem().getItemStack();
        if (itemStack.getType() == getMaterial() && matchesItem(itemStack) && isNotWorthy(event.getEntity()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void inventoryInteract(InventoryClickEvent event)
    {
        ItemStack itemStack = event.getCurrentItem();
        HumanEntity humanEntity = event.getWhoClicked();
        if (matchesItem(itemStack) && humanEntity instanceof Player)
        {
            if (isNotWorthy(humanEntity)) event.setCancelled(true);
        }
    }

    private static final int maxStrikeDistance = 28;
    private static final int cooldownTimeInTicks = 40;

    private void strikeLightning(Player player)
    {
        Block block = player.getTargetBlock(null, maxStrikeDistance);
        player.getWorld().strikeLightning(block.getLocation());
        player.setCooldown(Material.TRIDENT, cooldownTimeInTicks);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (event.getAction() == Action.LEFT_CLICK_AIR && matchesItem(itemInMainHand) && player.getCooldown(Material.TRIDENT) == 0)
        {
            strikeLightning(player);
        }
    }
}
