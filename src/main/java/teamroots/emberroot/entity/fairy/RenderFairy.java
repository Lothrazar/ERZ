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
    switch (entity.getDataManager().get(EntityFairy.variant)) {
      case 0: {
        return new ResourceLocation(Const.MODID, "textures/entity/green_fairy.png");
      }
      case 1: {
        return new ResourceLocation(Const.MODID, "textures/entity/purple_fairy.png");
      }
      case 2: {
        return new ResourceLocation(Const.MODID, "textures/entity/pink_fairy.png");
      }
      case 3: {
        return new ResourceLocation(Const.MODID, "textures/entity/orange_fairy.png");
      }
      default: {
        return new ResourceLocation(Const.MODID, "textures/entity/green_fairy.png");
      }
    }
  }
  public static class Factory implements IRenderFactory<EntityFairy> {
    @Override
    public Render<EntityFairy> createRenderFor(RenderManager manager) {
      return new RenderFairy(manager, ModelFairy.instance, 0);
    }
  }
}
