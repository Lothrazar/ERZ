package teamroots.emberroot.entity.wolftimber;

import javax.annotation.Nullable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;

public class EntityTimberWolf extends EntityMob {

  public static final String NAME = "timberwolf";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityTimberWolf.class, EnumCreatureType.MONSTER);
  public static boolean attackSkeleton;

  public EntityTimberWolf(World world) {
    super(world);
    //setSize(1.5f, 1.5f);
    this.experienceValue = 3;
  }

  protected void initEntityAI() {
    this.tasks.addTask(1, new EntityAISwimming(this));
    this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
    this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, true));
    this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(9, new EntityAILookIdle(this));
    this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
    if (attackSkeleton) {
      this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntitySkeleton>(this, EntitySkeleton.class, false));
    }
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/wolf_timber");
  }

  @Override
  public boolean isAIDisabled() {
    return false;
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224F);
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }

  public float getEyeHeight() {
    return this.isChild() ? this.height : 1.3F;
  }
}