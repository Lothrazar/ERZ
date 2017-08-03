package teamroots.emberroot.entity.sprout;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import teamroots.emberroot.Const;

public class EntitySprout extends EntityCreature {
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntitySprout.class, DataSerializers.VARINT);
  public static enum VariantColors {
    GREEN, TAN, RED, PURPLE, BLUE;
    public String nameLower() {
      return this.name().toLowerCase();
    } 
  }
  public EntitySprout(World world) {
    super(world);
    setSize(0.5f, 1.0f);
    this.experienceValue = 3;
  }
  @Override
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
  }
  protected void initEntityAI() {
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
  }
  @Override
  public boolean isAIDisabled() {
    return false;
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
  }
  public Integer getVariant() {
    return getDataManager().get(variant);
  }
  public VariantColors getVariantEnum() {
    return VariantColors.values()[getVariant()];
  }
  @Override
  public ResourceLocation getLootTable() {
    String colour = getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "entity/sprout_" + colour);
  }
  @Override
  public void onUpdate() {
    super.onUpdate();
    this.rotationYaw = this.rotationYawHead;
  }
  public float getEyeHeight() {
    return this.isChild() ? this.height : 1.3F;
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