package teamroots.emberroot;
import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigManager {
  public static Configuration config;
  //MOBS
  public static int deerSpawnWeight, sproutSpawnWeight, fairySpawnWeight, golemSpawnWeight;
  public static void init(File configFile) {
    if (config == null) {
      config = new Configuration(configFile);
      load();
    }
  }
  public static void load() {
//    config.addCustomCategoryComment("mobs", "Settings related to mobs.");
    deerSpawnWeight = config.getInt("deerSpawnWeight", "mobs", 20, 0, 32767, "Configures the spawning frequency of the Deer mob. Higher numbers mean more spawns.");
    sproutSpawnWeight = config.getInt("sproutSpawnWeight", "mobs", 6, 0, 32767, "Configures the spawning frequency of the Sprout mob. Higher numbers mean more spawns.");
    config.addCustomCategoryComment("structures", "Settings related to structures.");
    fairySpawnWeight = config.getInt("fairySpawnWeight", "mobs", 20, 0, 32767, "Configures the generation chance of the Fairy mob. Higher numbers mean more spawns.");
    golemSpawnWeight = config.getInt("golemSpawnWeight", "mobs", 25, 0, 32767, "Configures the generation chance of the Golem mob. Higher numbers mean more spawns.");
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
