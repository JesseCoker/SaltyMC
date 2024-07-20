package me.saltymc.core.items.item;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.List;

public class SuperExperienceBottle extends CustomItem
{
    public SuperExperienceBottle(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.EXPERIENCE_BOTTLE;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Super Experience Bottle").color(NamedTextColor.YELLOW);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Grants 100x more experience").color(NamedTextColor.YELLOW));
        lore.add(Component.text("than a normal bottle.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 98;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "SUPER_EXPERIENCE_BOTTLE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private NamespacedKey getSuperBottleKey()
    {
        return new NamespacedKey(plugin, "SUPER-EXP-BOTTLE");
    }

    @EventHandler
    public void onExperienceBottleThrow(ExpBottleEvent event)
    {
        ThrownExpBottle thrownExpBottle = event.getEntity();
        if (thrownExpBottle.getPersistentDataContainer().has(getSuperBottleKey()))
        {
            event.setExperience(event.getExperience() * 100);
        }
    }

    private void markExpBottleAsSuper(ThrownExpBottle bottle)
    {
        bottle.getPersistentDataContainer().set(getSuperBottleKey(), PersistentDataType.BOOLEAN, true);
    }

    @EventHandler
    public void onEvent(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();
        if (projectile instanceof ThrownExpBottle && shooter instanceof Player)
        {
            Player player = (Player) shooter;
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (matchesItem(itemInMainHand))
            {
                markExpBottleAsSuper((ThrownExpBottle) projectile);
            }
        }
    }
}
