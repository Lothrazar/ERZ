package teamroots.emberroot.entity.fairy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;

public class RenderFairy extends RenderLiving<EntityFairy> {
  public RenderFairy(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
    super(renderManager, modelBase, shadowSize);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityFairy entity) {
    String colourName = entity.getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/" + colourName + "_fairy.png");
  }
  public static class Factory implements IRenderFactory<EntityFairy> {
    @Override
    public Render<EntityFairy> createRenderFor(RenderManager manager) {
      return new RenderFairy(manager, ModelFairy.instance, 0);
    }
  }
}
