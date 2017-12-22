package teamroots.emberroot.entity.sprite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderSprite extends RenderLiving<EntitySprite> {
  public RenderSprite(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntitySprite entity) {
    return new ResourceLocation(Const.MODID, "textures/entity/spriteling/spirit.png");
  }
  @Override
  public void doRender(EntitySprite entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  public static class Factory implements IRenderFactory<EntitySprite> {
    @Override
    public RenderSprite createRenderFor(RenderManager manager) {
      return new RenderSprite(manager, ModelSprite.instance, 0);
    }
  }
}
