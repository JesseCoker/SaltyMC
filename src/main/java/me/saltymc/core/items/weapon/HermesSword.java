package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.AttributeManager;
import me.saltymc.core.items.AttributeHolder;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HermesSword extends CustomItem
{
    public HermesSword(Main plugin)
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
        return Component.text("Hermes Sword").color(NamedTextColor.GREEN);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Increased movement").color(NamedTextColor.YELLOW));
        lore.add(Component.text("and attack speed.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 100;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "HERMES_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_DAMAGE, 6.0, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_SPEED,1.8, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_MOVEMENT_SPEED, 40.0, EquipmentSlot.HAND));
        AttributeManager.setAttributes(itemStack, attributeHolder);
    }
}
