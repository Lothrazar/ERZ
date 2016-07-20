package elucent.roots.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySpriteling  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public Vec3d targetPosition = new Vec3d(posX,posY,posZ);
    Random random = new Random();
    //Constructor that's actually supposed to be used.
    public EntitySpriteling(World worldIn) {
        super(worldIn);
        setSize(0.5f,0.5f);
        this.isAirBorne = true;
    }
    
    @Override
    public void updateAITasks(){
    	super.updateAITasks();
    	Vec3d targetPosition = new Vec3d(posX,posY,posZ);
    	if (this.getAttackTarget() != null){
    		targetPosition = this.getAttackTarget().getPositionVector().addVector(0, this.getAttackTarget().getEyeHeight(), 0);
    	}
    	else {
    		if (this.ticksExisted % 200 == 0){
    			targetPosition = new Vec3d(posX+(random.nextDouble()-0.5)*20.0,this.getEntityWorld().getTopSolidOrLiquidBlock(this.getPosition()).getY()+3.0+(random.nextDouble()-0.5)*4.0,posZ+(random.nextDouble()-0.5)*20.0);
    		}
    	}
		double dx = targetPosition.xCoord - this.posX;
		double dy = targetPosition.yCoord - this.posY;
		double dz = targetPosition.zCoord - this.posZ;
		if (this.getAttackTarget() != null){
			this.setVelocity(this.motionX+dx/10.0, this.motionY+dy/10.0, this.motionZ+dz/10.0);
		}
		else {
			this.setVelocity(dx/200.0, dy/200.0, dz/200.0);
		}
		if (this.motionX + this.motionZ > 0.0001){
			Vec3d dVec = new Vec3d(motionX,motionY,motionZ).normalize();
			this.rotationYaw = 180+(float)(Math.PI-(Math.atan2(dVec.xCoord, dVec.zCoord)));
		}
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount){
    	if (source.getEntity() instanceof EntityLivingBase){
    		this.setAttackTarget((EntityLivingBase)source.getEntity());
    	}
    	return super.attackEntityFrom(source, amount);
    }
    
    @Override
    public boolean attackEntityAsMob(Entity entity){
    	if (entity instanceof EntityLivingBase){
    		this.setAttackTarget((EntityLivingBase)entity);
    	}
    	return super.attackEntityAsMob(entity);
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }


}