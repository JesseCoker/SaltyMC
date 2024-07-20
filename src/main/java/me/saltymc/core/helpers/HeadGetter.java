package me.saltymc.core.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadGetter
{
    public static ItemStack getPlayerHead(String playerName, TextComponent displayName)
    {
        ItemStack herobrineHead = new ItemStack(Material.PLAYER_HEAD);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        SkullMeta skullMeta = (SkullMeta) herobrineHead.getItemMeta();
        skullMeta.setOwningPlayer(offlinePlayer);
        skullMeta.displayName(Component.text(playerName + "'s Head"));
        skullMeta.displayName(displayName);
        herobrineHead.setItemMeta(skullMeta);
        return herobrineHead;
    }
}
