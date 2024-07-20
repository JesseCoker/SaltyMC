package me.saltymc.core.potions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class VanillaPotionCreator
{
    public static ItemStack createPotion(Material material, PotionType potionType)
    {
        ItemStack potion = new ItemStack(material);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.setBasePotionType(potionType);
        potion.setItemMeta(potionMeta);
        return potion;
    }
}
