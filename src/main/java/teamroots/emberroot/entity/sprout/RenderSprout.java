package teamroots.emberroot.entity.sprout;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderSprout extends RenderLiving<EntitySprout> {
  public RenderSprout(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntitySprout entity) {
    String colour = entity.getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/sprout_" + colour + ".png");
  }
  @Override
  public void doRender(EntitySprout entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  public static class Factory implements IRenderFactory<EntitySprout> {
    @Override
    public Render<EntitySprout> createRenderFor(RenderManager manager) {
      return new RenderSprout(manager, ModelSprout.instance, 0.15f);
    }
  }
}
