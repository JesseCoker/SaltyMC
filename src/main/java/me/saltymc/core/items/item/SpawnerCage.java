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

public class SpawnerCage extends CustomItem
{
    public SpawnerCage(Main plugin)
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
        return Component.text("Spawner Cage").color(NamedTextColor.GRAY);
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
        return "SPAWNER_CAGE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }
}
