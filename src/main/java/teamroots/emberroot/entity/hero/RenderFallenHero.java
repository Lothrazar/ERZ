package teamroots.emberroot.entity.hero;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderFallenHero extends RenderBiped<EntityFallenHero> {
  private static final ResourceLocation texture = new ResourceLocation(Const.MODID, "textures/entity/hero.png");
  public RenderFallenHero(RenderManager renderManagerIn) {
    super(renderManagerIn, new ModelZombie(), 0.5F);
    LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
      protected void initArmor() {
        this.modelLeggings = new ModelZombie(0.5F, true);
        this.modelArmor = new ModelZombie(1.0F, true);
      }
    };
    this.addLayer(layerbipedarmor);
  }
  @Override
  public void doRender(EntityFallenHero entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityFallenHero entity) {
    return texture;
  }
  public static class Factory implements IRenderFactory<EntityFallenHero> {
    @Override
    public Render<? super EntityFallenHero> createRenderFor(RenderManager manager) {
      return new RenderFallenHero(manager);
    }
  }
}
