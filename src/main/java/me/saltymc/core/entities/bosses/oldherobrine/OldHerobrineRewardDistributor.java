package me.saltymc.core.entities.bosses.oldherobrine;

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

public class OldHerobrineRewardDistributor
{
    private final ArrayList<Player> contributingPlayers = new ArrayList<>();

    private final OldHerobrineBossRewards herobrineBossRewards;

    public OldHerobrineRewardDistributor(OldHerobrineBossRewards herobrineBossRewards)
    {
        this.herobrineBossRewards = herobrineBossRewards;
    }

    private void rewardContributor(Player player)
    {
        if (player != null && player.isValid() && player.isOnline())
        {
            ItemStack shulkerBox = new ItemStack(Material.SHULKER_BOX);
            BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBox.getItemMeta();
            BlockState blockState = blockStateMeta.getBlockState();
            ShulkerBox shulker = (ShulkerBox) blockState;
            Inventory shulkerInventory = shulker.getInventory();
            herobrineBossRewards.getRewards().forEach(shulkerInventory::addItem);
            blockStateMeta.setBlockState(blockState);
            shulkerBox.setItemMeta(blockStateMeta);

            player.sendMessage(Component.text("You have received a reward! Check your inventory.").color(NamedTextColor.GOLD));
            player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 0.7f);
            player.getInventory().addItem(shulkerBox);
        }
    }

    public void rewardAllContributors()
    {
        contributingPlayers.forEach(this::rewardContributor);
    }

    public void addContributingPlayer(Player player)
    {
        if (!contributingPlayers.contains(player)) contributingPlayers.add(player);
    }
}
