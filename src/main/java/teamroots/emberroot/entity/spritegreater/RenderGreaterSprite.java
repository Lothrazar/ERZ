package teamroots.emberroot.entity.spritegreater;
 
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.entity.spriteling.EntitySpriteling;
import teamroots.emberroot.entity.spriteling.ModelSpriteling;
import teamroots.emberroot.entity.spriteling.RenderSpriteling;

public class RenderGreaterSprite extends RenderLiving<EntityGreaterSprite> {

	public RenderGreaterSprite(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityGreaterSprite entity) {
		return new ResourceLocation(Const.MODID,"textures/entity/spriteling/spirit.png");
	}  
	public static class Factory implements IRenderFactory<EntityGreaterSprite> {
    @Override
    public RenderGreaterSprite createRenderFor(RenderManager manager) {
      return new RenderGreaterSprite(manager, ModelGreaterSprite.instance, 0);
    }
  }
}
