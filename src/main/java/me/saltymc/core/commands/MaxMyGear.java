package me.saltymc.core.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class MaxMyGear implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (commandSender instanceof Player && commandSender.isOp())
        {
            Player player = (Player) commandSender;

            ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
            helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
            ItemMeta helmetMeta = helmet.getItemMeta();
            helmetMeta.setUnbreakable(true);
            helmet.setItemMeta(helmetMeta);

            ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
            chestplate.addEnchantment(Enchantment.THORNS, 3);
            ItemMeta chestplateMeta = chestplate.getItemMeta();
            chestplateMeta.setUnbreakable(true);
            chestplate.setItemMeta(chestplateMeta);

            ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
            ItemMeta leggingsMeta = leggings.getItemMeta();
            leggingsMeta.setUnbreakable(true);
            leggings.setItemMeta(leggingsMeta);

            ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
            ItemMeta bootsMeta = boots.getItemMeta();
            bootsMeta.setUnbreakable(true);
            boots.setItemMeta(bootsMeta);

            ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
            sword.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 8);
            ItemMeta swordMeta = sword.getItemMeta();
            swordMeta.setUnbreakable(true);
            sword.setItemMeta(swordMeta);

            ItemStack shield = new ItemStack(Material.SHIELD);
            ItemMeta shieldMeta = shield.getItemMeta();
            shieldMeta.setUnbreakable(true);
            shield.setItemMeta(shieldMeta);

            ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
            pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 8);
            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            pickaxeMeta.setUnbreakable(true);
            pickaxe.setItemMeta(pickaxeMeta);

            ItemStack arrows = new ItemStack(Material.ARROW, 64);

            ItemStack cobblestone = new ItemStack(Material.COBBLESTONE, 64);

            ItemStack amethystShard = new ItemStack(Material.AMETHYST_SHARD, 64);

            ItemStack oxidizedCopper = new ItemStack(Material.OXIDIZED_COPPER, 64);

            ItemStack bow  = new ItemStack(Material.BOW);
            bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 8);
            bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
            bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setUnbreakable(true);
            bow.setItemMeta(bowMeta);

            ItemStack waterBucket = new ItemStack(Material.WATER_BUCKET);

            ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);

            EntityEquipment entityEquipment = player.getEquipment();
            entityEquipment.setHelmet(helmet);
            entityEquipment.setChestplate(chestplate);
            entityEquipment.setLeggings(leggings);
            entityEquipment.setBoots(boots);
            entityEquipment.setItemInOffHand(shield);

            int originalSlot = player.getInventory().getHeldItemSlot();

            player.getInventory().setHeldItemSlot(0);
            entityEquipment.setItemInMainHand(sword);

            player.getInventory().setHeldItemSlot(1);
            entityEquipment.setItemInMainHand(pickaxe);

            player.getInventory().setHeldItemSlot(2);
            entityEquipment.setItemInMainHand(arrows);

            player.getInventory().setHeldItemSlot(3);
            entityEquipment.setItemInMainHand(cobblestone);

            player.getInventory().setHeldItemSlot(4);
            entityEquipment.setItemInMainHand(amethystShard);

            player.getInventory().setHeldItemSlot(5);
            entityEquipment.setItemInMainHand(oxidizedCopper);

            player.getInventory().setHeldItemSlot(6);
            entityEquipment.setItemInMainHand(bow);

            player.getInventory().setHeldItemSlot(7);
            entityEquipment.setItemInMainHand(waterBucket);

            player.getInventory().setHeldItemSlot(8);
            entityEquipment.setItemInMainHand(steak);

            player.getInventory().setHeldItemSlot(originalSlot);
        }
        return true;
    }
}
