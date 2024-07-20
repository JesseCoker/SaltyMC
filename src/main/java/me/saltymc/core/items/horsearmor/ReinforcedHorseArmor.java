package me.saltymc.core.items.horsearmor;

import me.saltymc.core.Main;
import me.saltymc.core.items.AttributeHolder;
import me.saltymc.core.items.AttributeManager;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ReinforcedHorseArmor extends CustomItem
{
    public ReinforcedHorseArmor(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_HORSE_ARMOR;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Reinforced Horse Armor");
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Really strong horse armor.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
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
        return "REINFORCED_HORSE_ARMOR";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ARMOR, 20.0, null));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ARMOR_TOUGHNESS, 2.0, null));
        AttributeManager.setAttributes(itemStack, attributeHolder);
    }
}
