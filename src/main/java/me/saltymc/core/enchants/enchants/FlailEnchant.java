package me.saltymc.core.enchants.enchants;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.CustomEnchant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FlailEnchant extends CustomEnchant
{
    public FlailEnchant(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Flail").color(TextColor.color(23, 156, 127));
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Damages a mob when hit by your").color(NamedTextColor.YELLOW));
        lore.add(Component.text("fishing hook.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    public String getItemId()
    {
        return "FLAIL_ENCHANTMENT";
    }

    @Override
    protected ItemStack applyEnchantment(ItemStack itemStack)
    {
        return null;
    }
}
