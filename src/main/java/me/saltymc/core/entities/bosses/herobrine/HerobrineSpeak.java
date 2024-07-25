package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.entities.CustomBoss;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
        // Boss dialogue
        lines.add(Component.text("You have made a terrible mistake.").color(NamedTextColor.RED));
        lines.add(Component.text("Now you will come with me to the nether.").color(NamedTextColor.RED));
        lines.add(Component.text("Your efforts are futile.").color(NamedTextColor.RED));
        lines.add(Component.text("Despair, for there is no hope.").color(NamedTextColor.RED));
        lines.add(Component.text("I will end this world.").color(NamedTextColor.RED));
        lines.add(Component.text("Say goodbye.").color(NamedTextColor.RED));
        lines.add(Component.text("I will consume the souls of every living being.").color(NamedTextColor.RED));
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
            player.sendMessage((Component.text("Herobrine: ").append(textComponent)).decorate(TextDecoration.BOLD));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 1.0f, 0.2f);
        });
    }
}
