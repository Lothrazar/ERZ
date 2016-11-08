package elucent.roots.entity;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.Util;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntitySpriteGuardian  extends EntityFlying {// implements IRangedAttackMob {
    public float range = 64;
    public ArrayList<Vec3d> pastPositions = new ArrayList<Vec3d>();
    public static final DataParameter<Float> targetDirectionX = EntityDataManager.<Float>createKey(EntitySpriteGuardian.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> targetDirectionY = EntityDataManager.<Float>createKey(EntitySpriteGuardian.class, DataSerializers.FLOAT);
    public static final DataParameter<Integer> pacifiedTimer = EntityDataManager.<Integer>createKey(EntitySpriteGuardian.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> pacified = EntityDataManager.<Boolean>createKey(EntitySpriteGuardian.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> tracking = EntityDataManager.<Boolean>createKey(EntitySpriteGuardian.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Boolean> hasGuards = EntityDataManager.<Boolean>createKey(EntitySpriteGuardian.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Integer> fadeTimer = EntityDataManager.<Integer>createKey(EntitySpriteGuardian.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> projectiles = EntityDataManager.<Integer>createKey(EntitySpriteGuardian.class, DataSerializers.VARINT);
    public float addDirectionX = 0;
    public float addDirectionY = 0;
    Random random = new Random();
    public Vec3d moveVec = new Vec3d(0,0,0);
    public Vec3d prevMoveVec = new Vec3d(0,0,0);
    
    
    public SoundEvent ambientSound;
    public SoundEvent hurtSound;
    public SoundEvent departureSound;
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
    
    public EntitySpriteGuardian(World worldIn) {
    	super(worldIn);
        setSize(2.0f, 2.0f);
        this.noClip = true;
        this.isAirBorne = true;
		this.experienceValue = 20;
		ambientSound = new SoundEvent(new ResourceLocation("roots:bossAmbient"));
		departureSound = new SoundEvent(new ResourceLocation("roots:bossDeath"));
		hurtSound = new SoundEvent(new ResourceLocation("roots:bossHurt"));
		for (int i = 0; i < 30; i ++){
			pastPositions.add(new Vec3d(posX,posY,posZ));
		}
		this.rotationYaw = rand.nextInt(240)+60;
    }
    
    @Override
    public ITextComponent getDisplayName(){
    	return new TextComponentString("Guardian of Sprites");
    }
    
    @Override
    public boolean isNonBoss(){
    	return false;
    }
    
    @Override
    protected void entityInit(){
    	super.entityInit();
        this.getDataManager().register(pacified, Boolean.valueOf(false));
        this.getDataManager().register(tracking, Boolean.valueOf(false));
        this.getDataManager().register(hasGuards, Boolean.valueOf(true));
        this.getDataManager().register(targetDirectionX, Float.valueOf(0));
        this.getDataManager().register(targetDirectionY, Float.valueOf(0));
        this.getDataManager().register(pacifiedTimer, Integer.valueOf(0));
        this.getDataManager().register(fadeTimer, Integer.valueOf(0));
        this.getDataManager().register(projectiles, Integer.valueOf(0));
    }
    
    @Override
    public void collideWithEntity(Entity entity){
    	if (this.getAttackTarget() != null && this.getHealth() > 0 && !getDataManager().get(pacified).booleanValue()){
    		if (entity.getUniqueID().compareTo(this.getAttackTarget().getUniqueID()) == 0){
    			((EntityLivingBase)entity).attackEntityFrom(DamageSource.generic, 8.0f);
    			float magnitude = (float)Math.sqrt(motionX*motionX+motionZ*motionZ);
    			((EntityLivingBase)entity).knockBack(this,6.0f*magnitude+0.1f, -motionX/magnitude+0.1, -motionZ/magnitude+0.1);
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
    	float velocityScale = 1.0f;
    	float addedMotionY = 0.0f;
    	if (getDataManager().get(pacified) && getDataManager().get(fadeTimer) == 0){
    		getDataManager().set(pacifiedTimer, getDataManager().get(pacifiedTimer)+1);
    		getDataManager().setDirty(pacifiedTimer);
    	}
    	if (getDataManager().get(pacifiedTimer) > 800){
    		for (int i = 0; i < 5; i ++){
				Vec3d location = pastPositions.get(rand.nextInt(20)).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(3.0f));
				Roots.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
    		}
    	}
    	if (getDataManager().get(pacifiedTimer) > 1000 && getDataManager().get(fadeTimer) == 0){
    		for (int i = 0; i < 20; i ++){
    			for (int j = 0; j < 5; j ++){
    				Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(3.0f));
    				Roots.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
    			}
    			for (int j = 0; j < 2; j ++){
    				Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(1.5f));
    				Roots.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
    			}
    		}
    		getEntityWorld().playSound(posX, posY, posZ, departureSound, SoundCategory.NEUTRAL, random.nextFloat()*0.1f+0.95f, random.nextFloat()*0.1f+0.95f, false);
    		if (!getEntityWorld().isRemote){
        		getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
        		for (int i = 0; i < 24; i ++){
    	    		if (rand.nextInt(2) == 0){
    	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
    	    		}
    	    	}
        		for (int i = 0; i < 5; i ++){
        			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldSubstance,1)));
        		}
        		for (int i = 0; i < 8; i ++){
    	    		if (rand.nextInt(2) == 0){
    	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldSubstance,1)));
    	    		}
    	    	}
        		getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.runeStone,1)));
        		for (int i = 0; i < 5; i ++){
    	    		if (rand.nextInt(2) == 0){
    	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.runeStone,1)));
    	    		}
    	    	}
        		int chance = rand.nextInt(4);
        		if (chance == 0){
        			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmConjuration,1)));
    	    	}
        		if (chance == 1){
        			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmEvocation,1)));
    	    	}
        		if (chance == 2){
        			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmIllusion,1)));
    	    	}
        		if (chance == 3){
        			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmRestoration,1)));
    	    	}
        	}
    		setDead();
    	}
	    if (this.ticksExisted % 20 == 0){
    		List<EntityPlayer> players = getEntityWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX-72,posY-72,posZ-72,posX+72,posY+72,posZ+72));
    		boolean foundPrevious = false;
    		if (this.getAttackTarget() != null){
	    		for (int i = 0; i < players.size(); i ++){
	    			if (players.get(i).getUniqueID().compareTo(getAttackTarget().getUniqueID()) == 0){
	    				foundPrevious = true;
	    			}
	    		}
    		}
    		if (!foundPrevious && players.size() > 0){
    			this.setAttackTarget(players.get(rand.nextInt(players.size())));
    		}
    		else if (!foundPrevious){
    			for (int i = 0; i < 20; i ++){
        			for (int j = 0; j < 5; j ++){
        				Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(3.0f));
        				Roots.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
        			}
        			for (int j = 0; j < 2; j ++){
        				Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(1.5f));
        				Roots.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
        			}
        		}
        		getEntityWorld().playSound(posX, posY, posZ, departureSound, SoundCategory.NEUTRAL, random.nextFloat()*0.1f+0.95f, random.nextFloat()*0.1f+0.95f, false);
        		this.setDead();
    		}
	    	if (random.nextInt(6) == 0){
	    		getEntityWorld().playSound(posX, posY, posZ, ambientSound, SoundCategory.NEUTRAL, random.nextFloat()*0.1f+0.95f, random.nextFloat()*0.1f+0.95f, false);
	    	}
	    }
	    if (getHealth() < getMaxHealth()*0.25f){
	    	if (getDataManager().get(hasGuards)){
	    		getDataManager().set(hasGuards, false);
	    		getDataManager().setDirty(hasGuards);
	    		if (!getEntityWorld().isRemote){
	    			for (int i = 0; i < 3; i ++){
	    				EntityGreaterSprite sprite = new EntityGreaterSprite(getEntityWorld());
	    				sprite.setPosition(posX, posY, posZ);
	    				sprite.setHostile();
	    				sprite.setAttackTarget(getAttackTarget());
	    				sprite.onInitialSpawn(getEntityWorld().getDifficultyForLocation(getPosition()), (IEntityLivingData)null);
	                    getEntityWorld().spawnEntityInWorld(sprite);
	    			}
	    		}
	    	}
	    }
	    if (getHealth() < getMaxHealth()*0.65f){
		    if (ticksExisted % 400 == 0 && !getDataManager().get(pacified) && getDataManager().get(projectiles) == 0){
		    	getDataManager().set(projectiles, 6);
		    	getDataManager().setDirty(projectiles);
		    }
		    if (getDataManager().get(projectiles) > 0 && !getDataManager().get(pacified)){
		    	for (int i = 0; i < getDataManager().get(projectiles); i ++){
					Vec3d location = pastPositions.get(1).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(3.0f));
					Roots.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
					location = pastPositions.get(1).add((new Vec3d(rand.nextFloat()-0.5,rand.nextFloat()-0.5,rand.nextFloat()-0.5)).scale(1.5f));
					Roots.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), location.xCoord, location.yCoord+1.35f, location.zCoord, 0, 0, 0, 107, 255, 28);
		    	}
		    	if (ticksExisted % 60 == 0 && random.nextBoolean() && !getEntityWorld().isRemote){
		    		getDataManager().set(projectiles,getDataManager().get(projectiles)-1);
		    		getDataManager().setDirty(projectiles);
		    		EntitySpriteProjectile proj = new EntitySpriteProjectile(getEntityWorld());
	    			proj.setPosition(pastPositions.get(1).xCoord, pastPositions.get(1).yCoord, pastPositions.get(1).zCoord);
	    			proj.onInitialSpawn(getEntityWorld().getDifficultyForLocation(getPosition()), null);
	    			proj.initSpecial(getAttackTarget(), 6.0f);
	    			getEntityWorld().spawnEntityInWorld(proj);
	    			getEntityWorld().playSound(posX, posY, posZ, new SoundEvent(new ResourceLocation("roots:staffCast")), SoundCategory.HOSTILE, 0.95f+random.nextFloat()*0.1f, 0.7f+random.nextFloat()*0.1f, false);
					for (int i = 0; i < 40; i ++){
						Roots.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY+height/2.0f, posZ, Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), Math.pow(1.15f*(random.nextFloat()-0.5f),3.0), 107, 255, 28);
					}
		    	}
		    }
	    }
	    if (getAttackTarget() != null){
	    	float distanceToTarget = (float)Math.sqrt(Math.pow(posX-getAttackTarget().posX, 2)+Math.pow(posZ-getAttackTarget().posZ, 2));
	    	if (distanceToTarget < 10 && getDataManager().get(tracking)){
	    		getDataManager().set(tracking, false);
	    		getDataManager().setDirty(tracking);
	    	}
	    	if (distanceToTarget > 25 && !getDataManager().get(tracking)){
	    		getDataManager().set(tracking, true);
	    		getDataManager().setDirty(tracking);
	    	}
	    	if (!getDataManager().get(pacified)){
	    		velocityScale = 1.0f+(25.0f-distanceToTarget)/20.0f;
	    	}
	    	else {
	    		velocityScale = (Math.max(0, 20.0f-(float)getDataManager().get(pacifiedTimer)))/20.0f;
	    	}
	    }
	    if (getAttackTarget() != null && getDataManager().get(tracking) && !this.getEntityWorld().isRemote){
	    	this.getDataManager().set(targetDirectionX, (float)Math.toRadians(Util.yawDegreesBetweenPointsSafe(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY+getAttackTarget().getEyeHeight()/2.0, getAttackTarget().posZ, getDataManager().get(targetDirectionX))));
			this.getDataManager().set(targetDirectionY, (float)Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY+getAttackTarget().getEyeHeight()/2.0, getAttackTarget().posZ)));
			this.getDataManager().setDirty(targetDirectionX);
		 	this.getDataManager().setDirty(targetDirectionY);
	    }
	    if (getAttackTarget() == null && this.ticksExisted % 50 == 0 && !this.getEntityWorld().isRemote){
			this.getDataManager().set(targetDirectionX, (float)Math.toRadians(random.nextFloat()*360.0f));
			this.getDataManager().set(targetDirectionY, (float)Math.toRadians(random.nextFloat()*180.0f-90.0f));
		 	this.getDataManager().setDirty(targetDirectionX);
		 	this.getDataManager().setDirty(targetDirectionY);
		}
	    if (getAttackTarget() != null && getDataManager().get(pacified) && !this.getEntityWorld().isRemote){
	    	
	    }
    	if (this.ticksExisted % 20 == 0){
    		prevMoveVec = moveVec;
    		moveVec = Util.lookVector(this.getDataManager().get(targetDirectionX),this.getDataManager().get(targetDirectionY)).scale(0.25f*velocityScale);
    	}
    	
    	float motionInterp = ((float)(this.ticksExisted%20))/20.0f;
    	
		this.motionX = (1.0f-motionInterp)*prevMoveVec.xCoord+(motionInterp)*moveVec.xCoord;
		this.motionY = (1.0f-motionInterp)*prevMoveVec.yCoord+(motionInterp)*moveVec.yCoord;
		this.motionZ = (1.0f-motionInterp)*prevMoveVec.zCoord+(motionInterp)*moveVec.zCoord;
		
		this.rotationYaw = (float)Math.toRadians(Util.yawDegreesBetweenPointsSafe(0, 0, 0, motionX, motionY, motionZ, rotationYaw));
		this.rotationPitch = (float)Math.toRadians(Util.pitchDegreesBetweenPoints(0, 0, 0, motionX, motionY, motionZ));
		
		if (getDataManager().get(pacifiedTimer) < 20){
			for (int i = 1; i < pastPositions.size(); i ++){
				if (pastPositions.get(i).xCoord == 0 && pastPositions.get(i).yCoord == 0 && pastPositions.get(i).zCoord == 0){
					pastPositions.set(i, pastPositions.get(i-1));
				}
			}
			for (int i = pastPositions.size()-1; i > 0; i --){
				pastPositions.set(i, pastPositions.get(i).scale(0.5).add(pastPositions.get(i-1).scale(0.5)));
			}
	    	pastPositions.set(0, new Vec3d(posX,posY,posZ));
		}
		
		if (getDataManager().get(pacifiedTimer) == 200 && getEntityWorld().isRemote){
			if (getAttackTarget() instanceof EntityPlayer){
				((EntityPlayer)getAttackTarget()).addChatMessage(new TextComponentString(TextFormatting.ITALIC+I18n.format("roots.dialogue.guardian1")));
			}
		}
		
		if (getDataManager().get(pacifiedTimer) == 400 && getEntityWorld().isRemote){
			if (getAttackTarget() instanceof EntityPlayer){
				((EntityPlayer)getAttackTarget()).addChatMessage(new TextComponentString(TextFormatting.ITALIC+I18n.format("roots.dialogue.guardian2")));
			}
		}
		
		if (getDataManager().get(pacifiedTimer) == 600 && getEntityWorld().isRemote){
			if (getAttackTarget() instanceof EntityPlayer){
				((EntityPlayer)getAttackTarget()).addChatMessage(new TextComponentString(TextFormatting.ITALIC+I18n.format("roots.dialogue.guardian3")));
			}
		}
		
		if (getDataManager().get(pacifiedTimer) == 800 && getEntityWorld().isRemote){
			if (getAttackTarget() instanceof EntityPlayer){
				((EntityPlayer)getAttackTarget()).addChatMessage(new TextComponentString(TextFormatting.ITALIC+I18n.format("roots.dialogue.guardian4")));
			}
		}
    }
    
    @Override
    public boolean isEntityInvulnerable(DamageSource source){
    	if (getDataManager().get(pacified)){
    		return true;
    	}
    	return false;
    }
    
    @Override
    public int getBrightnessForRender(float partialTicks){
    	return 255;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount){
    	getEntityWorld().playSound(posX, posY, posZ, hurtSound, SoundCategory.NEUTRAL, random.nextFloat()*0.1f+0.95f, random.nextFloat()*0.1f+0.95f, false);
    	if (source.getEntity() instanceof EntityLivingBase){
    		this.setAttackTarget((EntityLivingBase)source.getEntity());
    	}
    	return super.attackEntityFrom(source, amount);
    }
    
    @Override
    public void damageEntity(DamageSource source, float amount){
    	if (this.getHealth() - amount <= 0 && !getDataManager().get(pacified).booleanValue()){
    		this.setHealth(1);
    		this.bossInfo.setPercent(0);
    		getDataManager().set(pacified, true);
    		getDataManager().setDirty(pacified);
    		if (source.getEntity() instanceof EntityPlayer){
    			EntityPlayer player = (EntityPlayer)source.getEntity();
    			if (!player.hasAchievement(RegistryManager.achieveGuardianBoss)){
    				PlayerManager.addAchievement(player, RegistryManager.achieveGuardianBoss);
    			}
    		}
    	}
    	else {
    		if (!getDataManager().get(pacified).booleanValue()){
    			super.damageEntity(source, amount);
    			this.bossInfo.setPercent(getHealth()/getMaxHealth());
    		}
    	}
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
    public void setDead(){
    	super.setDead();
    	getEntityWorld().playSound(posX, posY, posZ, hurtSound, SoundCategory.NEUTRAL, random.nextFloat()*0.1f+0.95f, (random.nextFloat()*0.1f+0.7f)/2.0f, false);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(320.0);
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
		getDataManager().set(targetDirectionX, compound.getFloat("targetDirectionX"));
		getDataManager().set(targetDirectionY, compound.getFloat("targetDirectionY"));
		getDataManager().set(pacifiedTimer, compound.getInteger("pacifiedTimer"));
		getDataManager().set(fadeTimer, compound.getInteger("fadeTimer"));
		getDataManager().set(pacified, compound.getBoolean("pacified"));
		getDataManager().set(tracking, compound.getBoolean("tracking"));
		getDataManager().set(hasGuards, compound.getBoolean("hasGuards"));
		getDataManager().set(projectiles, compound.getInteger("projectiles"));
		getDataManager().setDirty(targetDirectionX);
		getDataManager().setDirty(targetDirectionY);
		getDataManager().setDirty(pacified);
		getDataManager().setDirty(tracking);
		getDataManager().setDirty(hasGuards);
		getDataManager().setDirty(pacifiedTimer);
		getDataManager().setDirty(projectiles);
		getDataManager().setDirty(fadeTimer);
		this.bossInfo.setPercent(getHealth()/getMaxHealth());
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("targetDirectionX", getDataManager().get(targetDirectionX));
		compound.setFloat("targetDirectionY", getDataManager().get(targetDirectionY));
		compound.setBoolean("pacified", getDataManager().get(pacified));
		compound.setBoolean("tracking", getDataManager().get(tracking));
		compound.setBoolean("hasGuards", getDataManager().get(hasGuards));
		compound.setInteger("pacifiedTimer", getDataManager().get(pacifiedTimer));
		compound.setInteger("projectiles", getDataManager().get(projectiles));
		compound.setInteger("fadeTimer", getDataManager().get(fadeTimer));
	}
	
	public float getFade(float partialTicks){
		if (getDataManager().get(fadeTimer) == 0){
			return 1.0f;
		}
		else {
			return Math.max(0, (20.0f-((float)getDataManager().get(fadeTimer)+partialTicks))/20.0f);
		}
	}
	
	@Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }
}