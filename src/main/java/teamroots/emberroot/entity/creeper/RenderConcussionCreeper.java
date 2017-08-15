package teamroots.emberroot.entity.creeper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.util.RenderUtil;

public class RenderConcussionCreeper extends RenderCreeper {
  //  public static final Factory FACTORY = new Factory();
 public RenderConcussionCreeper(RenderManager rm) {
    super(rm);
  }
  /**
   * Returns the location of an entity's texture. Doesn't seem to be called
   * unless you call Render.bindEntityTexture.
   */
  @Override
  protected ResourceLocation getEntityTexture(EntityCreeper entity) {
    
    String colour = ((EntityConcussionCreeper)entity).getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "textures/entity/creeper_" + colour + ".png");
  }
  @Override
  public void doRender(EntityCreeper entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  public static class Factory implements IRenderFactory<EntityCreeper> {
    @Override
    public Render<? super EntityCreeper> createRenderFor(RenderManager manager) {
      return new RenderConcussionCreeper(manager);
    }
  }
}
