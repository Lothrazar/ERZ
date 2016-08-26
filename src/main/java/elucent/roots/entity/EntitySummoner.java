package elucent.roots.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import elucent.roots.Roots;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySummoner extends Entity {
	Random random = new Random();
	public Class result = null;
	public Vec3d color1;
	public Vec3d color2;
	public boolean large = false;
	public SoundEvent spawnSound;
	public EntitySummoner(World world, Class result, double posX, double posY, double posZ, Vec3d color1, Vec3d color2, boolean large) {
		super(world);
		this.result = result;
		this.color1 = color1;
		this.color2 = color2;
		this.large = large;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		spawnSound = new SoundEvent(new ResourceLocation("roots:charging"));
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		for (float i = 0; i < 360; i += 60.0f){
			if (!large){
				float lifeCoeff = ((float)ticksExisted)/100.0f;
				float dx = (1.2f-lifeCoeff)*2.0f*(float)Math.sin(Math.toRadians(i+lifeCoeff*12.0f*(ticksExisted)));
				float dz = (1.2f-lifeCoeff)*2.0f*(float)Math.cos(Math.toRadians(i+lifeCoeff*12.0f*(ticksExisted)));
				if (random.nextBoolean()){
					Roots.proxy.spawnParticleMagicAltarFX(getEntityWorld(), posX+dx, posY, posZ+dz, 0, 0, 0, color1.xCoord, color1.yCoord, color1.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicAltarFX(getEntityWorld(), posX+dx, posY, posZ+dz, 0, 0, 0, color2.xCoord, color2.yCoord, color2.zCoord);
				}
			}
			else {
				float lifeCoeff = ((float)ticksExisted)/100.0f;
				float dx = (1.2f-lifeCoeff)*6.0f*(float)Math.sin(Math.toRadians(i+lifeCoeff*-12.0f*(ticksExisted)));
				float dz = (1.2f-lifeCoeff)*6.0f*(float)Math.cos(Math.toRadians(i+lifeCoeff*-12.0f*(ticksExisted)));
				if (random.nextBoolean()){
					Roots.proxy.spawnParticleMagicAltarFX(getEntityWorld(), posX+dx, posY, posZ+dz, 0, 0, 0, color1.xCoord, color1.yCoord, color1.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicAltarFX(getEntityWorld(), posX+dx, posY, posZ+dz, 0, 0, 0, color2.xCoord, color2.yCoord, color2.zCoord);
				}
				dx = (1.2f-lifeCoeff)*3.0f*(float)Math.sin(Math.toRadians(i+lifeCoeff*12.0f*(ticksExisted)));
				dz = (1.2f-lifeCoeff)*3.0f*(float)Math.cos(Math.toRadians(i+lifeCoeff*12.0f*(ticksExisted)));
				if (random.nextBoolean()){
					Roots.proxy.spawnParticleMagicAltarBigFX(getEntityWorld(), posX+dx, posY, posZ+dz, 0, 0, 0, color1.xCoord, color1.yCoord, color1.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicAltarBigFX(getEntityWorld(), posX+dx, posY, posZ+dz, 0, 0, 0, color2.xCoord, color2.yCoord, color2.zCoord);
				}
			}
		}
		if (ticksExisted > 100){
			EntityLivingBase toSpawn;
			try {
				World world = this.getEntityWorld();
				toSpawn = (EntityLivingBase)result.getConstructor(World.class).newInstance(world);
				if (toSpawn instanceof EntityCreature){
					((EntityCreature)toSpawn).setWorld(world);
					((EntityCreature)toSpawn).onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
				}
				if (toSpawn instanceof EntitySlime){
					((EntitySlime)toSpawn).setWorld(world);
					((EntitySlime)toSpawn).onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
				}
				toSpawn.setPosition(posX,posY,posZ);	
				if (!world.isRemote){
					world.spawnEntityInWorld(toSpawn);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}	
			for (int i = 0; i < 80; i ++){
				if (random.nextBoolean()){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), color1.xCoord, color1.yCoord, color1.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), color2.xCoord, color2.yCoord, color2.zCoord);
				}
			}
			getEntityWorld().playSound(posX, posY, posZ, spawnSound, SoundCategory.NEUTRAL, random.nextFloat()*0.2f+1.9f, random.nextFloat()*0.2f+1.9f, false);
	    	
			setDead();
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.kill();
		this.getEntityWorld().removeEntity(this);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}
}
