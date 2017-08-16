package teamroots.emberroot.entity.creeper;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import teamroots.emberroot.Const;
import teamroots.emberroot.EmberRootZoo;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.entity.endermini.EntityEnderminy;
import teamroots.emberroot.entity.endermini.EntityEnderminy.VariantColors;
import teamroots.emberroot.util.TeleportUtil;

/**
 * Original author: https://github.com/CrazyPants
 */
public class EntityConcussionCreeper extends EntityCreeper {
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntityConcussionCreeper.class, DataSerializers.VARINT);
  public static enum VariantColors {
    TP, POISON, REGEN, BLUE;
    public String nameLower() {
      return this.name().toLowerCase();
    }
  }
  public static final String NAME = "creeper";
  private static final int concussionCreeperExplosionRange = 16;//TODO: CONFIGS FOR THESE
  private static final int concussionCreeperMaxTeleportRange = 16;
  private static final int concussionCreeperConfusionDuration = 600;
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityConcussionCreeper.class, EnumCreatureType.MONSTER);
  private Field fTimeSinceIgnited;
  private Field fFuseTime;
  public EntityConcussionCreeper(World world) {
    super(world);
    
    try {
      fTimeSinceIgnited = ReflectionHelper.findField(EntityCreeper.class, "timeSinceIgnited", "field_70833_d");
      fFuseTime = ReflectionHelper.findField(EntityCreeper.class, "fuseTime", "field_82225_f");
    }
    catch (Exception e) {
      EmberRootZoo.instance.logger.error("Could not create ender creeper  logic as fields not found");
    }
  }
  public Integer getVariant() {
    return dataManager.get(variant);
  }
  public VariantColors getVariantEnum() {
    return VariantColors.values()[getVariant()];
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue( 0.25F);
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected void entityInit() {
    super.entityInit();
    dataManager.register(variant, rand.nextInt(VariantColors.values().length));
  }
  private void spawnLingeringPotion(PotionType type) {
    ItemStack potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), type);
    EntityPotion entitypotion = new EntityPotion(world, this, potion);
    //    Vec3d lookVec = getLookVec();
    //    entitypotion.rotationPitch -= -20.0F;
    entitypotion.setThrowableHeading(0, 1, 0, 0.75F, 1.0F);
    if (world.isRemote == false) {
      world.spawnEntity(entitypotion);
    }
  }
  @Override
  public void onUpdate() {
    if (isEntityAlive()) {
      int timeSinceIgnited = getTimeSinceIgnited();
      int fuseTime = getFuseTime();
      if (timeSinceIgnited >= fuseTime - 1) {
        setTimeSinceIgnited(0);
        int range = concussionCreeperExplosionRange;
        switch (this.getVariantEnum()) {
          case POISON:
            spawnLingeringPotion(PotionTypes.POISON);
          break;
          case REGEN:
            spawnLingeringPotion(PotionTypes.LONG_REGENERATION);
          break;
          case BLUE:
            spawnLingeringPotion(PotionTypes.LONG_WEAKNESS);
          break;
          case TP:
            AxisAlignedBB bb = new AxisAlignedBB(posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range);
            List<EntityLivingBase> ents = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
            for (EntityLivingBase ent : ents) {
              if (ent != this) {
                if (!world.isRemote) {
                  boolean done = false;
                  for (int i = 0; i < 20 && !done; i++) {
                    done = TeleportUtil.teleportRandomly(ent, concussionCreeperMaxTeleportRange);
                  }
                }
                if (ent instanceof EntityPlayer) {
                  world.playSound(ent.posX, ent.posY, ent.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
                  EmberRootZoo.proxy.setInstantConfusionOnPlayer((EntityPlayer) ent, concussionCreeperConfusionDuration);
                }
              }
            }
          break;
          default:
          break;
        }
        world.playSound(posX, posY, posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 4.0F,
            (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F, false);
        world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX, posY, posZ, 1.0D, 0.0D, 0.0D);
        setDead();
      }
    }
    super.onUpdate();
  }
  private void setTimeSinceIgnited(int i) {
    if (fTimeSinceIgnited == null) { return; }
    try {
      fTimeSinceIgnited.setInt(this, i);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private int getTimeSinceIgnited() {
    if (fTimeSinceIgnited == null) { return 0; }
    try {
      return fTimeSinceIgnited.getInt(this);
    }
    catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
  private int getFuseTime() {
    if (fFuseTime == null) { return 0; }
    try {
      return fFuseTime.getInt(this);
    }
    catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
  @Override
  public ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/creeper_concussion");
  }
}
