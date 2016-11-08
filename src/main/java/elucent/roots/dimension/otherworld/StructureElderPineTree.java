package elucent.roots.dimension.otherworld;

import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.block.BlockLogBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureElderPineTree implements IWorldGenerator {
	static Random random = new Random();
	public static void generateTree(World world, int x, int y, int z){
		int height = random.nextInt(12)+14;
<<<<<<< HEAD
		boolean canSpawn = world.getBlockState(new BlockPos(x,y-1,z)).getBlock() == RegistryManager.needleGrass;
		for (int i = 0; i < height; i ++){
			if (!world.isAirBlock(new BlockPos(x,y+i,z)) && world.getBlockState(new BlockPos(x,y+i,z)).getBlock() != RegistryManager.leavesElderpine){
				canSpawn = false;
			}
		}
		if (canSpawn){
			for (int i = 0; i < height; i ++){
				if (i > height/4){
					if (i % 2 == 0){
						float radius = (5.5f*(0.5f+0.5f*(2.0f/3.0f)*(((float)height)-((float)i))/((float)height)));
						GenerationUtil.generateDiscWithDecay(world, x,y+i,z, radius, 0.15f, RegistryManager.leavesElderpine.getDefaultState());
						GenerationUtil.generateDiscWithDecay(world, x,y+i+1,z, radius-2.0f, 0.15f, RegistryManager.leavesElderpine.getDefaultState());
						GenerationUtil.fillBox(world, x-(int)(radius-2), y+i, z, x+1+(int)(radius-2), y+i+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.X));
						GenerationUtil.fillBox(world, x, y+i, z-(int)(radius-2), x+1, y+i+1, z+(int)(radius-2)+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Z));
					}
					if (i == height-1){
						for (int j = 5; j > 0; j --){
							GenerationUtil.generateDisc(world, x,y+i+(5-j)+1,z, j/3.0f, RegistryManager.leavesElderpine.getDefaultState());
						}
					}
				}
				world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
=======
		for (int i = 0; i < height; i ++){
			if (i > height/4){
				if (i % 2 == 0){
					float radius = (5.5f*(0.5f+0.5f*(2.0f/3.0f)*(((float)height)-((float)i))/((float)height)));
					GenerationUtil.generateDiscWithDecay(world, x,y+i,z, radius, 0.15f, RegistryManager.leavesElderpine.getDefaultState());
					GenerationUtil.generateDiscWithDecay(world, x,y+i+1,z, radius-2.0f, 0.15f, RegistryManager.leavesElderpine.getDefaultState());
					GenerationUtil.fillBox(world, x-(int)(radius-2), y+i, z, x+1+(int)(radius-2), y+i+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.X));
					GenerationUtil.fillBox(world, x, y+i, z-(int)(radius-2), x+1, y+i+1, z+(int)(radius-2)+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Z));
				}
				if (i == height-1){
					for (int j = 5; j > 0; j --){
						GenerationUtil.generateDisc(world, x,y+i+(5-j)+1,z, j/3.0f, RegistryManager.leavesElderpine.getDefaultState());
					}
				}
			}
			world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
