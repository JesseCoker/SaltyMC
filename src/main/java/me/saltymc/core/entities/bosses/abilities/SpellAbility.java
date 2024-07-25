package me.saltymc.core.entities.bosses.abilities;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.items.weapon.AncientSpell;
import org.bukkit.Location;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpellAbility extends BossAbility
{
    public SpellAbility(Main plugin, CustomBoss customBoss)
    {
        super(plugin, customBoss);
    }

    private static final double SPELL_VELOCITY = 2.0;

    @Override
    public void start()
    {
        customBoss.equipItemInOffHand(new AncientSpell(plugin).getItem(1));

        Player nearestPlayer = customBoss.getNearestPlayer();
        if (isInRange(nearestPlayer))
        {
            shootSpell(nearestPlayer.getLocation());
        }
    }

    private boolean isInRange(Player player)
    {
        return player != null && customBoss.getDistanceToPlayer(player) <= customBoss.getBossRange();
    }

    private void shootSpell(Location location)
    {
        Vector targetVector = location.toVector();
        Vector bossVector = customBoss.getEntity().getLocation().toVector();
        Vector spellVelocity = targetVector.subtract(bossVector).normalize().multiply(SPELL_VELOCITY);
        customBoss.getEntity().launchProjectile(DragonFireball.class, spellVelocity);
    }
}
