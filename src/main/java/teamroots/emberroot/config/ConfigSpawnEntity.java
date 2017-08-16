package teamroots.emberroot.config;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import teamroots.emberroot.util.EntityUtil;

/**
 * for use with config files and linkin to EntityRegistry.addSpawn
 * 
 * Add a spawn entry for the supplied entity in the supplied {@link Biome} list
 * entityClass Entity class added weightedProb Probability min Min spawn count
 * max Max spawn count typeOfCreature Type of spawn biomes List of biomes
 * 
 * https://minecraft.gamepedia.com/Data_values#Biome_IDs
 */
public class ConfigSpawnEntity {
  public static final String[] defaultBiomes = new String[] {
      "minecraft:plains", "minecraft:forest", "minecraft:swampland", "minecraft:savanna", "minecraft:mesa"
  };
  public Class<? extends EntityLiving> entityClass;
  public EnumCreatureType typeOfCreature;
  /**
   * defualts set by mod, defaults for config values before config file applies
   */
  public MobProperties defaults = new MobProperties();
  /**
   * values read from config files
   */
  public MobProperties settings = new MobProperties();
  String category;
  public ConfigSpawnEntity(Class<? extends EntityLiving> clz, EnumCreatureType type) {
    this.entityClass = clz;
    this.typeOfCreature = type;
    category = clz.getSimpleName();
  }
  public ConfigSpawnEntity setDefaultProperties(int dhealth, float attack,  int follow) {
    this.defaults.maxHealth = dhealth;
    this.defaults.attack = attack;
    this.defaults.followRange = follow;
    return this;
  }
  public ConfigSpawnEntity setDefaultSpawns(int pmin, int pmax, int pweight) {
    return setDefaultSpawns(pmin, pmax, pweight, true);
  }
  public ConfigSpawnEntity setDefaultSpawns(int pmin, int pmax, int pweight, boolean useAllBiomes) {
    defaults.min = pmin;
    defaults.max = pmax;
    defaults.weightedProb = pweight;
    defaults.useAllBiomes = useAllBiomes;
    return this;
  }
  public ConfigSpawnEntity setDefaultBiome(boolean all, String[] biomelist) {
    defaults.useAllBiomes = all;
    defaults.biomes = biomelist;
    return this;
  }
  public void syncConfig(Configuration config) {
    settings.min = config.getInt("minSpawnCount", category, defaults.min, 0, 500, "Smallest spawn group.");
    settings.max = config.getInt("maxSpawnCount", category, defaults.max, 0, 500, "Biggest spawn group.");
    if (settings.max < settings.min) {
      settings.max = settings.min + 1;//the least we could do
    }
    settings.weightedProb = config.getInt("weightProbability", category, defaults.weightedProb, 0, 500, "Configures the spawning frequency. Higher numbers mean more spawns.");
    settings.useAllBiomes = config.getBoolean("allBiomes", category, defaults.useAllBiomes, "Try to spawn in every biome.  If false, it will use the whitelist in this config ");
    settings.biomes = config.getStringList("biomeWhitelist", category, defaultBiomes, "Biomes this will spawn into.  Add support for any modded biome here.  Ignored whenever allBiomes is true. https://minecraft.gamepedia.com/Data_values#Biome_IDs");
    settings.maxHealth = config.getInt("maxHealth", category, defaults.maxHealth, 1, 100, "Max health of the mob");
    if (settings.followRange >= 0) {
      settings.followRange = config.getInt("followRange", category, defaults.followRange, 0, 2, "Base follow range");
    }
    //-1 means either property is invalid, or is locked

    if (defaults.attack >= 0) {
      settings.attack = config.getFloat("baseDamage", category, defaults.attack, 0, 100, "Base attack, before weapons and buffs");
    }
  }
  public Biome[] getBiomeFilter() {
    List<Biome> allBiomes = new ArrayList<Biome>();
    Biome found;
    for (String b : settings.biomes) {
      found = Biome.REGISTRY.getObject(new ResourceLocation(b));
      if (found != null) {
        allBiomes.add(found);
      }
    }
    return allBiomes.toArray(new Biome[0]);
  }
  /**
   * some settings arent set in a static registration way, they must be set for
   * each instance spawned such as attack, health, speed
   * 
   * @param living
   * @param settings
   */
  public static void syncInstance(EntityLivingBase living, MobProperties settings) {
    EntityUtil.setMaxHealth(living, settings.maxHealth);
  //  System.out.println("syncInstance SET SPEED " + living.getName() + " " + settings.speed);
//    if (settings.speed > 0)
//      EntityUtil.setSpeed(living, settings.speed);
    if (settings.attack >= 0)
      EntityUtil.setBaseDamage(living, settings.attack);
    if (settings.followRange >= 0)
      EntityUtil.setFollow(living, settings.followRange);  
//    entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(s);
      
  }
  /**
   * just a simple struct
   *
   * TODO: would be better with getter/setter pattern BUT its not so bad, the
   * config system enforces min/max/valid values for us already just dont ever
   * set them again at runtime
   */
  public static class MobProperties {
    public int weightedProb;
    public int min;
    public int max;
    public String[] biomes;
    public int maxHealth;
    public float attack;
    public boolean useAllBiomes;
   // public double speed;
    public int followRange;
  }
}
