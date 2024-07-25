package me.saltymc.core.entities.bosses.herobrine;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.Random;

public class HerobrineBlockAttack
{
    private final Herobrine herobrine;

    private static final Random random = new Random();

    public HerobrineBlockAttack(Herobrine herobrine)
    {
        this.herobrine = herobrine;
    }

    private boolean isValidAttack(EntityDamageByEntityEvent event)
    {
        EntityDamageEvent.DamageCause damageCause = event.getCause();
        if (damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK) return true;
        if (damageCause == EntityDamageEvent.DamageCause.KILL) return true;
        if (damageCause == EntityDamageEvent.DamageCause.PROJECTILE)
        {
            Projectile projectile = (Projectile) event.getDamager();
            ProjectileSource projectileSource = projectile.getShooter();
            return projectileSource instanceof Player;
        }
        return false;
    }

    private boolean isValidDamage(EntityDamageEvent event)
    {
        EntityDamageEvent.DamageCause damageCause = event.getCause();
        return damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK
                || damageCause == EntityDamageEvent.DamageCause.PROJECTILE;
    }

    public void cancelInvalidDamage(EntityDamageEvent event)
    {
        if (!isValidDamage(event)) event.setCancelled(true);
    }

    public void cancelInvalidDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!isValidAttack(event)) event.setCancelled(true);
    }

    public void blockValidAttacks(EntityDamageByEntityEvent event)
    {
        int blockChance = random.nextInt(HerobrineSettings.Block.BLOCK_ATTACK_CHANCE);
        if (isValidAttack(event) && blockChance == 0)
        {
            event.setCancelled(true);
            int blockMethod = random.nextInt(3);
            if (blockMethod == 0) teleport();
            else if (blockMethod == 1) useShield();
            else sideHop();
        }
    }

    private void teleport()
    {
        herobrine.getHerobrineTeleport().randomTeleport();
    }

    private void useShield()
    {
        herobrine.equipItemInOffHand(new ItemStack(Material.SHIELD));
        herobrine.playSound(herobrine.getEntity().getLocation(), Sound.ITEM_SHIELD_BLOCK);
    }

    private void sideHop()
    {
        Vector herobrineVector = herobrine.getEntity().getLocation().toVector().setY(0).normalize();
        Vector perpendicularVector = new Vector(herobrineVector.getZ(), 0, herobrineVector.getX());
        perpendicularVector.multiply(HerobrineSettings.Block.SIDE_HOP_HORIZONTAL_VELOCITY);
        perpendicularVector.setY(HerobrineSettings.Block.SIDE_HOP_VERTICAL_VELOCITY);
        herobrine.getEntity().setVelocity(perpendicularVector);
    }
}
