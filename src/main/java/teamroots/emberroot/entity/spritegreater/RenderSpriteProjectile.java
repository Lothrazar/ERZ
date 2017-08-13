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

public class RenderSpriteProjectile extends RenderLiving<EntitySpriteProjectile> {

	public RenderSpriteProjectile(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}
	
	@Override
	public boolean canRenderName(EntitySpriteProjectile entity){
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpriteProjectile entity) {
		return new ResourceLocation(Const.MODID,"textures/entity/spriteling/spirit.png");
	}
}
