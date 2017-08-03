package teamroots.emberroot.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.Const; 
import teamroots.emberroot.model.ModelSprout;

public class RenderSprout extends RenderLiving<EntitySprout> {

	public RenderSprout(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySprout entity) {
		switch(entity.getDataManager().get(EntitySprout.variant)){
			case 0: { return new ResourceLocation(Const.MODID,"textures/entity/sprout_green.png");}
			case 1: { return new ResourceLocation(Const.MODID,"textures/entity/sprout_tan.png");}
			case 2: { return new ResourceLocation(Const.MODID,"textures/entity/sprout_red.png");}
			default: {return new ResourceLocation(Const.MODID,"textures/entity/sprout_green.png");}
		}
	}
	
	public static class Factory implements IRenderFactory<EntitySprout> {
		@Override
		public Render<EntitySprout> createRenderFor(RenderManager manager) {
			return new RenderSprout(manager,   ModelSprout.instance, 0.15f);
		}
	}
}
