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

public class EntitySpriteProjectile  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public float addDirectionX = 0;
    public float addDirectionY = 0;
    public float twirlTimer = 0;
    public int lifetime = 80;
    Random random = new Random();
    EntityLivingBase target = null;
    public float damage = 2.0f;

    public EntitySpriteProjectile(World worldIn) {
    	super(worldIn);
        setSize(1.0f,1.0f);
        this.isAirBorne = true;
        this.setInvisible(true);
    }
    
    public void initSpecial(EntityLivingBase target, float damage){
    	this.target = target;
    	this.damage = damage;
    }
    
    @Override
    public void collideWithEntity(Entity entity){
    	if (target != null){
	    	if (entity.getUniqueID().compareTo(target.getUniqueID()) == 0){
	    		target.attackEntityFrom(DamageSource.generic, damage);
	    		this.getEntityWorld().removeEntity(this);
				for (int i = 0; i < 80; i ++){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
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
    	lifetime --;
    	if (lifetime == 0){
    		this.getEntityWorld().removeEntity(this);
    	}
    	if (target != null){
			rotationYaw = (float)Math.toRadians(Util.yawDegreesBetweenPoints(posX, posY, posZ, target.posX, target.posY+target.getEyeHeight(), target.posZ));
			rotationPitch = (float)Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, target.posX, target.posY+target.getEyeHeight(), target.posZ));
		    Vec3d moveVec = Util.lookVector(this.rotationYaw,this.rotationPitch).scale(0.35f);
			this.setVelocity(0.5f*motionX+0.5f*moveVec.xCoord,0.5f*motionY+0.5f*moveVec.yCoord,0.5f*motionZ+0.5f*moveVec.zCoord);
			for (double i = 0; i < 3; i ++){
				double x = prevPosX + (1.0-i/3.0)*(posX-prevPosX);
				double y = prevPosY + (1.0-i/3.0)*(posY-prevPosY);
				double z = prevPosZ + (1.0-i/3.0)*(posZ-prevPosZ);
				Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), x, y, z, -0.125*moveVec.xCoord, -0.125*moveVec.yCoord, -0.125*moveVec.zCoord, 76, 230, 0);
			}
		}
    }
    
    @Override
    public int getBrightnessForRender(float partialTicks){
    	return 255;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount){
    	if (source.getEntity() != null){
    		this.getEntityWorld().removeEntity(this);
			for (int i = 0; i < 40; i ++){
				Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
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