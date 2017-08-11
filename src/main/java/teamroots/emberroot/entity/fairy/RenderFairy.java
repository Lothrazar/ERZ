package teamroots.emberroot.entity.fairy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderFairy extends RenderLiving<EntityFairy> {
  public RenderFairy(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityFairy entity) {
    String colourName = entity.getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/fairy_" + colourName + ".png");
  }
  @Override
  public void doRender(EntityFairy entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  public static class Factory implements IRenderFactory<EntityFairy> {
    @Override
    public Render<EntityFairy> createRenderFor(RenderManager manager) {
      return new RenderFairy(manager, ModelFairy.instance, 0);
    }
  }
}
