package elucent.roots.entity;

import java.util.Random;
import java.util.UUID;

import elucent.roots.Roots;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAttachedSpell extends Entity {
	Entity entity;
	Random random = new Random();
	int lifetime = 0;
	double potency = 0;
	double efficiency = 0;
	double size = 0;
	UUID casterId = new UUID(0,0);
	ComponentBase component = null;
	public EntityAttachedSpell(World world, EntityLivingBase caster, Entity entity, ComponentBase component, double potency, double efficiency, double size) {
		super(world);
		this.casterId = caster.getUniqueID();
		this.component = component;
		this.efficiency = efficiency;
		this.entity = entity;
		this.potency = potency;
		this.lifetime = 20;
		this.posX = entity.posX;
		this.posY = entity.posY;
		this.posZ = entity.posZ;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		this.posX = (entity.getEntityBoundingBox().maxX+entity.getEntityBoundingBox().minX)/2.0-0.5;
		this.posY = (entity.getEntityBoundingBox().maxY+entity.getEntityBoundingBox().minY)/2.0-0.5;
		this.posZ = (entity.getEntityBoundingBox().maxZ+entity.getEntityBoundingBox().minZ)/2.0-0.5;
		if (entity == null){
			this.kill();
			this.setDead();
			this.getEntityWorld().removeEntity(this);
		}
		component.castingAction((EntityLivingBase)entity, ticksExisted, potency, efficiency, size);
		for (int i = 0; i < 1; i ++){
			int side = random.nextInt(6);
			Vec3d color = random.nextBoolean() ? component.primaryColor : component.secondaryColor;
			if (side == 0){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX, posY+random.nextDouble(), posZ+random.nextDouble(), 0, 0, 0, color.xCoord, color.yCoord, color.zCoord);
			}
			if (side == 1){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+1.0, posY+random.nextDouble(), posZ+random.nextDouble(), 0, 0, 0, color.xCoord, color.yCoord, color.zCoord);
			}
			if (side == 2){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY, posZ+random.nextDouble(), 0, 0, 0, color.xCoord, color.yCoord, color.zCoord);
			}
			if (side == 3){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY+1.0, posZ+random.nextDouble(), 0, 0, 0, color.xCoord, color.yCoord, color.zCoord);
			}
			if (side == 4){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY+random.nextDouble(), posZ, 0, 0, 0, color.xCoord, color.yCoord, color.zCoord);
			}
			if (side == 5){
				Roots.proxy.spawnParticleMagicAuraFX(this.getEntityWorld(), posX+random.nextDouble(), posY+random.nextDouble(), posZ+1.0, 0, 0, 0, color.xCoord, color.yCoord, color.zCoord);
			}
		}
		lifetime --;
		if (lifetime <= 0){
			for (int i = 0; i < 40; i ++){
				if (random.nextInt(2) == 0){
					Roots.proxy.spawnParticleMagicFX(getEntityWorld(), posX, posY, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), component.primaryColor.xCoord, component.primaryColor.yCoord, component.primaryColor.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicFX(getEntityWorld(), posX, posY, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), component.secondaryColor.xCoord, component.secondaryColor.yCoord, component.secondaryColor.zCoord);
				}
			}
			getEntityWorld().playSound(posX, posY, posZ, new SoundEvent(new ResourceLocation("roots:staffCast")), SoundCategory.PLAYERS, 0.95f+0.1f*rand.nextFloat(), 0.95f+0.1f*rand.nextFloat(), false);
			component.doEffect(getEntityWorld(), casterId, new Vec3d(0,0,0), EnumCastType.SPELL, posX, posY, posZ, potency, efficiency, size);
			this.kill();
			this.setDead();
			this.getEntityWorld().removeEntity(this);
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.kill();
		this.getEntityWorld().removeEntity(this);
		this.lifetime = compound.getInteger("lifetime");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("lifetime", lifetime);
	}
}
