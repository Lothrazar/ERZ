package teamroots.emberroot.entity.spriteguardian;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.EmberRootZoo;
import teamroots.emberroot.entity.sprite.EntitySprite;
import teamroots.emberroot.entity.spritegreater.EntityGreaterSprite;
import teamroots.emberroot.entity.spritegreater.EntitySpriteProjectile;
import teamroots.emberroot.util.Util;

public class EntitySpriteGuardianBoss extends EntityFlying {// implements IRangedAttackMob {
  public float range = 64;
  public ArrayList<Vec3d> pastPositions = new ArrayList<Vec3d>();
  public static final DataParameter<Float> targetDirectionX = EntityDataManager.<Float> createKey(EntitySpriteGuardianBoss.class, DataSerializers.FLOAT);
  public static final DataParameter<Float> targetDirectionY = EntityDataManager.<Float> createKey(EntitySpriteGuardianBoss.class, DataSerializers.FLOAT);
  public static final DataParameter<Integer> pacifiedTimer = EntityDataManager.<Integer> createKey(EntitySpriteGuardianBoss.class, DataSerializers.VARINT);
  public static final DataParameter<Boolean> pacified = EntityDataManager.<Boolean> createKey(EntitySpriteGuardianBoss.class, DataSerializers.BOOLEAN);
  public static final DataParameter<Boolean> tracking = EntityDataManager.<Boolean> createKey(EntitySpriteGuardianBoss.class, DataSerializers.BOOLEAN);
  public static final DataParameter<Boolean> hasGuards = EntityDataManager.<Boolean> createKey(EntitySpriteGuardianBoss.class, DataSerializers.BOOLEAN);
  public static final DataParameter<Integer> fadeTimer = EntityDataManager.<Integer> createKey(EntitySpriteGuardianBoss.class, DataSerializers.VARINT);
  public static final DataParameter<Integer> projectiles = EntityDataManager.<Integer> createKey(EntitySpriteGuardianBoss.class, DataSerializers.VARINT);
  public static final String NAME = "rootsonespriteboss";
  public float addDirectionX = 0;
  public float addDirectionY = 0;
  Random random = new Random();
  public Vec3d moveVec = new Vec3d(0, 0, 0);
  public Vec3d prevMoveVec = new Vec3d(0, 0, 0);
  public static SoundEvent ambientSound = new SoundEvent(new ResourceLocation(Const.MODID, "bossambient"));
  public static SoundEvent hurtSound = new SoundEvent(new ResourceLocation(Const.MODID, "bosshurt"));
  public static SoundEvent departureSound = new SoundEvent(new ResourceLocation(Const.MODID, "bossdeath"));
  private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
  public EntitySpriteGuardianBoss(World worldIn) {
    super(worldIn);
    setSize(2.0f, 2.0f);
    this.noClip = true;
    this.isAirBorne = true;
    this.experienceValue = 20;
    for (int i = 0; i < 30; i++) {
      pastPositions.add(new Vec3d(posX, posY, posZ));
    }
    this.rotationYaw = rand.nextInt(240) + 60;
  }
  //  @Override
  //  public ITextComponent getDisplayName() {
  //    return new TextComponentString("Guardian of Sprites:LANG FILE");
  //  }
  @Override
  public boolean isNonBoss() {
    return false;
  }
  @Override
  protected void entityInit() {
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
  public void collideWithEntity(Entity entity) {
    if (this.getAttackTarget() != null && this.getHealth() > 0 && !getDataManager().get(pacified).booleanValue()) {
      if (entity.getUniqueID().compareTo(this.getAttackTarget().getUniqueID()) == 0) {
        ((EntityLivingBase) entity).attackEntityFrom(DamageSource.GENERIC, 4.0f);
        float magnitude = (float) Math.sqrt(motionX * motionX + motionZ * motionZ);
        ((EntityLivingBase) entity).knockBack(this, 3.0f * magnitude + 0.1f, -motionX / magnitude + 0.1, -motionZ / magnitude + 0.1);
        ((EntityLivingBase) entity).attackEntityAsMob(this);
        ((EntityLivingBase) entity).setRevengeTarget(this);
      }
    }
  }
  @Override
  public void updateAITasks() {
    super.updateAITasks();
  }
  @Override
  public void onUpdate() {
    super.onUpdate();
    float velocityScale = 1.0f;
    float addedMotionY = 0.0f;
    if (getDataManager().get(pacified) && getDataManager().get(fadeTimer) > 0) {
      getDataManager().set(fadeTimer, getDataManager().get(fadeTimer) - 1);
      getDataManager().setDirty(fadeTimer);
    }
    if (getDataManager().get(fadeTimer) > 0) {
      for (int i = 0; i < 5; i++) {
        Vec3d location = pastPositions.get(rand.nextInt(20)).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(3.0f));
        EmberRootZoo.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
      }
    }
    if (getDataManager().get(fadeTimer) == 0 && getDataManager().get(pacified)) {
      for (int i = 0; i < 20; i++) {
        for (int j = 0; j < 5; j++) {
          Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(3.0f));
          EmberRootZoo.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
        }
        for (int j = 0; j < 2; j++) {
          Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(1.5f));
          EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
        }
      }
      getEntityWorld().playSound(posX, posY, posZ, departureSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 0.95f, false);
      if (!getEntityWorld().isRemote) {
        //        getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.otherworldLeaf, 1)));
        //        for (int i = 0; i < 24; i++) {
        //          if (rand.nextInt(2) == 0) {
        //            getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.otherworldLeaf, 1)));
        //          }
        //        }
        //        for (int i = 0; i < 5; i++) {
        //          getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.otherworldSubstance, 1)));
        //        }
        //        for (int i = 0; i < 8; i++) {
        //          if (rand.nextInt(2) == 0) {
        //            getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.otherworldSubstance, 1)));
        //          }
        //        }
        //        getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.runeStone, 1)));
        //        for (int i = 0; i < 5; i++) {
        //          if (rand.nextInt(2) == 0) {
        //            getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.runeStone, 1)));
        //          }
        //        }
        //        int chance = rand.nextInt(4);
        //        if (chance == 0) {
        //          getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.itemCharmConjuration, 1)));
        //        }
        //        if (chance == 1) {
        //          getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.itemCharmEvocation, 1)));
        //        }
        //        if (chance == 2) {
        //          getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.itemCharmIllusion, 1)));
        //        }
        //        if (chance == 3) {
        //          getEntityWorld().spawnEntity(new EntityItem(getEntityWorld(), posX, posY + 0.5, posZ, new ItemStack(RegistryManager.itemCharmRestoration, 1)));
        //        }
      }
      setDead();
    }
    if (this.ticksExisted % 20 == 0) {
      List<EntityPlayer> players = getEntityWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - 72, posY - 72, posZ - 72, posX + 72, posY + 72, posZ + 72));
      boolean foundPrevious = false;
      if (this.getAttackTarget() != null) {
        for (int i = 0; i < players.size(); i++) {
          if (players.get(i).getUniqueID().compareTo(getAttackTarget().getUniqueID()) == 0) {
            foundPrevious = true;
          }
        }
      }
      if (!foundPrevious && players.size() > 0) {
        this.setAttackTarget(players.get(rand.nextInt(players.size())));
      }
      else if (!foundPrevious && this.ticksExisted > 100) {
        for (int i = 0; i < 20; i++) {
          for (int j = 0; j < 5; j++) {
            Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(3.0f));
            EmberRootZoo.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
          }
          for (int j = 0; j < 2; j++) {
            Vec3d location = pastPositions.get(i).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(1.5f));
            EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
          }
        }
        getEntityWorld().playSound(posX, posY, posZ, departureSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 0.95f, false);
        this.setDead();
      }
      if (random.nextInt(6) == 0) {
        getEntityWorld().playSound(posX, posY, posZ, ambientSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 0.95f, false);
      }
    }
    if (getHealth() < getMaxHealth() * 0.25f) {
      if (getDataManager().get(hasGuards)) {
        getDataManager().set(hasGuards, false);
        getDataManager().setDirty(hasGuards);
        if (!getEntityWorld().isRemote) {
          for (int i = 0; i < 2; i++) {
            EntityGreaterSprite sprite = new EntityGreaterSprite(getEntityWorld());
            sprite.setPosition(posX + 5.0f * (random.nextFloat() - 0.5f), posY + 5.0f * (random.nextFloat() - 0.5f), posZ + 5.0f * (random.nextFloat() - 0.5f));
            sprite.setHostile();
            sprite.setHealth(sprite.getMaxHealth() / 2.0f);
            sprite.setAttackTarget(getAttackTarget());
            sprite.onInitialSpawn(getEntityWorld().getDifficultyForLocation(getPosition()), (IEntityLivingData) null);
            getEntityWorld().spawnEntity(sprite);
          }
        }
      }
    }
    if (getHealth() < getMaxHealth() * 0.65f) {
      if (ticksExisted % 300 == 0 && !getDataManager().get(pacified) && getDataManager().get(projectiles) == 0) {
        getDataManager().set(projectiles, 6);
        getDataManager().setDirty(projectiles);
      }
      if (getDataManager().get(projectiles) > 0 && !getDataManager().get(pacified) && getAttackTarget() != null) {
        float distanceToTarget = (float) Math.sqrt(Math.pow(posX - getAttackTarget().posX, 2) + Math.pow(posZ - getAttackTarget().posZ, 2));
        if (distanceToTarget > 10) {
          for (int i = 0; i < getDataManager().get(projectiles); i++) {
            Vec3d location = pastPositions.get(1).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(3.0f));
            EmberRootZoo.proxy.spawnParticleMagicSmallSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
            location = pastPositions.get(1).add((new Vec3d(rand.nextFloat() - 0.5, rand.nextFloat() - 0.5, rand.nextFloat() - 0.5)).scale(1.5f));
            EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), location.x, location.y + 1.35f, location.z, 0, 0, 0, 107, 255, 28);
          }
          if (ticksExisted % 30 == 0 && random.nextBoolean() && !getEntityWorld().isRemote) {
            getDataManager().set(projectiles, getDataManager().get(projectiles) - 1);
            getDataManager().setDirty(projectiles);
            EntitySpriteProjectile proj = new EntitySpriteProjectile(getEntityWorld());
            proj.setPosition(pastPositions.get(1).x, pastPositions.get(1).y, pastPositions.get(1).z);
            proj.onInitialSpawn(getEntityWorld().getDifficultyForLocation(getPosition()), null);
            proj.initSpecial(getAttackTarget(), 4.0f);
            getEntityWorld().spawnEntity(proj);
            getEntityWorld().playSound(posX, posY, posZ, EntitySprite.staffcast, SoundCategory.HOSTILE, 0.95f + random.nextFloat() * 0.1f, 0.7f + random.nextFloat() * 0.1f, false);
            for (int i = 0; i < 40; i++) {
              EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY + height / 2.0f, posZ, Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), 107, 255, 28);
            }
          }
        }
      }
    }
    if (getAttackTarget() != null) {
      float distanceToTarget = (float) Math.sqrt(Math.pow(posX - getAttackTarget().posX, 2) + Math.pow(posZ - getAttackTarget().posZ, 2));
      if (distanceToTarget > 30 && !getDataManager().get(tracking)) {
        getDataManager().set(tracking, true);
        getDataManager().setDirty(tracking);
      }
      if (!getDataManager().get(pacified)) {
        velocityScale = 1.0f + Math.max(-0.875f, (25.0f - distanceToTarget) / 20.0f);
      }
      else {
        velocityScale = (Math.max(0, 20.0f - Math.min(20.0f, 200.0f - (float) getDataManager().get(fadeTimer)))) / 200.0f;
      }
    }
    if (getAttackTarget() != null && getDataManager().get(tracking) && !this.getEntityWorld().isRemote) {
      this.getDataManager().set(targetDirectionX, (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY + getAttackTarget().getEyeHeight() / 2.0, getAttackTarget().posZ, getDataManager().get(targetDirectionX))));
      this.getDataManager().set(targetDirectionY, (float) Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, getAttackTarget().posX, getAttackTarget().posY + getAttackTarget().getEyeHeight() / 2.0, getAttackTarget().posZ)));
      this.getDataManager().setDirty(targetDirectionX);
      this.getDataManager().setDirty(targetDirectionY);
    }
    if (getAttackTarget() == null && this.ticksExisted % 25 == 0 && !this.getEntityWorld().isRemote) {
      this.getDataManager().set(targetDirectionX, (float) Math.toRadians(random.nextFloat() * 360.0f));
      this.getDataManager().set(targetDirectionY, (float) Math.toRadians(random.nextFloat() * 180.0f - 90.0f));
      this.getDataManager().setDirty(targetDirectionX);
      this.getDataManager().setDirty(targetDirectionY);
    }
    if (getAttackTarget() != null && getDataManager().get(pacified) && !this.getEntityWorld().isRemote) {}
    int interval = 25;
    if (this.ticksExisted % 200 >= 180) {
      interval = 5;
    }
    if (this.ticksExisted % interval == 0 || this.ticksExisted < 3) {
      prevMoveVec = moveVec;
      moveVec = Util.lookVector(this.getDataManager().get(targetDirectionX), this.getDataManager().get(targetDirectionY)).scale(0.45f * velocityScale);
    }
    float motionInterp = ((float) (this.ticksExisted % interval)) / interval;
    this.motionX = (1.0f - motionInterp) * prevMoveVec.x + (motionInterp) * moveVec.x;
    this.motionY = (1.0f - motionInterp) * prevMoveVec.y + (motionInterp) * moveVec.y;
    this.motionZ = (1.0f - motionInterp) * prevMoveVec.z + (motionInterp) * moveVec.z;
    this.rotationYaw = (float) Math.toRadians(Util.yawDegreesBetweenPointsSafe(0, 0, 0, motionX, motionY, motionZ, rotationYaw));
    this.rotationPitch = (float) Math.toRadians(Util.pitchDegreesBetweenPoints(0, 0, 0, motionX, motionY, motionZ));
    if (getDataManager().get(fadeTimer) > 180 || getDataManager().get(fadeTimer) == 0) {
      for (int i = 1; i < pastPositions.size(); i++) {
        if (pastPositions.get(i).x == 0 && pastPositions.get(i).y == 0 && pastPositions.get(i).z == 0) {
          pastPositions.set(i, pastPositions.get(i - 1));
        }
      }
      for (int i = pastPositions.size() - 1; i > 0; i--) {
        pastPositions.set(i, pastPositions.get(i).scale(0.5).add(pastPositions.get(i - 1).scale(0.5)));
      }
      pastPositions.set(0, new Vec3d(posX, posY, posZ));
    }
  }
  @Override
  public boolean isEntityInvulnerable(DamageSource source) {
    if (getDataManager().get(pacified)) { return true; }
    return false;
  }
  @Override
  public int getBrightnessForRender() {
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
    getEntityWorld().playSound(posX, posY, posZ, hurtSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, random.nextFloat() * 0.1f + 0.95f, false);
    if (source.getTrueSource() instanceof EntityLivingBase) {
      this.setAttackTarget((EntityLivingBase) source.getTrueSource());
      EntityLivingBase entity = ((EntityLivingBase) source.getTrueSource());
      this.moveVec.addVector(entity.getLookVec().x, entity.getLookVec().y, entity.getLookVec().z);
    }
    return super.attackEntityFrom(source, amount);
  }
  @Override
  public void damageEntity(DamageSource source, float amount) {
    if (this.getHealth() - amount <= 0 && !getDataManager().get(pacified).booleanValue()) {
      this.setHealth(1);
      this.bossInfo.setPercent(0);
      getDataManager().set(pacified, true);
      getDataManager().setDirty(pacified);
      getDataManager().set(fadeTimer, 200);
      if (source.getTrueSource() instanceof EntityPlayer) {
        EntityPlayer player = (EntityPlayer) source.getTrueSource();
        //        if (!player.hasAchievement(RegistryManager.achieveGuardianBoss)) {
        //          PlayerManager.addAchievement(player, RegistryManager.achieveGuardianBoss);
        //        }
      }
    }
    else {
      if (!getDataManager().get(pacified).booleanValue()) {
        super.damageEntity(source, amount);
        this.bossInfo.setPercent(getHealth() / getMaxHealth());
      }
    }
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
    getEntityWorld().playSound(posX, posY, posZ, hurtSound, SoundCategory.NEUTRAL, random.nextFloat() * 0.1f + 0.95f, (random.nextFloat() * 0.1f + 0.7f) / 2.0f, false);
  }
  @Override
  protected boolean canDespawn() {
    return false;
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(320.0);
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
    this.bossInfo.setPercent(getHealth() / getMaxHealth());
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
  public float getFade(float partialTicks) {
    if (getDataManager().get(fadeTimer) == 0) {
      return 1.0f;
    }
    else {
      return Math.max(0, (((float) getDataManager().get(fadeTimer) - partialTicks)) / 200.0f);
    }
  }
  @Override
  public void addTrackingPlayer(EntityPlayerMP player) {
    super.addTrackingPlayer(player);
    bossInfo.addPlayer(player);
  }
  @Override
  public void removeTrackingPlayer(EntityPlayerMP player) {
    super.removeTrackingPlayer(player);
    bossInfo.removePlayer(player);
  }
  public static BlockPattern golemPattern;
  public static BlockPattern getGolemPattern() {
    // if (golemPattern == null) {
    golemPattern = FactoryBlockPattern.start().aisle("$^$", "~#~", "###", "#~#").where(
        '^', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.EMERALD_BLOCK)))
        .where(
            '$', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.IRON_BLOCK)))
        .where(
            '#', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.END_STONE)))
        .where(
            '~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
        .build();
    //   }
    return golemPattern;
  }
  @Nullable
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/sprite_boss");
  }
}