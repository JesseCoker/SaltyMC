package me.saltymc.core.items.food;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class GoldenHead extends CustomItem
{
    public GoldenHead(Main plugin)
    {
        super(plugin);
    }

    @Override
    public Material getMaterial()
    {
        return Material.PLAYER_HEAD;
    }

    @Override
    public TextComponent getDisplayName()
    {
        return Component.text("Golden Head").color(NamedTextColor.GOLD);
    }

    @Override
    public List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Applies potion effects (10s):"));
        lore.add(Component.text("Absorption"));
        lore.add(Component.text("Speed I"));
        lore.add(Component.text("Regen I"));
        return lore;
    }

    @Override
    public int getModelId()
    {
        return 0;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "GOLDEN_HEAD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer("TheGoldenWarrier");
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(offlinePlayer);
        itemStack.setItemMeta(skullMeta);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && matchesItem(itemInMainHand))
        {
            itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10 * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 0));
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlaceHead(BlockPlaceEvent event)
    {
        ItemStack itemStack = event.getItemInHand();
        if (matchesItem(itemStack))
        {
            event.setCancelled(true);
        }
    }
}
