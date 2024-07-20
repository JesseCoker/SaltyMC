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

import java.util.ArrayList;
import java.util.List;

public class SideroShield extends CustomItem
{
    public SideroShield(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.SHIELD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Sidero Shield").color(NamedTextColor.WHITE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Increases its holder's max").color(NamedTextColor.YELLOW));
        lore.add(Component.text("health when equipped.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
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
        return "SIDERO_SHIELD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_MAX_HEALTH, 6.0, EquipmentSlot.OFF_HAND));
        AttributeManager.setAttributes(itemStack, attributeHolder);
    }
}
