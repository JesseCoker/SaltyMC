package me.saltymc.core.entities.bosses.herobrine;

import me.saltymc.core.entities.helpers.PositionHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.*;

public class HerobrineNetherSpread {

    private final Herobrine herobrine;

    private static final Random random = new Random();

    public HerobrineNetherSpread(Herobrine herobrine)
    {
        this.herobrine = herobrine;
    }

    private final ArrayList<Location> spores = new ArrayList<>();

    public void spread()
    {
        newSpore();
        spreadToSurroundingLocations();
    }

    private void newSpore()
    {
        Location herobrineLocation = herobrine.getEntity().getLocation();
        Location sporeLocation = PositionHelper
                .getRandomLocationInCircle(herobrineLocation, HerobrineSettings.NetherSpread.SPREAD_RADIUS);
        sporeLocation.setY(sporeLocation.getWorld().getHighestBlockAt(sporeLocation).getY());
        spores.add(sporeLocation);
        spreadToLocation(sporeLocation);
    }

    private void spreadToSurroundingLocations()
    {
        if (spores.size() > HerobrineSettings.NetherSpread.MAX_SPORES) spores.remove(random.nextInt(spores.size()));
        for (Location spore : spores)
        {
            int newX = random.nextInt(3) - 1;
            int newY = random.nextInt(3) - 1;
            int newZ = random.nextInt(3) - 1;
            spore = spore.add(new Vector(newX, newY, newZ));
            spreadToLocation(spore);
        }
    }

    private static final HashMap<Material, List<Material>> materialHashMap = new HashMap<>();
    static
    {
        List<Material> netherrack = Collections.singletonList(Material.NETHERRACK);
        List<Material> grass = Arrays.asList(Material.CRIMSON_NYLIUM, Material.WARPED_NYLIUM);
        List<Material> wood = Arrays.asList(Material.CRIMSON_STEM, Material.WARPED_STEM);

        materialHashMap.put(Material.DIRT, netherrack);
        materialHashMap.put(Material.GRASS_BLOCK, grass);
        materialHashMap.put(Material.STONE, netherrack);
        materialHashMap.put(Material.PODZOL, grass);
        materialHashMap.put(Material.SAND, netherrack);
        materialHashMap.put(Material.GRAVEL, netherrack);
        materialHashMap.put(Material.OAK_LOG, wood);
        materialHashMap.put(Material.BIRCH_LOG, wood);
        materialHashMap.put(Material.SPRUCE_LOG, wood);
        materialHashMap.put(Material.DARK_OAK_LOG, wood);
        materialHashMap.put(Material.ACACIA_LOG, wood);
        materialHashMap.put(Material.CHERRY_LOG, wood);
        materialHashMap.put(Material.MANGROVE_LOG, wood);
    }

    private void spreadToLocation(Location location)
    {
        Block block = location.getBlock();
        List<Material> materials = materialHashMap.get(block.getType());
        if (materials != null)
        {
            block.setType(materials.get(random.nextInt(materials.size())));
        }
    }
}
