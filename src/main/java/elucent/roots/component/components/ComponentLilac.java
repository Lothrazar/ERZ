package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ComponentLilac extends ComponentBase{
	Random random = new Random();
	public ComponentLilac(){
		super("lilac","Growth",Blocks.DOUBLE_PLANT,1,14);	
	}
	
	public boolean growBlockSafe(World world, BlockPos pos, int potency){
		if (world.getBlockState(pos).getBlock() instanceof IGrowable && random.nextInt(5-(int)potency) < 2){
			IBlockState prev = world.getBlockState(pos);
			((IGrowable)world.getBlockState(pos).getBlock()).grow(world, random, pos, world.getBlockState(pos));
			world.notifyBlockUpdate(pos, prev, world.getBlockState(pos), 8);
			return true;
		}
		if(world.getBlockState(pos).getBlock() == Blocks.NETHER_WART && random.nextInt(5-(int)potency) < 2){
			BlockNetherWart wart = (BlockNetherWart) world.getBlockState(pos).getBlock();
			IBlockState state = world.getBlockState(pos);
			int age = (Integer)state.getValue(wart.AGE).intValue();
			if(age < 3){
				IBlockState newState = state.withProperty(wart.AGE, Integer.valueOf(age + 1));
				world.setBlockState(pos, newState, 3);
				world.notifyBlockUpdate(pos, state, newState, 8);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			if (!world.isRemote){
				BlockPos pos = Util.getRayTraceNonFull(world, ((EntityPlayer)caster), 6+2*(int)size);
				for (int i = (-(int)size)-1; i < (int)size+2; i ++){
					for (int j = (-(int)size)-1; j < (int)size+2; j ++){
						growBlockSafe(world, pos.add((int)i,0,(int)j), (int)potency);
					}
				}
				boolean fullEfficiency = true;//growBlockSafe(world, pos, (int)potency) && growBlockSafe(world, pos.east(), (int)potency) && growBlockSafe(world, pos.west(), (int)potency) &&growBlockSafe(world, pos.north(), (int)potency) &&growBlockSafe(world, pos.south(), (int)potency);
				if (fullEfficiency){
					if (caster instanceof EntityPlayer){
						if (!((EntityPlayer)caster).hasAchievement(RegistryManager.achieveSpellGrowth)){
							PlayerManager.addAchievement(((EntityPlayer)caster), RegistryManager.achieveSpellGrowth);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			if (!world.isRemote){
				BlockPos pos = new BlockPos(x,y,z);
				for (int i = (-(int)size)-1; i < (int)size+2; i ++){
					for (int j = (-(int)size)-1; j < (int)size+2; j ++){
						growBlockSafe(world, pos.add((int)i,0,(int)j), (int)potency);
					}
				}
				boolean fullEfficiency = true;//growBlockSafe(world, pos, (int)potency) && growBlockSafe(world, pos.east(), (int)potency) && growBlockSafe(world, pos.west(), (int)potency) &&growBlockSafe(world, pos.north(), (int)potency) &&growBlockSafe(world, pos.south(), (int)potency);
				if (fullEfficiency){
					EntityPlayer player = world.getPlayerEntityByUUID(casterId);
					if (player != null){
						if (!player.hasAchievement(RegistryManager.achieveSpellGrowth)){
							PlayerManager.addAchievement(player, RegistryManager.achieveSpellGrowth);
						}
					}
				}
			}
		}
	}
}
