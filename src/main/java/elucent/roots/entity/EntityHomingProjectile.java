package elucent.roots.entity;

import java.util.List;
import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.Util;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHomingProjectile  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public float addDirectionX = 0;
    public float addDirectionY = 0;
    public float twirlTimer = 0;
    public Vec3d color = new Vec3d(255,255,255);
    public int lifetime = 80;
    Random random = new Random();
    EntityLivingBase target = null;
    public float damage = 2.0f;

    public EntityHomingProjectile(World worldIn) {
    	super(worldIn);
        setSize(1.0f,1.0f);
        this.isAirBorne = true;
        this.setInvisible(true);
    }
    
    @Override
    public boolean isEntityInvulnerable(DamageSource source){
    	return false;
    }
    
    public void initSpecial(EntityLivingBase target, float damage, Vec3d color){
    	this.target = target;
    	this.damage = damage;
    	this.color = color;
    }
    
    @Override
    public void collideWithEntity(Entity entity){
    	if (target != null){
	    	if (entity.getUniqueID().compareTo(target.getUniqueID()) == 0){
	    		target.attackEntityFrom(DamageSource.generic, damage);
	    		this.getEntityWorld().removeEntity(this);
				for (int i = 0; i < 40; i ++){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), color.xCoord, color.yCoord, color.zCoord);
				}
	    	}
    	}
    }
    
    @Override
    public void updateAITasks(){
    	super.updateAITasks();
    }
    
    @Override
    public void onUpdate(){
    	super.onUpdate();
        this.setEntityBoundingBox(new AxisAlignedBB(posX-0.75,posY-0.75,posZ-0.75,posX+0.75,posY+0.75,posZ+0.75));
    	lifetime --;
    	if (lifetime == 0){
    		this.getEntityWorld().removeEntity(this);
    	}
    	if (target != null){
			rotationYaw = (float)Math.toRadians(Util.yawDegreesBetweenPoints(posX, posY, posZ, target.posX, target.posY+target.getEyeHeight(), target.posZ));
			rotationPitch = (float)Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, target.posX, target.posY+target.getEyeHeight(), target.posZ));
		    Vec3d moveVec = Util.lookVector(this.rotationYaw,this.rotationPitch).scale(0.35f);
		    this.motionX = 0.5f*motionX+0.5f*moveVec.xCoord;
			this.motionY = 0.5f*motionY+0.5f*moveVec.yCoord;
			this.motionZ = 0.5f*motionZ+0.5f*moveVec.zCoord;
			for (double i = 0; i < 3; i ++){
				double x = posX*(i/3.0)+prevPosX*((1.0+i)/3.0);
				double y = posY*(i/3.0)+prevPosY*((1.0+i)/3.0);
				double z = posZ*(i/3.0)+prevPosZ*((1.0+i)/3.0);
				Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), x, y, z, -0.125*moveVec.xCoord, -0.125*moveVec.yCoord, -0.125*moveVec.zCoord, color.xCoord, color.yCoord, color.zCoord);
			}
		}
    }
    
    @Override
    public int getBrightnessForRender(float partialTicks){
    	return 255;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount){
    	if (this.ticksExisted > 2){
	    	this.getEntityWorld().removeEntity(this);
			for (int i = 0; i < 40; i ++){
				Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), color.xCoord, color.yCoord, color.zCoord);
			}
    	}
    	return false;
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    @Override
    protected boolean canDespawn() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.getEntityWorld().removeEntity(this);
	}
}