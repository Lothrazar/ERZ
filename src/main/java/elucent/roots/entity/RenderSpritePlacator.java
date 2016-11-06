package elucent.roots.entity;

import org.lwjgl.opengl.GL11;

import elucent.roots.model.entity.ModelSpriteling;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSpritePlacator extends RenderLiving<EntitySpritePlacator> {

	public RenderSpritePlacator(RenderManager renderManager, ModelBase modelBase, float shadowSize) {
		super(renderManager, modelBase, shadowSize);
	}
	
	@Override
	public boolean canRenderName(EntitySpritePlacator entity){
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySpritePlacator entity) {
		return new ResourceLocation("roots:textures/gui/guiWhite.png");
	}
}
