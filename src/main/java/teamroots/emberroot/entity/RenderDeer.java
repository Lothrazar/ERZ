package teamroots.emberroot.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const;
import teamroots.emberroot.model.ModelDeer; 

public class RenderDeer extends RenderLiving<EntityDeer> {

	public RenderDeer(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDeer entity) {
		if (entity.getDataManager().get(EntityDeer.hasRednose)){
			return new ResourceLocation(Const.MODID,"textures/entity/rudolph.png");
		}
		return new ResourceLocation(Const.MODID,"textures/entity/deer.png");
	}
	
	public static class Factory implements IRenderFactory<EntityDeer> {
		@Override
		public Render<EntityDeer> createRenderFor(RenderManager manager) {
			return new RenderDeer(manager, ModelDeer.instance, 0.35f);
		}
	}
}
