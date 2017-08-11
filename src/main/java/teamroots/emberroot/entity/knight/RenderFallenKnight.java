package teamroots.emberroot.entity.knight;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;

public class RenderFallenKnight extends RenderSkeleton {
  private static final ResourceLocation texture = new ResourceLocation(Const.MODID, "textures/entity/fallen_knight.png");
  public RenderFallenKnight(RenderManager rm) {
    super(rm);
  }
  @Override
  protected ResourceLocation getEntityTexture(AbstractSkeleton entity) {
    return texture;
  }
  public static class Factory implements IRenderFactory<EntitySkeleton> {
    @Override
    public Render<? super EntitySkeleton> createRenderFor(RenderManager manager) {
      return new RenderFallenKnight(manager);
    }
  }
}
