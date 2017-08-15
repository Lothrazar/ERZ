package teamroots.emberroot.entity.slimedirt;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import teamroots.emberroot.Const;
import teamroots.emberroot.config.ConfigSpawnEntity;
import teamroots.emberroot.util.SpawnUtil;

/**
 * Original author: https://github.com/CrazyPants
 */
public class EntityDireSlime extends EntityMagmaCube {
  public static final String NAME = "slime";
  public static enum VariantColors {
    DIRT, SAND, STONE, GRAVEL, NETHER, SNOW, END;
    public String nameLower() {
      return this.name().toLowerCase();
    }
  }
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntityDireSlime.class, DataSerializers.VARINT);
  public enum SlimeConf {
    SMALL(1, 4, 3, 0.4), MEDIUM(2, 8, 5, 0.2), LARGE(4, 20, 8, 0.4);
    public final int size;
    public final double health;
    public final double attackDamage;
    public final double chance;
    private SlimeConf(int size, double health, double attackDamage, double chance) {
      this.size = size;
      this.health = health;
      this.attackDamage = attackDamage;
      this.chance = chance;
    }
    static SlimeConf getConfForSize(int size) {
      for (SlimeConf conf : values()) {
        if (conf.size == size) { return conf; }
      }
      return SMALL;
    }
    SlimeConf bigger() {
      int index = ordinal() + 1;
      if (index >= values().length) { return null; }
      return values()[index];
    }
  }
  public static ConfigSpawnEntity config = new ConfigSpawnEntity(EntityDireSlime.class, EnumCreatureType.MONSTER);
  public EntityDireSlime(World world) {
    super(world);
    setSlimeSize(1, false);
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
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
  }
  private boolean blockHereMatches(Block target, BlockPos pos) { //
    Block blockBelow = this.world.getBlockState(pos).getBlock();
    return OreDictionary.itemMatches(new ItemStack(target), new ItemStack(blockBelow), false);
  }
  @Override
  public void onUpdate() {
    super.onUpdate();
    Biome biome = this.world.getBiome(this.getPosition());
    BlockPos posHere = this.getPosition();
    BlockPos posDown = posHere.down();
    int newVariant = -1;
    if (biome.equals(Biomes.HELL) || blockHereMatches(Blocks.NETHERRACK, posDown)) {
      newVariant = VariantColors.NETHER.ordinal();
      newVariant = VariantColors.NETHER.ordinal();
    }
    else if (biome.equals(Biomes.SKY) || blockHereMatches(Blocks.END_STONE, posDown)) {
      newVariant = VariantColors.END.ordinal();
    }
    else if (blockHereMatches(Blocks.SNOW, posHere) || blockHereMatches(Blocks.SNOW_LAYER, posHere)) {
      newVariant = VariantColors.SNOW.ordinal();
    }
    else if (blockHereMatches(Blocks.DIRT, posDown) || blockHereMatches(Blocks.GRASS, posDown)) {
      newVariant = VariantColors.DIRT.ordinal();
    }
    else if (blockHereMatches(Blocks.SAND, posDown) || blockHereMatches(Blocks.SANDSTONE, posDown)) {
      newVariant = VariantColors.SAND.ordinal();
    }
    else if (blockHereMatches(Blocks.STONE, posDown) || blockHereMatches(Blocks.COBBLESTONE, posDown)) {
      newVariant = VariantColors.STONE.ordinal();
    }
    else if (blockHereMatches(Blocks.GRAVEL, posDown)) {
      newVariant = VariantColors.GRAVEL.ordinal();
    }
    else if (biome.isSnowyBiome() || blockHereMatches(Blocks.SNOW, posDown)) {
      newVariant = VariantColors.SNOW.ordinal();
    }
    if (newVariant >= 0 && newVariant != this.getVariant()) { //dont change from SAME -> SAME
      dataManager.set(variant, newVariant);
      dataManager.setDirty(variant);
    }
  }
  @Override
  public void setSlimeSize(int size, boolean doFullHeal) {
    super.setSlimeSize(size, doFullHeal);
    SlimeConf conf = SlimeConf.getConfForSize(size);
    getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(conf.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(conf.health);
    setHealth(getMaxHealth());
  }
  @Override
  public void onDeath(DamageSource damageSource) {
    super.onDeath(damageSource);
    if (!world.isRemote && damageSource != null && damageSource.getTrueSource() instanceof EntityPlayer) {
      SlimeConf nextConf = SlimeConf.getConfForSize(getSlimeSize()).bigger();
      if (nextConf != null && world.rand.nextFloat() <= nextConf.chance) {
        EntityDireSlime spawn = new EntityDireSlime(world);
        spawn.setSlimeSize(nextConf.size, true);
        spawn.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0);
        spawn.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(this)), null);
        if (SpawnUtil.isSpaceAvailableForSpawn(world, spawn, false)) {
          world.spawnEntity(spawn);
        }
      }
    }
  }
  @Override
  public void setDead() {
    //Override to prevent smaller slimes spawning
    isDead = true;
  }
  @Override
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.BLOCK_CRACK;
  }
  @Override
  protected boolean spawnCustomParticles() {
    int i = this.getSlimeSize();
    for (int j = 0; j < i * 8; ++j) {
      float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
      float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
      float f2 = MathHelper.sin(f) * (float) i * 0.5F * f1;
      float f3 = MathHelper.cos(f) * (float) i * 0.5F * f1;
      World world = this.world;
      EnumParticleTypes enumparticletypes = this.getParticleType();
      double d0 = this.posX + (double) f2;
      double d1 = this.posZ + (double) f3;
      world.spawnParticle(enumparticletypes, d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIRT.getDefaultState()));
    }
    return true;
  }
  @Override
  protected EntitySlime createInstance() {
    return new EntityDireSlime(this.world);
  }
  @Override
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    int i = MathHelper.floor(this.posX);
    int j = MathHelper.floor(this.posZ);
    if (!world.isAirBlock(new BlockPos(i, 0, j))) {
      double d0 = (getEntityBoundingBox().maxY - getEntityBoundingBox().minY) * 0.66D;
      int k = MathHelper.floor(this.posY - getYOffset() + d0);
      return world.getCombinedLight(new BlockPos(i, k, j), 0);
    }
    else {
      return 0;
    }
  }
  @Override
  public float getBrightness() {
    int i = MathHelper.floor(this.posX);
    int j = MathHelper.floor(this.posZ);
    if (!world.isAirBlock(new BlockPos(i, 0, j))) {
      double d0 = (getEntityBoundingBox().maxY - getEntityBoundingBox().minY) * 0.66D;
      int k = MathHelper.floor(this.posY - getYOffset() + d0);
      return world.getLightBrightness(new BlockPos(i, k, j));
    }
    else {
      return 0.0F;
    }
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    ConfigSpawnEntity.syncInstance(this, config.settings);
  }
  @Override
  protected int getAttackStrength() {
    int res = (int) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    return res;
  }
  @Override
  protected void setSize(float p_70105_1_, float p_70105_2_) {
    int i = this.getSlimeSize();
    super.setSize(i, i);
  }
  @Override
  public void onCollideWithPlayer(EntityPlayer player) {
    int i = getSlimeSize();
    if (canEntityBeSeen(player) && this.getDistanceSqToEntity(player) < (double) i * (double) i
        && player.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength())) {
      playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
    }
  }
  @Override
  protected float applyArmorCalculations(DamageSource ds, float dmg) {
    if (!ds.isUnblockable()) { return Math.min(Math.max(dmg - 3 - this.getSlimeSize(), this.getSlimeSize()) / 2, dmg); }
    return dmg;
  }
  @Override
  protected ResourceLocation getLootTable() {
    return new ResourceLocation(Const.MODID, "entity/slime_block");
  }
}
