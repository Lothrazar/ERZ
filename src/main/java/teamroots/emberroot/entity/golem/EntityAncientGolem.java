package teamroots.emberroot.entity.golem;
import java.awt.Color;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander; 
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import teamroots.emberroot.Const;

public class EntityAncientGolem extends EntityMob {
  private static final int FIRE_TICKRATE = 100;//fire every this many ticks (100)
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntityAncientGolem.class, DataSerializers.VARINT);
  public static enum VariantColors {
    ORANGE, BLUE, GREEN, PURPLE, RED;
    public String nameLower() {
      return this.name().toLowerCase();
    }
    /**
     * r,g,b passed into projectile shot
     * 
     * @return
     */
    public Color getColor() {
      switch (this) {
        case BLUE:
          return new Color(0, 173, 255);
        case GREEN:
          return new Color(57, 255, 56);
        case ORANGE:
          return new Color(255, 64, 16);
        case PURPLE:
          return new Color(255, 56, 249);
        case RED:
           return new Color(179, 3, 2);
      }
      return null;//new Color(0, 0, 0);
    }
  }
  public EntityAncientGolem(World worldIn) {
    super(worldIn);
    setSize(0.6f, 1.8f);
    this.experienceValue = 10;
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
    this.isImmuneToFire = true;
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
  }
  @Override
  protected void initEntityAI() {
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIAttackMelee(this, 0.46D, true));
    this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.46D));
    this.tasks.addTask(7, new EntityAIWander(this, 0.46D));
    this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(8, new EntityAILookIdle(this));
   
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
  }
  @Override
  public void onUpdate() {
    super.onUpdate();
    this.rotationYaw = this.rotationYawHead;
    if (this.ticksExisted % FIRE_TICKRATE == 0 && this.getAttackTarget() != null) {
      if (!getEntityWorld().isRemote) {
        EntityEmberProjectile proj = new EntityEmberProjectile(getEntityWorld());
        proj.getDataManager().set(EntityEmberProjectile.variant, this.getVariant());
        proj.initCustom(posX, posY + 1.6, posZ, getLookVec().x * 0.5, getLookVec().y * 0.5, getLookVec().z * 0.5, 4.0f, this.getUniqueID());
        getEntityWorld().spawnEntity(proj);
      }
    }
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    getDataManager().set(variant, compound.getInteger("variant"));
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("variant", getDataManager().get(variant));
  }
  @Override
  public ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/golem_" + getVariantEnum().nameLower());
  }
}
