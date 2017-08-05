package teamroots.emberroot.entity.slime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;

public class RenderWaterSlime extends RenderLiving<EntitySlime> {//RenderSlime {
  public RenderWaterSlime(RenderManager rm, ModelWaterSlime mainModel, float shadowSize) {
    super(rm, mainModel, shadowSize);
    this.addLayer(new LayerSlime(this));
  }
  /**
   * Renders the desired {@code T} type Entity.
   */
  @Override
  public void doRender(EntitySlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
    this.shadowSize = 0.25F * (float) entity.getSlimeSize();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  /**
   * Allows the render to do state modifications necessary before the model is
   * rendered.
   */
  @Override
  protected void preRenderCallback(EntitySlime entitylivingbaseIn, float partialTickTime) {
    float f = 0.999F;
    GlStateManager.scale(f, f, f);
    float f1 = (float) entitylivingbaseIn.getSlimeSize();
    float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
    float f3 = 1.0F / (f2 + 1.0F);
    GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
  }
  //private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation(Const.MODID,"textures/entity/slime_water.png");
  @Override
  protected ResourceLocation getEntityTexture(EntitySlime entity) {
    EntityRainbowSlime slime = (EntityRainbowSlime) entity;
    String colour = slime.getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/slime_" + colour + ".png");
  }
  public static class Factory implements IRenderFactory<EntityRainbowSlime> {
    @Override
    public RenderWaterSlime createRenderFor(RenderManager manager) {
      return new RenderWaterSlime(manager, ModelWaterSlime.instance, 0);
    }
  }
}
