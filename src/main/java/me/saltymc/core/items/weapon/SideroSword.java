package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.AttributeHolder;
import me.saltymc.core.items.AttributeManager;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SideroSword extends CustomItem
{

    private static final Random random = new Random();

    public SideroSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_SWORD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Sidero Sword").color(NamedTextColor.GRAY);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Mobs drop iron when hit.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Belongs to Midas's brother, Sidero.").color(NamedTextColor.YELLOW));
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
        return "SIDERO_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_DAMAGE, 8.0, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_SPEED, 1.4, EquipmentSlot.HAND));
        AttributeManager.setAttributes(itemStack, attributeHolder);
    }

    @EventHandler
    public void onHitEntity(EntityDamageByEntityEvent event)
    {
        Entity damagedEntity = event.getEntity();
        Entity damagerEntity = event.getDamager();
        if (damagedEntity instanceof LivingEntity && ((LivingEntity)damagedEntity).hasAI() && damagerEntity instanceof Player)
        {
            Player player = (Player) damagerEntity;
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (matchesItem(itemInMainHand))
            {
                int maxIngots = 4;
                int ingotsToDrop = random.nextInt(maxIngots + 1);
                ItemStack ingots = new ItemStack(Material.IRON_INGOT, ingotsToDrop);
                damagedEntity.getWorld().dropItemNaturally(damagedEntity.getLocation(), ingots);
            }
        }
    }
}
