package me.saltymc.core.items;

import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;

public class AttributeHolder
{
    private final ArrayList<AttributeData> attributeMap = new ArrayList<>();

    public AttributeHolder()
    {
    }

    public void addAttribute(AttributeData attributeData)
    {
        attributeMap.add(attributeData);
    }

    public ArrayList<AttributeData> getAttributes()
    {
        return attributeMap;
    }

    public static class AttributeData
    {
        private final Attribute attribute;
        private final double value;
        private final EquipmentSlot equipmentSlot;

        public AttributeData(Attribute attribute, Double value, EquipmentSlot equipmentSlot)
        {
            this.attribute = attribute;
            this.value = value;
            this.equipmentSlot = equipmentSlot;
        }

        public Attribute getAttribute()
        {
            return attribute;
        }

        public double getValue()
        {
            return value;
        }

        public EquipmentSlot getEquipmentSlot()
        {
            return equipmentSlot;
        }
    }
}