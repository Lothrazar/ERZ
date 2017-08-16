package teamroots.emberroot.entity.witch;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.data.Point3i;
import teamroots.emberroot.entity.ai.EntityAIRangedAttack;
import teamroots.emberroot.entity.cat.EntityWitherCat;
import teamroots.emberroot.entity.cat.EntityWitherCat.GrowthMode;
import teamroots.emberroot.util.EntityUtil;
import teamroots.emberroot.util.SpawnUtil;

/**
 * Original author: https://github.com/CrazyPants
 */
public class EntityWitherWitch extends EntityMob implements IRangedAttackMob {
  public static final String NAME = "witherwitch";
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityWitherWitch.class, EnumCreatureType.MONSTER);
  private int attackTimer;
  private EntityLivingBase attackedWithPotion;
  private int healTimer;
  private boolean isHealing;
  private boolean spawned;
  private boolean firstUpdate = true;
  private final List<EntityWitherCat> cats = new ArrayList<EntityWitherCat>();
  private List<NBTTagCompound> loadedCats;
  private final EntityAIRangedAttack rangedAttackAI;
  private int noActiveTargetTime;
  private int witherWitchMaxCats = 3;
  private int witherWitchMinCats = 1;
  public EntityWitherWitch(World world) {
    super(world);
    rangedAttackAI = new EntityAIRangedAttack(this, 1, 60, 10);
    tasks.addTask(1, new EntityAISwimming(this));
    tasks.addTask(2, rangedAttackAI);
    tasks.addTask(2, new EntityAIWander(this, 1.0D));
    tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(3, new EntityAILookIdle(this));
    targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected float applyPotionDamageCalculations(DamageSource damageSource, float damage) {
    //same as a vanilla witch
    damage = super.applyPotionDamageCalculations(damageSource, damage);
    if (damageSource.getTrueSource() == this) {
      damage = 0.0F;
    }
    if (damageSource.isMagicDamage()) {
      damage = (float) (damage * 0.15D);
    }
    return damage;
  }
  @Override
  public boolean isPotionApplicable(PotionEffect potion) {
    //    if(potion.getPotion().isBadEffect())
    //TODO: make witch immune to ALL bad effect?s? WE COULD ?> yeah
    return potion.getPotion() != MobEffects.WITHER && super.isPotionApplicable(potion);
  }
  @Override
  public void setRevengeTarget(EntityLivingBase target) {
    EntityLivingBase curTarget = this.getAttackTarget();
    super.setRevengeTarget(target);
    if (curTarget == target || world.isRemote || target == null) { return; }
    float distToSrc = getDistanceToEntity(target);
    if (distToSrc > getNavigator().getPathSearchRange() && distToSrc < 50) {
      getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(distToSrc + 2);
    }
  }
  @Override
  public IEntityLivingData onInitialSpawn(DifficultyInstance di, IEntityLivingData livingData) {
    spawned = true;
    return super.onInitialSpawn(di, livingData);
  }
  @Override
  public void onLivingUpdate() {
    if (world.isRemote) {
      super.onLivingUpdate();
      return;
    }
    if (firstUpdate) {
      if (spawned) {
        spawnCats();
      }
      else {
        loadCats();
      }
      firstUpdate = false;
    }
    doAttackActions();
    manageCats();
    super.onLivingUpdate();
  }
  protected void doAttackActions() {
    attackTimer--;
    healTimer--;
    EntityLivingBase target = getActiveTarget();
    if (target == null) {
      noActiveTargetTime++;
    }
    else {
      noActiveTargetTime = 0;
    }
    if (shouldStartHeal()) {
      ItemStack potion;
      if (rand.nextFloat() > 0.75) {
        potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_REGENERATION); //BrewingUtil.createRegenerationPotion(false, true, true);
      }
      else {
        potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.STRONG_HEALING);// BrewingUtil.createHealthPotion(false, false, true);
      }
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, potion);
      healTimer = 10;
      isHealing = true;
    }
    else if (target != null && getHeldItem(EnumHand.MAIN_HAND).isEmpty()) {
      ItemStack potion;
      if (getActiveTarget().isPotionActive(MobEffects.SLOWNESS)) {
        potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), PotionTypes.HARMING);
      }
      else {
        potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), PotionTypes.LONG_SLOWNESS);
      }
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, potion);
      attackTimer = 10;
      healTimer = 40;
    }
    else if (noActiveTargetTime > 40 && !isHealing && getHeldItem(EnumHand.MAIN_HAND).isEmpty() == false) {
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
      attackedWithPotion = null;
    }
    //the EntityPotion class validates if this potion is throwable, and if not it logs error "ThrownPotion entity {} has no item?!
    if (isHealing && healTimer <= 0 && getHeldItem(EnumHand.MAIN_HAND).getItem() == Items.SPLASH_POTION) {
      throwHealthPotion();
      isHealing = false;
    }
  }
  protected EntityLivingBase getActiveTarget() {
    EntityLivingBase res = getAttackTarget();
    if (res == null) {
      res = rangedAttackAI.getAttackTarget();
    }
    return res;
  }
  protected boolean shouldStartHeal() {
    if (isPotionActive(MobEffects.REGENERATION)) { return false; }
    return getHealth() < getMaxHealth() * 0.75 && rand.nextFloat() > 0.5 && healTimer <= 0;
  }
  @Override
  public void attackEntityWithRangedAttack(EntityLivingBase entity, float rangeRatio) {
    //the EntityPotion class validates if this potion is throwable, and if not it logs error "ThrownPotion entity {} has no item?!
    Item heldItem = getHeldItem(EnumHand.MAIN_HAND).getItem();
    if (attackTimer <= 0 &&
        (heldItem == Items.SPLASH_POTION ||
            heldItem == Items.LINGERING_POTION)
        && !isHealing) {
      attackedWithPotion = entity;
      double x = entity.posX + entity.motionX - posX;
      double y = entity.posY + entity.getEyeHeight() - 1.100000023841858D - posY;
      double z = entity.posZ + entity.motionZ - posZ;
      float groundDistance = MathHelper.sqrt(x * x + z * z);
      ItemStack potion = getHeldItem(EnumHand.MAIN_HAND);
      attackTimer = getHeldItem(EnumHand.MAIN_HAND).getMaxItemUseDuration();
      EntityPotion entitypotion = new EntityPotion(world, this, potion);
      entitypotion.rotationPitch -= -20.0F;
      entitypotion.setThrowableHeading(x, y + groundDistance * 0.2F, z, 0.75F, 8.0F);
      if (world.isRemote == false) {
        world.spawnEntity(entitypotion);
      }
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }
  }
  protected void throwHealthPotion() {
    ItemStack potion = getHeldItem(EnumHand.MAIN_HAND);
    //if its not a splash or lingering potion it will be an error
    EntityPotion entitypotion = new EntityPotion(world, this, potion);
    Vec3d lookVec = getLookVec();
    entitypotion.setThrowableHeading(lookVec.x * 0.5, -1, lookVec.z * 0.5, 0.75F, 1.0F);
    world.spawnEntity(entitypotion);
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
    healTimer = 80;
  }
  public void catDied(EntityWitherCat cat) {
    cats.remove(cat);
  }
  private void spawnCats() {
    //    if(!Config.witherCatEnabled) {
    //      return;
    //    }
    int numCats = rand.nextInt(witherWitchMaxCats + 1);
    numCats = Math.max(numCats, witherWitchMinCats);
    for (int i = 0; i < numCats; i++) {
      Point3i startPoint = EntityUtil.getEntityPositionI(this);
      startPoint.x += 4 - rand.nextInt(9);
      startPoint.z += 4 - rand.nextInt(9);
      Point3i spawnLoc = new Point3i();
      if (SpawnUtil.findClearGround(world, startPoint, spawnLoc, 2, 10, true)) {
        spawnCat(spawnLoc);
      }
      else {
        return;
      }
    }
  }
  private void spawnCat(Point3i spawnLoc) {
    EntityWitherCat cat = new EntityWitherCat(world);
    cat.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(this)), null);
    cat.setOwner(this);
    cat.setPositionAndRotation(spawnLoc.x + 0.5, spawnLoc.y + 0.5, spawnLoc.z + 0.5, rotationYaw, 0);
    if (MinecraftForge.EVENT_BUS.post(new LivingSpawnEvent.CheckSpawn(cat, world, (float) cat.posX, (float) cat.posY, (float) cat.posZ))) { return; }
    if (!cat.getCanSpawnHere()) { return; }
    cats.add(cat);
    world.spawnEntity(cat);
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound root) {
    super.writeEntityToNBT(root);
    if (cats.isEmpty()) { return; }
    NBTTagList catsList = new NBTTagList();
    for (EntityWitherCat cat : cats) {
      if (!cat.isDead) {
        NBTTagCompound catRoot = new NBTTagCompound();
        cat.writeToNBT(catRoot);
        catsList.appendTag(catRoot);
      }
    }
    if (catsList.tagCount() > 0) {
      root.setTag("cats", catsList);
    }
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound root) {
    super.readEntityFromNBT(root);
    if (!root.hasKey("cats")) { return; }
    NBTTagList catsList = (NBTTagList) root.getTag("cats");
    loadedCats = new ArrayList<NBTTagCompound>(catsList.tagCount());
    for (int i = 0; i < catsList.tagCount(); i++) {
      NBTTagCompound catRoot = catsList.getCompoundTagAt(i);
      if (catRoot != null) {
        loadedCats.add(catRoot);
      }
    }
  }
  private void loadCats() {
    if (loadedCats == null) { return; }
    for (NBTTagCompound catRoot : loadedCats) {
      if (catRoot != null) {
        EntityWitherCat cat = new EntityWitherCat(world);
        cat.readFromNBT(catRoot);
        cat.setOwner(this);
        cats.add(cat);
        world.spawnEntity(cat);
      }
    }
  }
  protected void manageCats() {
    if (cats.isEmpty()) { return; }
    if (noActiveTargetTime > 40) {
      pacifyCats();
      return;
    }
    EntityLivingBase currentTarget = getActiveTarget();
    EntityLivingBase hitBy = this.getAttackTarget();
    if (hitBy == null) {
      //agro the cats if we have been hit or we have actually thrown a potion
      hitBy = attackedWithPotion;
    }
    angerCats(currentTarget, hitBy);
  }
  private void angerCats(EntityLivingBase targ, EntityLivingBase hitBy) {
    for (EntityWitherCat cat : cats) {
      if (cat.isAngry()) {
        if (cat.getAttackTarget() != targ) {
          cat.setAttackTarget(targ);
        }
      }
      else if (cat.getGrowthMode() != GrowthMode.GROW && hitBy != null) {
        cat.setGrowthMode(GrowthMode.GROW);
      }
    }
  }
  private void pacifyCats() {
    for (EntityWitherCat cat : cats) {
      if (cat.isAngry()) {
        cat.setGrowthMode(GrowthMode.SHRINK);
        if (cat.getAttackTarget() != null) {
          cat.setAttackTarget(null);
        }
      }
    }
  }
  @Override
  public void setSwingingArms(boolean swingingArms) {}
  @Override
  public ResourceLocation getLootTable() {
    //    String colour = getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "entity/witch");
  }
}
