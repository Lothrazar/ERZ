package teamroots.emberroot;
import java.util.ArrayList;
import java.util.List;
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
import teamroots.emberroot.entity.slime.EntityRainbowSlime;
import teamroots.emberroot.entity.slime.RenderWaterSlime;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.sprout.RenderSprout;

public class RegistryManager {
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  public static void registerAll() {
    int id = 0;
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "deer"), EntityDeer.class, "deer", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "deer"), intColor(159, 132, 88), intColor(94, 77, 51));
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "sprout"), EntitySprout.class, "sprout", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "sprout"), intColor(136, 189, 33), intColor(165, 232, 42));
    
    
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "fairy"), EntityFairy.class, "fairy", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "fairy"), intColor(255, 208, 255), intColor(209, 255, 173));

    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "rainbowslime"), EntityRainbowSlime.class, "rainbowslime", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "rainbowslime"), intColor(111, 208, 22), intColor(111, 255, 173));
    
    
    List<Biome> biomesDeer = new ArrayList<Biome>();
    List<Biome> biomesFairy = new ArrayList<Biome>();
    List<Biome> biomesSprout = new ArrayList<Biome>();
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.COOL)) {
      biomesDeer.add(b.biome);
      // biomesFairy.add(b.biome);
      biomesSprout.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.DESERT)) {
      //biomesDeer.add(b.biome);
      biomesFairy.add(b.biome);
      biomesSprout.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.ICY)) {
      biomesDeer.add(b.biome);
      // biomesFairy.add(b.biome);
      //  biomesSprout.add(b.biome);
    }
    for (BiomeEntry b : BiomeManager.getBiomes(BiomeType.WARM)) {
      biomesDeer.add(b.biome);
      biomesFairy.add(b.biome);
      biomesSprout.add(b.biome);
    }
    biomesFairy.addAll(BiomeManager.oceanBiomes);
    biomesDeer.addAll(BiomeManager.oceanBiomes);
    biomesSprout.addAll(BiomeManager.oceanBiomes);
    EntityRegistry.addSpawn(EntityDeer.class, ConfigManager.deerSpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomesDeer.toArray(new Biome[biomesDeer.size()]));
    EntityRegistry.addSpawn(EntitySprout.class, ConfigManager.sproutSpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomesSprout.toArray(new Biome[biomesSprout.size()]));
    EntityRegistry.addSpawn(EntityFairy.class, ConfigManager.fairySpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomesFairy.toArray(new Biome[biomesFairy.size()]));
  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void registerRendering(ModelRegistryEvent event) {
    RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntitySprout.class, new RenderSprout.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderFairy.Factory());
    RenderingRegistry.registerEntityRenderingHandler(EntityRainbowSlime.class, new RenderWaterSlime.Factory());
  }
}
