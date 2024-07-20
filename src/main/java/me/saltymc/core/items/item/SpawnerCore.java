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

public class SpawnerCore extends CustomItem
{
    public SpawnerCore(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_INGOT;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Spawner Core").color(NamedTextColor.DARK_BLUE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Crafting Ingredient").color(NamedTextColor.LIGHT_PURPLE));
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
        return true;
    }

    @Override
    public String getItemId()
    {
        return "SPAWNER_CORE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }
}
