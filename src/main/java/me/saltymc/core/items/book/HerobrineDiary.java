package me.saltymc.core.items.book;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class HerobrineDiary extends CustomItem
{
    public HerobrineDiary(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.WRITTEN_BOOK;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Herobrine's Diary").color(NamedTextColor.GOLD);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Contains secrets.").color(NamedTextColor.YELLOW));
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
        return true;
    }

    @Override
    public String getItemId()
    {
        return "HEROBRINE_DIARY";
    }

    private static final ArrayList<TextComponent> pages = new ArrayList<>();

    static
    {
        TextComponent page1 = Component.text("My name is Herobrine. I'm starting this diary as a way to express my anger. " +
                        "It was a horrible day. The sky was sunny, the cows were mooing, the sheep were baaing... " +
                        "That was the day Dad and I finally started harvesting the potatoes.");

        pages.add(page1);

        TextComponent page2 = Component.text("I miss those potatoes... Anyways, some dumb idiot named \"devocalized\" came up to me and was like " +
                "\"hey kid wanna see something cool?\" And I was like OK. Then he started stealing our potatoes! Like what??!!");

        pages.add(page2);

        TextComponent page3 = Component.text("I was so excited for those potatoes. " +
                "But then he did something... that I will never.. ever.. ever forgive him for........");

        pages.add(page3);

        TextComponent page4 = Component.text("HE KILLED SIR OINKS-A-LOT! My pooooor friend! How could someone be so evil?" +
                " Then he goes and wipes out my entire village to harvest our eggs. It was just me and my brother left in that village. " +
                "We were just kids, all alone.");

        pages.add(page4);

        TextComponent page5 = Component.text( "I knew at that point that I needed to seek revenge. FOR SIR OINKS-A-LOT!");

        pages.add(page5);
    }

    private void addPages(BookMeta bookMeta)
    {
        for (TextComponent page : pages)
        {
            bookMeta.addPages(page);
        }
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {
        BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
        bookMeta.setTitle("Herobrine's Diary");
        bookMeta.setAuthor("Herobrine");
        addPages(bookMeta);
        itemStack.setItemMeta(bookMeta);
    }
}
