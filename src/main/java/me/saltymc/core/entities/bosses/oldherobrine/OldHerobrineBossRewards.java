package me.saltymc.core.entities.bosses.oldherobrine;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class OldHerobrineBossRewards
{
    private final ArrayList<ItemStack> rewardList = new ArrayList<>();

    public OldHerobrineBossRewards() {}

    public ArrayList<ItemStack> getRewards()
    {
        return rewardList;
    }

    public void addReward(ItemStack itemStack)
    {
        rewardList.add(itemStack);
    }
}
