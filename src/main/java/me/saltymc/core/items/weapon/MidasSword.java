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

public class MidasSword extends CustomItem
{
    public MidasSword(Main plugin)
    {
        super(plugin);
    }

    private static final Random random = new Random();

    @Override
    protected Material getMaterial()
    {
        return Material.GOLDEN_SWORD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Midas Sword").color(NamedTextColor.GOLD);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Mobs drop gold when hit.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Increases luck when held.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Belongs to Sidero's brother, Midas.").color(NamedTextColor.YELLOW));
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
        return false;
    }

    @Override
    public String getItemId()
    {
        return "MIDAS_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_LUCK, 100.0, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_DAMAGE, 7.0, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_SPEED, 1.6, EquipmentSlot.HAND));
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
                ItemStack nuggets = new ItemStack(Material.GOLD_INGOT, ingotsToDrop);
                damagedEntity.getWorld().dropItemNaturally(damagedEntity.getLocation(), nuggets);
            }
        }
    }
}
