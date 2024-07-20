package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FlySword extends CustomItem
{
    public FlySword(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.NETHERITE_SWORD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Sword of Flight").color(NamedTextColor.AQUA);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Belongs to PurpleLemonSasha.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Allows flight when held.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Flight is lost upon taking damage.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 102;
    }

    @Override
    protected boolean doesDespawn()
    {
        return false;
    }

    @Override
    public String getItemId()
    {
        return "FLY_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private void enableFlight(Player player)
    {
        player.setCooldown(Material.NETHERITE_SWORD, 300);
        player.setAllowFlight(true);
        player.sendMessage(Component.text("Flight enabled.").color(NamedTextColor.GREEN));
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1.0f, 1.0f);
    }

    private void disableFlight(Player player)
    {
        player.setAllowFlight(false);
        player.sendMessage(Component.text("Flight disabled.").color(NamedTextColor.RED));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 1.0f, 1.0f);
    }

    private boolean isOnCooldown(Player player)
    {
        return player.getCooldown(Material.NETHERITE_SWORD) > 0;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand) && event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            if (player.getAllowFlight())
            {
                disableFlight(player);
            }
            else if (!isOnCooldown(player))
            {
                enableFlight(player);
            }
            else
            {
                player.sendMessage(Component.text("On cooldown!").color(NamedTextColor.RED));
            }
        }
    }
}
