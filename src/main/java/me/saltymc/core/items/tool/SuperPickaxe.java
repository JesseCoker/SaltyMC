package me.saltymc.core.items.tool;

import me.saltymc.core.Main;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuperPickaxe extends CustomItem
{
    public SuperPickaxe(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.DIAMOND_PICKAXE;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Super Pickaxe").color(NamedTextColor.BLUE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Mines an area of blocks.").color(NamedTextColor.YELLOW));
        lore.add(Component.text("Right click to change modes.").color(NamedTextColor.YELLOW));
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
        return "SUPER_PICKAXE";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    private final HashMap<Player, Mode> playerModes = new HashMap<>();

    static class Mode
    {
        public final int modeId;
        public final int minX;
        public final int maxX;
        public final int minY;
        public final int maxY;
        public final int minZ;
        public final int maxZ;
        public Mode(int modeId, int minX, int maxX, int minY, int maxY, int minZ, int maxZ)
        {
            this.modeId = modeId;
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }
    }

    private final Mode[] modes = {
            new Mode(0, 0, 0, 0, 0, 0, 0),
            new Mode(1, 0, 0, -1, 0, 0, 0),
            new Mode(2, -1, 1, -1, 1, -1, 1),
            new Mode(3, -1, 1, -1, 2, -1, 1),
    };

    private void messageMode(Player player, int modeId)
    {
        switch(modeId)
        {
            case 0:
                player.sendMessage(Component.text("Mode: Default").color(NamedTextColor.BLUE));
                break;
            case 1:
                player.sendMessage(Component.text("Mode: 1x2x1").color(NamedTextColor.BLUE));
                break;
            case 2:
                player.sendMessage(Component.text("Mode: 3x3x3").color(NamedTextColor.BLUE));
                break;
            case 3:
                player.sendMessage(Component.text("Mode: 3x4x3").color(NamedTextColor.BLUE));
                break;
        }
    }

    private Mode getMode(int modeId)
    {
        switch(modeId)
        {
            case 0:
                return modes[0];
            case 1:
                return modes[1];
            case 2:
                return modes[2];
            case 3:
                return modes[3];
        }
        return modes[0];
    }

    private void switchMode(Player player)
    {
        player.sendMessage(Component.text("Switching modes..").color(NamedTextColor.BLUE));
        Mode currentMode = playerModes.get(player);
        if (currentMode != null)
        {
            int newModeId = currentMode.modeId + 1;
            if (newModeId > 3) newModeId = 0;
            messageMode(player, newModeId);
            playerModes.put(player, getMode(newModeId));
        }
        else
        {
            playerModes.put(player, getMode(1));
            messageMode(player, 1);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (matchesItem(itemStack) && event.getHand() == EquipmentSlot.HAND &&
                (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR))
        {
            switchMode(player);
        }
    }

    private void attemptDestroyBlock(ItemStack tool, Block block, Material expectedMaterial)
    {
        if (block.getType() == expectedMaterial) block.breakNaturally(tool);
    }

    private void mineArea(Block block, ItemStack tool, Mode mode)
    {
        Location blockLocation = block.getLocation();
        World world = block.getWorld();

        for (int x = mode.minX; x <= mode.maxX; ++x)
        {
            for (int y = mode.minY; y <= mode.maxY; ++y)
            {
                for (int z = mode.minZ; z <= mode.maxZ; ++z)
                {
                    if (x == 0 && y == 0 && z == 0) continue;
                    Block otherBlock = world.getBlockAt(blockLocation.clone().add(x, y, z));
                    attemptDestroyBlock(tool, otherBlock, block.getType());
                }
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand))
        {
            Mode mode = playerModes.get(player);
            if (mode == null) mode = modes[0];
            mineArea(event.getBlock(), itemInMainHand, mode);
        }
    }
}
