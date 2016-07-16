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

public class RitualPowerLifeDrain extends RitualPower {
	Random random = new Random();
	public RitualPowerLifeDrain(){
		super("lifedrain",EnumPowerType.TYPE_TARGET_ENTITY,64);
	}
	
	@Override
	public void onRightClickEntity(EntityPlayer player, World world, Entity entity){
		super.onRightClickEntity(player,world,entity);
		if (entity instanceof EntityLivingBase){
			float damageDealt = 0.0f;
			((EntityLivingBase)entity).attackEntityFrom(DamageSource.wither, 7.0f);
			damageDealt += 7.0f;
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX-4.0,entity.posY-4.0,entity.posZ-4.0,entity.posX+4.0,entity.posY+4.0,entity.posZ+4.0));
			for (int i = 0; i < targets.size(); i ++){
				if (targets.get(i).getUniqueID() != player.getUniqueID()){
					targets.get(i).attackEntityFrom(DamageSource.wither, 4);
					damageDealt += 4.0f;
					for (int j = 0; j < 40; j ++){
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, targets.get(i).posX, targets.get(i).posY+targets.get(i).height/2.0f, targets.get(i).posZ, Math.pow(0.5f*(random.nextFloat()-0.5f),3.0), Math.pow(0.5f*(random.nextFloat()-0.5f),3.0), Math.pow(0.5f*(random.nextFloat()-0.5f),3.0), 0);
					}
				}
			}
			((EntityLivingBase)entity).setLastAttacker(player);
			((EntityLivingBase)entity).setRevengeTarget(player);
			for (int i = 0; i < 40; i ++){
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entity.posX, entity.posY+entity.height/2.0f, entity.posZ, Math.pow(0.5f*(random.nextFloat()-0.5f),3.0), Math.pow(0.5f*(random.nextFloat()-0.5f),3.0), Math.pow(0.5f*(random.nextFloat()-0.5f),3.0), 0);
			}
			player.heal(0.5f*damageDealt);
		}
	}
}
