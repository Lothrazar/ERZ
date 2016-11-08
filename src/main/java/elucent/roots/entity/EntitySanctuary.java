package elucent.roots.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import elucent.roots.Roots;
import elucent.roots.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySanctuary extends Entity {
	int lifetime = 100;
	float radius = 2.0f;
	
	public EntitySanctuary(World world, double x, double y, double z, int potency, int size){
		super(world);
		this.setPosition(x, y, z);
		lifetime = 200+100*potency;
		radius = 2.0f+1.0f*size;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		List<Entity> entities = getEntityWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(posX-radius/2.0,posY,posZ-radius/2.0,posX+radius/2.0,posY+8,posZ+radius/2.0));
		for (int i = 0; i < entities.size(); i ++){
			if (entities.get(i) instanceof IProjectile){
				entities.get(i).setDead();
			}
			if (entities.get(i) instanceof EntityMob || entities.get(i) instanceof EntitySlime){
				((EntityLivingBase)entities.get(i)).knockBack(null, 0.1f, posX-entities.get(i).posX, posZ-entities.get(i).posZ);
			}
			if (entities.get(i) instanceof EntityPlayer){
				if (rand.nextInt(40) == 0 && ((EntityPlayer)entities.get(i)).getFoodStats().getFoodLevel() >= 18){
					((EntityPlayer)entities.get(i)).heal(1.0f);
					((EntityPlayer)entities.get(i)).getFoodStats().addExhaustion(1.75f);
				}
			}
		}
		for (int i = 0; i < 360; i += rand.nextInt(10)){
			double xComp = Math.sin(Math.toRadians(i));
			double zComp = Math.cos(Math.toRadians(i));
			if (rand.nextInt(3) == 0){
				if (rand.nextInt(2) == 0){
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX+(radius/2.0f)*xComp, posY, posZ+(radius/2.0f)*zComp, 0, 0, 0, 128, 16, 16);
				}
				else {
					Roots.proxy.spawnParticleMagicAuraFX(getEntityWorld(), posX+(radius/2.0f)*xComp, posY, posZ+(radius/2.0f)*zComp, 0, 0, 0, 128, 16, 64);
				}
			}
		}
		lifetime --;
		if (lifetime <= 0){
			setDead();
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
	}
}
