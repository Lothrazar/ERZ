package elucent.roots.ritual.powers;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.RootsNames;
import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPowerGrow extends RitualPower {
	Random random = new Random();
	public RitualPowerGrow(){
		super("grow",EnumPowerType.TYPE_TARGET_BLOCK);
	}
	
	public boolean growBlockSafe(World world, BlockPos pos){
		if (world.getBlockState(pos).getBlock() instanceof IGrowable){
			for (int j = 0; j < 40; j ++){
				world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX()+random.nextFloat(), pos.getY()+random.nextFloat(), pos.getZ()+random.nextFloat(), 0, 0.5f*(random.nextFloat()), 0, 0);
			}
			((IGrowable)world.getBlockState(pos).getBlock()).grow(world, random, pos, world.getBlockState(pos));
			return true;
		}
		if(world.getBlockState(pos).getBlock() == Blocks.NETHER_WART){
			for (int j = 0; j < 40; j ++){
				world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX()+random.nextFloat(), pos.getY()+random.nextFloat(), pos.getZ()+random.nextFloat(), 0, 0.5f*(random.nextFloat()), 0, 0);
			}
			BlockNetherWart wart = (BlockNetherWart) world.getBlockState(pos).getBlock();
			IBlockState state = world.getBlockState(pos);
			int age = (Integer)state.getValue(wart.AGE).intValue();
			if(age < 3){
				state = state.withProperty(wart.AGE, Integer.valueOf(age + 1));
				world.setBlockState(pos, state, 2);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onRightClickBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state){
		super.onRightClickBlock(player,world,pos,state);
		if (state.getBlock() instanceof IGrowable && state.getBlock() != Blocks.TALLGRASS){
			((IGrowable)state.getBlock()).grow(world, random, pos, state);
			this.growBlockSafe(world, pos.north());
			this.growBlockSafe(world, pos.south());
			this.growBlockSafe(world, pos.east());
			this.growBlockSafe(world, pos.west());
			for (int j = 0; j < 40; j ++){
				world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX()+random.nextFloat(), pos.getY()+random.nextFloat(), pos.getZ()+random.nextFloat(), 0, 0.5f*(random.nextFloat()), 0, 0);
			}
		}
	}
}
