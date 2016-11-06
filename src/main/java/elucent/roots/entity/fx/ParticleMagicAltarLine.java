package elucent.roots.entity.fx;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleMagicAltarLine  extends Particle {

	Random random = new Random();
	public double colorR = 0;
	public double colorG = 0;
	public double colorB = 0;
	public int lifetime = 8;
	
	public ResourceLocation texture = new ResourceLocation("roots:entity/magicParticle");
	public ParticleMagicAltarLine(World worldIn, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b) {
		super(worldIn, x,y,z,0,0,0);
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
		if (this.colorR > 1.0){
			this.colorR = this.colorR/255.0;
		}
		if (this.colorG > 1.0){
			this.colorG = this.colorG/255.0;
		}
		if (this.colorB > 1.0){
			this.colorB = this.colorB/255.0;
		}
		this.setRBGColorF(1, 1, 1);
		this.particleMaxAge = 16;
		this.particleGravity = 0.0f;
		this.particleScale = 0.5f;
		this.motionX = (vx-x)/(double)this.particleMaxAge;
		this.motionY = (vy-y)/(double)this.particleMaxAge;
		this.motionZ = (vz-z)/(double)this.particleMaxAge;
		this.particleAlpha = 0.0f;
	    TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
		this.setParticleTexture(sprite);
	}
	
	@Override
	public int getBrightnessForRender(float pTicks){
		return 255;
	}
	
	@Override
	public boolean isTransparent(){
		return true;
	}
	
	@Override
	public int getFXLayer(){
		return 1;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		float lifeCoeff = ((float)this.particleMaxAge-(float)this.particleAge)/(float)this.particleMaxAge;
		this.particleRed = (float) Math.min(1.0f, (float)colorR*(lifeCoeff)+(1.0-lifeCoeff));
		this.particleGreen = (float) Math.min(1.0f, (float)colorG*(lifeCoeff)+(1.0-lifeCoeff));
		this.particleBlue = (float) Math.min(1.0f, (float)colorB*(lifeCoeff)+(1.0-lifeCoeff));
		this.particleAlpha = 1.0f-lifeCoeff;
		this.particleScale = 0.5f+2.0f*(1.0f-lifeCoeff);
	}
}
