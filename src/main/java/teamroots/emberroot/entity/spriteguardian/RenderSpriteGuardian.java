package teamroots.emberroot.entity.spriteguardian;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.entity.spritegreater.ModelNull;

public class RenderSpriteGuardian extends RenderLiving<EntitySpriteGuardianBoss> {
  public RenderSpriteGuardian(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  public static class Factory implements IRenderFactory<EntitySpriteGuardianBoss> {
    @Override
    public RenderSpriteGuardian createRenderFor(RenderManager manager) {
      return new RenderSpriteGuardian(manager, ModelNull.instance, 0.5f);
    }
  }
  @Override
  public void renderModel(EntitySpriteGuardianBoss entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    boolean flag = !entity.isInvisible() || this.renderOutlines;
    boolean flag1 = !flag && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);
    if (flag || flag1) {
      if (!this.bindEntityTexture(entity)) { return; }
      if (flag1) {
        GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
      }
      this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      for (int i = 1; i < 15; i++) {
        if (i == 14) {
          ModelSpriteGuardianTail.instance.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, i);
        }
        else if (i == 1) {
          ModelSpriteGuardianSegmentFirst.instance.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, i);
        }
        else if (i == 2) {
          ModelSpriteGuardianSegmentLarge.instance.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, i);
        }
        else {
          ModelSpriteGuardianSegment.instance.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, i);
        }
      }
      if (flag1) {
        GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
      }
    }
  }
  @Override
  public boolean shouldRender(EntitySpriteGuardianBoss entity, ICamera camera, double camX, double camY, double camZ) {
    return true;
  }
  @Override
  protected ResourceLocation getEntityTexture(EntitySpriteGuardianBoss entity) {
    return new ResourceLocation(Const.MODID, "textures/entity/spriteling/spirit.png");
  }
}
