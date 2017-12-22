package teamroots.emberroot.entity.wolftimber;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderTimberWolf extends RenderLiving<EntityTimberWolf> {
  public RenderTimberWolf(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityTimberWolf entity) {
    return new ResourceLocation(Const.MODID, "textures/entity/timberwolf.png");
  }
  @Override
  public void doRender(EntityTimberWolf entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  public static class Factory implements IRenderFactory<EntityTimberWolf> {
    @Override
    public RenderTimberWolf createRenderFor(RenderManager manager) {
      return new RenderTimberWolf(manager, ModelTimberWolf.instance, 0);
    }
  }
}
