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

public class EterinSword extends CustomItem
{
    public EterinSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    public Material getMaterial()
    {
        return Material.NETHERITE_SWORD;
    }

    @Override
    public TextComponent getDisplayName()
    {
        return Component.text("Eterin's Sword").color(NamedTextColor.DARK_BLUE);
    }

    @Override
    public List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Reduced Attack Cooldown").color(NamedTextColor.YELLOW));
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
        return false;
    }

    @Override
    public String getItemId()
    {
        return "ETERIN_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }
}
