package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.AttributeHolder;
import me.saltymc.core.items.AttributeManager;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class AncientSword extends CustomItem
{
    public AncientSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.NETHERITE_AXE;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Ancient Sword").color(TextColor.color(0, 153, 153));
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Legendary Item").color(NamedTextColor.GOLD));
        lore.add(Component.text("Curses foes so they can't").color(NamedTextColor.YELLOW));
        lore.add(Component.text("escape.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Summons a warden when held").color(NamedTextColor.YELLOW));
        lore.add(Component.text("upon its wielder's death.").color(NamedTextColor.YELLOW));
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
        return "ANCIENT_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        AttributeHolder attributeHolder = new AttributeHolder();
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_DAMAGE, 12.0, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_ATTACK_SPEED, 1.1, EquipmentSlot.HAND));
        attributeHolder.addAttribute(new AttributeHolder.AttributeData(Attribute.GENERIC_MOVEMENT_SPEED, 10.0, EquipmentSlot.HAND));
        AttributeManager.setAttributes(itemStack, attributeHolder);
    }

    private void curse(LivingEntity entity)
    {
        if (entity instanceof Player)
        {
            entity.sendMessage(Component.text("Cursed!").color(NamedTextColor.DARK_GRAY));
        }

        entity.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 200, 0));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 0));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 0));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 200, 0));

        entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_SCULK_SHRIEKER_SHRIEK, 1.0f, 1.0f);
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
            if (matchesItem(itemInMainHand))
            {
                curse(damageeLivingEntity);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand))
        {
            player.getWorld().spawnEntity(player.getLocation(), EntityType.WARDEN);
        }
    }
}