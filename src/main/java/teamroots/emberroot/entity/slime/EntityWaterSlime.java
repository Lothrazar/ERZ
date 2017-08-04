package teamroots.emberroot.entity.slime;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import teamroots.emberroot.Const;

public class EntityWaterSlime extends EntitySlime {
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntitySlime.class, DataSerializers.VARINT);
  public static enum VariantColors {
    BLUE, GREY;//water, snow, clay
    public String nameLower() {
      return this.name().toLowerCase();
    }
  }
  public EntityWaterSlime(World worldIn) {
    super(worldIn);
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
  //  @Override
  //  public ResourceLocation getLootTable() {
  //    String colour = getVariantEnum().nameLower();
  //    return new ResourceLocation(Const.MODID, "entity/slime_" + colour);
  //  }
  private boolean isBaby() {
    return this.getSlimeSize() == 1;
  }
  @Override
  public void setDead() {
    if (!this.world.isRemote 
        && this.isBaby() && this.getHealth() <= 0
        && this.world.isAirBlock(this.getPosition())) {
      IBlockState setBlock = null;
      switch (this.getVariantEnum()) {
        case BLUE:
          setBlock = Blocks.WATER.getDefaultState();
        break;
        case GREY:
          setBlock = Blocks.CLAY.getDefaultState();
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
    super.setDead();
  }
  /**
   * make sure that on death we get one of our own
   */
  @Override
  protected EntitySlime createInstance() {
    return new EntityWaterSlime(this.world);
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
}
