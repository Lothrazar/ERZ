package teamroots.emberroot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.deer.RenderDeer;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.fairy.RenderFairy;
import teamroots.emberroot.entity.golem.DamageGolem;
import teamroots.emberroot.entity.golem.EntityAncientGolem;
import teamroots.emberroot.entity.golem.EntityEmberProjectile;
import teamroots.emberroot.entity.golem.RenderAncientGolem;
import teamroots.emberroot.entity.golem.RenderEmberPacket;
import teamroots.emberroot.entity.hero.EntityFallenHero;
import teamroots.emberroot.entity.hero.RenderFallenHero;
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.slime.RenderWaterSlime;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.sprout.RenderSprout;

public class RegistryManager {
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  public static void registerAll() {
    Roots.damage_ember = new DamageGolem();
    int id = 0;
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "deer"), EntityDeer.class, "deer", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "deer"), intColor(159, 132, 88), intColor(94, 77, 51));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "sprout"), EntitySprout.class, "sprout", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "sprout"), intColor(136, 189, 33), intColor(165, 232, 42));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "fairy"), EntityFairy.class, "fairy", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "fairy"), intColor(255, 208, 255), intColor(209, 255, 173));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "rainbowslime"), EntityRainbowSlime.class, "rainbowslime", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "rainbowslime"), intColor(111, 208, 22), intColor(111, 255, 173));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "ember_projectile"), EntityEmberProjectile.class, "ember_projectile", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "ancient_golem"), EntityAncientGolem.class, "ancient_golem", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "ancient_golem"), intColor(48, 38, 35), intColor(79, 66, 61));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "hero"), EntityFallenHero.class, "hero", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "hero"), intColor(159, 255, 222), intColor(222, 111, 51));
    List<Biome> biomesDeer = new ArrayList<Biome>();
    List<Biome> biomesFairy = new ArrayList<Biome>();
    List<Biome> biomesSprout = new ArrayList<Biome>();
    List<Biome> biomesSlimes = new ArrayList<Biome>();
    List<Biome> heroBiomes = new ArrayList<Biome>();
    List<BiomeEntry> biomeGolems = new ArrayList<BiomeEntry>();
    biomeGolems.addAll(BiomeManager.getBiomes(BiomeType.COOL));
    biomeGolems.addAll(BiomeManager.getBiomes(BiomeType.DESERT));
    biomeGolems.addAll(BiomeManager.getBiomes(BiomeType.ICY));
    biomeGolems.addAll(BiomeManager.getBiomes(BiomeType.WARM));
    List<Biome> biomes = new ArrayList<Biome>();
    for (BiomeEntry b : biomeGolems) {
      biomes.add(b.biome);
    }
    biomes.addAll(BiomeManager.oceanBiomes);
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.COOL)) {
      biomesDeer.add(b.biome);
      biomesSprout.add(b.biome);
      biomesSlimes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.DESERT)) {
      biomesFairy.add(b.biome);
      biomesSprout.add(b.biome);
      biomesSlimes.add(b.biome);
      heroBiomes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.ICY)) {
      biomesDeer.add(b.biome);
      biomesSlimes.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.WARM)) {
      biomesDeer.add(b.biome);
      biomesFairy.add(b.biome);
      biomesSprout.add(b.biome);
      heroBiomes.add(b.biome);
    }
    biomesFairy.addAll(BiomeManager.oceanBiomes);
    biomesDeer.addAll(BiomeManager.oceanBiomes);
    biomesSprout.addAll(BiomeManager.oceanBiomes);
    EntityRegistry.addSpawn(EntityFallenHero.class, ConfigManager.heroSpawn, 3, 7, EnumCreatureType.AMBIENT, heroBiomes.toArray(new Biome[0]));
    EntityRegistry.addSpawn(EntityDeer.class, ConfigManager.deerSpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomesDeer.toArray(new Biome[0]));
    EntityRegistry.addSpawn(EntitySprout.class, ConfigManager.sproutSpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomesSprout.toArray(new Biome[0]));
    EntityRegistry.addSpawn(EntityFairy.class, ConfigManager.fairySpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomesFairy.toArray(new Biome[0]));
    EntityRegistry.addSpawn(EntityRainbowSlime.class, ConfigManager.slimeSpawnWeight, 1, 3, EnumCreatureType.MONSTER, biomesSlimes.toArray(new Biome[0]));
    EntityRegistry.addSpawn(EntityAncientGolem.class, ConfigManager.golemSpawnWeight, 1, 1, EnumCreatureType.MONSTER, biomes.toArray(new Biome[0]));
  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerRendering(ModelRegistryEvent event) {
    RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySprout.class, new RenderSprout.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderFairy.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityRainbowSlime.class, new RenderWaterSlime.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityEmberProjectile.class, new RenderEmberPacket(Minecraft.getMinecraft().getRenderManager()));
    RenderingRegistry.registerEntityRenderingHandler(EntityAncientGolem.class, new RenderAncientGolem.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFallenHero.class, new RenderFallenHero.Factory());
  }
}
