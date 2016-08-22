package elucent.roots.tileentity;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntitySpiritConduitRenderer extends TileEntitySpecialRenderer {
	public ResourceLocation texture = new ResourceLocation("roots:textures/blocks/spiritFontDetail.png");
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		if (te instanceof TileEntitySpiritConduit){
			TileEntitySpiritConduit tef = (TileEntitySpiritConduit)te;
			if (tef.powered){
				float angle = tef.angle + 24.0f*partialTicks;
				this.bindTexture(texture);
				GlStateManager.disableLighting();
				GlStateManager.enableBlend();
				GlStateManager.enableAlpha();
				GlStateManager.pushMatrix();
				GlStateManager.translate(x+0.5, y+1.0, z+0.5);
				GlStateManager.rotate(angle, 0, 1, 0);
				GlStateManager.translate(-(x+0.5), -(y+1.0), -(z+0.5));
				GlStateManager.disableCull();
				
				Tessellator tess = Tessellator.getInstance();
				VertexBuffer buffer = tess.getBuffer();
				buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
				buffer.pos((x+0.5)-0.1875, (y+1.0)+0.1875, z+0.5).tex(0.5, 0.0).lightmap(255, 255).color(255, 255, 255, 255).endVertex();
				buffer.pos((x+0.5)+0.1875, (y+1.0)+0.1875, z+0.5).tex(0.875, 0.0).lightmap(255, 255).color(255, 255, 255, 255).endVertex();
				buffer.pos((x+0.5)+0.1875, (y+1.0)-0.1875, z+0.5).tex(0.875, 0.375).lightmap(255, 255).color(255, 255, 255, 255).endVertex();
				buffer.pos((x+0.5)-0.1875, (y+1.0)-0.1875, z+0.5).tex(0.5, 0.375).lightmap(255, 255).color(255, 255, 255, 255).endVertex();
				tess.draw();
				GlStateManager.popMatrix();
				GlStateManager.enableCull();
				GlStateManager.enableLighting();
				GlStateManager.disableBlend();
				GlStateManager.disableAlpha();
			}
		}
	}
	
}
