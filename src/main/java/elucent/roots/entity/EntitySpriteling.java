package elucent.roots.entity;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EntitySpriteling extends EntityLiving {
	Random random = new Random();
	public EntitySpriteling(World world, double x, double y, double z) {
		super(world);
		this.setPosition(x, y, z);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount){
		super.attackEntityFrom(source, amount);
		return true;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		this.setPosition(posX+motionX, posY+motionY, posZ+motionZ);
	}
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		this.doBlockCollisions();
		this.setRotation(this.rotationYaw+0.1f*(random.nextFloat()-0.5f), this.rotationPitch+0.1f*(random.nextFloat()-0.5f));
		this.motionX = 0.25*Math.sin(this.rotationYaw)*Math.cos(this.rotationPitch);
		this.motionY = 0.25*Math.sin(this.rotationPitch);
		this.motionZ = 0.25*Math.cos(this.rotationYaw)*Math.cos(this.rotationPitch);
		this.setVelocity(motionX,motionY,motionZ);
		this.doBlockCollisions();
	}
	
	@Override
	public boolean isEntityInvulnerable(DamageSource source){
		return false;
	}
	
	@Override
	public boolean isBeingRidden(){
		return false;
	}
	
	@Override
	public AxisAlignedBB getEntityBoundingBox(){
		return new AxisAlignedBB(posX-0.25,posY-0.25,posZ-0.25,posX+0.25,posY+0.25,posZ+0.25);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox(){
		return new AxisAlignedBB(posX-0.25,posY-0.25,posZ-0.25,posX+0.25,posY+0.25,posZ+0.25);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(){
		return new AxisAlignedBB(posX-0.25,posY-0.25,posZ-0.25,posX+0.25,posY+0.25,posZ+0.25);
	}

	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
	}
}
