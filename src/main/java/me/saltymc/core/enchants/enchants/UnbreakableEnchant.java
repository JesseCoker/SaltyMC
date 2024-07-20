package me.saltymc.core.enchants.enchants;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.CustomEnchant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UnbreakableEnchant extends CustomEnchant
{

    public UnbreakableEnchant(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Unbreakable").color(NamedTextColor.LIGHT_PURPLE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Makes an item fully unbreakable.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    public String getItemId()
    {
        return "UNBREAKABLE_ENCHANTMENT";
    }

    @Override
    protected ItemStack applyEnchantment(ItemStack itemStack)
    {
        ItemStack result = new ItemStack(itemStack);
        ItemMeta itemMeta = result.getItemMeta();
        itemMeta.setUnbreakable(true);
        result.setItemMeta(itemMeta);
        return result;
    }
}