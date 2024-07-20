package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EmeraldSword extends CustomItem
{
    public EmeraldSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    public Material getMaterial()
    {
        return Material.IRON_SWORD;
    }

    @Override
    public TextComponent getDisplayName()
    {
        return Component.text("Emerald Sword").color(NamedTextColor.GREEN);
    }

    @Override
    public List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Special Item").color(NamedTextColor.WHITE));
        return lore;
    }

    @Override
    public int getModelId()
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
        return "EMERALD_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }
}
