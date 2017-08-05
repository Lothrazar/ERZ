package teamroots.emberroot;
import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamroots.emberroot.entity.deer.EntityDeer;

public class ConfigManager {
  private static final String category = "mobs";
  public static Configuration config;
  //MOBS
  public static int deerSpawnWeight, sproutSpawnWeight, fairySpawnWeight, golemSpawnWeight, slimeSpawnWeight,
      heroSpawn;
  public static void init(File configFile) {
    if (config == null) {
      config = new Configuration(configFile);
      load();
    }
  }
  public static void load() {
    //    config.addCustomCategoryComment("mobs", "Settings related to mobs.");
    EntityDeer.chanceRudolf = config.getInt("chanceRudolf", category, 120, 1, 32767, "The odds of a deer having a red nose.  Lower is more likely to be red.");
    deerSpawnWeight = config.getInt("deerSpawnWeight", category, 20, 0, 32767, "Configures the spawning frequency of the Deer mob. Higher numbers mean more spawns.");
    sproutSpawnWeight = config.getInt("sproutSpawnWeight", category, 6, 0, 32767, "Configures the spawning frequency of the Sprout mob. Higher numbers mean more spawns.");
    config.addCustomCategoryComment("structures", "Settings related to structures.");
    fairySpawnWeight = config.getInt("fairySpawnWeight", category, 20, 0, 32767, "Configures the generation chance of the Fairy mob. Higher numbers mean more spawns.");
    golemSpawnWeight = config.getInt("golemSpawnWeight", category, 25, 0, 32767, "Configures the generation chance of the Golem mob. Higher numbers mean more spawns.");
    slimeSpawnWeight = config.getInt("rainbowSlimeWeight", category, 3, 0, 32767, "Configures the generation chance of the alternate slime mobs. Higher numbers mean more spawns.");
    heroSpawn = config.getInt("heroSpawnWeigcategory", category, 3, 0, 32767, "Configures the generation chance of the Fallen Hero. Higher numbers mean more spawns.");
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
