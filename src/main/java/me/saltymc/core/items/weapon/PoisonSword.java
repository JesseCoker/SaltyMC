package me.saltymc.core.items.weapon;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PoisonSword extends CustomItem
{
    public PoisonSword(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_SWORD;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Poison Sword").color(NamedTextColor.DARK_GREEN);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Inflicts poison.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 101;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "POISON_SWORD";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private void poisonEntity(LivingEntity livingEntity)
    {
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
    }

    @EventHandler
    public void onHitEntity(EntityDamageByEntityEvent event)
    {
        Entity damagee = event.getEntity();
        Entity damager = event.getDamager();
        if (damager instanceof Player && damagee instanceof LivingEntity)
        {
            Player player = (Player) damager;
            LivingEntity livingEntity = (LivingEntity) damagee;
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (matchesItem(itemInMainHand)) poisonEntity(livingEntity);
        }
    }
}
