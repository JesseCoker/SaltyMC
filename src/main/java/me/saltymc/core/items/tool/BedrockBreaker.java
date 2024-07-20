package me.saltymc.core.items.tool;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BedrockBreaker extends CustomItem
{
    public BedrockBreaker(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_INGOT;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Bedrock Breaker").color(NamedTextColor.DARK_GRAY);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Can break bedrock.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Requires a strong will to use.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Don't get too close.").color(NamedTextColor.YELLOW));
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
        return "BEDROCK_BREAKER";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private void dealKB(Player player, Block block)
    {
        Vector playerPosition = player.getLocation().toVector();
        Vector blockPosition = block.getLocation().toVector();
        if (playerPosition.distance(blockPosition) < 3.0)
        {
            Vector knockbackVector = playerPosition.subtract(blockPosition).normalize().multiply(4);
            Vector upVector = new Vector(0, 1.4, 0);
            player.setVelocity(knockbackVector.add(upVector));
        }
    }

    private void breakBedrock(Player player, Block block, ItemStack bedrockBreaker)
    {
        if (block != null && block.getType() == Material.BEDROCK)
        {
            bedrockBreaker.setAmount(bedrockBreaker.getAmount() - 1);
            block.setType(Material.AIR);
            block.getWorld().spawnParticle(Particle.DRAGON_BREATH, block.getLocation(), 120);
            block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0f, 1.0f);
            block.getWorld().playSound(block.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            player.damage(16.0f);
            dealKB(player, block);
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand) && (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK))
        {
            breakBedrock(player, event.getClickedBlock(), itemInMainHand);
        }
    }
}
