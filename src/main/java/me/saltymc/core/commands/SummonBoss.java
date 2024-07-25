package me.saltymc.core.commands;

import me.saltymc.core.Main;
import me.saltymc.core.entities.bosses.herobrine.Herobrine;
import me.saltymc.core.entities.bosses.nyx.Nyx;
import me.saltymc.core.entities.bosses.sidero.Sidero;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SummonBoss implements CommandExecutor
{
    private static Main plugin;

    public SummonBoss(Main plugin)
    {
        SummonBoss.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (commandSender instanceof Player && commandSender.isOp() && strings.length == 1)
        {
            Player player = (Player) commandSender;
            switch (strings[0])
            {
                case "HEROBRINE":
                    Herobrine herobrine = new Herobrine(plugin);
                    herobrine.summon(player.getLocation());
                    return true;
                case "SIDERO":
                    Sidero sidero = new Sidero(plugin);
                    sidero.summon(player.getLocation());
                    return true;
                case "NYX":
                    Nyx nyx = new Nyx(plugin);
                    nyx.summon(player.getLocation());
                    return true;
            }
        }
        return false;
    }
}
