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

public class StructureAzureShrub implements IWorldGenerator {
	static Random random = new Random();
	public static void generateShrub(World world, int x, int y, int z){
<<<<<<< HEAD
		int height = random.nextInt(2)+1;
		boolean canSpawn = world.getBlockState(new BlockPos(x,y-1,z)).getBlock() == RegistryManager.needleGrass;
		for (int i = 0; i < height; i ++){
			if (!world.isAirBlock(new BlockPos(x,y+i,z))){
				canSpawn = false;
			}
		}
		if (canSpawn){
			GenerationUtil.fillBox(world, x-1, y+height, z-1, x+2, y+height+1, z+2, RegistryManager.leavesAzurepine.getDefaultState());
			GenerationUtil.fillBox(world, x, y+height+1, z-1, x+1, y+height+2, z+2, RegistryManager.leavesAzurepine.getDefaultState());
			GenerationUtil.fillBox(world, x-1, y+height+1, z, x+2, y+height+2, z+1, RegistryManager.leavesAzurepine.getDefaultState());
			world.setBlockState(new BlockPos(x,y+height+2,z), RegistryManager.leavesAzurepine.getDefaultState());
			GenerationUtil.fillBox(world, x, y, z, x+1, y+height+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
		}
=======
		int height = random.nextInt(1)+1;
		GenerationUtil.fillBox(world, x-1, y+height, z-1, x+2, y+height+1, z+2, RegistryManager.leavesAzurepine.getDefaultState());
		GenerationUtil.fillBox(world, x, y+height+1, z-1, x+1, y+height+2, z+2, RegistryManager.leavesAzurepine.getDefaultState());
		GenerationUtil.fillBox(world, x-1, y+height+1, z, x+2, y+height+2, z+1, RegistryManager.leavesAzurepine.getDefaultState());
		world.setBlockState(new BlockPos(x,y+height+2,z), RegistryManager.leavesAzurepine.getDefaultState());
		GenerationUtil.fillBox(world, x, y, z, x+1, y+height+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
