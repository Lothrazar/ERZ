package teamroots.emberroot.entity.spritegreater;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.EmberRootZoo;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.sprite.ISprite;
import teamroots.emberroot.util.Util;

public class EntityGreaterSprite extends EntityFlying implements ISprite {// implements IRangedAttackMob {
  public float range = 64;
  public static final DataParameter<Float> targetDirectionX = EntityDataManager.<Float> createKey(EntityGreaterSprite.class, DataSerializers.FLOAT);
  public static final DataParameter<Float> targetDirectionY = EntityDataManager.<Float> createKey(EntityGreaterSprite.class, DataSerializers.FLOAT);
  public static final DataParameter<Integer> dashTimer = EntityDataManager.<Integer> createKey(EntityGreaterSprite.class, DataSerializers.VARINT);
  public static final DataParameter<Integer> shootTimer = EntityDataManager.<Integer> createKey(EntityGreaterSprite.class, DataSerializers.VARINT);
  public static final DataParameter<Float> happiness = EntityDataManager.<Float> createKey(EntityGreaterSprite.class, DataSerializers.FLOAT);
  public static final DataParameter<Boolean> stunned = EntityDataManager.<Boolean> createKey(EntityGreaterSprite.class, DataSerializers.BOOLEAN);
  public static final DataParameter<BlockPos> targetBlock = EntityDataManager.<BlockPos> createKey(EntityGreaterSprite.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<BlockPos> lastTargetBlock = EntityDataManager.<BlockPos> createKey(EntityGreaterSprite.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<BlockPos> lastLastTargetBlock = EntityDataManager.<BlockPos> createKey(EntityGreaterSprite.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<Boolean> hostile = EntityDataManager.<Boolean> createKey(EntityGreaterSprite.class, DataSerializers.BOOLEAN);
  public static final String NAME = "rootsonespritegreater";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityGreaterSprite.class, EnumCreatureType.MONSTER);
  public float addDirectionX = 0;
  public float addDirectionY = 0;
  public float twirlTimer = 0;
  public float prevYaw1 = 0;
  public float prevYaw2 = 0;
  public float prevYaw3 = 0;
  public float prevYaw4 = 0;
  public float prevYaw5 = 0;
  public float prevPitch1 = 0;
  public float prevPitch2 = 0;
  public float prevPitch3 = 0;
  public float prevPitch4 = 0;
  public float prevPitch5 = 0;
  public Vec3d moveVec = new Vec3d(0, 0, 0);
  public Vec3d prevMoveVec = new Vec3d(0, 0, 0);
  Random random = new Random();
  public int offset = random.nextInt(25);
  public EntityGreaterSprite(World worldIn) {
    super(worldIn);
    setSize(1.0f, 1.0f);
    this.noClip = true;
    this.isAirBorne = true;
    this.experienceValue = 20;
    this.rotationYaw = rand.nextInt(240) + 60;
  }
  @Override
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(targetDirectionX, Float.valueOf(0));
    this.getDataManager().register(targetDirectionY, Float.valueOf(0));
    this.getDataManager().register(shootTimer, Integer.valueOf(0));
    this.getDataManager().register(dashTimer, Integer.valueOf(0));
    this.getDataManager().register(happiness, Float.valueOf(0));
    this.getDataManager().register(stunned, Boolean.valueOf(false));
    this.getDataManager().register(hostile, Boolean.valueOf(false));
    this.getDataManager().register(targetBlock, new BlockPos(0, -1, 0));
    this.getDataManager().register(lastTargetBlock, new BlockPos(0, -1, 0));
    this.getDataManager().register(lastLastTargetBlock, new BlockPos(0, -1, 0));
  }
  public void setHostile() {
    this.getDataManager().set(hostile, true);
    this.getDataManager().setDirty(hostile);
  }
  @Override
  public void collideWithEntity(Entity entity) {
    if (this.getAttackTarget() != null && this.getHealth() > 0 && !getDataManager().get(stunned).booleanValue()) {
      if (entity.getUniqueID().compareTo(this.getAttackTarget().getUniqueID()) == 0) {
        ((EntityLivingBase) entity).attackEntityFrom(DamageSource.GENERIC, 4.0f);
        float magnitude = (float) Math.sqrt(motionX * motionX + motionZ * motionZ);
        ((EntityLivingBase) entity).knockBack(this, 4.0f * magnitude + 0.1f, -motionX / magnitude + 0.1, -motionZ / magnitude + 0.1);
        ((EntityLivingBase) entity).attackEntityAsMob(this);
        ((EntityLivingBase) entity).setRevengeTarget(this);
      }
    }
  }
  @Override
  public void updateAITasks() {
    super.updateAITasks();
  }
  //    @Override
  //    public void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source){
  //    	super.dropLoot(wasRecentlyHit,lootingModifier,source);
  //    	if (!getEntityWorld().isRemote){
  //    		getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
  //    		for (int i = 0; i < 13+lootingModifier; i ++){
  //	    		if (rand.nextInt(2) == 0){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
  //	    		}
  //	    	}
  //    		for (int i = 0; i < 3+lootingModifier; i ++){
  //	    		if (rand.nextInt(2) == 0){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.runeStone,1)));
  //	    		}
  //	    	}
  //    		if (rand.nextInt(5) == 0){
  //	    		int chance = rand.nextInt(4);
  //	    		if (chance == 0){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmConjuration,1)));
  //		    	}
  //	    		if (chance == 1){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmEvocation,1)));
  //		    	}
  //	    		if (chance == 2){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmIllusion,1)));
  //		    	}
  //	    		if (chance == 3){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.itemCharmRestoration,1)));
  //		    	}
  //    		}
  //    	}
  //    }
  @Override
  public void onUpdate() {
    super.onUpdate();
    prevYaw5 = prevYaw4;
    prevYaw4 = prevYaw3;
    prevYaw3 = prevYaw2;
    prevYaw2 = prevYaw1;
    prevYaw1 = rotationYaw;
    prevPitch5 = prevPitch4;
    prevPitch4 = prevPitch3;
    prevPitch3 = prevPitch2;
    prevPitch2 = prevPitch1;
    prevPitch1 = rotationPitch;
    if (getDataManager().get(happiness) > 0) {
      if (this.ticksExisted % 2 == 0) {
        EmberRootZoo.proxy.spawnParticleMagicSparkleScalableFX(getEntityWorld(), 24, posX + width * 0.5f * (random.nextFloat() - 0.5f), posY + height * 0.5f + height * (random.nextFloat() - 0.5f), posZ + width * 0.5f * (random.nextFloat() - 0.5f), 0, 0, 0, this.getDataManager().get(happiness).floatValue() / 20.0f, 107, 255, 28);
      }
    }
    if (this.getDataManager().get(targetBlock).getY() == -1) {
      this.getDataManager().set(targetBlock, this.getPosition());
      this.getDataManager().setDirty(targetBlock);
    }
    //    	if (this.ticksExisted % 4000 == 0 && !this.getDataManager().get(stunned)){
    //    		if (random.nextInt(6) == 0 && !this.getEntityWorld().isRemote){
    //    			getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(),posX,posY,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
    //    		}
    //    	}
    if (getDataManager().get(stunned).booleanValue()) {
      this.setAttackTarget(null);
    }
    if (!getDataManager().get(stunned).booleanValue()) {
      if (this.ticksExisted % 20 == 0) {
        if (random.nextInt(4) == 0 && this.getDataManager().get(stunned).booleanValue() == false) {
          getEntityWorld().playSound(posX, posY, posZ, EntitySprite.ambientSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 0.7f, false);
        }
      }
      if (twirlTimer > 0) {
        twirlTimer -= 1.0f;
      }
      if (getDataManager().get(dashTimer) > 0) {
        getDataManager().set(dashTimer, getDataManager().get(dashTimer) - 1);
        getDataManager().setDirty(dashTimer);
      }
      if (getDataManager().get(shootTimer) > 0) {
        getDataManager().set(shootTimer, getDataManager().get(shootTimer) - 1);
        getDataManager().setDirty(shootTimer);
        if (!getEntityWorld().isRemote && getDataManager().get(shootTimer) == 0) {
          EntitySpriteProjectile proj = new EntitySpriteProjectile(getEntityWorld());
          proj.setPosition(posX, posY, posZ);
          proj.onInitialSpawn(getEntityWorld().getDifficultyForLocation(getPosition()), null);
          proj.initSpecial(getAttackTarget(), 4.0f);
          getEntityWorld().spawnEntity(proj);
          getEntityWorld().playSound(posX, posY, posZ, EntitySprite.staffcast, SoundCategory.HOSTILE, 0.95f + random.nextFloat() * 0.1f, 0.7f + random.nextFloat() * 0.1f, false);
          for (int i = 0; i < 40; i++) {
            EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY + height / 2.0f, posZ, Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), 107, 255, 28);
          }
        }
      }
      if (this.getAttackTarget() != null && !this.getEntityWorld().isRemote) {
        if (getDataManager().get(targetBlock).getY() != -1) {
          getDataManager().set(targetBlock, new BlockPos(0, -1, 0));
          getDataManager().setDirty(targetBlock);
        }
        if (getDataManager().get(dashTimer) <= 0) {
          this.getDataManager().set(targetDirectionX, (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY + getAttackTarget().getEyeHeight() / 2.0, getAttackTarget().posZ, getDataManager().get(targetDirectionX).doubleValue())));
          this.getDataManager().set(targetDirectionY, (float) Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY + getAttackTarget().getEyeHeight() / 2.0, getAttackTarget().posZ)));
          this.getDataManager().setDirty(targetDirectionX);
          this.getDataManager().setDirty(targetDirectionY);
        }
        if (this.ticksExisted % 20 == 0 && random.nextInt(8) == 0 && getDataManager().get(dashTimer) <= 0) {
          getDataManager().set(shootTimer, 50);
          getDataManager().setDirty(shootTimer);
        }
        if (this.ticksExisted % 20 == 0 && random.nextInt(3) == 0 && getDataManager().get(shootTimer) <= 0) {
          getDataManager().set(dashTimer, 20);
          getDataManager().setDirty(dashTimer);
          twirlTimer = 20;
        }
      }
      else if (getDataManager().get(targetBlock).getY() != -1) {
        if (this.ticksExisted % 40 == 0 && !this.getEntityWorld().isRemote) {
          Vec3d target = new Vec3d(getDataManager().get(targetBlock).getX() + 0.5 + (random.nextFloat() - 0.5f) * 11.0f, getDataManager().get(targetBlock).getY() + 4.0 + (random.nextFloat() - 0.5f) * 11.0f, getDataManager().get(targetBlock).getZ() + 0.5 + (random.nextFloat() - 0.5f) * 11.0f);
          this.getDataManager().set(targetDirectionX, (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(posX, posY, posZ, target.x, target.y, target.z, getDataManager().get(targetDirectionX))));
          this.getDataManager().set(targetDirectionY, (float) Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, target.x, target.y, target.z)));
          this.getDataManager().setDirty(targetDirectionX);
          this.getDataManager().setDirty(targetDirectionY);
        }
      }
      else {
        if (this.ticksExisted % 40 == 0 && !this.getEntityWorld().isRemote) {
          this.getDataManager().set(targetDirectionX, (float) Math.toRadians(random.nextFloat() * 360.0f));
          this.getDataManager().set(targetDirectionY, (float) Math.toRadians(random.nextFloat() * 180.0f - 90.0f));
          this.getDataManager().setDirty(targetDirectionX);
          this.getDataManager().setDirty(targetDirectionY);
        }
      }
      if (this.ticksExisted % 5 == 0) {
        prevMoveVec = moveVec;
        moveVec = Util.lookVector(this.getDataManager().get(targetDirectionX), this.getDataManager().get(targetDirectionY)).scale(getAttackTarget() != null ? (getDataManager().get(dashTimer) > 0 ? 0.2 : 0.15) : 0.1);
      }
      float motionInterp = ((float) (this.ticksExisted % 5)) / 5.0f;
      this.motionX = (1.0f - motionInterp) * prevMoveVec.x + (motionInterp) * moveVec.x;
      this.motionY = (1.0f - motionInterp) * prevMoveVec.y + (motionInterp) * moveVec.y;
      this.motionZ = (1.0f - motionInterp) * prevMoveVec.z + (motionInterp) * moveVec.z;
      this.rotationYaw = (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(0, 0, 0, motionX, motionY, motionZ, rotationYaw));
      this.rotationPitch = (float) Math.toRadians(Util.pitchDegreesBetweenPoints(0, 0, 0, motionX, motionY, motionZ));
      if (getDataManager().get(dashTimer) > 0) {
        EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX + ((random.nextDouble()) - 0.5), posY + 0.25 + ((random.nextDouble()) - 0.5), posZ + ((random.nextDouble()) - 0.5), -0.25 * moveVec.x, -0.25 * moveVec.y, -0.25 * moveVec.z, 76, 230, 0);
      }
      if (getDataManager().get(happiness) < -25 && this.ticksExisted % 20 == 0 && this.getAttackTarget() == null) {
        List<EntityPlayer> players = (List<EntityPlayer>) getEntityWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - 16.0, posY - 16.0, posZ - 16.0, posX + 16.0, posY + 16.0, posZ + 16.0));
        if (players.size() > 0) {
          this.setAttackTarget(players.get(0));
        }
      }
    }
    else {
      if (this.getHealth() > 0.75f * getMaxHealth()) {
        getDataManager().set(stunned, false);
        getDataManager().setDirty(stunned);
      }
      this.rotationPitch *= 0.9;
      this.motionX = 0.9 * motionX;
      this.motionY = -0.05;
      this.motionZ = 0.9 * motionZ;
    }
    if (getDataManager().get(hostile)) {
      getDataManager().set(happiness, -900.0f);
      getDataManager().setDirty(happiness);
    }
    if (this.getHappiness() > 0) {
      this.setHappiness(getHappiness() - 0.001f);
    }
  }
  @Override
  public int getBrightnessForRender() {
    if (getDataManager().get(stunned).booleanValue()) { return 128; }
    float f = 0.5F;
    f = MathHelper.clamp(f, 0.0F, 1.0F);
    int i = super.getBrightnessForRender();
    int j = i & 255;
    int k = i >> 16 & 255;
    j = j + (int) (f * 15.0F * 16.0F);
    if (j > 240) {
      j = 240;
    }
    return j | k << 16;
  }
  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {
    getEntityWorld().playSound(posX, posY, posZ, EntitySprite.hurtSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 0.7f, false);
    getDataManager().set(happiness, getDataManager().get(happiness) - 5);
    this.getDataManager().setDirty(happiness);
    if (source.getTrueSource() instanceof EntityLivingBase) {
      this.setAttackTarget((EntityLivingBase) source.getTrueSource());
    }
    return super.attackEntityFrom(source, amount);
  }
  @Override
  public boolean attackEntityAsMob(Entity entity) {
    if (entity instanceof EntityLivingBase) {
      this.setAttackTarget((EntityLivingBase) entity);
    }
    return super.attackEntityAsMob(entity);
  }
  @Override
  public boolean isAIDisabled() {
    return false;
  }
  @Override
  public void setDead() {
    super.setDead();
    getEntityWorld().playSound(posX, posY, posZ, EntitySprite.hurtSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, (random.nextFloat() * 0.1f + 0.7f) / 2.0f, false);
  }
  @Override
  protected boolean canDespawn() {
    return false;
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    //    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0);
    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2);
    //SPEED 2
    ConfigSpawnEntity.syncInstance(this, config.settings);
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
    getDataManager().set(dashTimer, compound.getInteger("dashTimer"));
    getDataManager().set(shootTimer, compound.getInteger("shootTimer"));
    getDataManager().set(happiness, compound.getFloat("happiness"));
    getDataManager().set(stunned, compound.getBoolean("stunned"));
    getDataManager().set(hostile, compound.getBoolean("hostile"));
    getDataManager().set(targetBlock, new BlockPos(compound.getInteger("targetBlockX"), compound.getInteger("targetBlockY"), compound.getInteger("targetBlockZ")));
    getDataManager().set(lastTargetBlock, new BlockPos(compound.getInteger("lastTargetBlockX"), compound.getInteger("lastTargetBlockY"), compound.getInteger("lastTargetBlockZ")));
    getDataManager().set(lastLastTargetBlock, new BlockPos(compound.getInteger("lastLastTargetBlockX"), compound.getInteger("lastLastTargetBlockY"), compound.getInteger("lastLastTargetBlockZ")));
    getDataManager().setDirty(targetDirectionX);
    getDataManager().setDirty(targetDirectionY);
    getDataManager().setDirty(dashTimer);
    getDataManager().setDirty(shootTimer);
    getDataManager().setDirty(happiness);
    getDataManager().setDirty(stunned);
    getDataManager().setDirty(hostile);
    getDataManager().setDirty(targetBlock);
    getDataManager().setDirty(lastTargetBlock);
    getDataManager().setDirty(lastLastTargetBlock);
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setFloat("targetDirectionX", getDataManager().get(targetDirectionX));
    compound.setFloat("targetDirectionY", getDataManager().get(targetDirectionY));
    compound.setInteger("dashTimer", getDataManager().get(dashTimer));
    compound.setInteger("shootTimer", getDataManager().get(shootTimer));
    compound.setFloat("happiness", getDataManager().get(happiness));
    compound.setBoolean("stunned", getDataManager().get(stunned));
    compound.setBoolean("hostile", getDataManager().get(hostile));
    compound.setInteger("targetBlockX", getDataManager().get(targetBlock).getX());
    compound.setInteger("targetBlockY", getDataManager().get(targetBlock).getY());
    compound.setInteger("targetBlockZ", getDataManager().get(targetBlock).getZ());
    compound.setInteger("lastTargetBlockX", getDataManager().get(lastTargetBlock).getX());
    compound.setInteger("lastTargetBlockY", getDataManager().get(lastTargetBlock).getY());
    compound.setInteger("lastTargetBlockZ", getDataManager().get(lastTargetBlock).getZ());
    compound.setInteger("lastLastTargetBlockX", getDataManager().get(lastLastTargetBlock).getX());
    compound.setInteger("lastLastTargetBlockY", getDataManager().get(lastLastTargetBlock).getY());
    compound.setInteger("lastLastTargetBlockZ", getDataManager().get(lastLastTargetBlock).getZ());
  }
  @Override
  public float getHappiness() {
    return getDataManager().get(happiness).floatValue();
  }
  @Override
  public void setHappiness(float value) {
    getDataManager().set(happiness, value);
    getDataManager().setDirty(happiness);
  }
  @Override
  public void setTargetPosition(BlockPos pos) {
    if (!pos.equals(getDataManager().get(lastTargetBlock)) && !pos.equals(getDataManager().get(targetBlock))) {
      getDataManager().set(lastLastTargetBlock, getDataManager().get(lastTargetBlock));
      getDataManager().setDirty(lastLastTargetBlock);
      getDataManager().set(lastTargetBlock, getDataManager().get(targetBlock));
      getDataManager().setDirty(lastTargetBlock);
      getDataManager().set(targetBlock, pos);
      getDataManager().setDirty(targetBlock);
    }
  }
  @Override
  public BlockPos getTargetPosition() {
    return getDataManager().get(targetBlock);
  }
  @Nullable
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/sprite_greater");
  }
}