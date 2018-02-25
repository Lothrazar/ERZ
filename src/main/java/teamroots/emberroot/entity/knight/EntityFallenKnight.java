package teamroots.emberroot.entity.knight;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.ai.EntityAIMountedArrowAttack;
import teamroots.emberroot.entity.ai.EntityAIMountedAttackOnCollide;
import teamroots.emberroot.entity.mount.EntityFallenMount;
import teamroots.emberroot.util.EntityUtil;
import teamroots.emberroot.util.SpawnUtil;

/**
 * Original author: https://github.com/CrazyPants
 */
public class EntityFallenKnight extends EntitySkeleton {
  public static float CHANCE_BOW;
  public static final String NAME = "knight_fallen";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityFallenKnight.class, EnumCreatureType.MONSTER);
  public static boolean attackVillagers;
  private final EntityAIBreakDoor breakDoorAI = new EntityAIBreakDoor(this);
  private boolean canBreakDoors = false;
  private EntityLivingBase lastAttackTarget = null;
  private boolean firstUpdate = true;
  private boolean isMounted = false;
  private boolean spawned = false;
  public static float fallenKnightChanceMounted = 0.75f;
  private float fallenKnightChancePerArmorPiece = 0.66f;
  private float fallenKnightChanceArmorUpgrade = 0.2f;
  public EntityFallenKnight(World world) {
    super(world);
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected void initEntityAI() {
    super.initEntityAI();
    if (attackVillagers) {
      targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, false));
    }
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
  }
  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
  }
  @Override
  protected SoundEvent getHurtSound(DamageSource s) {
    return SoundEvents.ENTITY_ZOMBIE_HURT;
  }
  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_ZOMBIE_DEATH;
  }
  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (firstUpdate && !world.isRemote) {
      spawnMount();
    }
    if (isRidingMount()) {
      EntityLiving entLiving = ((EntityLiving) getRidingEntity());
      if (lastAttackTarget != getAttackTarget() || firstUpdate) {
        EntityUtil.cancelCurrentTasks(entLiving);
        lastAttackTarget = getAttackTarget();
      }
    }
    firstUpdate = false;
    if (!isMounted == isRidingMount()) {
      getNavigator().clearPathEntity();
      isMounted = isRidingMount();
    }
    //    if (isBurning() && isRidingMount()) {
    //      //getRidingEntity().setFire(8);
    //    }
  }
  private boolean isRidingMount() {
    return isRiding() && getRidingEntity().getClass() == EntityFallenMount.class;
  }
  @Override
  protected void despawnEntity() {
    Entity mount = getRidingEntity();
    super.despawnEntity();
    if (isDead && mount != null) {
      mount.setDead();
    }
  }
  private void spawnMount() {
    if (isRiding() || !spawned) {
      return;
    }
    EntityFallenMount mount = null;
    if (rand.nextFloat() <= fallenKnightChanceMounted) {
      mount = new EntityFallenMount(world);
      mount.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
      DifficultyInstance di = world.getDifficultyForLocation(new BlockPos(mount));
      mount.onInitialSpawn(di, null);
      //NB: don;t check for entity collisions as we know the knight will collide
      if (!SpawnUtil.isSpaceAvailableForSpawn(world, mount, false)) {
        mount = null;
      }
    }
    if (mount != null) {
      setCanPickUpLoot(false);
      setCanBreakDoors(false);
      world.spawnEntity(mount);
      startRiding(mount);
    }
  }
  private void addRandomArmor() {
    float occupiedDiffcultyMultiplier = EntityUtil.getDifficultyMultiplierForLocation(world, posX, posY, posZ);
    int equipmentLevel = getRandomEquipmentLevel(occupiedDiffcultyMultiplier);
    int armorLevel = equipmentLevel;
    if (armorLevel == 1) {
      //Skip gold armor, I don't like it
      armorLevel++;
    }
    float chancePerPiece = fallenKnightChancePerArmorPiece;
    chancePerPiece *= (1 + occupiedDiffcultyMultiplier); //If we have the max occupied factor, double the chance of improved armor
    for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
      ItemStack itemStack = getItemStackFromSlot(slot);
      if (itemStack == null && rand.nextFloat() <= chancePerPiece) {
        Item item = EntityLiving.getArmorByChance(slot, armorLevel);
        if (item != null) {
          ItemStack stack = new ItemStack(item);
          if (armorLevel == 0) {
            ((ItemArmor) item).setColor(stack, 0);
          }
          setItemStackToSlot(slot, stack);
        }
      }
    }
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getWeaponForLevel());
  }
  private int getRandomEquipmentLevel(float occupiedDiffcultyMultiplier) {
    float chanceImprovedArmor = fallenKnightChanceArmorUpgrade;
    chanceImprovedArmor *= (1 + occupiedDiffcultyMultiplier); //If we have the max occupied factor, double the chance of improved armor   
    int armorLevel = rand.nextInt(2);
    for (int i = 0; i < 2; i++) {
      if (rand.nextFloat() <= chanceImprovedArmor) {
        armorLevel++;
      }
    }
    return armorLevel;
  }
  protected boolean isHardDifficulty() {
    return EntityUtil.isHardDifficulty(world);
  }
  private ItemStack getWeaponForLevel() {
    ////have a better chance of not getting a wooden or stone sword
    if (world.rand.nextDouble() < CHANCE_BOW) {
      return new ItemStack(Items.BOW);
    }
    int swordLevel = isHardDifficulty() ? 2 : 1;//TODO: refactor
    if (swordLevel < 2) {
      swordLevel += rand.nextInt(isHardDifficulty() ? 3 : 2);
      swordLevel = Math.min(swordLevel, 2);
    }
    switch (swordLevel) {
      case 0:
        return new ItemStack(Items.WOODEN_SWORD);
      case 1:
        return new ItemStack(Items.STONE_SWORD);
      case 2:
        return new ItemStack(Items.IRON_SWORD);
      case 4:
        return new ItemStack(Items.DIAMOND_SWORD);
    }
    return new ItemStack(Items.IRON_SWORD);
  }
  @Override
  public IEntityLivingData onInitialSpawn(DifficultyInstance di, IEntityLivingData livingData) {
    spawned = true;
    //From base entity living class
    //getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextGaussian() * 0.05D, 1));
    //    func_189768_a(SkeletonType.NORMAL);//skeleton types do not exist anymore in 1.11.2. so its always normal.
    addRandomArmor();
    setEnchantmentBasedOnDifficulty(di); //enchantEquipment();
    float f = di.getClampedAdditionalDifficulty();
    this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
    setCanPickUpLoot(rand.nextFloat() < 0.55F * f);
    setCanBreakDoors(rand.nextFloat() < f * 0.1F);
    return livingData;
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound root) {
    super.writeEntityToNBT(root);
    root.setBoolean("canBreakDoors", canBreakDoors);
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound root) {
    super.readEntityFromNBT(root);
    setCanBreakDoors(root.getBoolean("canBreakDoors"));
  }
  private void setCanBreakDoors(boolean val) {
    if (canBreakDoors != val) {
      canBreakDoors = val;
      if (canBreakDoors) {
        tasks.addTask(1, breakDoorAI);
      }
      else {
        tasks.removeTask(breakDoorAI);
      }
    }
  }
  @Override
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/knight_fallen");
  }
}
