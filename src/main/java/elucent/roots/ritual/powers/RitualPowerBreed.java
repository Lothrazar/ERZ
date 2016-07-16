package elucent.roots.ritual.powers;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.RootsNames;
import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPowerBreed extends RitualPower {
	Random random = new Random();
	public RitualPowerBreed(){
		super("breed",EnumPowerType.TYPE_TARGET_ENTITY,128);
	}
	
	@Override
	public void onRightClickEntity(EntityPlayer player, World world, Entity entity){
		super.onRightClickEntity(player,world,entity);
		if (entity instanceof EntityLivingBase){
			ArrayList<EntityAnimal> animals = (ArrayList<EntityAnimal>) world.getEntitiesWithinAABB(EntityAnimal.class, new AxisAlignedBB(entity.posX-4.0,entity.posY-4.0,entity.posZ-4.0,entity.posX+4.0,entity.posY+4.0,entity.posZ+4.0));
			for (int i = 0; i < animals.size(); i ++){
				animals.get(i).setInLove(player);
				animals.get(i).getEntityData().setInteger("InLove", 400);
			}
		}
	}
}
