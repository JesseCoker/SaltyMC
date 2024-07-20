package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.ProjectileSource;

import java.util.ArrayList;
import java.util.List;

public class AncientSpell extends CustomItem
{
    public AncientSpell(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.BOOK;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Ancient Spell").color(TextColor.color(41, 142, 153));
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("A spell from a strange dimension.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Shoots a poisonous fireball.").color(NamedTextColor.YELLOW));
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
        return "ANCIENT_SPELL";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        itemStack.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        Block clickedBlock = player.getTargetBlock(null, 30);
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        int minimumClickDistance = 6;
        if (matchesItem(itemInMainHand) && player.getCooldown(Material.BOOK) == 0 &&
                player.getLocation().distance(clickedBlock.getLocation()) > minimumClickDistance &&
                (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 1.0f, 1.0f);
            player.launchProjectile(DragonFireball.class);
            player.setCooldown(Material.BOOK, 30);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        Projectile projectile = event.getEntity();
        ProjectileSource projectileSource = event.getEntity().getShooter();
        if (projectileSource instanceof Player && projectile instanceof DragonFireball)
        {
            Player player = (Player) projectileSource;
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void breakBlockEvent(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand)) event.setCancelled(true);
    }
}
