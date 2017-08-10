package teamroots.emberroot.entity.owl;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;

public class RenderOwl extends RenderLiving<EntityOwl> {
  private static final ResourceLocation TEX = new ResourceLocation(Const.MODID, "textures/entity/owl.png");
  private int debug1 = 0;
  private int debug2 = 1;
  public RenderOwl(RenderManager renderManager) {
    super(renderManager, new ModelOwl(), 0.5F);
  }
  @Override
  public void doRender(EntityOwl entity, double x, double y, double z, float entityYaw, float partialTicks) {
    //    RenderUtil.renderEntityBoundingBox(entity, x, y, z);    
    //    debug2 = 0;
    if (debug1 == debug2) {//WTF is this crap
      mainModel = new ModelOwl();
      debug1++;
    }
    entity.calculateAngles(partialTicks);
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityOwl p_110775_1_) {
    return TEX;
  }
  public static class Factory implements IRenderFactory<EntityOwl> {
    @Override
    public Render<? super EntityOwl> createRenderFor(RenderManager manager) {
      return new RenderOwl(manager);
    }
  }
}
