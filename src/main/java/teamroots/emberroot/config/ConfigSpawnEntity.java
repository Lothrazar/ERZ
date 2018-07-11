package teamroots.emberroot.config;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import teamroots.emberroot.util.EntityUtil;

/**
 * for use with config files and linkin to EntityRegistry.addSpawn
 * 
 * Add a spawn entry for the supplied entity in the supplied {@link Biome} list entityClass Entity class added weightedProb Probability min Min spawn count max Max spawn count typeOfCreature Type of
 * spawn biomes List of biomes
 * 
 * https://minecraft.gamepedia.com/Data_values#Biome_IDs
 */
public class ConfigSpawnEntity {

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

  public ConfigSpawnEntity setDefaultProperties(int dhealth, float attack, int follow) {
    this.defaults.maxHealth = dhealth;
    this.defaults.attack = attack;
    this.defaults.followRange = follow;
    this.defaults.useAllBiomes = true;// just the default
    return this;
  }

  public ConfigSpawnEntity setSpeeds(float mSpeed) {
    this.defaults.movementSpeed = mSpeed;
    return this;
  }

  public ConfigSpawnEntity setDefaultSpawns(int pmin, int pmax, int pweight) {
    defaults.min = pmin;
    defaults.max = pmax;
    defaults.weightedProb = pweight;
    return this;
  }

  public ConfigSpawnEntity setDefaultBiomesAll() {
    defaults.useAllBiomes = true;
    return this;
  }

  public ConfigSpawnEntity setDefaultBiome(String[] biomelist) {
    defaults.useAllBiomes = false;
    defaults.biomes = biomelist;
    return this;
  }

  public void syncConfig(Configuration config) {
    settings.min = config.getInt("minSpawnCount", category, defaults.min, 0, 32, "Smallest spawn group.");
    settings.max = config.getInt("maxSpawnCount", category, defaults.max, 0, 32, "Biggest spawn group.");
    if (settings.max < settings.min) {
      settings.max = settings.min + 1;//the least we could do
    }
    settings.weightedProb = config.getInt("weightProbability", category, defaults.weightedProb, 0, 100, "Configures the spawning frequency. Higher numbers mean more spawns.");
    settings.useAllBiomes = config.getBoolean("allBiomes", category, defaults.useAllBiomes, "Try to spawn in every biome.  If false, it will use the whitelist in this config ");
    settings.biomes = config.getStringList("biomeWhitelist", category, defaults.biomes, "Biomes this will spawn into.  Add support for any modded biome here.  Ignored whenever allBiomes is true. https://minecraft.gamepedia.com/Data_values#Biome_IDs");
    settings.maxHealth = config.getInt("maxHealth", category, defaults.maxHealth, 1, 100, "Max health of the mob");
    if (settings.followRange >= 0) {
      settings.followRange = config.getInt("followRange", category, defaults.followRange, 0, 32, "Base follow range");
    }
    //-1 means either property is invalid, or is locked
    if (defaults.attack >= 0) {
      settings.attack = config.getFloat("baseDamage", category, defaults.attack, 0, 100, "Base attack, before weapons and buffs");
    }
    if (defaults.movementSpeed > 0) {
      settings.movementSpeed = config.getFloat("movementSpeed", category, defaults.movementSpeed, 0, 2, "Base speed, before buffs.  (Does not apply to living mobs, you must kill and respawn to see new speed get applied)");
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

  @Override
  public String toString() {
    return this.settings.toString();
  }

  /**
   * some settings arent set in a static registration way, they must be set for each instance spawned such as attack, health, speed
   * 
   * @param living
   * @param settings
   */
  public static void syncInstance(EntityLivingBase living, MobProperties settings) {
    EntityUtil.setMaxHealth(living, settings.maxHealth);
    if (settings.attack >= 0)
      EntityUtil.setBaseDamage(living, settings.attack);
    if (settings.followRange >= 0)
      EntityUtil.setFollow(living, settings.followRange);
    if (settings.movementSpeed > 0) {
      EntityUtil.setSpeed(living, settings.movementSpeed);
    }
  }

  /**
   * just a simple struct
   *
   * TODO: would be better with getter/setter pattern BUT its not so bad, the config system enforces min/max/valid values for us already just dont ever set them again at runtime
   */
  public static class MobProperties {

    public int weightedProb;
    public int min;
    public int max;
    public String[] biomes = new String[0];
    public int maxHealth;
    public float attack;
    public boolean useAllBiomes = true;
    public int followRange;
    public float movementSpeed = 0;

    @Override
    public String toString() {
      String s = " min " + this.min + " max " + this.max + " weight " + this.weightedProb + " attack  " + this.attack + " follow" + this.followRange + " allBiomes : " + this.useAllBiomes;
      s += System.lineSeparator();
      s += "BIOMES: ";
      for (String b : this.biomes) {
        s += b + ",";
      }
      return s;
    }
  }
}
