package me.saltymc.core.entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;

public class CustomBossRewards
{
    private final ArrayList<ItemStack> rewardList = new ArrayList<>();
    private final ArrayList<Player> contributorList = new ArrayList<>();

    private Material shulkerMaterial = Material.SHULKER_BOX;

    public void addReward(ItemStack itemStack)
    {
        rewardList.add(itemStack);
    }

    public void addContributor(Player player)
    {
        if (!contributorList.contains(player)) contributorList.add(player);
    }

    public void setShulkerMaterial(Material shulkerMaterial)
    {
        this.shulkerMaterial = shulkerMaterial;
    }

    public void distributeRewards()
    {
        contributorList.forEach(this::rewardPlayer);
    }

    private void sendNotification(Player player)
    {
        player.sendMessage(Component.text("You have received a reward! Check your inventory.").color(NamedTextColor.GOLD));
        player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
    }

    private void rewardPlayer(Player player)
    {
        if (player.isOnline())
        {
            ItemStack shulkerBox = new ItemStack(shulkerMaterial);

            BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBox.getItemMeta();
            BlockState blockState = blockStateMeta.getBlockState();
            ShulkerBox shulker = (ShulkerBox) blockState;
            Inventory shulkerInventory = shulker.getInventory();
            rewardList.forEach(shulkerInventory::addItem);
            blockStateMeta.setBlockState(blockState);
            shulkerBox.setItemMeta(blockStateMeta);

            sendNotification(player);
            player.getInventory().addItem(shulkerBox);
        }
    }
}
