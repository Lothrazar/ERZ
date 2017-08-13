package teamroots.emberroot.entity.deer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderDeer extends RenderLiving<EntityDeer> {
  public RenderDeer(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityDeer entity) {
    if (entity.getDataManager().get(EntityDeer.hasRednose)) { return new ResourceLocation(Const.MODID, "textures/entity/deer_rudolph.png"); }
    String colour = entity.getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/deer_" + colour + ".png");
  }
  @Override
  public void doRender(EntityDeer entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  public static class Factory implements IRenderFactory<EntityDeer> {
    @Override
    public Render<EntityDeer> createRenderFor(RenderManager manager) {
      return new RenderDeer(manager, ModelDeer.instance, 0.35f);
    }
  }
}
