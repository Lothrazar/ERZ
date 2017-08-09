package teamroots.emberroot.config;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamroots.emberroot.Const;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.sprout.EntitySprout;

public class ConfigManager {
  public static Configuration config;
  public static List<ConfigSpawnEntity> entityConfigs;
  private static ConfigSpawnEntity configDeer;
  private static ConfigSpawnEntity sprout;
  private static ConfigSpawnEntity hero;
  private static ConfigSpawnEntity fairy;
  private static ConfigSpawnEntity slime;
  private static ConfigSpawnEntity gol;
  public static void init(File configFile) {
    config = new Configuration(configFile);
    entityConfigs = new ArrayList<ConfigSpawnEntity>();
    configDeer = new ConfigSpawnEntity(EntityDeer.class, EnumCreatureType.CREATURE);
    configDeer.setDefaults(3, 7, 20);
    entityConfigs.add(configDeer);
    sprout = new ConfigSpawnEntity(EntitySprout.class, EnumCreatureType.AMBIENT);
    sprout.setDefaults(3, 7, 20);
    entityConfigs.add(sprout);
    hero = new ConfigSpawnEntity(EntityFallenHero.class, EnumCreatureType.CREATURE);
    hero.setDefaults(1, 1, 15);
    entityConfigs.add(hero);
    fairy = new ConfigSpawnEntity(EntityFairy.class, EnumCreatureType.CREATURE);
    fairy.setDefaults(5, 10, 20);
    entityConfigs.add(fairy);
    slime = new ConfigSpawnEntity(EntityRainbowSlime.class, EnumCreatureType.CREATURE);
    slime.setDefaults(1, 1, 25);
    entityConfigs.add(slime);
    gol = new ConfigSpawnEntity(EntityAncientGolem.class, EnumCreatureType.CREATURE);
    gol.setDefaults(1, 1, 15);
    entityConfigs.add(gol);
    load();
  }
  public static void load() {
    for (ConfigSpawnEntity cfg : entityConfigs) {
      cfg.syncConfig(config);
    }
    EntityDeer.chanceRudolf = config.getInt("ChanceRudolf", configDeer.category, 120, 1, 32767, "The odds of a deer having a red nose.  Lower is more likely to be red.");
    EntityRainbowSlime.canPlaceBlocks = config.getBoolean("CanPlacelocks", slime.category, true, "True means slimes can place blocks on death (water, clay, snow, depends on the color type).  This also gets disabled using mobGriefing gamerule.  ");
    if (config.hasChanged()) {
      config.save();
    }
  }
  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equalsIgnoreCase(Const.MODID)) {
      load();
    }
  }
}
