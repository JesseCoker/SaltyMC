package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class EggSword extends CustomItem
{
    public EggSword(Main plugin)
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
        TextComponent part1 = Component.text("Egg ").color(NamedTextColor.GREEN);
        TextComponent part2 = Component.text("Sword").color(NamedTextColor.YELLOW);
        return part1.append(part2);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Reward for collecting every spawn egg.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Spawn eggs held in the off hand.").color(NamedTextColor.YELLOW));
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
        return false;
    }

    @Override
    public String getItemId()
    {
        return "EGG_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private EntityType getSpawnEggEntityType(ItemStack itemStack)
    {
        if (itemStack.getItemMeta() instanceof SpawnEggMeta)
        {
            String materialName = itemStack.getType().name();
            return EntityType.valueOf(materialName.replace("_SPAWN_EGG", ""));
        }
        return null;
    }

    private void useSpawnEgg(Player player, Block block)
    {
        Location spawnLocation = block.getLocation().add(new Vector(0, 1, 0));

        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        EntityType entityType = getSpawnEggEntityType(itemInOffHand);
        if (entityType != null)
        {
            player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, spawnLocation, 30, 0.2, 0.2, 0.2, 0.01);
            player.getWorld().playSound(spawnLocation, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            player.getWorld().spawnEntity(spawnLocation, entityType);
            itemInOffHand.setAmount(itemInOffHand.getAmount() - 1);
        }
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (event.getAction() == Action.LEFT_CLICK_AIR && matchesItem(itemInMainHand))
        {
            int maxRange = 50;
            Block clickedBlock = player.getTargetBlockExact(maxRange);
            if (clickedBlock != null && player.getLocation().distance(clickedBlock.getLocation()) <= maxRange)
            {
                useSpawnEgg(player, clickedBlock);
            }
        }
    }
}
