package me.saltymc.core.entities;

import me.saltymc.core.Main;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

abstract public class CustomBoss implements Listener
{
    // Plugin
    protected static Main plugin;

    // Constants
    protected static final long TICK_RATE = 2L;
    protected final CustomBossRewards customBossRewards = new CustomBossRewards();
    protected static final Random random = new Random();

    // References
    private Entity bossEntity;

    protected long totalTicks = 0;

    // Task
    private int taskId;

    // Abstracts
    abstract protected EntityType getEntityType();

    abstract protected double getMaxHealth();

    abstract protected double getMovementSpeed();

    abstract protected TextComponent getBossName();

    abstract protected BossBar.Color getBossBarColor();

    abstract protected BossBar.Overlay getBossBarOverlay();

    abstract protected void setup();

    abstract protected void giveArmor();

    abstract protected void setRewards();

    abstract protected void tick();

    public CustomBoss(Main plugin)
    {
        CustomBoss.plugin = plugin;
    }

    public void summon(Location location)
    {
        bossEntity = location.getWorld().spawnEntity(location, getEntityType());

        bossEntity.customName(getBossName());

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        setHealth();
        setMovementSpeed();
        setup();
        giveArmor();
        setRewards();

        // set canpickupitems to false

        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        taskId = scheduler.scheduleSyncRepeatingTask(plugin, new BossTick(), 0L, TICK_RATE);
    }

    public int getBossRange()
    {
        return 100;
    }

    public boolean isAlive()
    {
        return bossEntity != null && bossEntity.isValid() && !bossEntity.isDead();
    }

    public long getTicksBetween(long TICKS)
    {
        return totalTicks - TICKS;
    }

    private class BossTick implements Runnable
    {
        private final CustomBossBar customBossBar = new CustomBossBar(getEntity(), getMaxHealth(), getBossRange(), getBossName(), getBossBarColor(), getBossBarOverlay());

        @Override
        public void run()
        {
            if (isAlive())
            {
                tick();
            }
            else {
                markDead();
            }

            customBossBar.update();
            totalTicks += TICK_RATE;
        }
    }

    private void markDead()
    {
        if (getEntity().getHealth() == 0.0) customBossRewards.distributeRewards();

        Bukkit.getScheduler().cancelTask(taskId);
        bossEntity.remove();
        bossEntity = null;
        HandlerList.unregisterAll(this);
    }

    public Player getNearestPlayer()
    {
        Player nearestPlayer = null;
        double nearestDistance = 0.0;

        for (Player player : Bukkit.getOnlinePlayers())
        {
            double distance = getDistanceToPlayer(player);
            if ((nearestPlayer == null || distance < nearestDistance) && distance < getBossRange() && player.getGameMode() == GameMode.SURVIVAL)
            {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        }
        return nearestPlayer;
    }

    public List<Player> getNearbyPlayers()
    {
        return Bukkit.getOnlinePlayers().stream().filter(
                player -> getEntity().getLocation().distance(player.getLocation()) < getBossRange()).collect(Collectors.toList());
    }

    public LivingEntity getEntity()
    {
        return (LivingEntity) bossEntity;
    }

    public long getTotalTicks()
    {
        return totalTicks;
    }

    private void setHealth()
    {
        LivingEntity livingEntity = getEntity();
        AttributeInstance genericMaxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (genericMaxHealth != null)
        {
            genericMaxHealth.setBaseValue(getMaxHealth());
            livingEntity.setHealth(getMaxHealth());
        }
    }

    private void setMovementSpeed()
    {
        LivingEntity livingEntity = getEntity();
        AttributeInstance genericMaxHealth = livingEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (genericMaxHealth != null)
        {
            genericMaxHealth.setBaseValue(getMovementSpeed());
        }
    }

    public double getDistanceToPlayer(Player player)
    {
        return bossEntity.getLocation().distance(player.getLocation());
    }

    protected Vector getDirection(Player player)
    {
        Vector playerVector = player.getLocation().toVector();
        Vector bossVector = bossEntity.getLocation().toVector();
        return playerVector.subtract(bossVector).normalize();
    }

    public void playSound(Location location, Sound soundType)
    {
        getEntity().getWorld().playSound(location, soundType, 1.0f, 1.0f);
    }

    public void spawnParticles(Particle particle, Location location)
    {
        getEntity().getWorld().spawnParticle(particle, location, 120, null);
    }

    public boolean isThisEntity(Entity entity)
    {
        return bossEntity.getUniqueId().equals(entity.getUniqueId());
    }

    public void equipItemInMainHand(ItemStack itemStack)
    {
        EntityEquipment entityEquipment = ((LivingEntity) bossEntity).getEquipment();
        if (entityEquipment != null) entityEquipment.setItemInMainHand(itemStack);
    }

    public void equipItemInOffHand(ItemStack itemStack)
    {
        EntityEquipment entityEquipment = ((LivingEntity) bossEntity).getEquipment();
        if (entityEquipment != null) entityEquipment.setItemInOffHand(itemStack);
    }

    protected Projectile shootProjectile(Class<? extends Projectile> projectile, Location target, float speed)
    {
        Vector bossVector = getEntity().getLocation().toVector();
        Vector targetVector = target.toVector();
        return getEntity().launchProjectile(projectile, targetVector.subtract(bossVector).normalize().multiply(speed));
    }

    protected void onDamage(EntityDamageByEntityEvent event)
    {
        Entity damagee = event.getEntity();
        Entity damager = event.getDamager();

        if (isThisEntity(damagee) && damager instanceof Player)
        {
            Player player = (Player) damager;
            customBossRewards.addContributor(player);
        }
        else if (isThisEntity(damagee) && damager instanceof Projectile)
        {
            Projectile projectile = (Projectile) damager;
            if (projectile.getShooter() instanceof Player) customBossRewards.addContributor((Player) projectile.getShooter());
        }
    }
}
