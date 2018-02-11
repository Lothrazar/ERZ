package teamroots.emberroot.config;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import teamroots.emberroot.Const;
import teamroots.emberroot.entity.cat.EntityWitherCat;
import teamroots.emberroot.entity.creeper.EntityConcussionCreeper;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.frozen.EntityFrozenKnight;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.knight.EntityFallenKnight;
import teamroots.emberroot.entity.mount.EntityFallenMount;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.slimedirt.EntityDireSlime;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.spritegreater.EntityGreaterSprite;
import teamroots.emberroot.entity.spriteling.EntitySpriteling;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.witch.EntityWitherWitch;
import teamroots.emberroot.entity.wolfdire.EntityDireWolf;
import teamroots.emberroot.entity.wolftimber.EntityTimberWolf;

public class ConfigManager {
  public static final int LIGHT_LEVEL = 7;
  public static Configuration config;
  public static List<ConfigSpawnEntity> entityConfigs;
  public static boolean renderDebugHitboxes;
  public static void init(File configFile) {
    config = new Configuration(configFile);
    entityConfigs = new ArrayList<ConfigSpawnEntity>();
    //spawn is min,max,weight.      props is health, attack, speed, followRange
    //-1 means unlisted from config
    //?? is this true? # Attack damage of Enderminies. 7=Enderman damage, 3=Zombie damage
    //default spawns is (min, max, weight)                     properties are (health, attack, followDist)
    entityConfigs.add(EntityWitherCat.config.setDefaultSpawns(1, 1, 1).setDefaultProperties(20, 2, 32));
    entityConfigs.add(EntityConcussionCreeper.config.setDefaultSpawns(1, 1, 30).setDefaultProperties(20, -1, 32));
    entityConfigs.add(EntityDeer.config.setDefaultSpawns(3, 7, 20).setDefaultProperties(15, -1, 32));
    entityConfigs.add(EntityDireSlime.config.setDefaultSpawns(1, 1, 1).setDefaultProperties(12, 2, 32).setDefaultBiome( new String[] {
        "minecraft:plains", "minecraft:hell", "minecraft:sky", "minecraft:swampland", "minecraft:taiga",
        "minecraft:desert_hills",
        "minecraft:mutated_jungle",
        "minecraft:savanna_rock" }));
    entityConfigs.add(EntityEnderminy.config.setDefaultSpawns(2, 5, 17).setDefaultProperties(15, 2, 32));
    entityConfigs.add(EntityFairy.config.setDefaultSpawns(1, 4, 20).setDefaultProperties(18, -1, 16));
    entityConfigs.add(EntityAncientGolem.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(25, 5, 16));
    entityConfigs.add(EntityFallenHero.config.setDefaultSpawns(1, 1, 5).setDefaultProperties(25, 4, 75));
    entityConfigs.add(EntityRainbowSlime.config.setDefaultSpawns(1, 1, 1).setDefaultProperties(-1, -1, 32).setDefaultBiomesAll());
  
    entityConfigs.add(EntityDireWolf.config.setDefaultSpawns(1, 1, 4).setDefaultProperties(16, 1, 40).setSpeeds(0.2F)); 
    entityConfigs.add(EntityWitherWitch.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(30, -1, 16));
    entityConfigs.add(EntityOwl.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(10, -1, -1));
    entityConfigs.add(EntityFallenMount.config.setDefaultSpawns(0, 0, 0).setDefaultProperties(30, -1, -1));
    entityConfigs.add(EntityFallenKnight.config.setDefaultSpawns(1, 3, 20).setDefaultProperties(20, 4, 32));
    entityConfigs.add(EntitySprout.config.setDefaultSpawns(2, 4, 10).setDefaultProperties(6, -1, 32));//0.2F,
    //roots1 direwolf
    entityConfigs.add(EntityTimberWolf.config.setDefaultSpawns(1, 5, 10).setDefaultProperties(32, 4, 32)
        .setDefaultBiome( new String[] { "minecraft:ice_flats", "minecraft:extreme_hills", "minecraft:savanna", "minecraft:mesa", "minecraft:roofed_forest", "minecraft:taiga", "minecraft:taiga_cold", "minecraft:ice_mountains" }));
    //smallest ones get a bit of a wider spawn
    entityConfigs.add(EntitySpriteling.config.setDefaultSpawns(1, 1, 1).setDefaultProperties(8, 4, 32).setDefaultBiome( new String[] {
        "minecraft:plains", "minecraft:hell", "minecraft:sky", "minecraft:jungle_hills" }));
    //these babies only spawn in end
    entityConfigs.add(EntitySprite.config.setDefaultSpawns(1, 1, 1).setDefaultProperties(20, 6, 32)
        .setDefaultBiome( new String[] { "minecraft:ice_mountains", "minecraft:taiga", "minecraft:ice_mountains", "minecraft:ice_flats", "minecraft:frozen_river", "minecraft:frozen_ocean", "minecraft:taiga_cold", "minecraft:taiga_cold_hills", "minecraft:mutated_ice_flats", "minecraft:mutated_taiga_cold"
        }));
    entityConfigs.add(EntityGreaterSprite.config.setDefaultSpawns(1, 1, 1).setDefaultProperties(26, 6, 32).setDefaultBiome( new String[] { "minecraft:sky" }));
    entityConfigs.add(EntityFrozenKnight.config.setDefaultSpawns(1, 1, 15).setDefaultProperties(20, 4, 32).setDefaultBiomesAll());
    load();
  }
  private static void load() {
    for (ConfigSpawnEntity cfg : entityConfigs) {
      cfg.syncConfig(config);
    }
    EntityDeer.chanceRudolf = config.getInt("ChanceRudolf", EntityDeer.config.category, 120, 1, 32767, "The odds of a deer having a red nose.  Lower is more likely to be red.");
    EntityRainbowSlime.canPlaceBlocks = config.getBoolean("CanPlacelocks", EntityRainbowSlime.config.category, true, "True means slimes can place blocks on death (water, clay, snow, depends on the color type).  This also gets disabled using mobGriefing gamerule.  ");
    EntityRainbowSlime.canPotionsDeath = config.getBoolean("CanSpawnPotions", EntityRainbowSlime.config.category, true, "True means slimes can spawn lingering potions on death (not all, depends on the color type).  ");
    EntityDeer.lureWithWheat = config.getBoolean("LureWithWheat", EntityDeer.config.category, true, "You can lure deer to follow you with wheat.");
    EntityEnderminy.attackIfLookingAtPlayer = config.getBoolean("AttackIfLookedAt", EntityEnderminy.config.category, false, "Changing this to true means looking at this mob will cause it to attack just like a regular enderman.");
    EntityFairy.tameWithGlowstone = config.getBoolean("Tameable", EntityFairy.config.category, true, "You can tame this with glowstone so it follows you.");
    EntityFrozenKnight.attacksVillagers = config.getBoolean("AttacksVillagers", EntityFrozenKnight.config.category, false, "This will hunt out and slay villagers.");
    EntityFrozenKnight.avoidWolves = config.getBoolean("AvoidWolves", EntityFrozenKnight.config.category, true, "This will avoid wolves.");
    EntityFrozenKnight.spawnsWithArmor = config.getBoolean("SpawnsWithArmor", EntityFrozenKnight.config.category, true, "This spawns with random armor.");
    EntityFrozenKnight.appliesSlowPotion = config.getBoolean("AppliesSlowPotion", EntityFrozenKnight.config.category, true, "Applies slowness to player on attack.");
    EntityAncientGolem.attacksSomeMobs = config.getBoolean("AttacksMobs", EntityAncientGolem.config.category, true, "Sometimes attacks hostile mobs.");
    EntityFallenHero.avoidCreepers = config.getBoolean("AvoidsCreepers", EntityFallenHero.config.category, true, "Avoids Creepers.");
    EntityFallenHero.temptWithGold = config.getBoolean("TemptWithGold", EntityFallenHero.config.category, true, "Lure this with gold ingots (remember it is passive to the player initially, and fights off some hostiles).");
    EntityFallenKnight.attackVillagers = config.getBoolean("AttackVillagers", EntityFallenKnight.config.category, false, "Attacks Villagers.");
    EntityFallenKnight.fallenKnightChanceMounted = config.getFloat("ChanceSpawnsWithMount", EntityFallenKnight.config.category, 0.75F, 0, 1, "Percentage chance this spawns with a mount.");
    EntityOwl.temptSpiderEye = config.getBoolean("TemptAndBreedSpiderEye", EntityOwl.config.category, true, "Spider Eye used to tempt and breed.");
    EntitySprout.canTempt = config.getBoolean("CanTempt", EntitySprout.config.category, true, "Tempt this with seeds.");
    EntityDireWolf.direWolfPackAttackEnabled = config.getBoolean("PackAttack", EntityDireWolf.config.category, true, "They attack as a pack.");
    EntityTimberWolf.attackSkeleton = config.getBoolean("AttackSkeleton", EntityTimberWolf.config.category, true, "Attacks Skeletons.");
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
