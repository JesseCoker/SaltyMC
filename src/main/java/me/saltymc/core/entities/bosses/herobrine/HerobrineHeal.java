package me.saltymc.core.entities.bosses.herobrine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class HerobrineHeal
{
    private final Herobrine herobrine;
    private final HerobrineSpeak herobrineSpeak;

    public HerobrineHeal(Herobrine herobrine, HerobrineSpeak herobrineSpeak)
    {
        this.herobrine = herobrine;
        this.herobrineSpeak = herobrineSpeak;
    }

    public void healOnKill(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        if (herobrine.getDistanceToPlayer(player) <= herobrine.getBossRange())
        {
            double health = herobrine.getEntity().getHealth();
            double maxHealth = herobrine.getMaxHealth();
            herobrine.getEntity().setHealth(Math.min(health + HerobrineSettings.Heal.HEAL_AMOUNT, maxHealth));
            herobrineSpeak.say(Component.text("I warned you.").color(NamedTextColor.BLACK));
        }
    }
}
