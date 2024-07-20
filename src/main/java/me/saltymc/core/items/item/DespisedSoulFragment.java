package me.saltymc.core.items.item;

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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DespisedSoulFragment extends CustomItem
{
    public DespisedSoulFragment(Main plugin)
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
        return Component.text("Despised Soul Fragment").color(NamedTextColor.GRAY);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("A small part of Herobrine's soul.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Can be purified by a an individual").color(NamedTextColor.YELLOW));
        lore.add(Component.text("with a pure soul.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Requires a whole stack to purify.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 103;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "DESPISED_SOUL_FRAGMENT";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private boolean isPureSoul(Player player)
    {
        ArrayList<String> pureSouls = new ArrayList<>();
        pureSouls.add(".Laufeal");
        pureSouls.add(".ClassifiedFiles");
        pureSouls.add("PurpleLemonSasha");
        pureSouls.add("Food2411");
        return pureSouls.contains(player.getName());
    }

    private void purifySoul(Player player, ItemStack despisedSoul)
    {
        if (!isPureSoul(player))
        {
            player.sendMessage(Component.text("Your soul is not pure. Find someone who is kind and peaceful.").color(NamedTextColor.RED));
            return;
        }

        if (despisedSoul.getAmount() == 64)
        {
            despisedSoul.setAmount(0);
            player.getInventory().addItem(new PureSoul(plugin).getItem(1));
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 1.0f, 1.0f);
            player.sendMessage(Component.text("You heal his soul with your pure heart."));
        }
        else
        {
            player.sendMessage(Component.text("Requires a whole stack of fragments to purify.").color(NamedTextColor.RED));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand) && event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
            purifySoul(player, itemInMainHand);
        }
    }
}
