package teamroots.emberroot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.proxy.ClientProxy;
import teamroots.emberroot.util.IRenderEntityLater;

public class EventManager {
  public static long ticks = 0;
  EntityLivingBase playerMorph = null;
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent event) {
    ResourceLocation particleGlow = new ResourceLocation(Const.MODID, "entity/particle_glow");
    event.getMap().registerSprite(particleGlow);
    ResourceLocation particleSmoke = new ResourceLocation(Const.MODID, "entity/particle_smoke");
    event.getMap().registerSprite(particleSmoke);
//    ResourceLocation particleFiery = new ResourceLocation(Const.MODID, "entity/particle_fiery");
//    event.getMap().registerSprite(particleFiery);
//    ResourceLocation particlePetal = new ResourceLocation(Const.MODID, "entity/particle_petal");
//    event.getMap().registerSprite(particlePetal);
//    ResourceLocation particleThorn = new ResourceLocation(Const.MODID, "entity/particle_thorn");
//    event.getMap().registerSprite(particleThorn);
    ResourceLocation particleStar = new ResourceLocation(Const.MODID, "entity/particle_star");
    event.getMap().registerSprite(particleStar);
//    ResourceLocation particleRune1 = new ResourceLocation(Const.MODID, "entity/particle_rune_1");
//    event.getMap().registerSprite(particleRune1);
//    ResourceLocation particleRune2 = new ResourceLocation(Const.MODID, "entity/particle_rune_2");
//    event.getMap().registerSprite(particleRune2);
//    ResourceLocation particleRune3 = new ResourceLocation(Const.MODID, "entity/particle_rune_3");
//    event.getMap().registerSprite(particleRune3);
//    ResourceLocation particleRune4 = new ResourceLocation(Const.MODID, "entity/particle_rune_4");
//    event.getMap().registerSprite(particleRune4);
  }
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onTick(TickEvent.ClientTickEvent event) {
    if (event.side == Side.CLIENT) {
      ClientProxy.particleRenderer.updateParticles();
      ticks++;
    }
  }
  @SideOnly(Side.CLIENT)
  public static void renderEntityStatic(Entity entityIn, float partialTicks, boolean b, Render render) {
    if (entityIn.ticksExisted == 0) {
      entityIn.lastTickPosX = entityIn.posX;
      entityIn.lastTickPosY = entityIn.posY;
      entityIn.lastTickPosZ = entityIn.posZ;
    }
    //        double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double)partialTicks;
    //        double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double)partialTicks;
    //        double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double)partialTicks;
    float f = entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks;
    int i = entityIn.getBrightnessForRender();
    if (entityIn.isBurning()) {
      i = 15728880;
    }
    int j = i % 65536;
    int k = i / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    ((IRenderEntityLater) render).renderLater(entityIn, -TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ, f, partialTicks);
  }
  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRenderAfterWorld(RenderWorldLastEvent event) {
    //OpenGlHelper.glUseProgram(ShaderUtil.lightProgram);
    GlStateManager.pushMatrix();
    for (Entity e : Minecraft.getMinecraft().world.getLoadedEntityList()) {
      Render render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(e);
      if (render instanceof IRenderEntityLater) {
        renderEntityStatic(e, Minecraft.getMinecraft().getRenderPartialTicks(), true, render);
      }
    }
    GlStateManager.popMatrix();
    if (Roots.proxy instanceof ClientProxy && Minecraft.getMinecraft().player != null) {
      ClientProxy.particleRenderer.renderParticles(Minecraft.getMinecraft().player, event.getPartialTicks());
    }
    //OpenGlHelper.glUseProgram(0);
  }
  /**
   * Witchery Author Tribute
   * 
   * @param event
   */
  @SubscribeEvent
  public void onWitchKingReturn(PlayerLoggedInEvent event) {
    if (event.player.getGameProfile().getName().equalsIgnoreCase("Emoniph")) {
      for (int i = 0; i < event.player.world.playerEntities.size(); i++) {
        event.player.world.playerEntities.get(i).sendMessage(new TextComponentString("The Witch King has returned!"));
      }
    }
  }
}
