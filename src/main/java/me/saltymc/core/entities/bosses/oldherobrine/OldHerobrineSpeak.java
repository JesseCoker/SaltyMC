package me.saltymc.core.entities.bosses.oldherobrine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.ArrayList;

public class OldHerobrineSpeak
{
    private static final float MESSAGE_RANGE = 150.0f;

    private final Zombie herobrine;

    public OldHerobrineSpeak(Zombie herobrine)
    {
        this.herobrine = herobrine;
    }

    public void messageNearbyPlayers(TextComponent message)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (herobrine.getLocation().distance(player.getLocation()) < MESSAGE_RANGE)
            {
                player.sendMessage(message);
                herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1.0f, 0.5f);
            }
        }
    }

    public void sayMessage(int count)
    {
        ArrayList<TextComponent> messages = new ArrayList<>();
        messages.add(Component.text("You are coming with me to hell.").color(NamedTextColor.RED));
        messages.add(Component.text("No one will ever remember you.").color(NamedTextColor.RED));
        messages.add(Component.text("I will never leave you alone.").color(NamedTextColor.RED));
        messages.add(Component.text("May you regret your decisions.").color(NamedTextColor.RED));
        messages.add(Component.text("I will destroy this world!").color(NamedTextColor.RED));
        messages.add(Component.text("This is the end for you.").color(NamedTextColor.RED));
        messages.add(Component.text("You cannot stop me.").color(NamedTextColor.RED));
        messages.add(Component.text("There is no use trying.").color(NamedTextColor.RED));
        messages.add(Component.text("I will consume your soul for power.").color(NamedTextColor.RED));
        messages.add(Component.text("I will kill you again and again.").color(NamedTextColor.RED));
        messages.add(Component.text("Say goodbye to everything you love.").color(NamedTextColor.RED));
        messages.add(Component.text("Was it worth it?").color(NamedTextColor.RED));
        messages.add(Component.text("Everything that you have worked for, gone.").color(NamedTextColor.RED));
        messages.add(Component.text("And what could you possibly get out of it?").color(NamedTextColor.RED));
        messages.add(Component.text("I have nothing that you could possibly use.").color(NamedTextColor.RED));
        messages.add(Component.text("Was it just out of curiosity?").color(NamedTextColor.RED));
        messages.add(Component.text("Were you just trying to challenge yourself?").color(NamedTextColor.RED));
        messages.add(Component.text("Were you seeking ancient treasures?").color(NamedTextColor.RED));
        messages.add(Component.text("Your kind only deserves to suffer.").color(NamedTextColor.RED));
        messages.add(Component.text("You only cause harm to this world.").color(NamedTextColor.RED));
        messages.add(Component.text("You kill and take from us- from what once was.").color(NamedTextColor.RED));
        messages.add(Component.text("You've left nothing for me.").color(NamedTextColor.BLACK));
        messages.add(Component.text("You can't expect me to have mercy.").color(NamedTextColor.RED));
        messages.add(Component.text("THIS IS THE END FOR YOU!").color(NamedTextColor.DARK_RED));
        messages.add(Component.text("I HATE YOU!").color(NamedTextColor.RED));
        messages.add(Component.text("I HAAAATEEEEEE YOU!!!").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        messages.add(Component.text("ENOUGH!").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        messages.add(Component.text("I WILL NOT TOLERATE YOUR EXISTENCE ANY LONGER!").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        messages.add(Component.text("ENOUGH!!!!!!!!!").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        messages.add(Component.text("ENOUGH.").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("YOU MUST DIE!").color(NamedTextColor.BLACK).decorate(TextDecoration.OBFUSCATED));
        messages.add(Component.text("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").color(NamedTextColor.BLACK).decorate(TextDecoration.OBFUSCATED));
        messages.add(Component.text("YOU CANNOT SURVIVE!!!! IT IS IMPOSSIBLE!").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("YOU  ARE  DEAD  TO  ME").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("I WILL KILL YOUUU").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("you really are the worst").color(NamedTextColor.BLACK).decorate(TextDecoration.ITALIC));
        messages.add(Component.text("you.. you will suffer for eternity.........").color(NamedTextColor.DARK_RED).decorate(TextDecoration.ITALIC));
        messages.add(Component.text("ETERNITY!!!!!!!!!!!!!").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
        messages.add(Component.text("ARE YOU AFRAID?").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("I  CAN  SMELL  YOUR  FEAR").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("I").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("WILL").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("END").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("YOU").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("SUFFER!!!!!!").color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD));
        messages.add(Component.text("MUAHAHAHAHAHA").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("MUAHAHAHAHAHHAHAHAHAHAHA HAHAHA HAHAHA HAHAHA").color(NamedTextColor.BLACK).decorate(TextDecoration.BOLD));
        messages.add(Component.text("HAHAHA HAHAHA HAHAHA HAHAHA HAHAHA HAHAHA HAHAHA").color(NamedTextColor.BLACK).decorate(TextDecoration.ITALIC));
        messages.add(Component.text("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
        ).color(NamedTextColor.BLACK).decorate(TextDecoration.OBFUSCATED));

        if (count < messages.size())
        {
            messageNearbyPlayers(messages.get(count));
        }
    }
}
