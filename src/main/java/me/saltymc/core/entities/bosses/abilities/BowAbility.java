package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class BowAbility extends BossAbility {

    private final ItemStack bow;
    private final float velocity;

    public BowAbility(Main plugin, CustomBoss customBoss, ItemStack bow, float velocity)
    {
        super(plugin, customBoss);
        this.bow = bow;
        this.velocity = velocity;
    }

    @Override
    public void start()
    {
        customBoss.equipItemInMainHand(bow);
        shootAtNearestPlayer();
    }

    public void shootAtNearestPlayer()
    {
        LivingEntity bossEntity = customBoss.getEntity();
        Player nearestPlayer = customBoss.getNearestPlayer();
        if (nearestPlayer != null && bossEntity.hasLineOfSight(nearestPlayer))
        {
            Vector playerVector = nearestPlayer.getLocation().toVector();
            Vector bossVector = bossEntity.getLocation().toVector();
            Vector projectileVector = playerVector.subtract(bossVector).normalize().multiply(velocity);
            bossEntity.launchProjectile(Arrow.class, projectileVector);
        }
    }
}
