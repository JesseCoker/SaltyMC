package me.saltymc.core.items.item;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PureSoul extends CustomItem
{
    public PureSoul(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.GOLD_NUGGET;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Pure Soul").color(NamedTextColor.WHITE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Crafting Ingredient").color(NamedTextColor.LIGHT_PURPLE));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 104;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "PURE_SOUL";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }
}
