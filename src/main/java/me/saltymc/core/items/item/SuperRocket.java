package me.saltymc.core.items.item;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;

public class SuperRocket extends CustomItem
{
    public SuperRocket(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.FIREWORK_ROCKET;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Super Rocket").color(NamedTextColor.BLUE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
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
        return true;
    }

    @Override
    public String getItemId()
    {
        return "SUPER_ROCKET";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        FireworkMeta fireworkMeta = (FireworkMeta) itemStack.getItemMeta();
        fireworkMeta.setPower(100);
        itemStack.setItemMeta(fireworkMeta);
    }
}
