package me.saltymc.core.items.item;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class RuneStone extends CustomItem
{
    private static final Random random = new Random();

    public RuneStone(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.NETHER_STAR;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Rune Stone").color(NamedTextColor.WHITE).decorate(TextDecoration.OBFUSCATED).decorate(TextDecoration.BOLD);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Combining this with an item").color(NamedTextColor.YELLOW));
        lore.add(Component.text("in an anvil upgrades a random").color(NamedTextColor.YELLOW));
        lore.add(Component.text("enchantment.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Costs 50 levels to use.").color(NamedTextColor.YELLOW));
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
        return "RUNE_STONE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        if (!(entity instanceof Player) && killer != null)
        {
            handleDrop(entity, killer);
        }
    }

    private final String runeKey = "runestone-level";

    private boolean hasMaxRunes(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, runeKey);
        Integer runeCount = pdc.get(namespacedKey, PersistentDataType.INTEGER);
        int maxRunes = 3;
        return runeCount != null && runeCount >= maxRunes;
    }

    private void upgradeRandomEnchantment(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Set<Map.Entry<Enchantment, Integer>> entrySet = itemMeta.getEnchants().entrySet();

        List<Enchantment> validEnchantments = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : entrySet)
        {
            if (entry.getKey().getMaxLevel() != 1)
            {
                validEnchantments.add(entry.getKey());
            }
        }

        if (!validEnchantments.isEmpty())
        {
            int randomEnchantment = random.nextInt(validEnchantments.size());

            for (Map.Entry<Enchantment, Integer> entry : entrySet)
            {
                Enchantment enchantment = entry.getKey();
                if (enchantment == validEnchantments.get(randomEnchantment))
                {
                    int enchantmentLevel = entry.getValue();
                    itemMeta.removeEnchant(enchantment);
                    itemMeta.addEnchant(enchantment, enchantmentLevel + 1, true);
                    break;
                }
            }
        }

        itemStack.setItemMeta(itemMeta);
    }

    private void updateRuneCount(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, runeKey);
        Integer runeCount = pdc.get(namespacedKey, PersistentDataType.INTEGER);
        if (runeCount == null) runeCount = 0;
        pdc.set(namespacedKey, PersistentDataType.INTEGER, runeCount + 1);

        itemStack.setItemMeta(itemMeta);
    }

    private final String loreText = "Runes: ";

    private String getNewRuneLore(ItemStack itemStack)
    {
        char runeCharacter = '⬟';

        ItemMeta itemMeta = itemStack.getItemMeta();

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, runeKey);
        Integer runeCount = pdc.get(namespacedKey, PersistentDataType.INTEGER);

        if (runeCount == null) runeCount = 1;

        StringBuilder newLoreText = new StringBuilder(loreText);
        for (int index = 0; index < runeCount; ++index)
        {
            newLoreText.append(runeCharacter);
        }

        return newLoreText.toString();
    }

    private void updateRuneLore(ItemStack itemStack)
    {
        String newLoreText = getNewRuneLore(itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();

        List<Component> itemLore = itemMeta.lore();

        if (itemLore == null) itemLore = new ArrayList<>();

        boolean updatedLore = false;
        for (int index = 0; index < itemLore.size(); ++index)
        {
            TextComponent textComponent = (TextComponent) itemLore.get(index);
            String content = textComponent.content();
            if (content.startsWith(loreText))
            {
                itemLore.set(index,Component.text(newLoreText));
                updatedLore = true;
                break;
            }
        }

        if (!updatedLore)
        {
            itemLore.add(Component.text(newLoreText));
        }

        itemMeta.lore(itemLore);
        itemStack.setItemMeta(itemMeta);
    }

    private ItemStack applyRune(ItemStack itemStack)
    {
        ItemStack result = new ItemStack(itemStack);

        upgradeRandomEnchantment(result);
        updateRuneCount(result);
        updateRuneLore(result);

        return result;
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event)
    {
        AnvilInventory anvilInventory = event.getInventory();
        ItemStack firstItem = anvilInventory.getFirstItem();
        ItemStack secondItem = anvilInventory.getSecondItem();

        if (firstItem != null && secondItem != null && matchesItem(secondItem) && secondItem.getAmount() == 1 && !hasMaxRunes(firstItem))
        {
            anvilInventory.setMaximumRepairCost(50);
            anvilInventory.setRepairCost(50);
            ItemStack result = applyRune(firstItem);
            event.setResult(result);
        }
    }

    public void handleDrop(LivingEntity entity, Player killer)
    {
        int dropChance = entity.fromMobSpawner() ? 12000 : 1000;
        if (random.nextInt(dropChance) == 0)
        {
            RuneStone runeStone = new RuneStone(plugin);
            ItemStack itemStack = runeStone.getItem(1);
            entity.getWorld().dropItemNaturally(entity.getLocation(), itemStack);
            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0f, 1.0f);
            killer.sendMessage(Component.text("An immensely rare relic has dropped!").color(NamedTextColor.LIGHT_PURPLE));
            killer.sendMessage(Component.text("Your feel your soul fill with darkness...").color(NamedTextColor.DARK_GRAY));
            killer.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 180, 5));
        }
    }
}
