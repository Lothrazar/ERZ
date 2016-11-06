package elucent.roots.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
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

public class EntitySpellProjectile  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public float addDirectionX = 0;
    public float addDirectionY = 0;
    public float twirlTimer = 0;
    public Vec3d color1 = new Vec3d(255,255,255);
    public Vec3d color2 = new Vec3d(255,255,255);
    public int lifetime = 120;
    Random random = new Random();
    public double potency = 0.0;
    public double efficiency = 0.0;
    public double size = 0.0;
    public ComponentBase component = null;
    public float pitch = 0.0f;
    public UUID casterId = null;

    public EntitySpellProjectile(World worldIn) {
    	super(worldIn);
        setSize(1.0f,1.0f);
        this.setInvisible(true);
    }
    
    @Override
    public boolean isEntityInvulnerable(DamageSource source){
    	return true;
    }
    
    public void initSpecial(ComponentBase component, double potency, double efficiency, double size, UUID id, double yaw, double pitch){
    	this.component = component;
    	this.potency = potency;
    	this.efficiency = efficiency;
    	this.size = size;
    	this.casterId = id;
    	this.color1 = component.primaryColor;
    	this.color2 = component.secondaryColor;
    	this.rotationYaw = (float)yaw;
    	this.rotationPitch = -(float)pitch;
    	this.pitch = this.rotationPitch;
    }
    
    @Override
    public void collideWithEntity(Entity entity){
    	if (casterId != null){
	    	if (entity.getUniqueID().compareTo(casterId) != 0){
		    	component.doEffect(getEntityWorld(), casterId, new Vec3d(motionX,motionY,motionZ).normalize(), EnumCastType.SPELL, posX, posY, posZ, potency, efficiency, size);
		    	setDead();
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
    	this.rotationPitch = this.pitch;
    	lifetime --;
    	if (lifetime == 0){
    		this.getEntityWorld().removeEntity(this);
    	}
		Vec3d moveVec = Util.lookVector(this.rotationYaw,this.rotationPitch).scale(0.7f);
	    this.motionX = moveVec.xCoord;
		this.motionY = moveVec.yCoord;
		this.motionZ = moveVec.zCoord;
		double x = this.getEntityBoundingBox().minX*0.5+this.getEntityBoundingBox().maxX*0.5;
		double y = this.getEntityBoundingBox().minY*0.5+this.getEntityBoundingBox().maxY*0.5;
		double z = this.getEntityBoundingBox().minZ*0.5+this.getEntityBoundingBox().maxZ*0.5;
		double dx = 0;
		double dy = 0;
		double dz = 0;
			if (component != null){
			for (double i = 0; i < 1; i ++){
				if (random.nextBoolean()){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), x, y, z, -0.125*moveVec.xCoord, -0.125*moveVec.yCoord, -0.125*moveVec.zCoord, color1.xCoord, color1.yCoord, color1.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), x, y, z, -0.125*moveVec.xCoord, -0.125*moveVec.yCoord, -0.125*moveVec.zCoord, color2.xCoord, color2.yCoord, color2.zCoord);
				}
			}
			if (isCollided){
				component.doEffect(getEntityWorld(), casterId, new Vec3d(motionX,motionY,motionZ).normalize(), EnumCastType.SPELL, posX, posY, posZ, potency, efficiency, size);
		    	setDead();
				for (int i = 0; i < 40; i ++){
					if (random.nextBoolean()){
						Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), color1.xCoord, color1.yCoord, color1.zCoord);
					}
					else {
						Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), color2.xCoord, color2.yCoord, color2.zCoord);
					}
				}
		    }
		}
    }
    
    @Override
    public int getBrightnessForRender(float partialTicks){
    	return 255;
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(9999.0);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
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