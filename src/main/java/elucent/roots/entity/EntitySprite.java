package elucent.roots.entity;

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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySprite  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public static final DataParameter<Float> targetDirectionX = EntityDataManager.<Float>createKey(EntitySprite.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> targetDirectionY = EntityDataManager.<Float>createKey(EntitySprite.class, DataSerializers.FLOAT);
    public static final DataParameter<Integer> dashTimer = EntityDataManager.<Integer>createKey(EntitySprite.class, DataSerializers.VARINT);
    public float addDirectionX = 0;
    public float addDirectionY = 0;
    public float twirlTimer = 0;
    public float prevYaw1 = 0;
    public float prevYaw2 = 0;
    public float prevYaw3 = 0;
    public float prevPitch1 = 0;
    public float prevPitch2 = 0;
    public float prevPitch3 = 0;
    Random random = new Random();

    public EntitySprite(World worldIn) {
    	super(worldIn);
        setSize(0.75f,0.75f);
        this.isAirBorne = true;
    }
    
    @Override
    protected void entityInit(){
    	super.entityInit();
        this.getDataManager().register(targetDirectionX, Float.valueOf(0));
        this.getDataManager().register(targetDirectionY, Float.valueOf(0));
        this.getDataManager().register(dashTimer, Integer.valueOf(0));
    }
    
    @Override
    public void collideWithEntity(Entity entity){
    	if (this.getAttackTarget() != null && !this.isDead){
    		if (entity.getUniqueID().compareTo(this.getAttackTarget().getUniqueID()) == 0){
    			((EntityLivingBase)entity).attackEntityFrom(DamageSource.generic, 4.0f);
    			float magnitude = (float)Math.sqrt(motionX*motionX+motionZ*motionZ);
    			((EntityLivingBase)entity).knockBack(this, 4.0f*magnitude, -motionX/magnitude, -motionZ/magnitude);
    			((EntityLivingBase)entity).attackEntityAsMob(this);
    			((EntityLivingBase)entity).setRevengeTarget(this);
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
    	
    	prevYaw3 = prevYaw2;
    	prevYaw2 = prevYaw1;
    	prevYaw1 = rotationYaw;
    	
    	prevPitch3 = prevPitch2;
    	prevPitch2 = prevPitch1;
    	prevPitch1 = rotationPitch;
    	
    	if (twirlTimer > 0){
    		twirlTimer -= 1.0f;
    	}
    	if (getDataManager().get(dashTimer) > 0){
    		getDataManager().set(dashTimer, getDataManager().get(dashTimer)-1);
    		getDataManager().setDirty(dashTimer);
    	}
    	if (this.getAttackTarget() != null && !this.getEntityWorld().isRemote){
    		if (getDataManager().get(dashTimer) <= 0){
    			this.getDataManager().set(targetDirectionX, (float)Math.toRadians(Util.yawDegreesBetweenPoints(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY+getAttackTarget().getEyeHeight(), getAttackTarget().posZ)));
    			this.getDataManager().set(targetDirectionY, (float)Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY+getAttackTarget().getEyeHeight(), getAttackTarget().posZ)));
    			this.getDataManager().setDirty(targetDirectionX);
    		}
    		if (this.ticksExisted % 20 == 0 && random.nextInt(3) == 0){
    			getDataManager().set(dashTimer, 20);
        		getDataManager().setDirty(dashTimer);
    			twirlTimer = 20;
    		}
    	}
    	else {
    		if (this.ticksExisted % 20 == 0 && !this.getEntityWorld().isRemote){
    			this.getDataManager().set(targetDirectionX, (float)Math.toRadians(random.nextFloat()*360.0f));
    			this.getDataManager().set(targetDirectionY, (float)Math.toRadians(random.nextFloat()*180.0f-90.0f));
    		 	this.getDataManager().setDirty(targetDirectionX);
    	    }
    		if (this.ticksExisted % 40 == 0 && random.nextInt(8) == 0){
    			twirlTimer = 20;
    		}
    	}
		addDirectionX = (float) (addDirectionX+getDataManager().get(targetDirectionX))/2.0f;
		addDirectionY = (float) (addDirectionY+getDataManager().get(targetDirectionY))/2.0f;
		addDirectionY -= Math.toRadians(5.0f*(posY-(getEntityWorld().getTopSolidOrLiquidBlock(getPosition()).getY()+3.0)));
		this.rotationYaw = (rotationYaw*0.8f+addDirectionX*0.2f);
		this.rotationPitch = (rotationPitch*0.8f+addDirectionY*0.2f);
		Vec3d moveVec = Util.lookVector(this.rotationYaw,this.rotationPitch).scale(getAttackTarget() != null ? (getDataManager().get(dashTimer) > 0 ? 0.4 : 0.2) : 0.1);
		this.setVelocity(moveVec.xCoord,moveVec.yCoord,moveVec.zCoord);
		if (getDataManager().get(dashTimer) > 0){
			Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX+((random.nextDouble())-0.5)*0.2, posY+0.25+((random.nextDouble())-0.5)*0.2, posZ+((random.nextDouble())-0.5)*0.2, -0.25*moveVec.xCoord, -0.25*moveVec.yCoord, -0.25*moveVec.zCoord, 76, 230, 0);
		}
    }
    
    @Override
    public int getBrightnessForRender(float partialTicks){
    	return 255;
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }


}