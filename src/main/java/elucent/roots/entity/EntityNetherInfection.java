package elucent.roots.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import elucent.roots.Roots;
import elucent.roots.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityNetherInfection extends Entity {
	Entity entity;
	Random random = new Random();
	UUID casterId = null;
	int damage = 15;
	float spinDirection = 1.0f;
	float angle = 0.0f;
	
	public EntityNetherInfection(World world, UUID casterId, Entity entity, int potency, int size){
		super(world);
		this.casterId = casterId;
		this.entity = entity;
		this.damage = 11+2*potency;
		this.posX = entity.posX;
		this.posY = entity.posY;
		this.posZ = entity.posZ;
		if (random.nextInt(2) == 0){
			this.spinDirection *= -1;
		}
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if (casterId != null){
			if (entity != null){
				this.posX = (entity.posX);
				this.posY = (entity.posY)+entity.getEyeHeight()/2.0f;
				this.posZ = (entity.posZ);
			}
			if (entity == null || entity.isDead){
				List<EntityLivingBase> entities = (List<EntityLivingBase>) getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX-5.0,posY-5.0,posZ-5.0,posX+5.0,posY+5.0,posZ+5.0));
				if (entities.size() == 0){
					this.kill();
					this.getEntityWorld().removeEntity(this);
				}
				else {
					ArrayList<Entity> validTargets = new ArrayList<Entity>();
					for (int i = 0; i < entities.size(); i ++){
						if (entities.get(i).getUniqueID().compareTo(casterId) != 0){
							validTargets.add(entities.get(i));
						}
					}
					if (validTargets.size() > 0){
						this.entity = validTargets.get(random.nextInt(validTargets.size()));
						for (int i = 0; i < 40; i ++){
							float coeff = ((float)i)/40.0f;
							this.posX = this.posX*(1.0-coeff)+entity.posX*(coeff);
							this.posY = this.posY*(1.0-coeff)+(entity.posY+entity.getEyeHeight()/2.0f)*(coeff);
							this.posZ = this.posZ*(1.0-coeff)+entity.posZ*(coeff);
							if (random.nextInt(2) == 0){
								Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, 0, 0, 0, 166, 37, 48);
							}
							else {
								Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, 0, 0, 0, 92, 21, 26);
							}
						}
					}
					else {
						this.kill();
						this.getEntityWorld().removeEntity(this);
					}
				}
			}
			else {
				if (entity instanceof EntityLivingBase && ticksExisted % 20 == 0){
					if (random.nextInt(5) == 0){
						this.spinDirection *= -1.0f;
					}
					entity.attackEntityFrom(DamageSource.generic, 1.0f);
					damage -= 1;
					if (damage <= 0){
						this.kill();
						this.getEntityWorld().removeEntity(this);
					}
				}
			}
			
			angle += 4.0f*spinDirection;
			for (float i = 0; i < 360; i += 120.0f){
				Vec3d moveVec = Util.lookVector((float)Math.toRadians(angle+i), 0).scale(0.2);
				if (random.nextInt(2) == 0){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, moveVec.xCoord, moveVec.yCoord, moveVec.zCoord, 166, 37, 48);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX, posY, posZ, moveVec.xCoord, moveVec.yCoord, moveVec.zCoord, 92, 21, 26);
				}
			}
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.damage = compound.getInteger("damage");
		casterId = new UUID(compound.getLong("UUIDMost"), compound.getLong("UUIDLeast"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("damage", damage);
		compound.setLong("UUIDMost", casterId.getMostSignificantBits());
		compound.setLong("UUIDLeast", casterId.getLeastSignificantBits());
	}
}
