package elucent.roots.entity;

import elucent.roots.Util;
import elucent.roots.model.ModelHolder;
import elucent.roots.model.entity.ModelSpriteGuardianSegment;
import elucent.roots.model.entity.ModelSpriteGuardianSegmentFirst;
import elucent.roots.model.entity.ModelSpriteGuardianSegmentLarge;
import elucent.roots.model.entity.ModelSpriteGuardianTail;
import elucent.roots.model.entity.ModelSpriteling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSpriteGuardian extends RenderLiving<EntitySpriteGuardian> {

	public RenderSpriteGuardian(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}
	
	@Override
	public void renderModel(EntitySpriteGuardian entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor){
		boolean flag = !entity.isInvisible() || this.renderOutlines;
        boolean flag1 = !flag && !entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);

        if (flag || flag1)
        {
            if (!this.bindEntityTexture(entity))
            {
                return;
            }

            if (flag1)
            {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            for (int i = 1; i < 10; i ++){
            	if (i == 9){
            		((ModelSpriteGuardianTail)ModelHolder.entityModels.get("spriteguardiantail")).render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor,i);
                }
            	else if (i ==1){
            		((ModelSpriteGuardianSegmentFirst)ModelHolder.entityModels.get("spriteguardiansegmentfirst")).render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor,i);
            	}
            	else if (i ==2){
            		((ModelSpriteGuardianSegmentLarge)ModelHolder.entityModels.get("spriteguardiansegmentlarge")).render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor,i);
            	}
            	else {
            		((ModelSpriteGuardianSegment)ModelHolder.entityModels.get("spriteguardiansegment")).render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor,i);
            	}
            }
            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
    }

	@Override
	protected ResourceLocation getEntityTexture(EntitySpriteGuardian entity) {
		return new ResourceLocation("roots:textures/entity/spriteling/spiritTexture.png");
	}
}
