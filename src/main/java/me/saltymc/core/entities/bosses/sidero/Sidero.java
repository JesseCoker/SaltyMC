package me.saltymc.core.entities.bosses.sidero;

import me.saltymc.core.Main;

import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.helpers.HeadGetter;
import me.saltymc.core.items.armor.*;
import me.saltymc.core.items.horsearmor.ReinforcedHorseArmor;
import me.saltymc.core.items.tool.BedrockBreaker;
import me.saltymc.core.items.weapon.SideroShield;
import me.saltymc.core.items.item.SpawnerCage;
import me.saltymc.core.items.weapon.HermesSword;
import me.saltymc.core.items.weapon.SideroSword;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class Sidero extends CustomBoss
{

    public Sidero(Main plugin)
    {
        super(plugin);
    }

    @Override
    protected EntityType getEntityType()
    {
        return EntityType.ZOMBIE;
    }

    @Override
    protected int getMaxHealth()
    {
        return 300;
    }

    @Override
    protected double getMovementSpeed()
    {
        return 0.3;
    }

    @Override
    protected TextComponent getBossName()
    {
        return Component.text("Sidero").color(NamedTextColor.WHITE);
    }

    @Override
    protected BossBar.Color getBossBarColor()
    {
        return BossBar.Color.RED;
    }

    @Override
    protected BossBar.Overlay getBossBarOverlay()
    {
        return BossBar.Overlay.NOTCHED_6;
    }

    @Override
    protected void setup()
    {
        ((Zombie)getEntity()).setAdult();
    }

    @Override
    protected void giveArmor()
    {
        EntityEquipment entityEquipment = ((Zombie)getEntity()).getEquipment();

        ItemStack head = HeadGetter.getPlayerHead("Exnda", Component.text("Sidero's Head"));

        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setUnbreakable(true);
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggingsMeta.setUnbreakable(true);
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);

        entityEquipment.setHelmet(head);
        entityEquipment.setChestplate(chestplate);
        entityEquipment.setLeggings(leggings);
        entityEquipment.setBoots(boots);

        entityEquipment.setHelmetDropChance(0.0f);
        entityEquipment.setChestplateDropChance(0.0f);
        entityEquipment.setLeggingsDropChance(0.0f);
        entityEquipment.setBootsDropChance(0.0f);
        entityEquipment.setItemInMainHandDropChance(0.0f);
        entityEquipment.setItemInOffHandDropChance(0.0f);

        sideroSword = new SideroSword(plugin).getItem(1);
        sideroSword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        sideroSword.addEnchantment(Enchantment.KNOCKBACK, 2);
        entityEquipment.setItemInMainHand(sideroSword);

        sideroShield = new ItemStack(Material.SHIELD);
        sideroShield.addEnchantment(Enchantment.DURABILITY, 3);
        entityEquipment.setItemInOffHand(sideroShield);
    }

    @Override
    protected void setRewards()
    {
        customBossRewards.setShulkerMaterial(Material.WHITE_SHULKER_BOX);
        customBossRewards.addReward(new SideroSword(plugin).getItem(1));
        customBossRewards.addReward(new HermesSword(plugin).getItem(1));
        customBossRewards.addReward(new SideroShield(plugin).getItem(1));
        customBossRewards.addReward(new ReinforcedIronHelmet(plugin).getItem(1));
        customBossRewards.addReward(new ReinforcedIronChestplate(plugin).getItem(1));
        customBossRewards.addReward(new ReinforcedIronLeggings(plugin).getItem(1));
        customBossRewards.addReward(new ReinforcedIronBoots(plugin).getItem(1));
        customBossRewards.addReward(new ReinforcedHorseArmor(plugin).getItem(1));
        customBossRewards.addReward(new ItemStack(Material.IRON_NUGGET, 64));
        customBossRewards.addReward(new ItemStack(Material.IRON_INGOT, 64));
        customBossRewards.addReward(new ItemStack(Material.IRON_BLOCK, 24));
        customBossRewards.addReward(new ItemStack(Material.IRON_BARS, 24));
        customBossRewards.addReward(new ItemStack(Material.IRON_DOOR, 24));
        customBossRewards.addReward(new ItemStack(Material.IRON_TRAPDOOR, 24));
        customBossRewards.addReward(new ItemStack(Material.CHAIN, 24));
        customBossRewards.addReward(new SpawnerCage(plugin).getItem(24));
        customBossRewards.addReward(new ItemStack(Material.LANTERN, 12));
        customBossRewards.addReward(new ItemStack(Material.SOUL_LANTERN, 12));
        customBossRewards.addReward(new ItemStack(Material.ANVIL, 6));
        customBossRewards.addReward(new BedrockBreaker(plugin).getItem(2));
        ItemStack sideroHead = HeadGetter.getPlayerHead("Exnda", Component.text("Sidero's Head"));
        ItemMeta itemMeta = sideroHead.getItemMeta();
        itemMeta.displayName(Component.text("Sidero's Head").color(NamedTextColor.WHITE));
        sideroHead.setItemMeta(itemMeta);
        customBossRewards.addReward(sideroHead);
    }

    ItemStack sideroSword = null;
    ItemStack sideroShield = null;

    private boolean BUSY = false;
    private float REACH = SideroSettings.DEFAULT_REACH;
    private long LAST_STUN_TICK = 0;
    private long JUMP_ATTACK_TICKS = 0;
    private long SPAWN_IRON_GOLEM_TICKS = SideroSettings.MIN_TICKS_SPAWN_IRON_GOLEM;
    private long SPAWN_WOLF_TICKS = SideroSettings.MIN_TICKS_SPAWN_WOLF;
    private long SPAWN_LAVA_TICKS = 0;
    private long THROW_DEBUFF_POT_TICKS = 0;
    private long USE_BOW_TICKS = 0;

    @Override
    protected void tick()
    {
        targetPlayer(getNearestPlayer());
        playFootsteps();
        equipDefaults();

        if (getTicksBetween(LAST_STUN_TICK) >= SideroSettings.STUN_TIME)
        {
            attackPlayer();
        }

        if (BUSY) return;

        int nextMove = random.nextInt(40);

        if (nextMove <= 5 && getTicksBetween(JUMP_ATTACK_TICKS) >= SideroSettings.MIN_TICKS_JUMP_ATTACK)
        {
            prepareJumpAttack();
            JUMP_ATTACK_TICKS = totalTicks;
        }
        else if (nextMove == 6 && getTicksBetween(SPAWN_IRON_GOLEM_TICKS) >= SideroSettings.MIN_TICKS_SPAWN_IRON_GOLEM)
        {
            prepareToSpawnIronGolems();
            SPAWN_IRON_GOLEM_TICKS = totalTicks;
        }
        else if (nextMove == 7 && getTicksBetween(SPAWN_WOLF_TICKS) >= SideroSettings.MIN_TICKS_SPAWN_WOLF)
        {
            prepareToSpawnWolves();
            SPAWN_WOLF_TICKS = totalTicks;
        }
        else if (nextMove == 8 && getTicksBetween(SPAWN_LAVA_TICKS) >= SideroSettings.MIN_TICKS_SPAWN_LAVA)
        {
            prepareToSpawnLava();
            SPAWN_LAVA_TICKS = totalTicks;
        }
        else if (nextMove == 9 && getTicksBetween(THROW_DEBUFF_POT_TICKS) >= SideroSettings.MIN_TICKS_THROW_DEBUFF_POT)
        {
            prepareToThrowDebuffPot();
            THROW_DEBUFF_POT_TICKS = totalTicks;
        }
        else if (nextMove == 10 && getTicksBetween(USE_BOW_TICKS) >= SideroSettings.MIN_TICKS_USE_BOW)
        {
            prepareToUseBow();
            USE_BOW_TICKS = totalTicks;
        }
    }

    private long LAST_ATTACK = 0;
    private void attackPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && getDistanceToPlayer(nearestPlayer) <= REACH && getTicksBetween(LAST_ATTACK) >= SideroSettings.ATTACK_RATE)
        {
            int noDamageTicks = nearestPlayer.getNoDamageTicks();
            nearestPlayer.setNoDamageTicks(0);
            getEntity().attack(nearestPlayer);
            nearestPlayer.setNoDamageTicks(noDamageTicks);
            LAST_ATTACK = totalTicks;
        }
    }

    private void targetPlayer(Player player)
    {
        ((Zombie)getEntity()).setTarget(player);
    }

    // FOOTSTEPS //

    long LAST_FOOTSTEP_TICK = 0;
    private void playFootsteps()
    {
        LivingEntity entity = getEntity();
        Vector velocity = entity.getVelocity();
        if (Math.abs(velocity.getX()) > 0 && Math.abs(velocity.getZ()) > 0 && entity.isOnGround() &&
                getTicksBetween(LAST_FOOTSTEP_TICK) > SideroSettings.MIN_FOOTSTEP_TICKS)
        {
            playSound(entity.getLocation(), Sound.ITEM_ARMOR_EQUIP_NETHERITE);
            LAST_FOOTSTEP_TICK = totalTicks;
        }
    }

    private void equipDefaults()
    {
        if (!BUSY) equipItemInMainHand(sideroSword);
    }

    // JUMP ATTACK //

    private void jumpAttack()
    {
        if (!isAlive()) return;

        REACH = SideroSettings.DEFAULT_REACH;

        Zombie sidero = (Zombie)(getEntity());
        Player nearestPlayer = getNearestPlayer();
        if (
                isAlive() && nearestPlayer != null &&
                getDistanceToPlayer(nearestPlayer) <= SideroSettings.MAX_CHARGE_DISTANCE &&
                getDistanceToPlayer(nearestPlayer) >= SideroSettings.MIN_CHARGE_DISTANCE)
        {
            sidero.lookAt(nearestPlayer);
            sidero.setJumping(true);
            sidero.setVelocity(getDirection(nearestPlayer).multiply(SideroSettings.CHARGE_SPEED));
        }
        equipItemInOffHand(sideroShield);
        BUSY = false;
    }

    private void prepareJumpAttack()
    {
        BUSY = true;
        REACH = SideroSettings.PREPARE_JUMP_REACH;
        equipItemInOffHand(null);
        playSound(getEntity().getLocation(), Sound.ENTITY_HORSE_JUMP);
        Bukkit.getScheduler().runTaskLater(plugin, this::jumpAttack, SideroSettings.CHARGE_DELAY);
    }

    // SPAWN IRON GOLEMS //

    private void spawnIronGolem()
    {
        if (isAlive())
        {
            Entity entity = getEntity().getWorld().spawnEntity(getEntity().getLocation(), EntityType.IRON_GOLEM);
            IronGolem ironGolem = (IronGolem) entity;
            ironGolem.setAggressive(true);
            ironGolem.setTarget(getNearestPlayer());
            ironGolem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            NamespacedKey namespacedKey = new NamespacedKey(plugin, SideroSettings.IRON_GOLEM_KEY);
            ironGolem.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BOOLEAN, true);
        }
        BUSY = false;
    }

    private void prepareToSpawnIronGolems()
    {
        BUSY = true;
        equipItemInMainHand(new ItemStack(Material.IRON_GOLEM_SPAWN_EGG));
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnIronGolem, 30L);
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnIronGolem, 40L);
    }

    @EventHandler
    public void onMinionTarget(EntityTargetEvent event)
    {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
        if ((entity instanceof IronGolem || entity instanceof Wolf) && target != null && isThisEntity(target))
        {
            event.setCancelled(true);
        }
        else if (isThisEntity(entity) && (target instanceof IronGolem || target instanceof Wolf))
        {
            event.setCancelled(true);
        }
    }

    // SPAWN WOLVES //

    private void spawnWolf()
    {
        if (isAlive())
        {
            Entity entity = getEntity().getWorld().spawnEntity(getEntity().getLocation(), EntityType.WOLF);
            Wolf wolf = (Wolf) entity;
            wolf.setAggressive(true);
            wolf.setTarget(getNearestPlayer());
        }
        BUSY = false;
    }

    private void prepareToSpawnWolves()
    {
        BUSY = true;
        equipItemInMainHand(new ItemStack(Material.WOLF_SPAWN_EGG));
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnWolf, 30L);
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnWolf, 40L);
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnWolf, 50L);
    }

    // SPAWN LAVA //

    private void spawnLava()
    {
        Player nearestPlayer = getNearestPlayer();
        if (isAlive() && nearestPlayer != null && getDistanceToPlayer(nearestPlayer) <= SideroSettings.SPECIAL_REACH)
        {
            Block block = getEntity().getWorld().getBlockAt(nearestPlayer.getLocation());
            if (block.getType() == Material.AIR) block.setType(Material.LAVA);
        }
        BUSY = false;
    }

    private void prepareToSpawnLava()
    {
        BUSY = true;
        equipItemInMainHand(new ItemStack(Material.LAVA_BUCKET));
        Bukkit.getScheduler().runTaskLater(plugin, this::spawnLava, 15L);
    }

    // THROW DEBUFF POT //

    private void throwDebuffPot(ItemStack potion)
    {
        Player nearestPlayer = getNearestPlayer();
        if (isAlive() && nearestPlayer != null &&
                getDistanceToPlayer(nearestPlayer) >= SideroSettings.MIN_DEBUFF_DISTANCE &&
                getDistanceToPlayer(nearestPlayer) <= SideroSettings.MAX_DEBUFF_DISTANCE)
        {
            Projectile projectile = shootProjectile(ThrownPotion.class, nearestPlayer.getLocation(), 1);
            ThrownPotion thrownPotion = (ThrownPotion) projectile;
            thrownPotion.setItem(potion);
            playSound(getEntity().getLocation(), Sound.ENTITY_SPLASH_POTION_THROW);
        }
        BUSY = false;
    }

    private void prepareToThrowDebuffPot()
    {
        BUSY = true;
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.setBasePotionType(PotionType.WEAKNESS);
        potion.setItemMeta(potionMeta);
        equipItemInMainHand(new ItemStack(potion));
        Bukkit.getScheduler().runTaskLater(plugin, ()->throwDebuffPot(potion), 40L);
    }

    // USE BOW //

    private void useBow()
    {
        Player nearestPlayer = getNearestPlayer();
        if (isAlive() && nearestPlayer != null)
        {
            shootProjectile(Arrow.class, nearestPlayer.getLocation(), 1.5f);
        }
        BUSY = false;
    }

    private void prepareToUseBow()
    {
        BUSY = true;
        equipItemInMainHand(new ItemStack(Material.BOW));
        Bukkit.getScheduler().runTaskLater(plugin, this::useBow, 40L);
    }

    // OTHER

    private boolean isBlocking()
    {
        EntityEquipment entityEquipment = getEntity().getEquipment();
        return entityEquipment != null && entityEquipment.getItemInOffHand().getType() == Material.SHIELD;
    }

    private void dealThornsDamage(Player player)
    {
        double dx = player.getX() - getEntity().getX();
        double dz = player.getZ() - getEntity().getZ();
        float hurtDirection = (float) Math.toDegrees(Math.atan2(dz, dx));
        player.damage(0.0);
        player.setHurtDirection(hurtDirection);
        player.setHealth(Math.max(player.getHealth() - 8.0, 0.0));
        playSound(getEntity().getLocation(), Sound.ENCHANT_THORNS_HIT);
    }

    @EventHandler
    public void onDamageBoss(EntityDamageByEntityEvent event)
    {
        super.onDamageBoss(event);

        Entity damagee = event.getEntity();
        Entity damager = event.getDamager();
        EntityDamageEvent.DamageCause damageCause = event.getCause();
        if (isThisEntity(damagee) && damager instanceof Player && isBlocking() && damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
        {
            Player player = (Player) damager;
            dealThornsDamage(player);
            playSound(getEntity().getLocation(), Sound.ITEM_SHIELD_BLOCK);
            event.setCancelled(true);
        }
        else if (isThisEntity(damagee) && damager instanceof Projectile && isBlocking())
        {
            Projectile projectile = (Projectile) damager;
            if (projectile.getShooter() instanceof Player)
            {
                dealThornsDamage((Player)projectile.getShooter());
            }

            playSound(getEntity().getLocation(), Sound.ITEM_SHIELD_BLOCK);
            event.setCancelled(true);
        }
        else
        {
            LAST_STUN_TICK = totalTicks;
        }
    }
}