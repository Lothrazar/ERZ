package elucent.roots.dimension;

import org.lwjgl.opengl.GL11;

import elucent.roots.ConfigManager;
import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.util.RenderUtil;
import elucent.roots.util.StructUV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.IRenderHandler;

public class OtherworldProvider extends WorldProvider {
	
	public OtherworldProvider(){
	}
	
	@Override
	public Biome getBiomeForCoords(BlockPos pos){
		return RegistryManager.biomeOtherworld;
	}
	
	@Override
	public DimensionType getDimensionType() {
		return RegistryManager.dimOtherworld;
	}
	
	@Override
	public long getWorldTime(){
		return 6000;
	}
	
	@Override
	public boolean canDoRainSnowIce(Chunk chunk){
		return false;
	}
	
	@Override
	public int getRespawnDimension(EntityPlayerMP player){
		return DimensionType.OVERWORLD.getId();
	}
	
	@SideOnly(Side.CLIENT)
	public static class OtherworldSky extends IRenderHandler {
		public static OtherworldSky INSTANCE = new OtherworldSky();

		@Override
		public void render(float partialTicks, WorldClient world, Minecraft mc) {
			EntityPlayer p = Minecraft.getMinecraft().thePlayer;
			if (p != null){
				if (p.getEntityWorld().provider instanceof OtherworldProvider){
					GlStateManager.pushMatrix();
		            GlStateManager.pushAttrib();
		            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		            GlStateManager.loadIdentity();
		            ResourceLocation texture = new ResourceLocation(Roots.MODID + ":textures/blocks/blank.png");
		            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		            GlStateManager.disableCull();
		            GlStateManager.disableFog();
		            GlStateManager.disableLighting();
		            GlStateManager.enableAlpha();
		            GlStateManager.enableBlend();
			        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			        
		            Tessellator tess = Tessellator.getInstance();
		            VertexBuffer buffer = tess.getBuffer();
		            
		            int l = p.getEntityWorld().getCombinedLight(p.getPosition(), 15);
		            int lx = l >> 0x10 & 0xFFFF;
		            int ly = l & 0xFFFF;

		            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1, 0, 0);
		            GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewY, 0, 1, 0);
		            GlStateManager.depthMask(false);
		            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
		            RenderUtil.addBox(buffer, -60, -60, -60, 60, 60, 60, new StructUV[]{new StructUV(0,0,4096,4096,4096,4096),
		            		new StructUV(0,0,4096,4096,4096,4096),
		            		new StructUV(0,0,4096,4096,4096,4096),
		            		new StructUV(0,0,4096,4096,4096,4096),
		            		new StructUV(0,0,4096,4096,4096,4096),
		            		new StructUV(0,0,4096,4096,4096,4096)}, new int[]{1,1,1,1,1,1});
		        	tess.draw();
		            GlStateManager.depthMask(true);
		            
		            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		            GlStateManager.enableCull();
		            GlStateManager.enableLighting();
		            GlStateManager.disableAlpha();
		            GlStateManager.enableFog();
		            GlStateManager.disableBlend();
		            GlStateManager.popAttrib();
		            GlStateManager.popMatrix();
	            }
			}
		}
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getCloudHeight(){
		return -10000;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getVoidFogYFactor(){
		return 1.0;
	}
	
	@Override
	public boolean isSurfaceWorld(){
		return false;
	}
	
<<<<<<< HEAD
	/*@Override
=======
	@Override
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
	public void generateLightBrightnessTable(){
        float f = 0.0F;

        for (int i = 0; i <= 15; ++i)
        {
<<<<<<< HEAD
            this.lightBrightnessTable[i] = 0.4f+0.6f*((float)i)/15.0f;
        }
    }*/
=======
            this.lightBrightnessTable[i] = 0.6f+0.4f*((float)i)/15.0f;
        }
    }
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
	
	@Override
	public int getHeight(){
		return 0;
	}
	
	@Override
	public int getAverageGroundLevel(){
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Vec3d getFogColor(float p1, float p2){
		return new Vec3d(169, 255, 122).scale(1.0f/256.0f);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks){
		return new Vec3d(169, 255, 122).scale(1.0f/256.0f);
	}
	
	@Override
	public boolean canRespawnHere(){
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IRenderHandler getSkyRenderer(){
		return OtherworldSky.INSTANCE;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator(){
		return new OtherworldChunkGenerator(this.worldObj);
	}

}
