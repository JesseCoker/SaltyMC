package me.saltymc.core.commands;

import me.saltymc.core.items.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveCustomItemCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (commandSender.isOp() && strings.length == 2)
        {
            String playerName = strings[0];
            String itemName = strings[1];
            Player player = getPlayerByName(playerName);
            CustomItem customItem = getItemByName(itemName);
            if (player != null && customItem != null)
            {
                player.getInventory().addItem(customItem.getItem(1));
                return true;
            }
        }
        return false;
    }

    private Player getPlayerByName(String name)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getName().equals(name))
            {
                return player;
            }
        }
        return null;
    }

    private CustomItem getItemByName(String name)
    {
        for (CustomItem customItem : CustomItem.registeredItems)
        {
            if (customItem.getItemId().equals(name))
            {
                return customItem;
            }
        }
        return null;
    }
}
