package me.saltymc.core.items.item;

import me.saltymc.core.Main;
import me.saltymc.core.entities.bosses.nyx.Nyx;
import me.saltymc.core.items.CustomItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Witch;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NyxSummoner extends CustomItem
{
    private static final Random random = new Random();

    public NyxSummoner(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected Material getMaterial()
    {
        return Material.IRON_INGOT;
    }

    @Override
    protected TextComponent getDisplayName()
    {
        return Component.text("Strange Summoner").color(NamedTextColor.DARK_PURPLE);
    }

    @Override
    protected List<Component> getLore()
    {
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Relic Item").color(NamedTextColor.LIGHT_PURPLE));
        lore.add(Component.text("Summons the goddess of").color(NamedTextColor.YELLOW));
        lore.add(Component.text("the night.").color(NamedTextColor.YELLOW));
        return lore;
    }

    @Override
    protected int getModelId()
    {
        return 101;
    }

    @Override
    protected boolean doesDespawn()
    {
        return true;
    }

    @Override
    public String getItemId()
    {
        return "NYX_SUMMONER";
    }

    @Override
    protected void modifyItem(ItemStack itemStack)
    {

    }

    static class SummonRunnable implements Runnable
    {
        private final Location summonLocation;

        public SummonRunnable(Location summonLocation)
        {
            this.summonLocation = summonLocation;
        }

        @Override
        public void run()
        {
            Nyx nyx = new Nyx(plugin);
            nyx.summon(summonLocation);
        }
    }

    private static final int dropChance = 10;
    private static final long summonDelayInTicks = 50L;

    private void useNyxSummoner(Player player, ItemStack summoner)
    {
        World world = player.getWorld();
        if (!world.isDayTime())
        {
            summoner.setAmount(summoner.getAmount() - 1);
            Location location = player.getLocation();
            location.getWorld().playSound(location, Sound.BLOCK_END_PORTAL_SPAWN, 1.0f, 1.0f);
            world.spawnParticle(Particle.PORTAL, location, 120, null);
            Bukkit.getScheduler().runTaskLater(plugin, new SummonRunnable(location), summonDelayInTicks);
        }
        else
        {
            player.sendMessage(Component.text("Can only be used at night.").color(NamedTextColor.RED));
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (matchesItem(itemInMainHand) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND)
        {
            useNyxSummoner(player, itemInMainHand);
        }
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Witch && !entity.fromMobSpawner() && random.nextInt(dropChance) == 0)
        {
            event.getDrops().add(new NyxSummoner(plugin).getItem(1));
        }
    }
}
