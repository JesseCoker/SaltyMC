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

public class HerobrineSword extends CustomItem
{
    public HerobrineSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.DIAMOND_SWORD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Herobrine Sword").color(NamedTextColor.GOLD);
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
        return 99;
    }

    @Override
    protected boolean doesDespawn()
    {
        return false;
    }

    @Override
    public String getItemId()
    {
        return "HEROBRINE_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        //itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemMeta swordMeta = itemStack.getItemMeta();
        swordMeta.setUnbreakable(true);
        itemStack.setItemMeta(swordMeta);
    }
}
