package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EnderSword extends CustomItem
{
    static final int requiredLevel = 100;
    static final int unactivatedId = 99;
    static final int activatedId = 100;

    public EnderSword(Main plugin)
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
        return Component.text("Ender Sword").color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Banishes a soul to the End when activated.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Activated when its wielder has 100 levels.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Consumes 100 levels.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 99;
    }

    @Override
    protected boolean doesDespawn()
    {
        return false;
    }

    @Override
    public String getItemId()
    {
        return "ENDER_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "activated"), PersistentDataType.BOOLEAN, false);
        itemStack.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent event)
    {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (entity instanceof Player && damager instanceof Player)
        {
            Player damagedPlayer = (Player) entity;
            Player damagerPlayer = (Player) damager;
            ItemStack weapon = damagerPlayer.getInventory().getItemInMainHand();
            int level = damagerPlayer.getLevel();
            if (matchesItem(weapon) && level >= requiredLevel && (damagedPlayer.getHealth() - event.getDamage()) <= 0)
            {
                event.setCancelled(true);
                damagerPlayer.setLevel(level - requiredLevel);
                banish(damagerPlayer, damagedPlayer);
                updateWeaponActivation(damagerPlayer, weapon);
            }
        }
    }

    private void banish(Player killer, Player player)
    {
        World the_end = Bukkit.getServer().getWorld("world_the_end");
        if (the_end != null)
        {
            killer.sendMessage(Component.text("You have banished " + player.getName() + " to The End.").color(NamedTextColor.RED));
            player.getWorld().strikeLightning(player.getLocation());
            player.teleport(new Location(the_end, 5392.0, 66.0, 3208.0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 12000, 1));
            player.sendMessage(Component.text("You have been banished to the End with the Ender Sword.").color(NamedTextColor.RED));
        }
    }

    @EventHandler
    public void onExperienceChange(PlayerExpChangeEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand))
        {
            updateWeaponActivation(player, itemInMainHand);
        }
    }

    @EventHandler
    public void onSlotChanged(PlayerItemHeldEvent event)
    {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();
        ItemStack newItem = inventory.getItem(event.getNewSlot());
        ItemStack oldItem = inventory.getItem(event.getPreviousSlot());
        if (newItem != null && matchesItem(newItem))
        {
            updateWeaponActivation(player, newItem);
        } else if (oldItem != null && matchesItem(oldItem))
        {
            deactivateWeapon(oldItem);
        }
    }

    private void activateWeapon(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(activatedId);
        itemStack.setItemMeta(itemMeta);
    }

    private void deactivateWeapon(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(unactivatedId);
        itemStack.setItemMeta(itemMeta);
    }

    private void updateWeaponActivation(Player player, ItemStack weapon)
    {
        if (player.getLevel() >= 100)
        {
            activateWeapon(weapon);
        }
        else {
            deactivateWeapon(weapon);
        }
    }
}
