package me.saltymc.core.commands;

import me.saltymc.core.Main;
import me.saltymc.core.items.weapon.DrownedTrident;
import me.saltymc.core.runnables.DrownedStorm;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StartStorm implements CommandExecutor
{
    private final Main plugin;

    public StartStorm(Main plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            World world = player.getWorld();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            DrownedTrident drownedTrident = new DrownedTrident(plugin);
            if (drownedTrident.matchesItem(itemInMainHand) && world.isClearWeather() && world.getEnvironment() == World.Environment.NORMAL)
            {
                DrownedStorm drownedStorm = new DrownedStorm(plugin, player.getWorld());
                drownedStorm.start();
                return true;
            }
        }
        commandSender.sendMessage(Component.text("You are not holding the Drowned Trident, it is already storming, or it is not a valid world.").color(NamedTextColor.RED));
        return false;
    }
}
