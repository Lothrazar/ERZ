package elucent.roots.entity;

import elucent.roots.model.entity.ModelSpriteling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

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
		return new ResourceLocation("roots:textures/entity/spriteling/spiritTexture.png");
	}
}
