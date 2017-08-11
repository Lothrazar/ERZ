package teamroots.emberroot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.cat.EntityWitherCat;
import teamroots.emberroot.entity.cat.RenderWitherCat;
import teamroots.emberroot.entity.creeper.EntityConcussionCreeper;
import teamroots.emberroot.entity.creeper.RenderConcussionCreeper;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.deer.RenderDeer;
import teamroots.emberroot.entity.dirtslime.EntityDireSlime;
import teamroots.emberroot.entity.dirtslime.RenderDireSlime;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.entity.endermini.RenderEnderminy;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.fairy.RenderFairy;
import teamroots.emberroot.entity.golem.DamageGolem;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.golem.EntityGolemLaser;
import teamroots.emberroot.entity.golem.RenderAncientGolem;
import teamroots.emberroot.entity.golem.RenderEmberPacket;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.hero.RenderFallenHero;
import teamroots.emberroot.entity.knight.EntityFallenKnight;
import teamroots.emberroot.entity.knight.RenderFallenKnight;
import teamroots.emberroot.entity.mount.EntityFallenMount;
import teamroots.emberroot.entity.mount.RenderFallenMount;
import teamroots.emberroot.entity.owl.EntityOwl;
import teamroots.emberroot.entity.owl.EntityOwlEgg;
import teamroots.emberroot.entity.owl.RenderEntityOwlEgg;
import teamroots.emberroot.entity.owl.RenderOwl;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.slime.RenderWaterSlime;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.sprout.RenderSprout;
import teamroots.emberroot.entity.witch.EntityWitherWitch;
import teamroots.emberroot.entity.witch.RenderWitherWitch;
import teamroots.emberroot.entity.wolf.EntityDireWolf;
import teamroots.emberroot.entity.wolf.RenderDirewolf;

public class RegistryManager {
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  public static void registerAll() {
    registerDamageSources();
    registerEntities();
    registerSpawning();
  }
  public static void registerSpawning() {
    List<Biome> allBiomes = new ArrayList<Biome>();
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.COOL)) {
      allBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.DESERT)) {
      allBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.ICY)) {
      allBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.WARM)) {
      allBiomes.add(b.biome);
    }
    allBiomes.addAll(BiomeManager.oceanBiomes);
    for (ConfigSpawnEntity cfg : ConfigManager.entityConfigs) {
      if (cfg.settings.weightedProb <= 0 || cfg.settings.max <= 0) {
        continue;
      }
      if (cfg.settings.useAllBiomes) {
        EntityRegistry.addSpawn(cfg.entityClass, cfg.settings.weightedProb, cfg.settings.min, cfg.settings.max, cfg.typeOfCreature, allBiomes.toArray(new Biome[0]));
      }
      else {
        EntityRegistry.addSpawn(cfg.entityClass, cfg.settings.weightedProb, cfg.settings.min, cfg.settings.max, cfg.typeOfCreature, cfg.getBiomeFilter());
      }
    }
  }
  public static void registerEntities() {
    int id = 0;
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityDeer.NAME), EntityDeer.class, EntityDeer.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityDeer.NAME), intColor(159, 132, 88), intColor(94, 77, 51));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntitySprout.NAME), EntitySprout.class, EntitySprout.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntitySprout.NAME), intColor(136, 189, 33), intColor(165, 232, 42));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFairy.NAME), EntityFairy.class, EntityFairy.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFairy.NAME), intColor(255, 208, 255), intColor(209, 255, 173));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityRainbowSlime.NAME), EntityRainbowSlime.class, EntityRainbowSlime.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityRainbowSlime.NAME), intColor(111, 208, 22), intColor(111, 255, 173));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "ember_projectile"), EntityGolemLaser.class, "ember_projectile", id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityAncientGolem.NAME), EntityAncientGolem.class, EntityAncientGolem.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityAncientGolem.NAME), intColor(48, 38, 35), intColor(79, 66, 61));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFallenHero.NAME), EntityFallenHero.class, EntityFallenHero.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFallenHero.NAME), intColor(159, 255, 222), intColor(222, 111, 51));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityConcussionCreeper.NAME), EntityConcussionCreeper.class, EntityConcussionCreeper.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityConcussionCreeper.NAME), 0x56FF8E, 0xFF0A22);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityOwl.NAME), EntityOwl.class, EntityOwl.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityOwl.NAME), 0xC17949, 0xFFDDC6);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityDireSlime.NAME), EntityDireSlime.class, EntityDireSlime.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityDireSlime.NAME), 0xb9855c, 0x593d29);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "EntityOwlEgg"), EntityOwlEgg.class, "EntityOwlEgg", id++, EmberRootZoo.instance, 64, 10, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityDireWolf.NAME), EntityDireWolf.class, EntityDireWolf.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityDireWolf.NAME), 0x606060, 0xA0A0A0);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityWitherCat.NAME), EntityWitherCat.class, EntityWitherCat.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityWitherCat.NAME), 0x303030, 0xFFFFFF);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityWitherWitch.NAME), EntityWitherWitch.class, EntityWitherWitch.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityWitherWitch.NAME), 0x26520D, 0x905E43);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityEnderminy.NAME), EntityEnderminy.class, EntityEnderminy.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityEnderminy.NAME), 0x27624D, 0x212121);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFallenKnight.NAME), EntityFallenKnight.class, EntityFallenKnight.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFallenKnight.NAME), 0x365A25, 0xA0A0A0);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, EntityFallenMount.NAME), EntityFallenMount.class, EntityFallenMount.NAME, id++, EmberRootZoo.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, EntityFallenMount.NAME), 0x365A25, 0xA0A0A0);
  }
  public static void registerDamageSources() {
    EmberRootZoo.damage_ember = new DamageGolem();
  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerRendering(ModelRegistryEvent event) {
    //item hax
    for (Item item : EmberRootZoo.instance.items) {
      String name = Const.MODID + ":" + item.getUnlocalizedName().replaceAll("item.", "");
      ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }
    RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySprout.class, new RenderSprout.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderFairy.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityRainbowSlime.class, new RenderWaterSlime.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityGolemLaser.class, new RenderEmberPacket(Minecraft.getMinecraft().getRenderManager()));
    RenderingRegistry.registerEntityRenderingHandler(EntityAncientGolem.class, new RenderAncientGolem.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenHero.class, new RenderFallenHero.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityConcussionCreeper.class, new RenderConcussionCreeper.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityDireSlime.class, new RenderDireSlime.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityOwl.class, new RenderOwl.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityDireWolf.class, new RenderDirewolf.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCat.class, new RenderWitherCat.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityWitherWitch.class, new RenderWitherWitch.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityEnderminy.class, new RenderEnderminy.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenKnight.class, new RenderFallenKnight.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenMount.class, new RenderFallenMount.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityOwlEgg.class, new RenderEntityOwlEgg.Factory());
  }
}
