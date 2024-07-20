package me.saltymc.core.entities;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CustomBossBar
{
    private final LivingEntity bossEntity;
    private final int bossRange;
    private final BossBar bossBar;
    private final int maxHealth;

    public CustomBossBar(LivingEntity bossEntity, int maxHealth, int bossRange, TextComponent bossName, BossBar.Color barColor, BossBar.Overlay barOverlay)
    {
        this.bossEntity = bossEntity;
        this.bossRange = bossRange;
        this.maxHealth = maxHealth;

        bossBar = BossBar.bossBar(bossName, 1.0f, barColor, barOverlay);
    }

    public void update()
    {
        if (bossEntity != null && bossEntity.isValid())
        {
            bossBar.progress((float) (bossEntity.getHealth() / maxHealth));
            Bukkit.getOnlinePlayers().forEach(this::showBossBar);
        }
        else
        {
            Bukkit.getOnlinePlayers().forEach(this::hideBossBar);
        }
    }

    private void showBossBar(Player player)
    {
        if (bossEntity.getLocation().distance(player.getLocation()) < bossRange)
        {
            player.showBossBar(bossBar);
        }
    }

    private void hideBossBar(Player player)
    {
        player.hideBossBar(bossBar);
    }
}
