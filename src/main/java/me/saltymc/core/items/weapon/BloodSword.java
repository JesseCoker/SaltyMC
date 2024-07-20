package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BloodSword extends CustomItem
{
    public BloodSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.DIAMOND_SWORD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Blood Sword").color(NamedTextColor.RED);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Steals health from others.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 101;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "BLOOD_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private void stealHealth(Player player, double damage)
    {
        double currentHealth = player.getHealth();
        double newHealth = currentHealth + damage / 4.0;
        AttributeInstance attributeInstance = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (attributeInstance != null)
        {
            double maxHealth = attributeInstance.getBaseValue();
            player.setHealth(Math.min(newHealth, maxHealth));
        }
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event)
    {
        Entity damager = event.getDamager();

        if (damager instanceof Player)
        {
            Player player = (Player) damager;
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (matchesItem(itemInMainHand)) stealHealth(player, event.getDamage());
        }
    }
}
