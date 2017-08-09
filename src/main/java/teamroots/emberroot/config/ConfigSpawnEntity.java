package teamroots.emberroot.config;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;

/**
 * for use with config files and linkin to EntityRegistry.addSpawn
 * 
 * Add a spawn entry for the supplied entity in the supplied {@link Biome} list
 * entityClass Entity class added weightedProb Probability min Min spawn count
 * max Max spawn count typeOfCreature Type of spawn biomes List of biomes
 */
public class ConfigSpawnEntity {
  public static final String[] defaultBiomes = new String[] {
      "minecraft:ocean",
      "minecraft:plains",
      "minecraft:desert",
      "minecraft:extreme_hills",
      "minecraft:forest",
      "minecraft:taiga",
      "minecraft:swampland",
      "minecraft:river",
      "minecraft:hell",
      "minecraft:ice_flats",
      "minecraft:beaches",
      "minecraft:forest_hills", "minecraft:jungle", "minecraft:birch_forest", "minecraft:roofed_forest", "minecraft:savanna", "minecraft:mesa"
  };
  public Class<? extends EntityLiving> entityClass;
  public int weightedProb;
  public int min;
  public int max;
  public EnumCreatureType typeOfCreature;
  public String[] biomes;
  private int defaultMin;
  private int defaultMax;
  private int defaultWeight;
  String category;
  public boolean useAllBiomes;
  public ConfigSpawnEntity(Class<? extends EntityLiving> clz, EnumCreatureType type) {
    this.entityClass = clz;
    this.typeOfCreature = type;
    category = clz.getSimpleName();
  }
  public void setDefaults(int pmin, int pmax, int pweight) {
    defaultMin = pmin;
    defaultMax = pmax;
    defaultWeight = pweight;
  }
  public void syncConfig(Configuration config) {
    min = config.getInt("minSpawnCount", category, defaultMin, 0, 500, "Smallest spawn group.");
    max = config.getInt("maxSpawnCount", category, defaultMax, 0, 500, "Biggest spawn group.");
    if (max < min) {
      max = min + 1;//the least we could do
    }
    weightedProb = config.getInt("weightProbability", category, defaultWeight, 0, 500, "Configures the spawning frequency. Higher numbers mean more spawns.");
    useAllBiomes = config.getBoolean("allBiomes", category, true, "Try to spawn in every biome.  If false, it will use the whitelist in this config ");
    biomes = config.getStringList("biomeWhitelist", category, defaultBiomes, "Biomes this will spawn into.  Add support for any modded biome here.  Ignored whenever allBiomes is true. ");
  }
  public Biome[] getBiomeFilter() {
    List<Biome> allBiomes = new ArrayList<Biome>();
    Biome found;
    for (String b : biomes) {
      found = Biome.REGISTRY.getObject(new ResourceLocation(b));
      if (found != null) {
        allBiomes.add(found);
      }
    }
    return allBiomes.toArray(new Biome[0]);
  }
}
