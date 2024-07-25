package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.Main;
import me.saltymc.core.entities.CustomBoss;
import me.saltymc.core.helpers.HeadGetter;
import me.saltymc.core.items.item.DespisedSoulFragment;
import me.saltymc.core.items.weapon.HerobrineAxe;
import me.saltymc.core.items.weapon.HerobrineSword;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Herobrine extends CustomBoss
{
    public Herobrine(Main plugin)
    {
        super(plugin);
        herobrineMoveset = new HerobrineMoveset(plugin, this);
    }

    @Override
    protected EntityType getEntityType()
    {
        return EntityType.ZOMBIE;
    }

    @Override
    protected double getMaxHealth()
    {
        return 600.0;
    }

    @Override
    protected double getMovementSpeed()
    {
        return 0.4;
    }

    @Override
    protected TextComponent getBossName()
    {
        return Component.text("Herobrine").color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD);
    }

    @Override
    protected BossBar.Color getBossBarColor()
    {
        return BossBar.Color.RED;
    }

    @Override
    protected BossBar.Overlay getBossBarOverlay()
    {
        return BossBar.Overlay.NOTCHED_20;
    }

    @Override
    protected void setup()
    {
        Zombie zombie = (Zombie) getEntity();
        zombie.setAdult();
        zombie.setShouldBurnInDay(false);
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0));
    }

    @Override
    protected void giveArmor()
    {
        EntityEquipment entityEquipment = getEntity().getEquipment();
        if (entityEquipment != null)
        {
            entityEquipment.setHelmet(HeadGetter.getPlayerHead("Memerfront", Component.text("Herobrine's Head")));
            entityEquipment.setChestplate(null);
            entityEquipment.setLeggings(null);
            entityEquipment.setBoots(null);

            entityEquipment.setHelmetDropChance(0.0f);
        }
    }

    @Override
    protected void setRewards()
    {
        HerobrineRewards.setRewards(plugin, customBossRewards);
    }

    private final HerobrineMoveset herobrineMoveset;
    private final HerobrineSpeak herobrineSpeak = new HerobrineSpeak(this);
    private final HerobrineTeleport herobrineTeleport = new HerobrineTeleport(this);
    private final HerobrineBlockAttack herobrineBlockAttack = new HerobrineBlockAttack(this);
    private final HerobrineNetherSpread herobrineNetherSpread = new HerobrineNetherSpread(this);
    private final HerobrineHeal herobrineHeal = new HerobrineHeal(this, herobrineSpeak);

    public HerobrineTeleport getHerobrineTeleport()
    {
        return herobrineTeleport;
    }

    @Override
    protected void tick()
    {
        targetNearestPlayer();
        attackNearestPlayer();
        herobrineSpeak.nextSpeech();
        herobrineMoveset.nextMove();
        herobrineTeleport.attemptTeleport();
        herobrineNetherSpread.spread();
    }

    private void targetNearestPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && getDistanceToPlayer(nearestPlayer) <= getBossRange())
        {
            Zombie zombie = (Zombie) getEntity();
            zombie.setTarget(nearestPlayer);
            zombie.lookAt(nearestPlayer);
        }
    }

    private void attackNearestPlayer()
    {
        Player nearestPlayer = getNearestPlayer();
        if (nearestPlayer != null && getDistanceToPlayer(nearestPlayer) <= HerobrineSettings.Attack.REACH)
        {
            switchWeapon();
            nearestPlayer.setNoDamageTicks(0);
            getEntity().attack(nearestPlayer);
        }
    }

    private void switchWeapon()
    {
        if (random.nextInt(HerobrineSettings.Attack.USE_AXE_CHANCE) == 0)
        {
            equipItemInMainHand(new HerobrineAxe(plugin).getItem(1));
        }
        else
        {
            equipItemInMainHand(new HerobrineSword(plugin).getItem(1));
        }
    }

    private void dropDespisedSoul()
    {
        if (random.nextInt(HerobrineSettings.DropItem.DROP_DESPISED_SOUL_CHANCE) == 0)
        {
            Location location = getEntity().getLocation();
            location.getWorld().dropItemNaturally(location, new DespisedSoulFragment(plugin).getItem(1));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        super.onDamage(event);
        if (isThisEntity(event.getEntity()))
        {
            herobrineBlockAttack.cancelInvalidDamageByEntity(event);
            herobrineBlockAttack.blockValidAttacks(event);
        }
    }

    @EventHandler
    public void onKillPlayer(PlayerDeathEvent event)
    {
        herobrineHeal.healOnKill(event);
    }

    @EventHandler
    public void onTakeDamage(EntityDamageEvent event)
    {
        if (isThisEntity(event.getEntity()))
        {
            herobrineBlockAttack.cancelInvalidDamage(event);
            dropDespisedSoul();
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        if (getDistanceToPlayer(player) < getBossRange())
        {
            new HerobrineDestroyPlacedBlock(this).punish(event);
        }
    }

    @EventHandler
    public void onTransform(EntityTransformEvent event)
    {
        if (isThisEntity(event.getEntity())) event.setCancelled(true);
    }
}