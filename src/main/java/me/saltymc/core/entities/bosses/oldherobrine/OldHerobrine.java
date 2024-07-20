package me.saltymc.core.entities.bosses.oldherobrine;

import me.saltymc.core.Main;
import me.saltymc.core.enchants.enchants.UnbreakableEnchant;
import me.saltymc.core.items.book.HerobrineDiary;
import me.saltymc.core.items.food.FlightSoup;
import me.saltymc.core.items.item.*;
import me.saltymc.core.items.tool.BedrockBreaker;
import me.saltymc.core.items.tool.SuperPickaxe;
import me.saltymc.core.items.weapon.*;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class OldHerobrine implements Listener
{
    private static Main plugin;
    private Zombie herobrine;
    private BossBar bossBar;
    private int taskId;

    private static final float HEROBRINE_HEALTH = 600.0f;
    private static final float HEROBRINE_RANGE = 150.0f;
    private static final int HEROBRINE_MAX_MOBS = 40;
    private static final int BLOCK_UNDO_CHANCE = 4;
    private static final float JUMP_CRIT_DISTANCE = 3.7f;
    private static final float HEROBRINE_REACH = 3.5f;
    private static final float HEAL_ON_KILL = 100.0f;
    private static final int USE_AXE_CHANCE = 25;
    private static final int SUPER_ATTACK_CHANCE = 100;

    private static final Random random = new Random();

    private final OldHerobrineBossRewards herobrineBossRewards = new OldHerobrineBossRewards();
    private final OldHerobrineRewardDistributor rewardDistributor = new OldHerobrineRewardDistributor(herobrineBossRewards);

    private OldHerobrineSpeak herobrineSpeak;
    private static final OldHerobrineEggHelper herobrineEggHelper = new OldHerobrineEggHelper();

    private static final String PLACED_BY_HEROBRINE_KEY = "PLACED-BY-HEROBRINE";

    private int mobCount = 0;
    private int totalTicks = 0;
    private int lastOffHandTick = 0;
    private int messageCount = 0;
    private int phase = 1;

    ItemStack sword;
    ItemStack axe;
    ItemStack bow;

    public OldHerobrine(Main plugin)
    {
        OldHerobrine.plugin = plugin;
    }

    public void summonHerobrine(Location spawnLocation)
    {
        spawnHerobrine(spawnLocation);
        giveHerobrineHead();
        givePotionEffects();
        setMaxHealth();
        setCustomName();
        setAdult();
        createBossBar();
        setPDC();
        setDropChances();
        setRewards();

        herobrine.setCanPickupItems(false);

        herobrineSpeak = new OldHerobrineSpeak(herobrine);
        sword = new HerobrineSword(plugin).getItem(1);
        axe = new HerobrineAxe(plugin).getItem(1);
        bow = new HerobrineBow(plugin).getItem(1);

        BukkitScheduler bukkitScheduler = plugin.getServer().getScheduler();
        taskId = bukkitScheduler.scheduleSyncRepeatingTask(plugin, new HerobrineTick(), 2L, 2L);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void despawnHerobrine()
    {
        if (herobrine != null && herobrine.getHealth() > 0.0f)
        {
            herobrineSpeak.messageNearbyPlayers(Component.text("So you've given up already?").color(NamedTextColor.BLACK));
        }

        if (herobrine != null)
        {
            herobrine.remove();
            herobrine = null;
        }

        updateBossBar();
        HandlerList.unregisterAll(this);
    }

    class HerobrineTick implements Runnable
    {
        @Override
        public void run()
        {
            if (herobrine == null || !herobrine.isValid() || herobrine.getHealth() == 0.0f)
            {
                despawnHerobrine();
                plugin.getServer().getScheduler().cancelTask(taskId);
                return;
            }

            if (herobrine.getHealth() < HEROBRINE_HEALTH / 2)
            {
                phase = 2;
            }

            chooseWeapon();
            targetNearestPlayer();
            hitNearestPlayer();
            teleportOutOfObstacles();
            updateBossBar();

            if (herobrine.getWorld().isClearWeather())
            {
                herobrine.getWorld().setStorm(true);
            }

            if (totalTicks - lastOffHandTick > 20) unequipOffhand();

            int nextMove = random.nextInt(100);

            if (nextMove == 0) chargeAtPlayer();
            if (nextMove == 1)
            {
                herobrineSpeak.sayMessage(messageCount);
                ++messageCount;
            }
            else if (nextMove >= 2 && nextMove <= 4) teleportAtNearestPlayer();
            else if (nextMove == 5 && phase >= 2) useBow();
            else if (nextMove == 6 && phase >= 2) useTNT();
            else if (nextMove == 7 && phase >= 2 && mobCount < HEROBRINE_MAX_MOBS)
            {
                useSpawnEgg();
                ++mobCount;
            }
            else if (nextMove == 8) useFlintAndSteel();
            else if (nextMove == 9 && phase >= 2) useLavaBucket();
            else if (nextMove == 10 && phase >= 2) useSpell();
            else if (nextMove == 11) throwSnowball();
            else if (nextMove == 12) throwPotion();
            else if (nextMove == 13 && phase >= 2) useWitherSkulls();
            else if (nextMove == 14) placeCobwebs();
            else if (nextMove == 15 && phase >= 2) strikeLightning();
            else if (nextMove == 16 && phase >= 2) throwTrident();
            else if (nextMove == 17) jumpCrit();
            else if (nextMove == 18) teleportAtRandomPlayer();
            else if (nextMove == 19 && phase == 1) shootShulkerBullet();
            else if (nextMove == 20 && phase >= 2) teleportDirectlyOnPlayer();
            else if (nextMove == 21) jumpToSide();
            else if (nextMove == 22) superAttack();

            totalTicks += 2;
        }
    }

    private Player getNearestPlayer()
    {
        Player closestPlayer = null;
        double closestDistance = 0.0;

        if (herobrine == null) return null;

        for (Player player : Bukkit.getOnlinePlayers())
        {
            double distance = player.getLocation().distance(herobrine.getLocation());
            if ((closestPlayer == null || distance < closestDistance) && distance < HEROBRINE_RANGE && player.getGameMode() == GameMode.SURVIVAL)
            {
                closestDistance = distance;
                closestPlayer = player;
            }
        }
        return closestPlayer;
    }

    private Player getRandomPlayer()
    {
        ArrayList<Player> nearbyPlayers = new ArrayList<>();

        if (herobrine == null) return null;

        for (Player player : Bukkit.getOnlinePlayers())
        {
            double distance = player.getLocation().distance(herobrine.getLocation());
            if (distance < HEROBRINE_RANGE && player.getGameMode() == GameMode.SURVIVAL)
            {
                nearbyPlayers.add(player);
            }
        }

        if (nearbyPlayers.isEmpty()) return null;

        return nearbyPlayers.get(random.nextInt(nearbyPlayers.size()));
    }

    private void targetNearestPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            herobrine.setTarget(nearestPlayer);
        }
    }

    private void chooseWeapon()
    {
        int axeChance = random.nextInt(USE_AXE_CHANCE);
        if (axeChance == 0)
        {
            equipItemInMainHand(axe);
        }
        else {
            equipItemInMainHand(sword);
        }
    }

    private void hitNearestPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && herobrine.getLocation().distance(nearestPlayer.getLocation()) < HEROBRINE_REACH)
        {
            int noDamageTicks = nearestPlayer.getNoDamageTicks();
            nearestPlayer.setNoDamageTicks(0);
            herobrine.attack(nearestPlayer);
            nearestPlayer.setNoDamageTicks(noDamageTicks);
            nearestPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 0));
        }
    }

    private void teleportOutOfObstacles()
    {
        Block block = herobrine.getWorld().getBlockAt(herobrine.getLocation());
        Block blockAbove = herobrine.getWorld().getBlockAt(herobrine.getLocation().add(new Vector(0, 1, 0)));
        if (block.getType() == Material.LAVA || block.getType() == Material.COBWEB ||
                blockAbove.getType() == Material.LAVA || blockAbove.getType() == Material.COBWEB)
        {
            teleportAtNearestPlayer();
        }
    }

    private void chargeAtPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            Vector playerVector = nearestPlayer.getLocation().toVector().setY(0);
            Vector herobrineVector = herobrine.getLocation().toVector().setY(0);
            float chargeSpeed = 3.5f;
            Vector chargeVector = playerVector.subtract(herobrineVector).normalize().multiply(chargeSpeed);
            herobrine.setVelocity(chargeVector);
        }
    }

    private void teleportAtPLayer(Player player)
    {
        double randomDistance = random.nextDouble() * 4 + 4;
        double randomAngle = Math.toRadians(random.nextInt(360));
        double xOffset = randomDistance * Math.cos(randomAngle);
        double zOffset = randomDistance * Math.sin(randomAngle);

        double newX = herobrine.getLocation().getX() + xOffset;
        double newZ = herobrine.getLocation().getZ() + zOffset;
        double newY = herobrine.getWorld().getHighestBlockAt(new Location(herobrine.getWorld(), newX, 0, newZ)).getY();

        herobrine.teleport(new Location(player.getWorld(), newX, newY, newZ).add(new Vector(0, 1, 0)));
        herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.5f);
        herobrine.getWorld().spawnParticle(Particle.PORTAL, herobrine.getLocation(), 120);
    }

    private void teleportAtNearestPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            teleportAtPLayer(nearestPlayer);
        }
    }

    void teleportAtRandomPlayer()
    {
        Player randomPlayer = getRandomPlayer();
        if (randomPlayer != null)
        {
            teleportAtPLayer(randomPlayer);
        }
    }

    private void equipItemInMainHand(ItemStack itemStack)
    {
        EntityEquipment entityEquipment = herobrine.getEquipment();
        entityEquipment.setItemInMainHand(itemStack);
    }

    private void equipItemInOffhand(ItemStack itemStack)
    {
        EntityEquipment entityEquipment = herobrine.getEquipment();
        entityEquipment.setItemInOffHand(itemStack);
        lastOffHandTick = totalTicks;
    }

    private void shootProjectile(Class<? extends Projectile> projectile, Player player, float speed)
    {
        Vector herobrineVector = herobrine.getLocation().toVector();
        Vector playerVector = player.getLocation().toVector();
        herobrine.launchProjectile(projectile, playerVector.subtract(herobrineVector).normalize().multiply(speed));
    }

    private void useBow()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            equipItemInOffhand(bow);
            shootProjectile(Arrow.class, nearestPlayer, 3.0f);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);
        }
    }

    private void useTNT()
    {
        int randomPlaceX = random.nextInt(6 + 1) - 3;
        int randomPlaceZ = random.nextInt(6 + 1) - 3;
        Block block = herobrine.getWorld().getBlockAt(herobrine.getLocation().add(new Vector(randomPlaceX, 0, randomPlaceZ)));
        if (block.getType() == Material.AIR)
        {
            herobrine.lookAt(block.getLocation());

            equipItemInOffhand(new ItemStack(Material.FLINT_AND_STEEL));

            Entity tnt = block.getWorld().spawnEntity(block.getLocation(), EntityType.PRIMED_TNT);
            tnt.getPersistentDataContainer().set(new NamespacedKey(plugin, PLACED_BY_HEROBRINE_KEY), PersistentDataType.BOOLEAN, true);

            tnt.getWorld().playSound(tnt.getLocation(), Sound.ENTITY_TNT_PRIMED, 1.0f, 1.0f);
        }
    }

    private void useSpawnEgg()
    {
        Material spawnEggType = herobrineEggHelper.useSpawnEgg(herobrine);
        equipItemInOffhand(new ItemStack(spawnEggType));
    }

    private void useFlintAndSteel()
    {
        int randomPlaceX = random.nextInt(6 + 1) - 3;
        int randomPlaceZ = random.nextInt(6 + 1) - 3;
        Block block = herobrine.getWorld().getBlockAt(herobrine.getLocation().add(new Vector(randomPlaceX, 0, randomPlaceZ)));
        Block belowBlock = herobrine.getWorld().getBlockAt(herobrine.getLocation().add(new Vector(randomPlaceX, -1, randomPlaceZ)));
        if (block.getType() == Material.AIR && belowBlock.getType() != Material.AIR)
        {
            herobrine.lookAt(block.getLocation());
            block.setType(Material.FIRE);

            equipItemInOffhand(new ItemStack(Material.FLINT_AND_STEEL));
        }
    }

    private void useLavaBucket()
    {
        float lavaRange = 5.0f;
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && ((Entity) nearestPlayer).isOnGround() && herobrine.getLocation().distance(nearestPlayer.getLocation()) < lavaRange)
        {
            Block block = herobrine.getWorld().getBlockAt(nearestPlayer.getLocation());
            if (block.getType() == Material.AIR)
            {
                teleportAtNearestPlayer();
                herobrine.lookAt(block.getLocation());
                block.setType(Material.LAVA);

                equipItemInOffhand(new ItemStack(Material.BUCKET));
            }
        }
    }

    private void useSpell()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            equipItemInOffhand(new AncientSpell(plugin).getItem(1));
            shootProjectile(DragonFireball.class, nearestPlayer, 3.0f);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_ENDER_DRAGON_SHOOT, 1.0f, 1.0f);
        }
    }

    private void throwSnowball()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            equipItemInOffhand(new ItemStack(Material.SNOWBALL));
            shootProjectile(Snowball.class, nearestPlayer, 2.0f);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
        }
    }

    private void throwPotion()
    {
        float throwDistance = 10.0f;
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && herobrine.getLocation().distance(nearestPlayer.getLocation()) < throwDistance)
        {
            ArrayList<PotionType> potionTypes = new ArrayList<>();
            potionTypes.add(PotionType.POISON);
            potionTypes.add(PotionType.WEAKNESS);
            potionTypes.add(PotionType.LONG_SLOWNESS);

            int randomPotionEffect = random.nextInt(potionTypes.size());

            Vector playerVector = nearestPlayer.getLocation().toVector();
            Vector herobrineVector = herobrine.getLocation().toVector();

            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
            potionMeta.setBasePotionType(potionTypes.get(randomPotionEffect));
            potion.setItemMeta(potionMeta);

            equipItemInOffhand(potion);

            ThrownPotion thrownPotion = herobrine.launchProjectile(ThrownPotion.class, playerVector.subtract(herobrineVector).normalize().multiply(2));
            thrownPotion.setItem(potion);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_SPLASH_POTION_THROW, 1.0f, 1.0f);
        }
    }

    private void destroyPlayerBed()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            Location bedLocation = nearestPlayer.getRespawnLocation();
            if (bedLocation != null && herobrine.getLocation().distance(bedLocation) < HEROBRINE_RANGE)
            {
                herobrineSpeak.messageNearbyPlayers(Component.text("No more respawning for " + nearestPlayer.getName() + '.').color(NamedTextColor.BLACK));
                herobrine.teleport(bedLocation.add(new Vector(0, 1, 0)));
                Block bed = bedLocation.getWorld().getBlockAt(bedLocation.add(new Vector(0, -1, 0)));

                bed.setType(Material.AIR);
                bed.getWorld().playSound(bedLocation, Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0f, 2.0f);
                bed.getWorld().spawnParticle(Particle.DUST_PLUME, bedLocation, 120);
            }
        }
    }

    private void useWitherSkulls()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            shootProjectile(WitherSkull.class, nearestPlayer, 1.0f);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_WITHER_SHOOT, 1.0f, 2.0f);
        }
    }

    private void placeCobwebs()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && ((LivingEntity)nearestPlayer).isOnGround())
        {
            Vector playerDirection = nearestPlayer.getLocation().getDirection().multiply(2);
            Location cobwebSpot = nearestPlayer.getLocation().add(playerDirection);
            Block block = nearestPlayer.getWorld().getBlockAt(cobwebSpot);
            if (block.getType() == Material.AIR)
            {
                herobrine.teleport(block.getLocation());
                herobrine.lookAt(block.getLocation());
                block.setType(Material.COBWEB);
            }
        }
    }

    class LightningStrikeRunnable implements Runnable
    {
        private int strikeCount = 0;
        private int lightningTaskId;
        private static final int maxStrikes = 24;
        private static final int strikeRange = 5;
        private static final int strikeChance = 6;

        @Override
        public void run()
        {
            Player nearestPlayer = getNearestPlayer();
            if (nearestPlayer != null && ((LivingEntity)nearestPlayer).isOnGround() && random.nextInt(strikeChance) == 0)
            {
                int randomX = random.nextInt(strikeRange * 2) - strikeRange;
                int randomZ = random.nextInt(strikeRange * 2) - strikeRange;
                nearestPlayer.getWorld().strikeLightning(nearestPlayer.getLocation().add(new Vector(randomX, 0, randomZ)));
                equipItemInOffhand(new ItemStack(Material.TRIDENT));
            }

            ++strikeCount;
            if (strikeCount == maxStrikes) plugin.getServer().getScheduler().cancelTask(lightningTaskId);
        }

        public void setTaskId(int lightningTaskId)
        {
            this.lightningTaskId = lightningTaskId;
        }
    }

    private void strikeLightning()
    {
        LightningStrikeRunnable lightningStrikeRunnable = new LightningStrikeRunnable();
        int lightningTaskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, lightningStrikeRunnable, 2L, 2L);
        lightningStrikeRunnable.setTaskId(lightningTaskId);
        equipItemInOffhand(new ItemStack(Material.TRIDENT));
    }

    private void throwTrident()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            equipItemInOffhand(new ItemStack(Material.TRIDENT));
            shootProjectile(Trident.class, nearestPlayer, 1.5f);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ITEM_TRIDENT_THROW, 1.0f, 1.0f);
        }
    }

    private void jumpCrit()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && herobrine.isOnGround() && herobrine.getLocation().distance(nearestPlayer.getLocation()) < JUMP_CRIT_DISTANCE)
        {
            herobrine.lookAt(nearestPlayer);
            herobrine.setJumping(true);
            Vector playerVector = nearestPlayer.getLocation().toVector().setY(0);
            Vector herobrineVector = herobrine.getLocation().toVector().setY(0);

            Vector crossProduct = playerVector.crossProduct(herobrineVector).setY(0);

            float movementSpeed = 0.5f;
            Vector chargeVector = playerVector.subtract(herobrineVector).normalize().multiply(movementSpeed).add(crossProduct.normalize().multiply(0.5));
            herobrine.setVelocity(chargeVector);
        }
    }

    private void shootShulkerBullet()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            shootProjectile(ShulkerBullet.class, nearestPlayer, 1.5f);
        }
    }

    private void teleportDirectlyOnPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            herobrine.teleport(nearestPlayer.getLocation());
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_WARDEN_ATTACK_IMPACT, 1.0f, 0.4f);
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
        }
    }

    private void jumpToSide()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null)
        {
            herobrine.lookAt(nearestPlayer);
            herobrine.setJumping(true);
            Vector playerVector = nearestPlayer.getLocation().toVector().normalize();
            Vector herobrineVector = herobrine.getLocation().toVector().normalize();
            Vector crossProduct = playerVector.crossProduct(herobrineVector).setY(0);
            if (random.nextBoolean()) crossProduct = crossProduct.multiply(-1);
            herobrine.setVelocity(crossProduct);
        }
    }

    private void superAttack()
    {
        if (random.nextInt(SUPER_ATTACK_CHANCE) == 0)
        {
            strikeLightning();

            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 0.6f);
        }
    }

    private void useShield()
    {
        equipItemInOffhand(new ItemStack(Material.SHIELD));
        herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1.0f, 1.0f);
    }

    private void unequipOffhand()
    {
        EntityEquipment entityEquipment = herobrine.getEquipment();
        entityEquipment.setItemInOffHand(null);
    }

    private void updateBossBar()
    {
        if (herobrine != null)
        {
            bossBar.progress((float) (herobrine.getHealth() / HEROBRINE_HEALTH));
        }

        for (Player player : Bukkit.getOnlinePlayers())
        {
            if (herobrine != null && player.getLocation().distance(herobrine.getLocation()) < HEROBRINE_RANGE)
            {
                player.showBossBar(bossBar);
            }
            else {
                player.hideBossBar(bossBar);
            }
        }
    }

    private void spawnHerobrine(Location location)
    {
        herobrine = (Zombie) location.getWorld().spawnEntity(location.add(new Vector(0, 2, 0)), EntityType.ZOMBIE);
    }

    private ItemStack getHerobrineHead()
    {
        ItemStack herobrineHead = new ItemStack(Material.PLAYER_HEAD);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer("Memerfront");
        SkullMeta skullMeta = (SkullMeta) herobrineHead.getItemMeta();
        skullMeta.setOwningPlayer(offlinePlayer);
        skullMeta.displayName(Component.text("Herobrine's Head").color(NamedTextColor.GRAY));
        herobrineHead.setItemMeta(skullMeta);
        return herobrineHead;
    }

    private void giveHerobrineHead()
    {
        EntityEquipment entityEquipment = herobrine.getEquipment();
        entityEquipment.setHelmet(getHerobrineHead());
    }

    private void givePotionEffects()
    {
        herobrine.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        herobrine.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        herobrine.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0));
    }

    private void setMaxHealth()
    {
        AttributeInstance GENERIC_MAX_HEALTH = herobrine.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if (GENERIC_MAX_HEALTH != null)
        {
            GENERIC_MAX_HEALTH.setBaseValue(HEROBRINE_HEALTH);
            herobrine.setHealth(HEROBRINE_HEALTH);
        }
    }

    private void setCustomName()
    {
        herobrine.customName(Component.text("Herobrine").color(NamedTextColor.GRAY));
    }

    private void setAdult()
    {
        herobrine.setAdult();
    }

    private void createBossBar()
    {
        bossBar = BossBar.bossBar(Component.text("Herobrine"), 0.5f, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20);
    }

    private void setPDC()
    {
        PersistentDataContainer pdc = herobrine.getPersistentDataContainer();
        pdc.set(getKey(), PersistentDataType.BOOLEAN, true);
    }

    private void setDropChances()
    {
        EntityEquipment entityEquipment = herobrine.getEquipment();
        entityEquipment.setItemInMainHandDropChance(0.0f);
        entityEquipment.setItemInOffHandDropChance(0.0f);
        entityEquipment.setHelmetDropChance(0.0f);
    }

    private void setRewards()
    {
        herobrineBossRewards.addReward(new ItemStack(Material.DIAMOND, 24));
        herobrineBossRewards.addReward(new ItemStack(Material.OBSIDIAN, 64));
        herobrineBossRewards.addReward(new HerobrineSword(plugin).getItem(1));
        herobrineBossRewards.addReward(new HerobrineAxe(plugin).getItem(1));
        herobrineBossRewards.addReward(new HerobrineBow(plugin).getItem(1));
        herobrineBossRewards.addReward(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        herobrineBossRewards.addReward(new ItemStack(Material.ANCIENT_DEBRIS, 2));
        herobrineBossRewards.addReward(new ItemStack(Material.NETHER_STAR, 3));
        herobrineBossRewards.addReward(new ItemStack(Material.TOTEM_OF_UNDYING));
        herobrineBossRewards.addReward(new ItemStack(Material.SHULKER_SHELL, 8));
        herobrineBossRewards.addReward(new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        herobrineBossRewards.addReward(new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2));
        herobrineBossRewards.addReward(new SuperExperienceBottle(plugin).getItem(30));
        herobrineBossRewards.addReward(new BedrockBreaker(plugin).getItem(6));
        herobrineBossRewards.addReward(new RuneStone(plugin).getItem(1));
        herobrineBossRewards.addReward(new SuperRocket(plugin).getItem(16));
        herobrineBossRewards.addReward(getHerobrineHead());
        herobrineBossRewards.addReward(new UnbreakableEnchant(plugin).getItem());
        herobrineBossRewards.addReward(new SpawnerCore(plugin).getItem(1));
        herobrineBossRewards.addReward(new ItemStack(Material.ZOMBIE_HORSE_SPAWN_EGG));
        herobrineBossRewards.addReward(new HerobrineDiary(plugin).getItem(1));
        herobrineBossRewards.addReward(new KnockbackStick(plugin).getItem(1));
        herobrineBossRewards.addReward(new SuperPickaxe(plugin).getItem(1));
        herobrineBossRewards.addReward(new FlightSoup(plugin).getItem(1));
        herobrineBossRewards.addReward(new FlightSoup(plugin).getItem(1));
    }

    private NamespacedKey getKey()
    {
        return new NamespacedKey(plugin, "IS-HEROBRINE");
    }

    private boolean isHerobrine(Entity entity)
    {
        return entity.getPersistentDataContainer().has(getKey());
    }

    @EventHandler
    public void onHerobrineDeath(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        if (isHerobrine(entity))
        {
            rewardDistributor.rewardAllContributors();
            herobrineSpeak.messageNearbyPlayers(Component.text("I will always come back.").color(NamedTextColor.RED));
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_WITHER_DEATH, 1.0f, 0.4f);
            event.setDroppedExp(150000);
        }
    }

    @EventHandler
    public void onHerobrineTarget(EntityTargetEvent event)
    {
        if (isHerobrine(event.getEntity()) && !(event.getTarget() instanceof Player))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        if (herobrine.getLocation().distance(player.getLocation()) < HEROBRINE_RANGE && random.nextInt(BLOCK_UNDO_CHANCE) == 0)
        {
            Block block = event.getBlock();
            herobrine.teleport(block.getLocation().add(new Vector(0, 1, 0)));
            block.setType(Material.AIR);
            block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_BREAK, 1.0f, 2.0f);
            block.getWorld().spawnParticle(Particle.DUST_PLUME, block.getLocation(), 120);
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (herobrine.getLocation().distance(player.getLocation()) < HEROBRINE_RANGE && random.nextInt(BLOCK_UNDO_CHANCE) == 0)
        {
            event.setCancelled(true);
            herobrine.teleport(block.getLocation().add(new Vector(0, 1, 0)));
            block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ANCIENT_DEBRIS_PLACE, 1.0f, 2.0f);
            block.getWorld().spawnParticle(Particle.DUST_PLUME, block.getLocation(), 120);
        }
    }

    private void waterBucketClutch()
    {
        EntityEquipment entityEquipment = herobrine.getEquipment();
        ItemStack bucket = new ItemStack(Material.BUCKET);
        entityEquipment.setItemInOffHand(bucket);
        Block block = herobrine.getWorld().getBlockAt(herobrine.getLocation().subtract(new Vector(0, 1, 0)));
        block.setType(Material.WATER);
    }

    private void blockDamageEvents(EntityDamageEvent event)
    {
        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (damageCause == EntityDamageEvent.DamageCause.FALL)
        {
            event.setCancelled(true);
            waterBucketClutch();
        }
        else if (damageCause == EntityDamageEvent.DamageCause.WITHER)
        {
            event.setCancelled(true);
        }
        else if (damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
        {
            event.setCancelled(true);
        }
        else if (damageCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
        {
            event.setCancelled(true);
        }
        else if (damageCause == EntityDamageEvent.DamageCause.CRAMMING)
        {
            teleportAtNearestPlayer();
        }
        else if (damageCause == EntityDamageEvent.DamageCause.SUFFOCATION)
        {
            teleportAtNearestPlayer();
        }
        else if (damageCause == EntityDamageEvent.DamageCause.MAGIC)
        {
            event.setCancelled(true);
        }
        else if (damageCause == EntityDamageEvent.DamageCause.THORNS)
        {
            event.setCancelled(true);
        }
        else if (damageCause == EntityDamageEvent.DamageCause.LIGHTNING)
        {
            event.setCancelled(true);
        }
        else if (damageCause == EntityDamageEvent.DamageCause.PROJECTILE)
        {
            int blockChance = random.nextInt(3);
            if (blockChance == 1)
            {
                event.setCancelled(true);
                useShield();
            }
            else if (blockChance == 2)
            {
                event.setCancelled(true);
                teleportAtNearestPlayer();
            }
        }
        else if (damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
        {
            int blockChance = random.nextInt(4);
            if (blockChance == 0)
            {
                event.setCancelled(true);
                useShield();
            }
        }
    }

    private void dropDispisedSoul()
    {
        herobrine.getWorld().dropItemNaturally(herobrine.getLocation(), new DespisedSoulFragment(plugin).getItem(1));
    }

    @EventHandler
    public void onHerobrineDamage(EntityDamageEvent event)
    {
        Entity entity = event.getEntity();
        if (isHerobrine(entity))
        {
            blockDamageEvents(event);
            if (!event.isCancelled())
            {
                dropDispisedSoul();
            }
        }
    }

    private void healHerobrineOnKill()
    {
        double health = herobrine.getHealth();
        AttributeInstance GENERIC_MAX_HEALTH = herobrine.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (GENERIC_MAX_HEALTH != null)
        {
            herobrine.setHealth(Math.min(health + HEAL_ON_KILL, GENERIC_MAX_HEALTH.getBaseValue()));
        }
    }

    @EventHandler
    public void onHerobrineKill(PlayerDeathEvent event)
    {
        Player player = event.getPlayer();
        if (herobrine.getLocation().distance(player.getLocation()) < HEROBRINE_RANGE)
        {
            healHerobrineOnKill();
            herobrineSpeak.messageNearbyPlayers(Component.text("I warned you.").color(NamedTextColor.BLACK));
        }
    }

    private void dealExplosionKB(Entity tnt, Player player)
    {
        Vector playerVector = player.getLocation().toVector();
        Vector tntVector = tnt.getLocation().toVector();
        player.setVelocity(playerVector.subtract(tntVector).normalize().multiply(3.0f).setY(1.4f));
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        Projectile projectile = event.getEntity();
        ProjectileSource projectileSource = event.getEntity().getShooter();
        if (projectileSource != null && isHerobrine((Entity)projectileSource) && projectile instanceof DragonFireball)
        {
            herobrine.getWorld().playSound(herobrine.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
        }

        Entity hitEntity = event.getHitEntity();
        if (hitEntity != null && isHerobrine(hitEntity) && projectileSource != null && !(projectileSource instanceof Player))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHerobrineTNTExplode(EntityExplodeEvent event)
    {
        Entity entity = event.getEntity();
        if (entity.getType() == EntityType.PRIMED_TNT && entity.getPersistentDataContainer().has(new NamespacedKey(plugin, PLACED_BY_HEROBRINE_KEY)))
        {
            event.setCancelled(true);
            entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

            for (Player player : Bukkit.getOnlinePlayers())
            {
                if (player.getLocation().distance(entity.getLocation()) < 10)
                {
                    dealExplosionKB(entity, player);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10 * 20, 0));
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        Entity damager = event.getDamager();
        Entity damagee = event.getEntity();

        if (isHerobrine(damagee) && damager instanceof Player)
        {
            Player damagerPlayer = (Player) damager;
            rewardDistributor.addContributingPlayer(damagerPlayer);
        }

        if (isHerobrine(damager) && damagee instanceof Player)
        {
            event.setDamage(8.0f);
        }
    }

    private void breakPortal(Player player)
    {
        Block block = player.getWorld().getBlockAt(player.getLocation());
        if (block.getType() == Material.NETHER_PORTAL)
        {
            block.setType(Material.AIR);
        }
    }

    @EventHandler
    public void onSwitchDimension(PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();
        if (herobrine.getLocation().distance(player.getLocation()) < HEROBRINE_RANGE)
        {
            event.setCancelled(true);
            herobrineSpeak.messageNearbyPlayers(Component.text("Where are you going " + player.getName() + '?').color(NamedTextColor.RED));
            breakPortal(player);
        }
    }
}
