package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.entities.CustomBoss;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;

import java.util.ArrayList;

public class HerobrineSpeak
{
    private final CustomBoss customBoss;

    public HerobrineSpeak(CustomBoss customBoss)
    {
        this.customBoss = customBoss;
    }

    private int messageIndex = 0;
    private long lastSpeakTick = 0;

    private static final ArrayList<TextComponent> lines = new ArrayList<>();

    static
    {
        lines.add(Component.text("You have made a terrible mistake.").color(NamedTextColor.RED));
        lines.add(Component.text("Now you will come with me to hell.").color(NamedTextColor.RED));
        lines.add(Component.text("There is no use trying to survive.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        lines.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
    }

    public void nextSpeech()
    {
        if (messageIndex < lines.size() &&  customBoss.getTicksBetween(lastSpeakTick) >= HerobrineSettings.Speak.TICKS_BETWEEN_SPEECH)
        {
            say(lines.get(messageIndex));
            ++messageIndex;
            lastSpeakTick = customBoss.getTotalTicks();
        }
    }

    public void say(TextComponent textComponent)
    {
        customBoss.getNearbyPlayers().forEach(player -> {
            player.sendMessage(textComponent);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1.0f, 0.2f);
        });
    }
}
