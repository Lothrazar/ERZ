package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import elucent.roots.PlayerManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentEffect;
import elucent.roots.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


public class ComponentBlueOrchid extends ComponentBase{
	Random random = new Random();
	public ComponentBlueOrchid(){
		super("blueorchid","Earthen Roots",Blocks.RED_FLOWER,14);	
	}
	
	public void placeBlockSafe(World world, BlockPos pos, IBlockState state, int potency){
		if (world.isAirBlock(pos)){
			world.setBlockState(pos, state);
		}
	}
	
	public void attemptAdd(World world, BlockPos pos, ArrayList<BlockPos> positions){
		if (world.isAirBlock(pos)){
			positions.add(pos);
		}
	}
	
	public int findTop(ArrayList<BlockPos> positions){
		int max = 1;
		for (int i = 0; i < positions.size(); i ++){
			if (positions.get(i).getY()+1 > max){
				max = positions.get(i).getY()+1;
			}
		}
		return max;
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			if (caster instanceof EntityPlayer && !world.isRemote){
				BlockPos pos = Util.getRayTrace(world,(EntityPlayer)caster,4+2*(int)size);
				IBlockState state = world.getBlockState(pos);
				Block block = world.getBlockState(pos).getBlock();
				if (block == Blocks.STONE || block == Blocks.DIRT || block == Blocks.GRASS || block == Blocks.SAND || block == Blocks.GRAVEL){
					if (block == Blocks.GRASS){
						state = Blocks.DIRT.getDefaultState();
						world.setBlockState(pos, state);
					}
					world.setBlockState(pos.up(), state);
					ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
					positions.add(pos);
					int maxPositions = 64+((int)size-3)*128;
					while (positions.size() < maxPositions){
						int s = positions.size();
						for (int j = 0; j < (double)s/4.0; j ++){
							if (random.nextFloat() > 0.975){
								positions.add(positions.get(j).north());
							}
							if (random.nextFloat() > 0.975){
								positions.add(positions.get(j).south());
							}
							if (random.nextFloat() > 0.975){
								positions.add(positions.get(j).east());
							}
							if (random.nextFloat() > 0.975){
								positions.add(positions.get(j).west());
							}
							if (random.nextFloat() > 0.25){
								positions.add(positions.get(j).up());
							}
							if (random.nextFloat() > 0.975){
								positions.add(positions.get(j).down());
							}
							if (positions.size() > maxPositions){
								break;
							}
						}
					}
					for (int i = 0; i < positions.size(); i ++){
						this.placeBlockSafe(world, positions.get(i), state, (int) potency);
					}
					ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX()-size,pos.getY()-size,pos.getZ()-size,pos.getX()+size,pos.getY()+size,pos.getZ()+size));
					for (int i = 0; i < targets.size(); i ++){
						if (targets.get(i).getUniqueID() != caster.getUniqueID()){
							targets.get(i).moveEntity(0, findTop(positions)+1, 0);
							targets.get(i).motionY = 0.35+random.nextDouble()+0.1*potency;
							if (targets.get(i) instanceof EntityPlayer){
								((EntityPlayer)targets.get(i)).velocityChanged = true;
							}
						}
					}
				}
			}
		}
	}
}
