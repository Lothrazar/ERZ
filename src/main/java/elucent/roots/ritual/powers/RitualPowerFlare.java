package elucent.roots.ritual.powers;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.RootsNames;
import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPowerFlare extends RitualPower {
	Random random = new Random();
	public RitualPowerFlare(){
		super("flare",EnumPowerType.TYPE_TARGET_ENTITY,160);
	}
	
	@Override
	public void onRightClickEntity(EntityPlayer player, World world, Entity entity){
		super.onRightClickEntity(player,world,entity);
		if (entity instanceof EntityLivingBase){
			((EntityLivingBase)entity).attackEntityFrom(DamageSource.inFire, 7.0f);
			world.createExplosion(null, entity.posX, entity.posY, entity.posZ, 0.001f, true);
			((EntityLivingBase)entity).setFire(4);
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX-4.0,entity.posY-4.0,entity.posZ-4.0,entity.posX+4.0,entity.posY+4.0,entity.posZ+4.0));
			for (int i = 0; i < targets.size(); i ++){
				if (targets.get(i).getUniqueID() != player.getUniqueID()){
					targets.get(i).setFire(4);
				}
			}
			((EntityLivingBase)entity).setLastAttacker(player);
			((EntityLivingBase)entity).setRevengeTarget(player);
			for (int i = 0; i < 40; i ++){
				world.spawnParticle(EnumParticleTypes.FLAME, entity.posX, entity.posY+entity.height/2.0f, entity.posZ, Math.pow(1.5f*(random.nextFloat()-0.5f),3.0), Math.pow(1.5f*(random.nextFloat()-0.5f),3.0), Math.pow(1.5f*(random.nextFloat()-0.5f),3.0), 0);
			}
		}
	}
}
