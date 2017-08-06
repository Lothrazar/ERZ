package teamroots.emberroot.entity.fairy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import com.google.common.base.Optional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.Const;
import teamroots.emberroot.EmberRootZoo;
import teamroots.emberroot.entity.ai.EntityAITemptFlying;
import teamroots.emberroot.proxy.ClientProxy;

public class EntityFairy extends EntityFlying {
  private static final float FOLLOW_OWNER_RANGE = 4.0f;
  public static final DataParameter<Integer> variant = EntityDataManager.<Integer> createKey(EntityFairy.class, DataSerializers.VARINT);
  public static final DataParameter<BlockPos> spawnPosition = EntityDataManager.<BlockPos> createKey(EntityFairy.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<BlockPos> targetPosition = EntityDataManager.<BlockPos> createKey(EntityFairy.class, DataSerializers.BLOCK_POS);
  public static final DataParameter<Boolean> tame = EntityDataManager.<Boolean> createKey(EntityFairy.class, DataSerializers.BOOLEAN);
  //public static final DataParameter<Boolean> sitting = EntityDataManager.<Boolean> createKey(EntityFairy.class, DataSerializers.BOOLEAN);
  protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>> createKey(EntityFairy.class, DataSerializers.OPTIONAL_UNIQUE_ID);
  public static enum VariantColors {
    GREEN, PURPLE, PINK, ORANGE, BLUE, YELLOW, RED;
    public String nameLower() {
      return this.name().toLowerCase();
    }
    //not related to actual enum, we just have RGB for each color
    public int red() {
      switch (this) {
        case GREEN://0
          return 177;
        case PURPLE://1
          return 219;
        case PINK://2
          return 255;
        case ORANGE://3
          return 255;
        case BLUE:
          return 163;
        case YELLOW:
          return 255;
        case RED:
          return 255;
      }
      return 0;
    }
    public int green() {
      switch (this) {
        case GREEN://0
          return 255;
        case PURPLE://1
          return 179;
        case PINK://2
          return 163;
        case ORANGE://3
          return 223;
        case BLUE:
          return 221;
        case YELLOW:
          return 242;
        case RED:
          return 98;
      }
      return 0;
    }
    public int blue() {
      switch (this) {
        case GREEN://0
          return 117;
        case PURPLE://1
          return 255;
        case PINK://2
          return 255;
        case ORANGE://3
          return 163;
        case BLUE:
          return 255;
        case YELLOW:
          return 179;
        case RED:
          return 114;
      }
      return 0;
    }
  }
  //public UUID owner = null;
  public EntityFairy(World world) {
    super(world);
    setSize(0.45f, 0.6f);
    this.experienceValue = 10;
  }
  @Override
  protected void initEntityAI() {
    this.tasks.addTask(0, new EntityAITemptFlying(this, 66.95D, Items.GLOWSTONE_DUST, false));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
  }
  public void setOwnerId(@Nullable UUID u) {
    this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(u));
  }
  @Nullable
  public UUID getOwnerId() {
    return (UUID) ((Optional) this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
  }
  public void setTamed(boolean tamed) {
    getDataManager().set(tame, tamed);
  }
  public boolean isTamed() {
    return getDataManager().get(tame);
  }
  @Override
  public boolean processInteract(EntityPlayer player, EnumHand hand) {
    if (player.getHeldItem(hand).isItemEqualIgnoreDurability(new ItemStack(Items.GLOWSTONE_DUST))) {
      if (isTamed()) {
        getDataManager().set(tame, false);
        this.setOwnerId(null);
        this.playUnTameEffect(7);
      }
      else {
        //if i am not tame, AND you give me glowstone
        getDataManager().set(tame, true);
        this.setOwnerId(player.getUniqueID());
        getDataManager().setDirty(tame);
        this.playTameEffect(7);
        player.getHeldItem(hand).shrink(1);
      }
      return true;
    }
    //    if (getDataManager().get(tame) && player.getHeldItem(hand).isEmpty() && player.world.isRemote == false) {
    //      //i am tame, and im being told to sit
    //      System.out.println("sit toggle ME " + !getDataManager().get(sitting));
    //      getDataManager().set(sitting, !getDataManager().get(sitting));
    //      getDataManager().setDirty(sitting);
    //    }
    return false;
  }
  @SideOnly(Side.CLIENT)
  protected void playTameEffect(int count) {
    EnumParticleTypes enumparticletypes = EnumParticleTypes.HEART;
    for (int i = 0; i < count; ++i) {
      double d0 = this.rand.nextGaussian() * 0.02D;
      double d1 = this.rand.nextGaussian() * 0.02D;
      double d2 = this.rand.nextGaussian() * 0.02D;
      this.world.spawnParticle(enumparticletypes, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
    }
  }
  @SideOnly(Side.CLIENT)
  protected void playUnTameEffect(int count) {
    EnumParticleTypes enumparticletypes = EnumParticleTypes.SMOKE_LARGE;
    for (int i = 0; i < count; ++i) {
      double d0 = this.rand.nextGaussian() * 0.02D;
      double d1 = this.rand.nextGaussian() * 0.02D;
      double d2 = this.rand.nextGaussian() * 0.02D;
      this.world.spawnParticle(enumparticletypes, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
    }
  }
  public Integer getVariant() {
    return getDataManager().get(variant);
  }
  public VariantColors getVariantEnum() {
    return VariantColors.values()[getVariant()];
  }
  public float getRed() {
    return getVariantEnum().red();
  }
  public float getGreen() {
    return getVariantEnum().green();
  }
  public float getBlue() {
    return getVariantEnum().blue();
  }
  @Override
  public boolean canBePushed() {
    return false;
  }
  //  protected void collideWithEntity(Entity entityIn) {}
  //  protected void collideWithNearbyEntities() {}
  @Override
  public void onUpdate() {
    super.onUpdate();
    if (world.isRemote) {
      for (int i = 0; i < 2; i++) {
        float x = (float) posX + 0.25f * (rand.nextFloat() - 0.5f);
        float y = (float) posY + 0.375f + 0.25f * (rand.nextFloat() - 0.5f);
        float z = (float) posZ + 0.25f * (rand.nextFloat() - 0.5f);
        spawnParticleGlow(world, x, y, z, 0.0375f * (rand.nextFloat() - 0.5f), 0.0375f * (rand.nextFloat() - 0.5f), 0.0375f * (rand.nextFloat() - 0.5f), getRed(), getGreen(), getBlue(), 0.125f, 6.0f + 6.0f * rand.nextFloat(), 20);
      }
    }
  }
  public static Random random = new Random();
  public static int counter = 0;
  @SideOnly(Side.CLIENT)
  public static void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime) {
    if (EmberRootZoo.proxy instanceof ClientProxy) {
      counter += random.nextInt(3);
      if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
        ClientProxy.particleRenderer.addParticle(new ParticleFairyGlow(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime));
      }
    }
  }
  @Override
  protected void updateAITasks() {
    super.updateAITasks();
    UUID owner = this.getOwnerId();
    if (getDataManager().get(tame) && owner != null && world.getPlayerEntityByUUID(owner) != null) {
      //this.noClip = true;
      EntityPlayer p = world.getPlayerEntityByUUID(owner);
      //      if (getDataManager().get(sitting)) {
      //        System.out.println("IM  sitting so dont move aw jeez");
      //        motionX =0;// *= 0.9;
      //        motionY   *= 0.9;
      //        motionZ  =0;//*= 0.9;
      //        if (p != null) {
      //          this.faceEntity(p, 30f, 30f);
      //        }
      //      } else
      if (!p.isSneaking()) {
        //   System.out.println("IM TMED so do some stuff");
        double targX = p.posX;
        double targY = p.posY + p.height;
        double targZ = p.posZ;
        int count = 1;
        if (this.getDistanceSqToEntity(p) < FOLLOW_OWNER_RANGE) {
          this.playTameEffect(2);
          List<EntityFairy> list = world.getEntitiesWithinAABB(EntityFairy.class, p.getEntityBoundingBox().expand(FOLLOW_OWNER_RANGE, FOLLOW_OWNER_RANGE, FOLLOW_OWNER_RANGE));
          List<EntityFairy> prunedList = new ArrayList<EntityFairy>();
          for (EntityFairy f : list) {
            if (f.getDataManager().get(tame) && f.getOwnerId() != null && f.getOwnerId().compareTo(p.getUniqueID()) == 0) {
              prunedList.add(f);
            }
          }
          //          System.out.println("How many do you own"+prunedList.size());
          //get ALL fairies owned by me and make a nice follow train
          for (int i = 0; i < prunedList.size(); i++) {
            if (prunedList.get(i).getUniqueID().compareTo(getUniqueID()) == 0) {
              float coeff = (float) i / (float) (prunedList.size());
              if (prunedList.size() > 1) {
                coeff = (float) i / (float) (prunedList.size() - 1f);
              }
              targX = (double) p.posX + (p.width * 1.5) * Math.sin(Math.toRadians((-p.rotationYaw - 90.0) - 180.0 * coeff));
              targY = (double) p.posY + p.height;
              targZ = (double) p.posZ + (p.width * 1.5) * Math.cos(Math.toRadians((-p.rotationYaw - 90.0) - 180.0 * coeff));
            }
            else {
              if (prunedList.get(i).getDataManager().get(variant) == getDataManager().get(variant)) {
                count++;
              }
            }
          }
        }
        //        		switch(getDataManager().get(variant)){
        //	        		case 0:{
        //	        			if (EffectManager.getDuration(p, EffectManager.effect_naturescure.name) < 2){
        //	        				EffectManager.assignEffect(p, EffectManager.effect_naturescure.name, 22, EffectNaturesCure.createData(count));
        //	        			}
        //	        			break;
        //	        		}
        //	        		case 1:{
        //	        			if (EffectManager.getDuration(p, EffectManager.effect_arcanism.name) < 2){
        //	        				EffectManager.assignEffect(p, EffectManager.effect_arcanism.name, 22, EffectArcanism.createData(count));
        //	        			}
        //	        			break;
        //	        		}
        //	        		case 2:{
        //	        			if (EffectManager.getDuration(p, EffectManager.effect_regen.name) < 2){
        //	        				EffectManager.assignEffect(p, EffectManager.effect_regen.name, 22, EffectRegen.createData(count));
        //	        			}
        //	        			break;
        //	        		}
        //	        		case 3:{
        //	        			if (EffectManager.getDuration(p, EffectManager.effect_fireresist.name) < 2){
        //	        				EffectManager.assignEffect(p, EffectManager.effect_fireresist.name, 22, EffectFireResist.createData(count));
        //	        			}
        //	        			break;
        //	        		}
        //        		}
        double dX = targX - this.posX;
        double dY = targY - this.posY;
        double dZ = targZ - this.posZ;
        double c = p.isSneaking() ? 0.3 : 1.0;
        this.motionX += (Math.signum(dX) * 1.4f * c - this.motionX) * 0.025D;
        this.motionY += (Math.signum(dY) * 2.2f * c - this.motionY) * 0.025D;
        this.motionZ += (Math.signum(dZ) * 1.4f * c - this.motionZ) * 0.025D;
        //System.out.println(motionX+", "+motionY+", "+motionZ);
        float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;
      }
      //else player is sneaking, let them escape
      //      else if (p == null) {
      //        motionX *= 0.9;
      //        motionY *= 0.9;
      //        motionZ *= 0.9;
      //      }
    }
    else {//else owner and/or player is empty
      this.noClip = false;
      if (this.getDataManager().get(spawnPosition).getY() < 0) {
        this.getDataManager().set(spawnPosition, getPosition());
        getDataManager().setDirty(spawnPosition);
        this.getDataManager().set(targetPosition, getPosition());
        getDataManager().setDirty(targetPosition);
      }
      if (getDataManager().get(targetPosition).compareTo(getDataManager().get(spawnPosition)) == 0 || this.rand.nextInt(30) == 0 || getDataManager().get(targetPosition).distanceSq((double) ((int) this.posX), (double) ((int) this.posY), (double) ((int) this.posZ)) < 3.0D) {
        this.getDataManager().set(targetPosition, new BlockPos(getDataManager().get(spawnPosition).getX() + this.rand.nextInt(15) - this.rand.nextInt(15), getDataManager().get(spawnPosition).getY() + this.rand.nextInt(11) - 2, getDataManager().get(spawnPosition).getZ() + this.rand.nextInt(15) - this.rand.nextInt(15)));
        getDataManager().setDirty(targetPosition);
      }
      //     BlockPos blockpos = new BlockPos(this);
      // BlockPos blockpos1 = blockpos.up();
      double dX = (double) this.getDataManager().get(targetPosition).getX() + 0.5D - this.posX;
      double dY = (double) this.getDataManager().get(targetPosition).getY() + 0.1D - this.posY;
      double dZ = (double) this.getDataManager().get(targetPosition).getZ() + 0.5D - this.posZ;
      this.motionX += (Math.signum(dX) * 0.5D - this.motionX) * 0.025D;
      this.motionY += (Math.signum(dY) * 0.7D - this.motionY) * 0.025D;
      this.motionZ += (Math.signum(dZ) * 0.5D - this.motionZ) * 0.025D;
      float f = (float) (MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
      float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
      this.moveForward = 0.5F;
      this.rotationYaw += f1;
    }
  }
  @Override
  public boolean doesEntityNotTriggerPressurePlate() {
    return true;
  }
  @Override
  protected boolean canTriggerWalking() {
    return false;
  }
  @Override
  public void fall(float distance, float damageMultiplier) {}
  @Override
  protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  @Override
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
    this.getDataManager().register(tame, false);
    //    this.getDataManager().register(sitting, false);
    this.getDataManager().register(variant, rand.nextInt(VariantColors.values().length));
    this.getDataManager().register(spawnPosition, new BlockPos(0, -1, 0));
    this.getDataManager().register(targetPosition, new BlockPos(0, -1, 0));
  }
  @Override
  public boolean isAIDisabled() {
    return false;
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
  }
  @Override
  public ResourceLocation getLootTable() {
    String colour = getVariantEnum().nameLower();
    return new ResourceLocation(Const.MODID, "entity/fairy_" + colour);
  }
  @Override
  public float getEyeHeight() {
    return this.height;
  }
  @Override
  public int getBrightnessForRender() {
    return 255;
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    String s = "";
    if (compound.hasKey("OwnerUUID", 8)) {
      s = compound.getString("OwnerUUID");
    }
    if (!s.isEmpty()) {
      try {
        this.setOwnerId(UUID.fromString(s));
        //  this.setTamed(true);
      }
      catch (Throwable var4) {
        //  this.setTamed(false);
      }
    }
    getDataManager().set(tame, compound.getBoolean("tame"));
    getDataManager().setDirty(tame);
    //    getDataManager().set(sitting, compound.getBoolean("sitting"));
    //    getDataManager().setDirty(sitting);
    getDataManager().set(variant, compound.getInteger("variant"));
    getDataManager().setDirty(variant);
    getDataManager().set(spawnPosition, new BlockPos(compound.getInteger("spawnX"), compound.getInteger("spawnY"), compound.getInteger("spawnZ")));
    getDataManager().setDirty(spawnPosition);
    getDataManager().set(targetPosition, new BlockPos(compound.getInteger("targetX"), compound.getInteger("targetY"), compound.getInteger("targetZ")));
    getDataManager().setDirty(targetPosition);
  }
  @Override
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    if (this.getOwnerId() == null) {
      compound.setString("OwnerUUID", "");
    }
    else {
      compound.setString("OwnerUUID", this.getOwnerId().toString());
    }
    compound.setBoolean("tame", getDataManager().get(tame));
    //    compound.setBoolean("sitting", getDataManager().get(sitting));
    compound.setInteger("variant", getDataManager().get(variant));
    compound.setInteger("spawnX", getDataManager().get(spawnPosition).getX());
    compound.setInteger("spawnY", getDataManager().get(spawnPosition).getY());
    compound.setInteger("spawnZ", getDataManager().get(spawnPosition).getZ());
    compound.setInteger("targetX", getDataManager().get(targetPosition).getX());
    compound.setInteger("targetY", getDataManager().get(targetPosition).getY());
    compound.setInteger("targetZ", getDataManager().get(targetPosition).getZ());
  }
}