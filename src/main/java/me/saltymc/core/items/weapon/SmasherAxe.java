package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SmasherAxe extends CustomItem
{
    public SmasherAxe(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_AXE;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("SMASHer Axe").color(NamedTextColor.GOLD);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Does a SMASH attack that deals").color(NamedTextColor.YELLOW));
        lore.add(Component.text("crazy amounts of knockback and").color(NamedTextColor.YELLOW));
        lore.add(Component.text("shreds through armor and shields").color(NamedTextColor.YELLOW));
        lore.add(Component.text("when fully charged.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("+2 SMASH Damage (Penetration)").color(NamedTextColor.GREEN));
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
        return "SMASHER_AXE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private void dealKB(Player damager, LivingEntity damagee)
    {
        Vector damagerPosition = damager.getLocation().toVector();
        Vector damageePosition = damagee.getLocation().toVector();
        Vector knockbackVector = damageePosition.subtract(damagerPosition).normalize().multiply(4);
        Vector upVector = new Vector(0, 1.0, 0);
        damagee.setVelocity(knockbackVector.add(upVector));
    }

    private void smash(Player damager, LivingEntity damagee)
    {
        damagee.damage(2.0);
        damagee.damageItemStack(EquipmentSlot.HEAD, 25);
        damagee.damageItemStack(EquipmentSlot.CHEST, 25);
        damagee.damageItemStack(EquipmentSlot.LEGS, 25);
        damagee.damageItemStack(EquipmentSlot.FEET, 25);

        if (damagee instanceof Player)
        {
            Player damageePlayer = (Player) damagee;
            ItemStack itemInMainHand = damageePlayer.getInventory().getItemInMainHand();
            ItemStack itemInOffHand = damageePlayer.getInventory().getItemInOffHand();
            if (itemInMainHand.getType() == Material.SHIELD) damagee.damageItemStack(EquipmentSlot.HAND, 35);
            if (itemInOffHand.getType() == Material.SHIELD) damagee.damageItemStack(EquipmentSlot.OFF_HAND, 35);
        }

        dealKB(damager, damagee);

        damagee.getWorld().spawnParticle(Particle.CRIT, damagee.getLocation(), 120);
        damagee.getWorld().playSound(damagee.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
    }

    @EventHandler
    public void onHitEntity(EntityDamageByEntityEvent event)
    {
        Entity damagee = event.getEntity();
        Entity damager = event.getDamager();
        if (damager instanceof Player && damagee instanceof LivingEntity)
        {
            Player player = (Player) damager;
            LivingEntity damageeLivingEntity = (LivingEntity) damagee;
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (matchesItem(itemInMainHand) && player.getAttackCooldown() >= 0.9f)
            {
                smash(player, damageeLivingEntity);
            }
        }
    }
}
