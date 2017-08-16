package teamroots.emberroot.entity.spriteling;
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

public class EntitySpriteling extends EntityFlying implements ISprite {// implements IRangedAttackMob {
  public static final DataParameter<Float> targetDirectionX = EntityDataManager.<Float> createKey(EntitySpriteling.class, DataSerializers.FLOAT);
  public static final DataParameter<Float> targetDirectionY = EntityDataManager.<Float> createKey(EntitySpriteling.class, DataSerializers.FLOAT);
  public static final DataParameter<Integer> dashTimer = EntityDataManager.<Integer> createKey(EntitySpriteling.class, DataSerializers.VARINT);
  public static final DataParameter<Float> happiness = EntityDataManager.<Float> createKey(EntitySpriteling.class, DataSerializers.FLOAT);
  public static final DataParameter<Boolean> stunned = EntityDataManager.<Boolean> createKey(EntitySpriteling.class, DataSerializers.BOOLEAN);
  public static final DataParameter<BlockPos> targetBlock = EntityDataManager.<BlockPos> createKey(EntitySpriteling.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<BlockPos> lastTargetBlock = EntityDataManager.<BlockPos> createKey(EntitySpriteling.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<BlockPos> lastLastTargetBlock = EntityDataManager.<BlockPos> createKey(EntitySpriteling.class, DataSerializers.BLOCK_POS);
  public static final String NAME = "rootsonespriteling";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntitySpriteling.class, EnumCreatureType.MONSTER);
  public float addDirectionX = 0;
  public float range = 64;
  public float addDirectionY = 0;
  public float twirlTimer = 0;
  public Vec3d moveVec = new Vec3d(0, 0, 0);
  public Vec3d prevMoveVec = new Vec3d(0, 0, 0);
  Random random = new Random();
  public int offset = random.nextInt(25);
  public EntitySpriteling(World worldIn) {
    super(worldIn);
    setSize(0.5f, 0.5f);
    this.isAirBorne = true;
    this.noClip = true;
    this.experienceValue = 5;
    this.rotationYaw = rand.nextInt(240) + 60;
  }
  @Override
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(targetDirectionX, Float.valueOf(0));
    this.getDataManager().register(targetDirectionY, Float.valueOf(0));
    this.getDataManager().register(dashTimer, Integer.valueOf(0));
    this.getDataManager().register(happiness, Float.valueOf(0));
    this.getDataManager().register(stunned, Boolean.valueOf(false));
    this.getDataManager().register(targetBlock, new BlockPos(0, -1, 0));
    this.getDataManager().register(lastTargetBlock, new BlockPos(0, -1, 0));
    this.getDataManager().register(lastLastTargetBlock, new BlockPos(0, -1, 0));
  }
  @Override
  public void collideWithEntity(Entity entity) {
    if (this.getAttackTarget() != null && this.getHealth() > 0 && !this.getDataManager().get(stunned).booleanValue()) {
      if (entity.getUniqueID().compareTo(this.getAttackTarget().getUniqueID()) == 0) {
        ((EntityLivingBase) entity).attackEntityFrom(DamageSource.GENERIC, 2.0f);
        float magnitude = (float) Math.sqrt(motionX * motionX + motionZ * motionZ);
        ((EntityLivingBase) entity).knockBack(this, 2.0f * magnitude + 0.1f, -motionX / magnitude + 0.1, -motionZ / magnitude + 0.1);
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
  //    		for (int i = 0; i < 4+lootingModifier; i ++){
  //	    		if (rand.nextInt(2) == 0){
  //	    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY+0.5,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
  //	    		}
  //	    	}
  //    	}
  //    }
  @Override
  public void onUpdate() {
    super.onUpdate();
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
    //    			getEntityWorld().spawnEntityInWorld(new EntityItem(getEntityWorld(),posX,posY,posZ,new ItemStack(RegistryManager.otherworldLeaf,1)));
    //    		}
    //    	}
    if (getDataManager().get(stunned).booleanValue()) {
      this.setAttackTarget(null);
    }
    if (!getDataManager().get(stunned).booleanValue()) {
      if (this.ticksExisted % 20 == 0) {
        if (random.nextInt(4) == 0 && this.getDataManager().get(stunned).booleanValue() == false) {
          getEntityWorld().playSound(posX, posY, posZ, EntitySprite.ambientSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 1.45f, false);
        }
      }
      if (twirlTimer > 0) {
        twirlTimer -= 1.0f;
      }
      if (getDataManager().get(dashTimer) > 0) {
        getDataManager().set(dashTimer, getDataManager().get(dashTimer) - 1);
        getDataManager().setDirty(dashTimer);
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
        if (this.ticksExisted % 20 == 0 && random.nextInt(4) == 0) {
          getDataManager().set(dashTimer, 20);
          getDataManager().setDirty(dashTimer);
          twirlTimer = 20;
        }
      }
      else if (getDataManager().get(targetBlock).getY() != -1) {
        if (this.ticksExisted % 40 == 0 && !this.getEntityWorld().isRemote) {
          Vec3d target = new Vec3d(getDataManager().get(targetBlock).getX() + 0.5 + (random.nextFloat() - 0.5f) * 7.0f, getDataManager().get(targetBlock).getY() + 4.0 + (random.nextFloat() - 0.5f) * 7.0f, getDataManager().get(targetBlock).getZ() + 0.5 + (random.nextFloat() - 0.5f) * 7.0f);
          this.getDataManager().set(targetDirectionX, (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(posX, posY, posZ, target.x, target.y, target.z, getDataManager().get(targetDirectionX).doubleValue())));
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
        if (this.ticksExisted % 40 == 0 && random.nextInt(8) == 0) {
          twirlTimer = 20;
        }
      }
      if (this.ticksExisted % 5 == 0) {
        prevMoveVec = moveVec;
        moveVec = Util.lookVector(this.getDataManager().get(targetDirectionX), this.getDataManager().get(targetDirectionY)).scale(getAttackTarget() != null ? (getDataManager().get(dashTimer) > 0 ? 0.4 : 0.3) : 0.2);
      }
      float motionInterp = ((float) (this.ticksExisted % 5)) / 5.0f;
      this.motionX = (1.0f - motionInterp) * prevMoveVec.x + (motionInterp) * moveVec.x;
      this.motionY = (1.0f - motionInterp) * prevMoveVec.y + (motionInterp) * moveVec.y;
      this.motionZ = (1.0f - motionInterp) * prevMoveVec.z + (motionInterp) * moveVec.z;
      this.rotationYaw = (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(0, 0, 0, motionX, motionY, motionZ, rotationYaw));
      this.rotationPitch = (float) Math.toRadians(Util.pitchDegreesBetweenPoints(0, 0, 0, motionX, motionY, motionZ));
      if (getDataManager().get(dashTimer) > 0) {
        EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX + ((random.nextDouble()) - 0.5) * 0.2, posY + 0.25 + ((random.nextDouble()) - 0.5) * 0.2, posZ + ((random.nextDouble()) - 0.5) * 0.2, -0.25 * moveVec.x, -0.25 * moveVec.y, -0.25 * moveVec.z, 107, 255, 28);
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
    getEntityWorld().playSound(posX, posY, posZ, EntitySprite.hurtSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 1.7f, false);
    getDataManager().set(happiness, getDataManager().get(happiness) - 5);
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
  public void setDead() {
    super.setDead();
    getEntityWorld().playSound(posX, posY, posZ, EntitySprite.hurtSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, (random.nextFloat() * 0.1f + 1.7f) / 2.0f, false);
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
    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2);
    ConfigSpawnEntity.syncInstance(this, config.settings);
    //    
    //    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0);
    //    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
    //    this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    //    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0);
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
    getDataManager().set(happiness, compound.getFloat("happiness"));
    getDataManager().set(stunned, compound.getBoolean("stunned"));
    getDataManager().set(targetBlock, new BlockPos(compound.getInteger("targetBlockX"), compound.getInteger("targetBlockY"), compound.getInteger("targetBlockZ")));
    getDataManager().set(lastTargetBlock, new BlockPos(compound.getInteger("lastTargetBlockX"), compound.getInteger("lastTargetBlockY"), compound.getInteger("lastTargetBlockZ")));
    getDataManager().set(lastLastTargetBlock, new BlockPos(compound.getInteger("lastLastTargetBlockX"), compound.getInteger("lastLastTargetBlockY"), compound.getInteger("lastLastTargetBlockZ")));
    getDataManager().setDirty(targetDirectionX);
    getDataManager().setDirty(targetDirectionY);
    getDataManager().setDirty(dashTimer);
    getDataManager().setDirty(happiness);
    getDataManager().setDirty(stunned);
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
    compound.setFloat("happiness", getDataManager().get(happiness));
    compound.setBoolean("stunned", getDataManager().get(stunned));
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
    if (!pos.equals(getDataManager().get(lastLastTargetBlock)) && !pos.equals(getDataManager().get(lastTargetBlock)) && !pos.equals(getDataManager().get(targetBlock))) {
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
    return new ResourceLocation(Const.MODID, "entity/sprite_mini");
  }
}