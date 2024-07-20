package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class HerobrineBow extends CustomItem
{
    public HerobrineBow(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.BOW;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Herobrine Bow").color(NamedTextColor.GOLD);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Unbreakable").color(NamedTextColor.GRAY));
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
        return "HEROBRINE_BOW";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        //itemStack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
        ItemMeta bowMeta = itemStack.getItemMeta();
        bowMeta.setUnbreakable(true);
        itemStack.setItemMeta(bowMeta);
    }
}
