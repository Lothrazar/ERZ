package teamroots.emberroot.entity.spritegreater;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import teamroots.emberroot.EmberRootZoo;
import teamroots.emberroot.util.Util;

public class EntitySpriteProjectile extends EntityFlying {// implements IRangedAttackMob {
  public static final String NAME = "rootsonespriteprojectile";
  public float range = 64;
  public float addDirectionX = 0;
  public float addDirectionY = 0;
  public float twirlTimer = 0;
  public int lifetime = 80;
  Random random = new Random();
  EntityLivingBase target = null;
  public float damage = 2.0f;
  public EntitySpriteProjectile(World worldIn) {
    super(worldIn);
    this.noClip = true;
    setSize(1.5f, 1.5f);
    this.isAirBorne = true;
    this.setInvisible(true);
  }
  public void initSpecial(EntityLivingBase target, float damage) {
    this.target = target;
    this.damage = damage;
  }
  @Override
  public boolean isEntityInvulnerable(DamageSource source) {
    return false;
  }
  @Override
  public void collideWithEntity(Entity entity) {
    if (Math.abs(entity.posX - posX) < 0.5 && Math.abs(entity.posY + entity.getEyeHeight() / 2.0 - posY) < 0.5 && Math.abs(entity.posZ - posZ) < 0.5) {
      if (target != null) {
        if (entity.getUniqueID().compareTo(target.getUniqueID()) == 0) {
          target.attackEntityFrom(DamageSource.GENERIC, damage);
          this.getEntityWorld().removeEntity(this);
          for (int i = 0; i < 20; i++) {
            EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY + height / 2.0f, posZ, Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), Math.pow(1.15f * (random.nextFloat() - 0.5f), 3.0), 107, 255, 28);
          }
        }
      }
    }
  }
  @Override
  public void updateAITasks() {
    super.updateAITasks();
  }
  @Override
  public void onUpdate() {
    super.onUpdate();
    lifetime--;
    if (lifetime == 0) {
      this.getEntityWorld().removeEntity(this);
    }
    if (target != null) {
      rotationYaw = (float) Math.toRadians(Util.yawDegreesBetweenPoints(posX, posY, posZ, target.posX, target.posY + target.getEyeHeight() / 2.0, target.posZ));
      rotationPitch = (float) Math.toRadians(Util.pitchDegreesBetweenPoints(posX, posY, posZ, target.posX, target.posY + target.getEyeHeight() / 2.0, target.posZ));
      Vec3d moveVec = Util.lookVector(this.rotationYaw, this.rotationPitch).scale(0.65f);
      this.motionX = 0.5f * motionX + 0.5f * moveVec.x;
      this.motionY = 0.5f * motionY + 0.5f * moveVec.y;
      this.motionZ = 0.5f * motionZ + 0.5f * moveVec.z;
      for (double i = 0; i < 1; i++) {
        double x = this.getEntityBoundingBox().minX * 0.5 + this.getEntityBoundingBox().maxX * 0.5;
        double y = this.getEntityBoundingBox().minY * 0.5 + this.getEntityBoundingBox().maxY * 0.5;
        double z = this.getEntityBoundingBox().minZ * 0.5 + this.getEntityBoundingBox().maxZ * 0.5;
        EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), x, y, z, -0.125 * moveVec.x, -0.125 * moveVec.y, -0.125 * moveVec.z, 107, 255, 28);
      }
    }
  }
  @Override
  public int getBrightnessForRender() {
    return 255;
  }
  @Override
  public boolean attackEntityFrom(DamageSource source, float amount) {
    this.setDead();
    for (int i = 0; i < 20; i++) {
      EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY + height / 2.0f, posZ, Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), 107, 255, 28);
    }
    //		if (source.getEntity() instanceof EntityArrow){
    //			if (((EntityArrow)source.getEntity()).shootingEntity instanceof EntityPlayer){
    //				EntityPlayer player = ((EntityPlayer)((EntityArrow)source.getEntity()).shootingEntity);
    //				if (!player.hasAchievement(RegistryManager.achieveArrowBlock)){
    //					PlayerManager.addAchievement(player, RegistryManager.achieveArrowBlock);
    //				}
    //			}
    //		}
    return false;
  }
  @Override
  public boolean attackEntityAsMob(Entity entity) {
    this.setDead();
    for (int i = 0; i < 20; i++) {
      EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY + height / 2.0f, posZ, Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), 107, 255, 28);
    }
    return false;
  }
  @Override
  public void damageEntity(DamageSource source, float amount) {
    this.setDead();
    for (int i = 0; i < 20; i++) {
      EmberRootZoo.proxy.spawnParticleMagicSparkleFX(getEntityWorld(), posX, posY + height / 2.0f, posZ, Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), Math.pow(0.95f * (random.nextFloat() - 0.5f), 3.0), 107, 255, 28);
    }
  }
  @Override
  public boolean isAIDisabled() {
    return false;
  }
  @Override
  protected boolean canDespawn() {
    return true;
  }
  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0);
    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.25D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(2.0D);
    this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0);
  }
  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
  }
  @Override
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    this.getEntityWorld().removeEntity(this);
  }
}