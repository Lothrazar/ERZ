package teamroots.emberroot.entity.golem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.Const;
import teamroots.emberroot.Roots;
import teamroots.emberroot.proxy.ClientProxy; 

public class GolemRegistry {
  public static DamageSource damage_ember;
  public static int intColor(int r, int g, int b) {
    return (r * 65536 + g * 256 + b);
  }
  public static void registerAll() {
     damage_ember = new DamageGolem();
    int id = 5;
    //TODO: MERGE WITH registrymanager eh
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "ember_projectile"), EntityEmberProjectile.class, "ember_projectile", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID, "ancient_golem"), EntityAncientGolem.class, "ancient_golem", id++, Roots.instance, 64, 1, true);
    EntityRegistry.registerEgg(new ResourceLocation(Const.MODID, "ancient_golem"), intColor(48, 38, 35), intColor(79, 66, 61));
    List<BiomeEntry> biomeEntries = new ArrayList<BiomeEntry>();
    biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.COOL));
    biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.DESERT));
    biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.ICY));
    biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.WARM));
    List<Biome> biomes = new ArrayList<Biome>();
    for (BiomeEntry b : biomeEntries) {
      biomes.add(b.biome);
    }
    biomes.addAll(BiomeManager.oceanBiomes);
    EntityRegistry.addSpawn(EntityAncientGolem.class, 25, 1, 1, EnumCreatureType.MONSTER, biomes.toArray(new Biome[biomes.size()]));
  }
  @SideOnly(Side.CLIENT)
  public static void registerEntityRendering() {
    //RenderingRegistry.registerEntityRenderingHandler(EntityEmberPacket.class, new RenderEmberPacket(Minecraft.getMinecraft().getRenderManager()));
    RenderingRegistry.registerEntityRenderingHandler(EntityEmberProjectile.class, new RenderEmberPacket(Minecraft.getMinecraft().getRenderManager()));
    RenderingRegistry.registerEntityRenderingHandler(EntityAncientGolem.class, new RenderAncientGolem.Factory());
    //  RenderingRegistry.registerEntityRenderingHandler(EntityEmberLight.class, new RenderEmberPacket(Minecraft.getMinecraft().getRenderManager()));
  }
  static float tickCounter = 0;
  static int ticks = 0;
  static EntityPlayer clientPlayer = null;
  double gaugeAngle = 0;
  Random random = new Random();
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent event) {
    event.getMap().registerSprite(ParticleMote.texture);
  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onTick(TickEvent.ClientTickEvent event) {
    if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START) {
      ticks++;
      ClientProxy.particleRendererGolem.updateParticles();
    }
  }
  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRenderAfterWorld(RenderWorldLastEvent event) {
    tickCounter++;
    if (Roots.proxy instanceof ClientProxy) {
      GlStateManager.pushMatrix();
      ClientProxy.particleRendererGolem.renderParticles(clientPlayer, event.getPartialTicks());
      GlStateManager.popMatrix();
    }
  }
}
