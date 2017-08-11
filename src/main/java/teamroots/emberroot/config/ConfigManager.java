package teamroots.emberroot.config;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamroots.emberroot.Const;
import teamroots.emberroot.entity.cat.EntityWitherCat;
import teamroots.emberroot.entity.creeper.EntityConcussionCreeper;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.dirtslime.EntityDireSlime;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.knight.EntityFallenKnight;
import teamroots.emberroot.entity.mount.EntityFallenMount;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.witch.EntityWitherWitch;
import teamroots.emberroot.entity.wolf.EntityDireWolf;

public class ConfigManager {
  public static Configuration config;
  public static List<ConfigSpawnEntity> entityConfigs;
  public static boolean renderDebugHitboxes;
  public static void init(File configFile) {
    config = new Configuration(configFile);
    entityConfigs = new ArrayList<ConfigSpawnEntity>();
    //spawn is min,max,weight.      props is health, attack, speed, followRange
    //-1 means unlisted from config
    //?? is this true? # Attack damage of Enderminies. 7=Enderman damage, 3=Zombie damage
    entityConfigs.add(EntityWitherCat.config.setDefaultSpawns(0, 0, 0).setDefaultProperties(20, 2, 0.25F, 32));
    entityConfigs.add(EntityConcussionCreeper.config.setDefaultSpawns(1, 1, 17).setDefaultProperties(20, -1, 0.25F, 32));
    entityConfigs.add(EntityDeer.config.setDefaultSpawns(3, 7, 20).setDefaultProperties(15, -1, 0.20000000298023224F, 32));
    entityConfigs.add(EntityDireSlime.config.setDefaultSpawns(1, 1, 17).setDefaultProperties(12, -1, 0.3F, 32));
    entityConfigs.add(EntityEnderminy.config.setDefaultSpawns(1, 1, 17).setDefaultProperties(15, 10, 0.3F, 32));
    entityConfigs.add(EntityFairy.config.setDefaultSpawns(5, 10, 20).setDefaultProperties(18, -1, -1, 16));
    entityConfigs.add(EntityAncientGolem.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(25, 5, 0.5F, 16));
    entityConfigs.add(EntityFallenHero.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(25, 4, 0.23F, 75));
    entityConfigs.add(EntityRainbowSlime.config.setDefaultSpawns(1, 1, 25).setDefaultProperties(-1, -1, 0.2F, 32));
    entityConfigs.add(EntityDireWolf.config.setDefaultSpawns(1, 1, 22).setDefaultProperties(20, 10, 0.5F, 40));
    entityConfigs.add(EntityWitherWitch.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(30, -1, 0.22F, 16));
    entityConfigs.add(EntityOwl.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(10, -1, -1, -1));
    entityConfigs.add(EntityFallenMount.config.setDefaultSpawns(0, 0, 0).setDefaultProperties(30, -1, -1, -1));
    entityConfigs.add(EntityFallenKnight.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(20, 4, -1, 32));
    entityConfigs.add(EntitySprout.config.setDefaultSpawns(2, 4, 1).setDefaultProperties(6, 0, 0.2F, 32));
    load();
  }
  public static void load() {
    for (ConfigSpawnEntity cfg : entityConfigs) {
      cfg.syncConfig(config);
    }
    EntityDeer.chanceRudolf = config.getInt("ChanceRudolf", EntityDeer.config.category, 120, 1, 32767, "The odds of a deer having a red nose.  Lower is more likely to be red.");
    EntityRainbowSlime.canPlaceBlocks = config.getBoolean("CanPlacelocks", EntityRainbowSlime.config.category, true, "True means slimes can place blocks on death (water, clay, snow, depends on the color type).  This also gets disabled using mobGriefing gamerule.  ");
    
    renderDebugHitboxes = config.getBoolean("renderDebugHitboxes", "global", false, "For TESTING purposes only: This renders a very ugly white shape along with the mob to show its physical hitbox.");
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
