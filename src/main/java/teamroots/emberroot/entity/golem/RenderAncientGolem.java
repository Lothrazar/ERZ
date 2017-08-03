package teamroots.emberroot.entity.golem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;

public class RenderAncientGolem extends RenderLiving<EntityAncientGolem> {
  public RenderAncientGolem(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  public boolean canRenderName(EntityAncientGolem entity) {
    return false;
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityAncientGolem entity) {
    return new ResourceLocation(Const.MODID, "textures/entity/golem_" + entity.getVariantEnum().nameLower() + ".png");
  }
  public static ModelBase model;
  public static class Factory implements IRenderFactory<EntityAncientGolem> {
    @Override
    public Render<? super EntityAncientGolem> createRenderFor(RenderManager manager) {
      return new RenderAncientGolem(manager, model, 0.5f);
    }
  }
}
