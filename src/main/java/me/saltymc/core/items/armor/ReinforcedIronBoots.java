package me.saltymc.core.items.armor;

import me.saltymc.core.Main;
import me.saltymc.core.items.AttributeHolder;
import me.saltymc.core.items.AttributeManager;
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

public class ReinforcedIronBoots extends CustomItem
{
    public ReinforcedIronBoots(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_BOOTS;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Reinforced Iron Boots");
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Increased armor and toughness.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Better than diamond.").color(NamedTextColor.YELLOW));
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
        return "REINFORCED_IRON_BOOTS";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ARMOR, 3.0, EquipmentSlot.FEET));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ARMOR_TOUGHNESS, 3.0, EquipmentSlot.FEET));
        AttributeManager.setAttributes(itemStack, attributeHolder);
    }
}
