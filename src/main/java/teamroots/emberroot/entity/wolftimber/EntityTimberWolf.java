package teamroots.emberroot.entity.wolftimber;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.deer.EntityDeer;

public class EntityTimberWolf extends EntityTameable {
  public static final DataParameter<Boolean> hasHorns = EntityDataManager.<Boolean> createKey(EntityTimberWolf.class, DataSerializers.BOOLEAN);
  public static final String NAME = "timberwolf";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityTimberWolf.class, EnumCreatureType.MONSTER);
  public EntityTimberWolf(World world) {
    super(world);
    setSize(1.5f, 1.5f);
    this.experienceValue = 3;
    //		EntityWolf wolf;
  }
  //	@Override
  //	public boolean getCanSpawnHere(){
  //		if (getEntityWorld().getLight(getPosition()) >= 9){
  //			if (getEntityWorld().getBlockState(getPosition().down()).getBlock() == RegistryManager.otherworldGrass
  //					|| getEntityWorld().getBlockState(getPosition().down(2)).getBlock() == RegistryManager.otherworldGrass){
  //				return true;
  //			}
  //		}
  //		return false;
  //	}
  //    public static void func_189790_b(DataFixer p_189790_0_)
  //    {
  //        EntityLiving.func_189752_a(p_189790_0_, "DireWolf");
  //    }
  protected void initEntityAI() {
    this.aiSit = new EntityAISit(this);
    this.tasks.addTask(1, new EntityAISwimming(this));
    this.tasks.addTask(2, this.aiSit);
    this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
    this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
    this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
    this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(9, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
    this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
    this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
    this.targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
        return p_apply_1_ instanceof EntitySheep || p_apply_1_ instanceof EntityRabbit || p_apply_1_ instanceof EntityDeer;
      }
    }));
    this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
  }
  @Nullable
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/wolf_timber");
  }
  @Override
  public void setScaleForAge(boolean child) {
    this.setScale(child ? 0.5f : 1.0f);
  }
  @Override
  public boolean isAIDisabled() {
    return false;
  }
  @Override
  public boolean processInteract(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (this.isTamed()) {
      if (stack != null) {
        if (stack.getItem() instanceof ItemFood) {
          ItemFood itemfood = (ItemFood) stack.getItem();
          if (itemfood.isWolfsFavoriteMeat() && this.getHealth() < getMaxHealth()) {
            if (!player.capabilities.isCreativeMode) {
              stack.shrink(1);
            }
            this.heal((float) itemfood.getHealAmount(stack));
            return true;
          }
        }
      }
      if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(stack)) {
        this.aiSit.setSitting(!this.isSitting());
        this.isJumping = false;
        this.navigator.clearPathEntity();
        this.setAttackTarget((EntityLivingBase) null);
      }
    }
    return super.processInteract(player, hand);
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224F);
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  public EntityAgeable createChild(EntityAgeable ageable) {
    return new EntityTimberWolf(ageable.world);
  }
  public float getEyeHeight() {
    return this.isChild() ? this.height : 1.3F;
  }
}