package teamroots.emberroot.entity.owl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.EmberRootZoo;

@SideOnly(Side.CLIENT)
public class RenderEntityOwlEgg extends RenderSnowball<EntityOwlEgg> {
  public RenderEntityOwlEgg(RenderManager renderManagerIn, RenderItem itemRendererIn) {
    super(renderManagerIn, EmberRootZoo.itemOwlEgg, itemRendererIn);
  }
  @Override
  public void doRender(EntityOwlEgg entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  @Override
  public ItemStack getStackToRender(EntityOwlEgg entityIn) {
    return new ItemStack(EmberRootZoo.itemOwlEgg);
  }
  public static class Factory implements IRenderFactory<EntityOwlEgg> {
    @Override
    public Render<? super EntityOwlEgg> createRenderFor(RenderManager manager) {
      return new RenderEntityOwlEgg(manager, Minecraft.getMinecraft().getRenderItem());
    }
  }
}
