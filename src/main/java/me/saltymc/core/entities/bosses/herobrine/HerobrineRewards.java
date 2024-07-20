package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.Main;
import me.saltymc.core.helpers.HeadGetter;
import me.saltymc.core.entities.CustomBossRewards;
import me.saltymc.core.enchants.enchants.UnbreakableEnchant;
import me.saltymc.core.items.book.HerobrineDiary;
import me.saltymc.core.items.food.FlightSoup;
import me.saltymc.core.items.item.RuneStone;
import me.saltymc.core.items.item.SpawnerCore;
import me.saltymc.core.items.item.SuperExperienceBottle;
import me.saltymc.core.items.item.SuperRocket;
import me.saltymc.core.items.tool.BedrockBreaker;
import me.saltymc.core.items.tool.SuperPickaxe;
import me.saltymc.core.items.weapon.HerobrineAxe;
import me.saltymc.core.items.weapon.HerobrineBow;
import me.saltymc.core.items.weapon.HerobrineSword;
import me.saltymc.core.items.weapon.KnockbackStick;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HerobrineRewards
{
    public static void setRewards(Main plugin, CustomBossRewards customBossRewards)
    {
        customBossRewards.setShulkerMaterial(Material.BLACK_SHULKER_BOX);
        customBossRewards.addReward(HeadGetter.getPlayerHead("Memerfront", Component.text("Herobrine's Head")));
        customBossRewards.addReward(new HerobrineSword(plugin).getItem(1));
        customBossRewards.addReward(new HerobrineAxe(plugin).getItem(1));
        customBossRewards.addReward(new HerobrineBow(plugin).getItem(1));
        customBossRewards.addReward(new SuperExperienceBottle(plugin).getItem(30));
        customBossRewards.addReward(new BedrockBreaker(plugin).getItem(6));
        customBossRewards.addReward(new RuneStone(plugin).getItem(1));
        customBossRewards.addReward(new SuperRocket(plugin).getItem(16));
        customBossRewards.addReward(new UnbreakableEnchant(plugin).getItem());
        customBossRewards.addReward(new SpawnerCore(plugin).getItem(1));
        customBossRewards.addReward(new HerobrineDiary(plugin).getItem(1));
        customBossRewards.addReward(new KnockbackStick(plugin).getItem(1));
        customBossRewards.addReward(new SuperPickaxe(plugin).getItem(1));
        customBossRewards.addReward(new FlightSoup(plugin).getItem(1));
        customBossRewards.addReward(new FlightSoup(plugin).getItem(1));
        customBossRewards.addReward(new ItemStack(Material.DIAMOND, 24));
        customBossRewards.addReward(new ItemStack(Material.OBSIDIAN, 64));
        customBossRewards.addReward(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        customBossRewards.addReward(new ItemStack(Material.ANCIENT_DEBRIS, 2));
        customBossRewards.addReward(new ItemStack(Material.NETHER_STAR, 3));
        customBossRewards.addReward(new ItemStack(Material.TOTEM_OF_UNDYING));
        customBossRewards.addReward(new ItemStack(Material.SHULKER_SHELL, 8));
        customBossRewards.addReward(new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        customBossRewards.addReward(new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2));
        customBossRewards.addReward(new ItemStack(Material.ZOMBIE_HORSE_SPAWN_EGG));
    }
}
