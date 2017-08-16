package teamroots.emberroot.entity.frozen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.util.EntityUtil;

/**
 * Original author: https://github.com/CrazyPants
 */
public class EntityFrozenKnight extends EntitySkeleton {
  public static final String NAME = "skeleton_frozen";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityFrozenKnight.class, EnumCreatureType.MONSTER);
 
  private float fallenKnightChancePerArmorPiece = 0.66f;
  private float fallenKnightChanceArmorUpgrade = 0.2f;
  private double fallenKnightChanceShield = 0.5f;
  public EntityFrozenKnight(World world) {
    super(world);
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes(); 
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue( 0.2F);
    
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected void initEntityAI() {
    //super.initEntityAI();
    this.tasks.addTask(1, new EntityAISwimming(this));
    // this.tasks.addTask(2, new EntityAIRestrictSun(this));
    // this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
//    
//    
//    this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
    this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(6, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    //    this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    //targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, false));
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
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, getSwordForLevel(equipmentLevel));
    if (Math.random() <= fallenKnightChanceShield) {
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
    }
  }
  //  private int getRandomEquipmentLevel() {
  //    return getRandomEquipmentLevel(EntityUtil.getDifficultyMultiplierForLocation(world, posX, posY, posZ));
  //  }
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
  private ItemStack getSwordForLevel(int swordLevel) {
    ////have a better chance of not getting a wooden or stone sword
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
    //From base entity living class
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextGaussian() * 0.05D, 1));
    //    func_189768_a(SkeletonType.NORMAL);//skeleton types do not exist anymore in 1.11.2. so its always normal.
    addRandomArmor();
    setEnchantmentBasedOnDifficulty(di); //enchantEquipment();
    float f = di.getClampedAdditionalDifficulty();
    this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);
    setCanPickUpLoot(rand.nextFloat() < 0.55F * f); 
    return livingData;
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound root) {
    super.writeEntityToNBT(root); 
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound root) {
    super.readEntityFromNBT(root); 
  }
 
  @Override
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/skeleton_frozen");
  }
  //  @Override
  //  public void onLivingUpdate() {
  //    //block from burning in sun
  //    super.onLivingUpdate();
  //  }
  @Override
  public boolean attackEntityAsMob(Entity entityIn) {
    if (entityIn instanceof EntityPlayer) {
      EntityPlayer p = (EntityPlayer) entityIn;
      if (p.isPotionActive(MobEffects.SLOWNESS) == false) {
        p.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
      }
    }
    return super.attackEntityAsMob(entityIn);
  }
}
