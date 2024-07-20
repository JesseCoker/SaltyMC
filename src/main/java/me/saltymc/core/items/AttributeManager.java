package me.saltymc.core.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AttributeManager
{

    private static void addAttribute(ItemStack itemStack, Attribute attribute, String attributeName, double amount, AttributeModifier.Operation operation, EquipmentSlot equipmentSlot)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();
        AttributeModifier attributeModifier = new AttributeModifier(UUID.randomUUID(), attributeName, amount, operation, equipmentSlot);
        itemMeta.addAttributeModifier(attribute, attributeModifier);
        if (!itemMeta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)) itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
    }

    private static TextComponent getTextComponent(String s)
    {
        return Component.text(s).color(NamedTextColor.BLUE).decoration(TextDecoration.ITALIC, false);
    }

    private static TextComponent setAttackDamage(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        double handDamage = 1.0;
        double actualDamage = attributeData.getValue() - handDamage;
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.attack_damage", actualDamage, AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent(String.format("%.1f", attributeData.getValue()) + " Base Damage");
    }

    private static TextComponent setAttackSpeed(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        double realAttackSpeed = attributeData.getValue() - 4.1;
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.attack_speed", realAttackSpeed, AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent(String.format("%.1f", attributeData.getValue()) + " Attack Speed");
    }

    private static TextComponent setMovementSpeed(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        double realSpeed = attributeData.getValue() / 100;
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.movement_speed", realSpeed, AttributeModifier.Operation.MULTIPLY_SCALAR_1, attributeData.getEquipmentSlot());
        return getTextComponent("+" + String.format("%.1f", attributeData.getValue()) + "% Movement Speed");
    }

    private static TextComponent setMaxHealth(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.max_health", attributeData.getValue(), AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent("+" + String.format("%.1f", attributeData.getValue()) + " Max Health");
    }

    private static TextComponent setArmor(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.armor", attributeData.getValue(), AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent("+" + (int)attributeData.getValue() + " Armor");
    }

    private static TextComponent setArmorToughness(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.armor_toughness", attributeData.getValue(), AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent("+" + (int)attributeData.getValue() + " Armor Toughness");
    }

    private static TextComponent setMaxAbsorption(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.max_absorption", attributeData.getValue(), AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent("+" + (int)attributeData.getValue() + " Max Absorption");
    }

    private static TextComponent setLuck(ItemStack itemStack, AttributeHolder.AttributeData attributeData)
    {
        addAttribute(itemStack, attributeData.getAttribute(),
                "generic.luck", attributeData.getValue(), AttributeModifier.Operation.ADD_NUMBER, attributeData.getEquipmentSlot());
        return getTextComponent("+" + (int)attributeData.getValue() + " Luck");
    }

    public static void setAttributes(ItemStack itemStack, AttributeHolder attributeHolder)
    {
        List<Component> oldLore = itemStack.lore();
        ArrayList<Component> attributeLore = new ArrayList<>();

        if (oldLore != null) attributeLore.addAll(oldLore);

        for (AttributeHolder.AttributeData attributeData : attributeHolder.getAttributes())
        {
            switch(attributeData.getAttribute())
            {
                case GENERIC_ATTACK_DAMAGE:
                    TextComponent attackDamageLore = setAttackDamage(itemStack, attributeData);
                    attributeLore.add(attackDamageLore);
                    break;
                case GENERIC_ATTACK_SPEED:
                    TextComponent attackSpeedLore = setAttackSpeed(itemStack, attributeData);
                    attributeLore.add(attackSpeedLore);
                    break;
                case GENERIC_MOVEMENT_SPEED:
                    TextComponent movementSpeedLore = setMovementSpeed(itemStack, attributeData);
                    attributeLore.add(movementSpeedLore);
                    break;
                case GENERIC_MAX_HEALTH:
                    TextComponent maxHealthLore = setMaxHealth(itemStack, attributeData);
                    attributeLore.add(maxHealthLore);
                    break;
                case GENERIC_ARMOR:
                    TextComponent armorLore = setArmor(itemStack, attributeData);
                    attributeLore.add(armorLore);
                    break;
                case GENERIC_ARMOR_TOUGHNESS:
                    TextComponent armorToughnessLore = setArmorToughness(itemStack, attributeData);
                    attributeLore.add(armorToughnessLore);
                    break;
                case GENERIC_MAX_ABSORPTION:
                    TextComponent maxAbsorptionLore = setMaxAbsorption(itemStack, attributeData);
                    attributeLore.add(maxAbsorptionLore);
                    break;
                case GENERIC_LUCK:
                    TextComponent luckLore = setLuck(itemStack, attributeData);
                    attributeLore.add(luckLore);
                    break;
            }
        }
        itemStack.lore(attributeLore);
    }
}