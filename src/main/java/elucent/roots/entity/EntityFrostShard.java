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
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;

public class EntityFrostShard  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public float addDirectionX = 0;
    public float addDirectionY = 0;
    public float twirlTimer = 0;
    public int lifetime = 40;
    Random random = new Random();
    public float damage = 2.0f;

    public EntityFrostShard(World worldIn) {
    	super(worldIn);
        setSize(1.0f,1.0f);
        this.isAirBorne = true;
        this.setInvisible(true);
        setVelocity(rand.nextFloat()-0.75,rand.nextFloat()*0.75+0.25,rand.nextFloat()-0.75); 
    }
    
    public void initSpecial(float damage){
    	this.damage = damage;
    }
    
    @Override
    public void collideWithEntity(Entity entity){
    	if ((entity instanceof EntityLivingBase)){
			entity.attackEntityFrom(DamageSource.generic, damage);
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:slowness"),40,5));
			this.getEntityWorld().removeEntity(this);
			for (int i = 0; i < 80; i ++){
				if (random.nextInt(3) == 0){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), 255, 255, 255);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), 136, 252, 255);
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
    	this.setVelocity(motionX, motionY-0.025, motionZ);
    	if (lifetime == 0){
    		this.getEntityWorld().removeEntity(this);
    	}
    	if (this.isCollided){
			for (int i = 0; i < 40; i ++){
				if (random.nextInt(3) == 0){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 255, 255, 255);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 136, 252, 255);
				}
			}
    		this.getEntityWorld().removeEntity(this);
    	}
		for (double i = 0; i < 3; i ++){
			double x = prevPosX + (1.0-i/3.0)*(posX-prevPosX);
			double y = prevPosY + (1.0-i/3.0)*(posY-prevPosY);
			double z = prevPosZ + (1.0-i/3.0)*(posZ-prevPosZ);
			if (random.nextInt(3) == 0){
				Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), x, y, z, -0.125*motionX, -0.125*motionY, -0.125*motionZ, 255, 255, 255);
			}
			else {
				Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), x, y, z, -0.125*motionX, -0.125*motionY, -0.125*motionZ, 136, 252, 255);
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
				if (random.nextInt(3) == 0){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 255, 255, 255);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 136, 252, 255);
				}
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