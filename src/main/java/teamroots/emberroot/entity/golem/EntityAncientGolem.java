package teamroots.emberroot.entity.golem;
import java.awt.Color;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.entity.deer.EntityDeer;

public class EntityAncientGolem extends EntityMob {
  private static final double MAX_HEALTH = 25.0D;
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntityAncientGolem.class, DataSerializers.VARINT);
  public static final DataParameter<Integer> FIRESPEED = EntityDataManager.<Integer> createKey(EntityAncientGolem.class, DataSerializers.VARINT);
  public static enum VariantColors {
    RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE;
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
        case YELLOW:
          return new Color(227, 225, 2);
        default:
        break;
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
    this.getDataManager().register(FIRESPEED, MathHelper.getInt(rand, 40, 110));
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
    switch (this.getVariantEnum()) {
      case ORANGE:
      case RED:
      case PURPLE:
        this.isImmuneToFire = true;
      break;
      case BLUE:
      case GREEN:
      default:
        this.isImmuneToFire = false;
      break;
    }
  }
  @Override
  public String getName() {
    if (this.hasCustomName()) {
      return this.getCustomNameTag();
    }
    else {
      String s = EntityList.getEntityString(this);
      if (s == null) {
        s = "generic";
      }
      String var = this.getVariantEnum().nameLower();
      return I18n.translateToLocal("entity." + s + "." + var + ".name");
    }
  }
  @Override
  protected void initEntityAI() {
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIAttackMelee(this, 0.46D, true));
    this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.46D));
    this.tasks.addTask(7, new EntityAIWander(this, 0.46D));
    this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    switch (this.getVariantEnum()) {
      case BLUE:
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntitySlime.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      break;
      case GREEN:
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityZombie.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      break;
      case ORANGE:
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      break;
      case PURPLE:
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityEnderman.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      break;
      case RED:
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPigZombie.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
      break;
      case YELLOW://gold is the only one starting passive to the player
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityDeer.class, true));
      break;
      default:
      break;
    }
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(MAX_HEALTH);
  }
  @Override
  public void onUpdate() {
    super.onUpdate();
    this.rotationYaw = this.rotationYawHead;
    if (this.ticksExisted % getDataManager().get(FIRESPEED) == 0 && this.getAttackTarget() != null) {
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
