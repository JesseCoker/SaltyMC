package me.saltymc.core.entities.bosses.nyx;

import com.destroystokyo.paper.event.entity.WitchConsumePotionEvent;
import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.enchants.enchants.CreeperGuardEnchant;
import me.saltymc.core.enchants.enchants.SoulboundEnchant;
import me.saltymc.core.items.item.Curse;
import me.saltymc.core.items.item.SuperExperienceBottle;
import me.saltymc.core.items.weapon.BloodSword;
import me.saltymc.core.items.weapon.Mjolnir;
import me.saltymc.core.items.weapon.PoisonSword;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Nyx extends CustomBoss
{
    public Nyx(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected EntityType getEntityType()
    {
        return EntityType.WITCH;
    }

    @Override
    protected int getMaxHealth()
    {
        return 280;
    }

    @Override
    protected double getMovementSpeed()
    {
        return 0.5;
    }

    @Override
    protected TextComponent getBossName()
    {
        return Component.text("Nyx").color(NamedTextColor.DARK_PURPLE);
    }

    @Override
    protected BossBar.Color getBossBarColor()
    {
        return BossBar.Color.BLUE;
    }

    @Override
    protected BossBar.Overlay getBossBarOverlay()
    {
        return BossBar.Overlay.NOTCHED_10;
    }

    @Override
    protected void setup()
    {

    }

    @Override
    protected void giveArmor()
    {

    }

    @Override
    protected void setRewards()
    {
        customBossRewards.setShulkerMaterial(Material.BLUE_SHULKER_BOX);
        customBossRewards.addReward(new PoisonSword(plugin).getItem(1));
        customBossRewards.addReward(new BloodSword(plugin).getItem(1));
        customBossRewards.addReward(new Mjolnir(plugin).getItem(1));
        customBossRewards.addReward(new SuperExperienceBottle(plugin).getItem(8));
        customBossRewards.addReward(new Curse(plugin).getItem(6));
        customBossRewards.addReward(new SoulboundEnchant(plugin).getItem());
        customBossRewards.addReward(new CreeperGuardEnchant(plugin).getItem());
        customBossRewards.addReward(new ItemStack(Material.REDSTONE, 64));
        customBossRewards.addReward(new ItemStack(Material.GLOWSTONE_DUST, 64));
        customBossRewards.addReward(new ItemStack(Material.GUNPOWDER, 64));
        customBossRewards.addReward(new ItemStack(Material.GLASS_BOTTLE, 64));
        customBossRewards.addReward(new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
    }

    private int NEXT_MOVE = 0;
    private long LAST_MOVE_TICK = 0;
    private long LAST_TELEPORT_TICK = 0;

    @Override
    protected void tick()
    {
        if (getTicksBetween(LAST_TELEPORT_TICK) >= NyxSettings.TICKS_BETWEEN_TELEPORTS)
        {
            teleportRandomly();
        }

        if (getTicksBetween(LAST_MOVE_TICK) >= NyxSettings.TICKS_BETWEEN_MOVES)
        {
            if (NEXT_MOVE == 0) prepareToSpawnVexes();
            else if (NEXT_MOVE == 1) prepareToShootArrows();
            else if (NEXT_MOVE == 2) prepareToSpawnFangs();
            else if (NEXT_MOVE == 3) prepareSpawnLightning();
            else if (NEXT_MOVE == 4) prepareRainTNT();
            else if (NEXT_MOVE == 5) spawnLava();
            else if (NEXT_MOVE == 6) prepareToThrowTridents();
            else if (NEXT_MOVE == 7) spawnLingeringPotions();
            ++NEXT_MOVE;
            if (NEXT_MOVE > 10) NEXT_MOVE = 0;
            LAST_MOVE_TICK = totalTicks;
        }
    }

    private void markEntity(Entity entity)
    {
        entity.getPersistentDataContainer().set(new NamespacedKey(plugin, NyxSettings.ENTITY_KEY), PersistentDataType.BOOLEAN, true);
    }

    private boolean isMarked(Entity entity)
    {
        return entity.getPersistentDataContainer().has(new NamespacedKey(plugin, NyxSettings.ENTITY_KEY));
    }

    private void teleportRandomly()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            spawnParticles(Particle.PORTAL, getEntity().getLocation());
            playSound(getEntity().getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT);
            int randomX = random.nextInt(NyxSettings.MAX_TELEPORT_DISTANCE * 2) - NyxSettings.MAX_TELEPORT_DISTANCE;
            int randomZ = random.nextInt(NyxSettings.MAX_TELEPORT_DISTANCE * 2) - NyxSettings.MAX_TELEPORT_DISTANCE;
            Location teleportLocation = nearestPlayer.getLocation().clone().add(new Vector(randomX, 0, randomZ));
            teleportLocation.setY(getEntity().getWorld().getHighestBlockAt(teleportLocation).getY() + 1);
            getEntity().teleport(teleportLocation);
            getEntity().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, NyxSettings.TELEPORT_GLOW_DURATION, 0));
        }
        LAST_TELEPORT_TICK = totalTicks;
    }

    // SPAWN FANGS //

    private void spawnFangs()
    {
        if (!isAlive()) return;
        for (int circle = 0; circle < NyxSettings.NUMBER_OF_FANG_CIRCLES; ++circle)
        {
            for (int angle = 0; angle <= 360; angle += 360 / NyxSettings.NUMBER_OF_FANGS_PER_CIRCLE)
            {
                double angleRadians = Math.toRadians(angle);
                double xOffset = (circle * NyxSettings.DISTANCE_PER_CIRCLE + NyxSettings.CIRCLE_OFFSET) * Math.cos(angleRadians);
                double zOffset = (circle * NyxSettings.DISTANCE_PER_CIRCLE + NyxSettings.CIRCLE_OFFSET) * Math.sin(angleRadians);
                Location location = getEntity().getLocation().clone().add(new Vector(xOffset, 0, zOffset));
                location.setY(getEntity().getWorld().getHighestBlockAt(location).getY() + 1);
                Entity fangs = getEntity().getWorld().spawnEntity(location, EntityType.EVOKER_FANGS);
                markEntity(fangs);
            }
        }
    }

    private void prepareToSpawnFangs()
    {
        playSound(getEntity().getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK);
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnFangs, 40L);
    }

    // SPAWN VEXES //

    private void popVex(Vex vex)
    {
        playSound(getEntity().getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT);
        spawnParticles(Particle.SMALL_FLAME, getEntity().getLocation());
        vex.remove();
    }

    private void spawnVex()
    {
        if (!isAlive()) return;
        Entity vex = getEntity().getWorld().spawnEntity(getEntity().getLocation(), EntityType.VEX);
        markEntity(vex);
    }

    private void prepareToSpawnVexes()
    {
        equipItemInMainHand(new ItemStack(Material.VEX_SPAWN_EGG));
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnVex, 50L);
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnVex, 70L);
    }

    // SHOOT ARROWS //

    private void enableArrowGravity(Arrow arrow)
    {
        arrow.setGravity(true);
    }

    private void spawnArrowWave(int wave)
    {
        for (int angle = 0; angle <= 360; angle += 360 / NyxSettings.NUMBER_OF_ARROWS_PER_CIRCLE)
        {
            double angleRadians = Math.toRadians(angle += NyxSettings.ANGLE_ADJUSTMENT_PER_ARROW_WAVE * wave);
            double xOffset = NyxSettings.ARROW_OFFSET * Math.cos(angleRadians);
            double zOffset = NyxSettings.ARROW_OFFSET * Math.sin(angleRadians);
            Location location = getEntity().getLocation().clone().add(new Vector(xOffset, 1.5, zOffset));
            Vector direction = new Vector(xOffset, 0, zOffset).normalize();
            playSound(location, Sound.ENTITY_ARROW_SHOOT);
            Arrow arrow = getEntity().getWorld().spawnArrow(location, direction, 0.6f, 0.0f);
            markEntity(arrow);
            arrow.setGravity(false);
            Bukkit.getScheduler().runTaskLater(plugin, () -> enableArrowGravity(arrow), NyxSettings.ARROW_GRAVITY_TIME);
        }
    }

    private void prepareToShootArrows()
    {
        playSound(getEntity().getLocation(), Sound.ITEM_CROSSBOW_LOADING_MIDDLE);
        equipItemInMainHand(new ItemStack(Material.ARROW));
        for (int wave = 0; wave < NyxSettings.NUMBER_OF_ARROW_WAVES; ++wave)
        {
            int finalWave = wave;
            Bukkit.getScheduler().runTaskLater(plugin, ()->spawnArrowWave(finalWave), wave * NyxSettings.TIME_BETWEEN_ARROW_WAVES);
        }
    }

    // SPAWN LIGHTNING //

    private void spawnLightning(Location location)
    {
        if (!isAlive()) return;

        LightningStrike lightningStrike = location.getWorld().strikeLightning(location);
        markEntity(lightningStrike);
    }

    private void spawnLightningWave(Location startingLocation, int wave)
    {
        if (!isAlive()) return;
        for (int angle = 0; angle <= 360; angle += 360 / NyxSettings.NUMBER_OF_LIGHTNING_STRIKES_PER_CIRCLE)
        {
            double angleRadians = Math.toRadians(angle);
            double xOffset = (NyxSettings.STARTING_LIGHTNING_OFFSET - NyxSettings.LIGHTNING_OFFSET_SUBTRACTION_PER_WAVE * wave) * Math.cos(angleRadians);
            double zOffset = (NyxSettings.STARTING_LIGHTNING_OFFSET - NyxSettings.LIGHTNING_OFFSET_SUBTRACTION_PER_WAVE * wave) * Math.sin(angleRadians);
            Location location = startingLocation.clone().add(new Vector(xOffset, 1.5, zOffset));
            location.setY(getEntity().getWorld().getHighestBlockAt(location).getY());
            Bukkit.getScheduler().runTaskLater(plugin, () -> spawnLightning(location), 25L);
        }
    }

    private void prepareSpawnLightning()
    {
        playSound(getEntity().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE);
        equipItemInMainHand(new ItemStack(Material.ENCHANTED_BOOK));
        Location location = getEntity().getLocation();
        for (int wave = 0; wave < NyxSettings.NUMBER_OF_LIGHTNING_WAVES; ++wave)
        {
            int finalWave = wave;
            Bukkit.getScheduler().runTaskLater(plugin, ()->spawnLightningWave(location, finalWave), wave * NyxSettings.TIME_BETWEEN_LIGHTNING_WAVES);
        }
    }

    // RAIN TNT //

    private void prepareRainTNT()
    {
        playSound(getEntity().getLocation(), Sound.ENTITY_TNT_PRIMED);
        equipItemInMainHand(new ItemStack(Material.TNT));
        for (int index = 0; index < NyxSettings.NUMBER_OF_TNT; ++index)
        {
            int randomX = random.nextInt(NyxSettings.MAX_TNT_DISTANCE * 2) - NyxSettings.MAX_TNT_DISTANCE;
            int randomZ = random.nextInt(NyxSettings.MAX_TNT_DISTANCE * 2) - NyxSettings.MAX_TNT_DISTANCE;
            Location location = getEntity().getLocation().clone().add(new Vector(randomX, NyxSettings.TNT_HEIGHT, randomZ));
            Entity entity = getEntity().getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
            markEntity(entity);
        }
    }

    // SPAWN LAVA

    private void removeLava(Block block)
    {
        if (block.getType() == Material.LAVA) block.setType(Material.AIR);
    }

    private void spawnLava()
    {
        playSound(getEntity().getLocation(), Sound.BLOCK_LAVA_POP);
        equipItemInMainHand(new ItemStack(Material.LAVA_BUCKET));
        for (int index = 0; index < NyxSettings.NUMBER_OF_LAVA; ++index)
        {
            int randomX = random.nextInt(NyxSettings.MAX_LAVA_DISTANCE * 2) - NyxSettings.MAX_LAVA_DISTANCE;
            int randomZ = random.nextInt(NyxSettings.MAX_LAVA_DISTANCE * 2) - NyxSettings.MAX_LAVA_DISTANCE;
            Location location = getEntity().getLocation().clone().add(new Vector(randomX, NyxSettings.LAVA_HEIGHT, randomZ));
            Block block = getEntity().getWorld().getBlockAt(location);
            if (block.getType() == Material.AIR)
            {
                block.setType(Material.LAVA);
                Bukkit.getScheduler().runTaskLater(plugin, ()->removeLava(block), NyxSettings.LAVA_REMOVE_DELAY);
            }
        }
    }

    // THROW TRIDENTS //

    private void spawnTridentWave()
    {
        if (!isAlive()) return;
        for (int angle = 0; angle <= 360; angle += 360 / NyxSettings.NUMBER_OF_TRIDENTS_PER_CIRCLE)
        {
            double angleRadians = Math.toRadians(angle);
            double xOffset = NyxSettings.TRIDENT_OFFSET * Math.cos(angleRadians);
            double zOffset = NyxSettings.TRIDENT_OFFSET * Math.sin(angleRadians);
            Location location = getEntity().getLocation().clone().add(new Vector(xOffset, 1.5, zOffset));
            Vector direction = new Vector(xOffset, 0, zOffset).normalize();
            playSound(location, Sound.ITEM_TRIDENT_THROW);
            Entity entity = getEntity().getWorld().spawnEntity(location, EntityType.TRIDENT);
            markEntity(entity);
            Trident trident = (Trident) entity;
            trident.setVelocity(direction.multiply(NyxSettings.TRIDENT_SPEED));
        }
    }

    private void prepareToThrowTridents()
    {
        playSound(getEntity().getLocation(), Sound.ITEM_TRIDENT_THROW);
        equipItemInMainHand(new ItemStack(Material.TRIDENT));
        for (int wave = 0; wave < NyxSettings.NUMBER_OF_TRIDENT_WAVES; ++wave)
        {
            Bukkit.getScheduler().runTaskLater(plugin, this::spawnTridentWave, wave * NyxSettings.TIME_BETWEEN_TRIDENT_WAVES);
        }
    }

    // THROW LINGERING POTIONS //

    private PotionType getRandomDebuffPotionType()
    {
        ArrayList<PotionType> DEBUFFS = new ArrayList<>();
        DEBUFFS.add(PotionType.POISON);
        DEBUFFS.add(PotionType.WEAKNESS);
        DEBUFFS.add(PotionType.SLOWNESS);
        DEBUFFS.add(PotionType.INSTANT_DAMAGE);

        return DEBUFFS.get(random.nextInt(DEBUFFS.size()));
    }

    private void dropLingeringPotion(int posX, int posZ)
    {
        if (!isAlive()) return;
        playSound(getEntity().getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION);
        equipItemInMainHand(new ItemStack(Material.LINGERING_POTION));
        Location dropLocation = getEntity().getLocation().clone().add(new Vector(posX, NyxSettings.LINGERING_POTION_HEIGHT, posZ));
        dropLocation.setY(getEntity().getWorld().getHighestBlockAt(dropLocation).getY() + 1);
        Entity entity = getEntity().getWorld().spawnEntity(dropLocation, EntityType.AREA_EFFECT_CLOUD);
        markEntity(entity);
        AreaEffectCloud areaEffectCloud = (AreaEffectCloud) entity;
        areaEffectCloud.setBasePotionType(getRandomDebuffPotionType());
    }

    private void spawnLingeringPotions()
    {
        for (int posX = -NyxSettings.LINGERING_POTION_RANGE; posX <= NyxSettings.LINGERING_POTION_RANGE; posX += NyxSettings.LINGERING_POTION_SPACING)
        {
            for (int posZ = -NyxSettings.LINGERING_POTION_RANGE; posZ <= NyxSettings.LINGERING_POTION_RANGE; posZ += NyxSettings.LINGERING_POTION_SPACING)
            {
                int finalPosX = posX;
                int finalPosZ = posZ;
                Bukkit.getScheduler().runTaskLater(plugin, ()->dropLingeringPotion(finalPosX, finalPosZ), random.nextInt(NyxSettings.MAX_LINGERING_POTION_STALL));
            }
        }
    }

    private void dealEntityDamage(EntityDamageByEntityEvent event)
    {
        Player player = (Player) event.getEntity();
        Entity damager = event.getDamager();

        double damage = 0;

        if (damager instanceof EvokerFangs) damage = NyxSettings.FANG_DAMAGE;
        else if (damager instanceof Vex)
        {
            damage = NyxSettings.VEX_DAMAGE;
            popVex((Vex) damager);
        }
        else if (damager instanceof Arrow) damage = NyxSettings.ARROW_DAMAGE;
        else if (damager instanceof LightningStrike) damage = NyxSettings.LIGHTNING_DAMAGE;
        else if (damager instanceof TNTPrimed) damage = NyxSettings.TNT_DAMAGE;
        else if (damager instanceof Trident) damage = NyxSettings.TRIDENT_DAMAGE;
        else if (damager instanceof AreaEffectCloud) damage = NyxSettings.LINGERING_POTION_DAMAGE;

        player.sendMessage(Component.text(damager.getName() + " dealt " + damage + " damage!").color(NamedTextColor.RED));
        event.setDamage(damage);
    }

    @EventHandler
    private void entityDamageByEntity(EntityDamageByEntityEvent event)
    {
        super.onDamageBoss(event);
        Entity damagee = event.getEntity();
        Entity damager = event.getDamager();
        if (damagee instanceof Player && isMarked(damager))
        {
            dealEntityDamage(event);
        }
    }

    @EventHandler
    public void onTakeDamage(EntityDamageEvent event)
    {
        EntityDamageEvent.DamageCause damageCause = event.getCause();
        if (isThisEntity(event.getEntity()))
        {
            if (damageCause != EntityDamageEvent.DamageCause.ENTITY_ATTACK &&
                    damageCause != EntityDamageEvent.DamageCause.PROJECTILE &&
                    damageCause != EntityDamageEvent.DamageCause.KILL)
            {
                event.setCancelled(true);
            }
            else
            {
                teleportRandomly();
            }
        }
    }

    private void dealKB(Player player, Location source)
    {
        float horizontalPower = 2.8f;
        float verticalPower = 1.4f;
        Vector playerVector = player.getLocation().toVector();
        Vector sourceVector = source.toVector();
        player.setVelocity(playerVector.subtract(sourceVector).normalize().multiply(horizontalPower).setY(verticalPower));
    }

    private void dealTNTKB(Entity tnt)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (tnt.getLocation().distance(player.getLocation()) < NyxSettings.TNT_DAMAGE_RANGE)
            {
                dealKB(player, tnt.getLocation());
            }
        }
    }

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent event)
    {
        Entity entity = event.getEntity();
        if (entity.getPersistentDataContainer().has(new NamespacedKey(plugin, NyxSettings.ENTITY_KEY)))
        {
            event.setCancelled(true);
            dealTNTKB(entity);
            playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE);
        }
    }

    @EventHandler
    public void onWitchUseItem(WitchConsumePotionEvent event)
    {
        if (isThisEntity(event.getEntity()))
        {
            event.setCancelled(true);
        }
    }
}
