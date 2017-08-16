package teamroots.emberroot.entity.owl;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.EmberRootZoo;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.ai.EntityAIFlyingAttackOnCollide;
import teamroots.emberroot.entity.ai.EntityAIFlyingFindPerch;
import teamroots.emberroot.entity.ai.EntityAIFlyingLand;
import teamroots.emberroot.entity.ai.EntityAIFlyingPanic;
import teamroots.emberroot.entity.ai.EntityAIFlyingShortWander;
import teamroots.emberroot.entity.ai.EntityAINearestAttackableTargetBounded;

/**
 * Original author: https://github.com/CrazyPants
 */
public class EntityOwl extends EntityAnimal implements IFlyingMob {//
  public static final String NAME = "owl";
  public static enum VariantColors {
    PLAIN, SILVER, BROWN, COPPER;
    public String nameLower() {
      return this.name().toLowerCase();
    }
  }
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntityOwl.class, DataSerializers.VARINT);
  public static final SoundEvent SOUND_HOOT = new SoundEvent(new ResourceLocation(Const.MODID, "owl.hoot_single"));
  public static final SoundEvent SOUND_HOOT_DOUBLE = new SoundEvent(new ResourceLocation(Const.MODID, "owl.hoot_double"));
  public static final SoundEvent SOUND_HURT = new SoundEvent(new ResourceLocation(Const.MODID, "owl.hurt"));
  private static final int owlTimeBetweenEggsMin = 11;
  private static final int owlTimeBetweenEggsMax = 77;
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityOwl.class, EnumCreatureType.CREATURE);
  private float wingRotation;
  private float prevWingRotation;
  private float wingRotDelta = 1.0F;
  private float destPos;
  private float prevDestPos;
  private float bodyAngle = 5;
  private float targetBodyAngle = 0;
  private float wingAngle;
  private double groundSpeedRatio = 0.25;
  private float climbRate = 0.25f;
  private float turnRate = 30;
  public int timeUntilNextEgg;
  private float owlSpiderDamageMultiplier = 0.5F;//TODO: CONFIG
  public EntityOwl(World worldIn) {
    super(worldIn);
    setSize(0.4F, 0.85F);
    stepHeight = 1.0F;
    int pri = 0;
    tasks.addTask(++pri, new EntityAIFlyingPanic(this, 2));
    tasks.addTask(++pri, new EntityAIFlyingAttackOnCollide(this, 2.5, false));
    tasks.addTask(++pri, new EntityAIMate(this, 1.0));
    tasks.addTask(++pri, new EntityAITempt(this, 1.0D, Items.SPIDER_EYE, false));
    tasks.addTask(++pri, new EntityAIFollowParent(this, 1.5));
    tasks.addTask(++pri, new EntityAIFlyingLand(this, 2));
    tasks.addTask(++pri, new EntityAIFlyingFindPerch(this, 2, 80));
    tasks.addTask(++pri, new EntityAIFlyingShortWander(this, 2, 150));
    tasks.addTask(++pri, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    tasks.addTask(++pri, new EntityAILookIdle(this));
    EntityAINearestAttackableTargetBounded<EntitySpider> targetSpiders = new EntityAINearestAttackableTargetBounded<EntitySpider>(this, EntitySpider.class,
        true, true);
    targetSpiders.setMaxDistanceToTarget(12);
    targetSpiders.setMaxVerticalDistanceToTarget(24);
    targetTasks.addTask(0, targetSpiders);
    moveHelper = new FlyingMoveHelper(this);
    timeUntilNextEgg = getNextLayingTime();
  }
  public Integer getVariant() {
    return getDataManager().get(variant);
  }
  public VariantColors getVariantEnum() {
    return VariantColors.values()[getVariant()];
  }
  @Override
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected PathNavigate createNavigator(World worldIn) {
    return new FlyingPathNavigate(this, worldIn);
  }
  @Override
  public FlyingPathNavigate getFlyingNavigator() {
    return (FlyingPathNavigate) getNavigator();
  }
  @Override
  public float getBlockPathWeight(BlockPos pos) {
    IBlockState bs = world.getBlockState(pos.down());
    return bs.getBlock().getMaterial(bs) == Material.LEAVES ? 10.0F : 0;
  }
  @Override
  public boolean attackEntityAsMob(Entity entityIn) {
    super.attackEntityAsMob(entityIn);
    float attackDamage = (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    if (entityIn instanceof EntitySpider) {
      attackDamage *= owlSpiderDamageMultiplier;
    }
    return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), attackDamage);
  }
  @Override
  public void onLivingUpdate() {
    // setDead();
    super.onLivingUpdate();
    prevWingRotation = wingRotation;
    prevDestPos = destPos;
    destPos = (float) (destPos + (onGround ? -1 : 4) * 0.3D);
    destPos = MathHelper.clamp(destPos, 0.0F, 1.0F);
    if (!onGround && wingRotDelta < 1.0F) {
      wingRotDelta = 1.0F;
    }
    wingRotDelta = (float) (wingRotDelta * 0.9D);
    float flapSpeed = 2f;
    double yDelta = Math.abs(posY - prevPosY);
    if (yDelta != 0) {
      // normalise between 0 and 0.02
      yDelta = Math.min(1, yDelta / 0.02);
      yDelta = Math.max(yDelta, 0.75);
      flapSpeed *= yDelta;
    }
    wingRotation += wingRotDelta * flapSpeed;
    if (!world.isRemote && !isChild() && --timeUntilNextEgg <= 0) {
      if (isOnLeaves()) {
        playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        dropItem(EmberRootZoo.itemOwlEgg, 1);
      }
      timeUntilNextEgg = getNextLayingTime();
    }
    AxisAlignedBB movedBB = getEntityBoundingBox().offset(0, motionY, 0);
    BlockPos ep = getPosition();
    BlockPos pos = new BlockPos(ep.getX(), movedBB.maxY, ep.getZ());
    IBlockState bs = world.getBlockState(pos);
    Block block = bs.getBlock();
    if (block.getMaterial(bs) != Material.AIR) {
      AxisAlignedBB bb = block.getCollisionBoundingBox(bs, world, pos);
      if (bb != null) {
        double ouch = movedBB.maxY - bb.minY;
        if (ouch == 0) {
          motionY = -0.1;
        }
        else {
          motionY = 0;
        }
      }
    }
    if (onGround) {
      motionX *= groundSpeedRatio;
      motionZ *= groundSpeedRatio;
    }
  }
  private boolean isOnLeaves() {
    IBlockState bs = world.getBlockState(getPosition().down());
    return bs.getBlock().getMaterial(bs) == Material.LEAVES;
  }
  /*
   * //this ONLY fires serverside. however motionX only affects things
   * clientside. so i moved the collision detection to the udptae
   * 
   * @Override public void moveEntityWithHeading(float strafe, float forward) {
   * 
   * System.out.println("isRemote"+this.world.isRemote);//always false so always
   * server System.out.println("!!strafe"+strafe);
   * System.out.println("!!forward"+forward); moveRelative(strafe, forward,
   * 0.1f);
   * 
   * // Dont fly up inot things AxisAlignedBB movedBB =
   * getEntityBoundingBox().offset(0, motionY, 0); BlockPos ep = getPosition();
   * BlockPos pos = new BlockPos(ep.getX(), movedBB.maxY, ep.getZ());
   * IBlockState bs = world.getBlockState(pos); Block block = bs.getBlock(); if
   * (block.getMaterial(bs) != Material.AIR) { AxisAlignedBB bb =
   * block.getCollisionBoundingBox(bs, world, pos); if (bb != null) { double
   * ouch = movedBB.maxY - bb.minY; if (ouch == 0) { motionY = -0.1; } else {
   * motionY = 0; } } }
   * 
   * 
   * // drag motionX *= 0.8; motionY *= 0.8; motionZ *= 0.8;
   * 
   * onGround = EntityUtil.isOnGround(this);
   * 
   * isAirBorne = !onGround;
   * 
   * if (onGround) { motionX *= groundSpeedRatio; motionZ *= groundSpeedRatio; }
   * 
   * addVelocity(motionX, motionY, motionZ);//moveEntity prevLimbSwingAmount =
   * limbSwingAmount; double deltaX = posX - prevPosX; double deltaZ = posZ -
   * prevPosZ; float f7 = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ) *
   * 4.0F; if (f7 > 1.0F) { f7 = 1.0F; } limbSwingAmount += (f7 -
   * limbSwingAmount) * 0.4F; limbSwing += limbSwingAmount;
   * 
   * }
   */
  @Override
  public boolean isEntityInsideOpaqueBlock() {
    if (noClip) {
      return false;
    }
    else {
      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
      for (int i = 0; i < 8; ++i) {
        int x = MathHelper.floor(posX + ((i >> 1) % 2 - 0.5F) * width * 0.8F);
        int y = MathHelper.floor(posY + ((i >> 0) % 2 - 0.5F) * 0.1F + getEyeHeight());
        // I added this check as it was sometimes clipping into the block above
        if (y > getEntityBoundingBox().maxY) {
          y = MathHelper.floor(getEntityBoundingBox().maxY);
        }
        int z = MathHelper.floor(posZ + ((i >> 2) % 2 - 0.5F) * width * 0.8F);
        if (pos.getX() != x || pos.getY() != y || pos.getZ() != z) {
          pos.setPos(x, y, z);
          if (world.getBlockState(pos).getBlock().isOpaqueCube(world.getBlockState(pos))) { return true; }
        }
      }
      return false;
    }
  }
  private void calculateWingAngle(float partialTicks) {
    float flapComletion = prevWingRotation + (wingRotation - prevWingRotation) * partialTicks;
    float onGroundTimerThing = prevDestPos + (destPos - prevDestPos) * partialTicks;
    wingAngle = (MathHelper.sin(flapComletion) + 1.0F) * onGroundTimerThing;
    if (onGround) {
      wingAngle = (float) Math.toRadians(3);
    }
  }
  private void calculateBodyAngle(float partialTicks) {
    if (onGround) {
      bodyAngle = 7;
      targetBodyAngle = 7;
      return;
    }
    // ignore y as we want no tilt going straight up or down
    Vec3d motionVec = new Vec3d(motionX, 0, motionZ);
    double speed = motionVec.lengthVector();
    // normalise between 0 - 0.1
    speed = Math.min(1, speed * 10);
    targetBodyAngle = 20 + ((float) speed * 30);
    if (targetBodyAngle == bodyAngle) { return; }
    if (targetBodyAngle > bodyAngle) {
      bodyAngle += (2 * partialTicks);
      if (bodyAngle > targetBodyAngle) {
        bodyAngle = targetBodyAngle;
      }
    }
    else {
      bodyAngle -= (1 * partialTicks);
      if (bodyAngle < targetBodyAngle) {
        bodyAngle = targetBodyAngle;
      }
    }
  }
  public void calculateAngles(float partialTicks) {
    calculateBodyAngle(partialTicks);
    calculateWingAngle(partialTicks);
  }
  public float getBodyAngle() {
    return (float) Math.toRadians(bodyAngle);
  }
  public float getWingAngle() {
    return wingAngle;
  }
  @Override
  public float getEyeHeight() {
    return height;
  }
  @Override
  protected void updateFallState(double y, boolean onGroundIn, IBlockState blockIn, BlockPos pos) {}
  @Override
  public int getTalkInterval() {
    return 5;//TODO Config.owlHootInterval;
  }
  @Override
  public void playLivingSound() {
    SoundEvent snd = getAmbientSound();
    if (snd == null) { return; }
    if (world != null && !world.isRemote && (world.isDaytime() || getAttackTarget() != null)) { return; }
    float volume = getSoundVolume() * 0.5f;//Config.owlHootVolumeMult;
    float pitch = 0.8f * getSoundPitch();
    playSound(snd, volume, pitch);
  }
  @Override
  protected SoundEvent getAmbientSound() {
    if (world.rand.nextBoolean()) {
      return SOUND_HOOT_DOUBLE;
    }
    else {
      return SOUND_HOOT;
    }
  }
  @Override
  protected SoundEvent getHurtSound(DamageSource s) {
    return SOUND_HURT;
  }
  @Override
  protected SoundEvent getDeathSound() {
    return SOUND_HURT;
  }
  @Override
  public EntityOwl createChild(EntityAgeable ageable) {
    return new EntityOwl(world);
  }
  @Override
  public boolean isBreedingItem(ItemStack stack) {
    return stack != null && stack.getItem() == Items.SPIDER_EYE;
  }
  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
  }
  @Override
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/owl");
  }
  @Override
  public float getMaxTurnRate() {
    return turnRate;
  }
  @Override
  public float getMaxClimbRate() {
    return climbRate;
  }
  @Override
  public EntityCreature asEntityCreature() {
    return this;
  }
  private int getNextLayingTime() {
    int dif = owlTimeBetweenEggsMax - owlTimeBetweenEggsMin;
    return owlTimeBetweenEggsMin + rand.nextInt(dif);
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    if (compound.hasKey("EggLayTime")) {
      this.timeUntilNextEgg = compound.getInteger("EggLayTime");
      getDataManager().set(variant, compound.getInteger("variant"));
      getDataManager().setDirty(variant);
    }
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("EggLayTime", this.timeUntilNextEgg);
    compound.setInteger("variant", getDataManager().get(variant));
  }
  @Override
  public boolean canBeLeashedTo(EntityPlayer player) {
    boolean ret = !this.getLeashed() && (this instanceof IMob);
    return ret;
  }
}
