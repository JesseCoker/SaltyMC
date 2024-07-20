package me.saltymc.core;

import me.saltymc.core.entities.bosses.herobrine.HerobrineSummon;
import me.saltymc.core.entities.bosses.sidero.SideroSummon;
import me.saltymc.core.commands.*;
import me.saltymc.core.enchants.*;
import me.saltymc.core.enchants.enchants.*;
import me.saltymc.core.items.CustomItem;
import me.saltymc.core.items.armor.ReinforcedIronBoots;
import me.saltymc.core.items.armor.ReinforcedIronChestplate;
import me.saltymc.core.items.armor.ReinforcedIronHelmet;
import me.saltymc.core.items.armor.ReinforcedIronLeggings;
import me.saltymc.core.items.book.HerobrineDiary;
import me.saltymc.core.items.food.FlightSoup;
import me.saltymc.core.items.food.GoldenHead;
import me.saltymc.core.items.horsearmor.ReinforcedHorseArmor;
import me.saltymc.core.items.item.*;
import me.saltymc.core.items.tool.BedrockBreaker;
import me.saltymc.core.items.tool.SuperPickaxe;
import me.saltymc.core.items.weapon.*;
import me.saltymc.core.loot.CustomLoot;
import me.saltymc.core.loot.ShulkerSpell;
import me.saltymc.core.recipes.CustomRecipe;
import me.saltymc.core.recipes.crafting.*;
import me.saltymc.core.recipes.furnace.CobbledDeepslateFurnaceRecipe;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginManager;

public class Register
{
    public static void registerEvents(Main plugin)
    {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new HerobrineSummon(plugin), plugin);
        pluginManager.registerEvents(new UnsafeEnchanting(plugin), plugin);
        pluginManager.registerEvents(new SideroSummon(plugin), plugin);
        pluginManager.registerEvents(new CustomLoot(plugin), plugin);
        pluginManager.registerEvents(new ShulkerSpell(plugin), plugin);
    }

    public static void registerItems(Main plugin)
    {
        CustomItem.register(new GoldenHead(plugin));
        CustomItem.register(new MidasSword(plugin));
        CustomItem.register(new SideroSword(plugin));
        CustomItem.register(new EmeraldSword(plugin));
        CustomItem.register(new EterinSword(plugin));
        CustomItem.register(new EnderSword(plugin));
        CustomItem.register(new DrownedTrident(plugin));
        CustomItem.register(new RuneStone(plugin));
        CustomItem.register(new Mjolnir(plugin));
        CustomItem.register(new FlightSoup(plugin));
        CustomItem.register(new EggSword(plugin));
        CustomItem.register(new AncientSword(plugin));
        CustomItem.register(new FlySword(plugin));
        CustomItem.register(new SmasherAxe(plugin));
        CustomItem.register(new BedrockBreaker(plugin));
        CustomItem.register(new AncientHandle(plugin));
        CustomItem.register(new NetherCrossguardPiece(plugin));
        CustomItem.register(new SkulkCrossguardPiece(plugin));
        CustomItem.register(new AncientCrossguard(plugin));
        CustomItem.register(new AncientBlade(plugin));
        CustomItem.register(new AncientSpell(plugin));
        CustomItem.register(new DespisedSoulFragment(plugin));
        CustomItem.register(new HerobrineSword(plugin));
        CustomItem.register(new HerobrineAxe(plugin));
        CustomItem.register(new HerobrineBow(plugin));
        CustomItem.register(new SuperRocket(plugin));
        CustomItem.register(new HerobrineDiary(plugin));
        CustomItem.register(new ReinforcedIronHelmet(plugin));
        CustomItem.register(new ReinforcedIronChestplate(plugin));
        CustomItem.register(new ReinforcedIronLeggings(plugin));
        CustomItem.register(new ReinforcedIronBoots(plugin));
        CustomItem.register(new HermesSword(plugin));
        CustomItem.register(new KnockbackStick(plugin));
        CustomItem.register(new SpawnerCore(plugin));
        CustomItem.register(new ReinforcedHorseArmor(plugin));
        CustomItem.register(new SpawnerCage(plugin));
        CustomItem.register(new Curse(plugin));
        CustomItem.register(new SideroShield(plugin));
        CustomItem.register(new SuperPickaxe(plugin));
        CustomItem.register(new PoisonSword(plugin));
        CustomItem.register(new SuperExperienceBottle(plugin));
        CustomItem.register(new NyxSummoner(plugin));
        CustomItem.register(new BloodSword(plugin));
    }

    private static void registerCommand(Main plugin, String commandName, CommandExecutor commandExecutor, TabCompleter tabCompleter)
    {
        PluginCommand command = plugin.getCommand(commandName);
        if (command != null)
        {
            command.setExecutor(commandExecutor);
            command.setTabCompleter(tabCompleter);
        }
    }

    public static void registerCommands(Main plugin)
    {
        registerCommand(plugin, "givecustomitem", new GiveCustomItemCommand(), new GiveCustomItemTabCompleter());
        registerCommand(plugin, "startstorm", new StartStorm(plugin), null);
        registerCommand(plugin, "maxmygear", new MaxMyGear(), null);
        registerCommand(plugin, "summonboss", new SummonBoss(plugin), null);
    }

    public static void registerRecipes(Main plugin)
    {
        CustomRecipe.register(new ElytraRecipe(plugin));
        CustomRecipe.register(new GoldenHeadRecipe(plugin));
        CustomRecipe.register(new CobbledDeepslateRecipe(plugin));
        CustomRecipe.register(new CobbledDeepslateFurnaceRecipe(plugin));
        CustomRecipe.register(new AncientHandleRecipe(plugin));
        CustomRecipe.register(new AncientCrossguardRecipe(plugin));
        CustomRecipe.register(new AncientBladeRecipe(plugin));
        CustomRecipe.register(new AncientSwordRecipe(plugin));
        CustomRecipe.register(new SoulboundEnchantmentRecipe(plugin));
        CustomRecipe.register(new SpawnerRecipe(plugin));
        //CustomRecipe.register(new EmeraldSwordRecipe(plugin));
    }

    public static void registerEnchantments(Main plugin)
    {
        CustomEnchant.register(new UnbreakableEnchant(plugin));
        CustomEnchant.register(new SoulboundEnchant(plugin));
        CustomEnchant.register(new CreeperGuardEnchant(plugin));
        CustomEnchant.register(new FlailEnchant(plugin));
        CustomEnchant.register(new FireHookEnchant(plugin));
    }
}
