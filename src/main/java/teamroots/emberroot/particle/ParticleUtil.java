package teamroots.emberroot.particle;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import teamroots.emberroot.Roots;
import teamroots.emberroot.entity.fairy.ParticleGlow;
import teamroots.emberroot.proxy.ClientProxy;

public class ParticleUtil {
	public static Random random = new Random();
	public static int counter = 0;
	
	public static void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime){
		if (Roots.proxy instanceof ClientProxy){
			counter += random.nextInt(3);
			if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2*Minecraft.getMinecraft().gameSettings.particleSetting) == 0){
				ClientProxy.particleRenderer.addParticle(new ParticleGlow(world,x,y,z,vx,vy,vz,r,g,b,a, scale, lifetime));
			}
		}
	}
 
	public static void spawnParticleStar(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime){
		if (Roots.proxy instanceof ClientProxy){
			counter += random.nextInt(3);
			if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2*Minecraft.getMinecraft().gameSettings.particleSetting) == 0){
				ClientProxy.particleRenderer.addParticle(new ParticleStar(world,x,y,z,vx,vy,vz,r,g,b,a, scale, lifetime));
			}
		}
	}
	
 

	public static void spawnParticleFloatingGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime){
		if (Roots.proxy instanceof ClientProxy){
			counter += random.nextInt(3);
			if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2*Minecraft.getMinecraft().gameSettings.particleSetting) == 0){
				ClientProxy.particleRenderer.addParticle(new ParticleFloatingGlow(world,x,y,z,vx,vy,vz,r,g,b,a, scale, lifetime));
			}
		}
	}
 
}
