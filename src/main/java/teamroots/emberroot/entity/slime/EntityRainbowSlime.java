package teamroots.emberroot.entity.slime;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;

public class EntityRainbowSlime extends EntitySlime {
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntitySlime.class, DataSerializers.VARINT);
  public static final String NAME = "rainbowslime";
  public static enum VariantColors {
    BLUE, GREY, WHITE, PURPLE, RED;//water, snow, clay
    public String nameLower() {
      return this.name().toLowerCase();
    }
  }
  public static boolean canPlaceBlocks;
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityRainbowSlime.class, EnumCreatureType.MONSTER);
  public EntityRainbowSlime(World worldIn) {
    super(worldIn);
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
  }
  public Integer getVariant() {
    return getDataManager().get(variant);
  }
  public VariantColors getVariantEnum() {
    return VariantColors.values()[getVariant()];
  }
  @Override
  protected void initEntityAI() {
    super.initEntityAI();
  }
  private boolean isBaby() {
    return this.getSlimeSize() == 1;
  }
  @Override
  public void setDead() {
    if (this.world.isRemote == false
        && canPlaceBlocks
        && this.world.getGameRules().getBoolean("mobGriefing")
        && this.isBaby()
        && this.getHealth() <= 0) {
      //skip setting block if mob griefing is false
      setBlockOnDeath();
    }
    setPotionsOnDeath();
    spawnChildSlimes();
    this.isDead = true;
  }
  private void setBlockOnDeath() {
    BlockPos pos = this.getPosition();
    IBlockState setBlock = null;
    IBlockState current = this.world.getBlockState(pos);
    switch (this.getVariantEnum()) {
      case BLUE:
        if (current.getBlock().isReplaceable(world, pos))
          setBlock = Blocks.WATER.getDefaultState();
        else if (current.getBlock() == Blocks.WATER)
          setBlock = Blocks.ICE.getDefaultState();
      break;
      case GREY:
        if (current.getBlock().isReplaceable(world, pos))
          setBlock = Blocks.CLAY.getDefaultState();
      break;
      case WHITE:
        if (current.getBlock().isReplaceable(world, pos))
          setBlock = Blocks.SNOW_LAYER.getDefaultState();
        if (current.getBlock() == Blocks.SNOW_LAYER)
          setBlock = Blocks.SNOW.getDefaultState();
      break;
      case PURPLE:
      break;
      case RED:
      break;
      default:
      break;
    }
    if (setBlock != null) {
      this.world.setBlockState(this.getPosition(), setBlock);
      //doesnt make it flow. idk
      // this.world.notifyBlockUpdate(this.getPosition(), setBlock, setBlock, 3);//make tha water flowwwwwww
    }
  }
  public void setPotionsOnDeath() {
    switch (this.getVariantEnum()) {
      case BLUE:
      break;
      case GREY:
      break;
      case PURPLE:
        this.makeAreaOfEffectCloud(PotionType.getPotionTypeForName("poison"));
      break;
      case WHITE:
      break;
      case RED:
        this.makeAreaOfEffectCloud(PotionType.getPotionTypeForName("regeneration"));
      break;
    }
  }
  private void makeAreaOfEffectCloud(PotionType type) {
    EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
    entityareaeffectcloud.setOwner(this);
    entityareaeffectcloud.setRadius(3.0F);
    entityareaeffectcloud.setRadiusOnUse(-0.5F);
    entityareaeffectcloud.setWaitTime(10);
    entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());
    entityareaeffectcloud.setPotion(type);
    for (PotionEffect e : type.getEffects())
      entityareaeffectcloud.addEffect(e);// new PotionEffect(MobEffects.POISON)
    this.world.spawnEntity(entityareaeffectcloud);
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
  /**
   * Just like vanilla except we set the variant to match the parent
   */
  private void spawnChildSlimes() {
    int size = this.getSlimeSize();
    if (!this.world.isRemote && size > 1 && this.getHealth() <= 0.0F) {
      int j = 2 + this.rand.nextInt(3);
      for (int k = 0; k < j; ++k) {
        float f = ((float) (k % 2) - 0.5F) * (float) size / 4.0F;
        float f1 = ((float) (k / 2) - 0.5F) * (float) size / 4.0F;
        EntityRainbowSlime entityslime = this.createInstance();
        entityslime.getDataManager().set(variant, this.getVariant());
        if (this.hasCustomName()) {
          entityslime.setCustomNameTag(this.getCustomNameTag());
        }
        if (this.isNoDespawnRequired()) {
          entityslime.enablePersistence();
        }
        entityslime.setSlimeSize(size / 2, true);
        entityslime.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
        this.world.spawnEntity(entityslime);
      }
    }
  }
  /**
   * make sure that on death we get one of our own
   */
  @Override
  protected EntityRainbowSlime createInstance() {
    return new EntityRainbowSlime(this.world);
  }
  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.WATER_SPLASH;
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    getDataManager().set(variant, compound.getInteger("variant"));
    getDataManager().setDirty(variant);
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("variant", getDataManager().get(variant));
  }
  @Override
  public ResourceLocation getLootTable() {
    String colour = getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "entity/slime_" + colour);
  }
}
