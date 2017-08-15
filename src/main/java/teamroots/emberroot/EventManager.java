package teamroots.emberroot;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.entity.golem.ParticleGolemLaser;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.spritegreater.EntityGreaterSprite;
import teamroots.emberroot.entity.spriteguardian.EntitySpriteGuardianBoss;
import teamroots.emberroot.entity.spriteling.EntitySpriteling;
import teamroots.emberroot.proxy.ClientProxy;
import teamroots.emberroot.util.IRenderEntityLater;

public class EventManager {
  @SubscribeEvent
  public void onBlockPlace(PlaceEvent event) {
    trySpawnBoss(event.getWorld(), event.getPos());
  }
  private void trySpawnBoss(World worldIn, BlockPos pos) {
    BlockPattern pattern = EntitySpriteGuardianBoss.getGolemPattern();
    BlockPattern.PatternHelper blockpattern$patternhelper = pattern.match(worldIn, pos);
    if (blockpattern$patternhelper != null) {
      //delete blocks
      for (int j = 0; j < pattern.getPalmLength(); ++j) {
        for (int k = 0; k < pattern.getThumbLength(); ++k) {
          worldIn.setBlockState(blockpattern$patternhelper.translateOffset(j, k, 0).getPos(), Blocks.AIR.getDefaultState(), 2);
        }
      }
      //then spawn entity
      BlockPos blockpos = blockpattern$patternhelper.translateOffset(1, 2, 0).getPos();
      EntitySpriteGuardianBoss entityirongolem = new EntitySpriteGuardianBoss(worldIn);
      entityirongolem.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
      worldIn.spawnEntity(entityirongolem);
      for (EntityPlayerMP entityplayermp1 : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entityirongolem.getEntityBoundingBox().grow(5.0D))) {
        CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp1, entityirongolem);
      }
      int numSpritelings = 4;//TODO: config or constants for these
      int numSprites = 2;
      int numSpriteGreater = 1;
      for (int i = 0; i < numSpritelings; i++) {
        EntitySpriteling e = new EntitySpriteling(worldIn);
        e.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
        worldIn.spawnEntity(e);
      }
      for (int i = 0; i < numSpriteGreater; i++) {
        EntityGreaterSprite e = new EntityGreaterSprite(worldIn);
        e.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
        worldIn.spawnEntity(e);
      }
      for (int i = 0; i < numSprites; i++) {
        EntitySprite e = new EntitySprite(worldIn);
        e.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.05D, (double) blockpos.getZ() + 0.5D, 0.0F, 0.0F);
        worldIn.spawnEntity(e);
      }
      //summon particles
      for (int j1 = 0; j1 < 120; ++j1) {
        worldIn.spawnParticle(EnumParticleTypes.SNOWBALL, (double) blockpos.getX() + worldIn.rand.nextDouble(), (double) blockpos.getY() + worldIn.rand.nextDouble() * 3.9D, (double) blockpos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
      }
      //send block updates for the setblockstoair above
      for (int k1 = 0; k1 < pattern.getPalmLength(); ++k1) {
        for (int l1 = 0; l1 < pattern.getThumbLength(); ++l1) {
          BlockWorldState blockworldstate1 = blockpattern$patternhelper.translateOffset(k1, l1, 0);
          worldIn.notifyNeighborsRespectDebug(blockworldstate1.getPos(), Blocks.AIR, false);
        }
      }
    }
  }
  public static long ticks = 0;
  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onTextureStitch(TextureStitchEvent event) {
    ResourceLocation particleGlow = new ResourceLocation(Const.MODID, "entity/particle_glow");
    event.getMap().registerSprite(particleGlow);
    ResourceLocation particleSmoke = new ResourceLocation(Const.MODID, "entity/particle_smoke");
    event.getMap().registerSprite(particleSmoke);
    ResourceLocation particleStar = new ResourceLocation(Const.MODID, "entity/particle_star");
    event.getMap().registerSprite(particleStar);
    event.getMap().registerSprite(ParticleGolemLaser.texture);
  }
  @SideOnly(Side.CLIENT)
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void onTick(TickEvent.ClientTickEvent event) {
    if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START) {
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
  static float tickCounter = 0;
  static EntityPlayer clientPlayer = null;
  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void onRenderAfterWorld(RenderWorldLastEvent event) {
    tickCounter++;
    //    if (Roots.proxy instanceof ClientProxy) {
    //      GlStateManager.pushMatrix();
    //      ClientProxy.particleRendererGolem.renderParticles(clientPlayer, event.getPartialTicks());
    //      GlStateManager.popMatrix();
    //    }
    //OpenGlHelper.glUseProgram(ShaderUtil.lightProgram);
    GlStateManager.pushMatrix();
    for (Entity e : Minecraft.getMinecraft().world.getLoadedEntityList()) {
      Render render = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(e);
      if (render instanceof IRenderEntityLater) {
        renderEntityStatic(e, Minecraft.getMinecraft().getRenderPartialTicks(), true, render);
      }
    }
    GlStateManager.popMatrix();
    if (EmberRootZoo.proxy instanceof ClientProxy && Minecraft.getMinecraft().player != null) {
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
