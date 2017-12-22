package teamroots.emberroot.entity.wolfdire;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderDirewolf extends RenderLiving<EntityDireWolf> {
  private int debugCounter = 0;
  public RenderDirewolf(RenderManager rm) {
    super(rm, new ModelDireWolf(), 0.5f);
  }
  protected float handleRotationFloat(EntityDireWolf wolf, float partialTick) {
    return wolf.getTailRotation();
  }
  @Override
  protected void preRenderCallback(EntityDireWolf entity, float partialTick) {
    if (debugCounter == 4) {
      mainModel = new ModelDireWolf();
      debugCounter++;
    }
    float scale = 1.25f;
    GL11.glPushMatrix();
    GL11.glTranslatef(0.1f, 0, 0);
    GL11.glScalef(scale - 0.1f, scale, scale);
  }
  @Override
  public void doRender(EntityDireWolf entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    GL11.glPopMatrix();
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityDireWolf entity) {
    String colour = entity.getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/wolf_" + colour + ".png");
  }
  public static class Factory implements IRenderFactory<EntityDireWolf> {
    @Override
    public Render<? super EntityDireWolf> createRenderFor(RenderManager manager) {
      return new RenderDirewolf(manager);
    }
  }
}
