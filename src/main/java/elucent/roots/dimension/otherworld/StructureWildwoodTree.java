package elucent.roots.dimension.otherworld;

import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.block.BlockLogBase;
import elucent.roots.dimension.RootsBiome;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureWildwoodTree extends RootsStructure {
	static Random random = new Random();
	public static void generateTree(World world, int x, int y, int z){
		int height = random.nextInt(4)+5;
		boolean canSpawn = world.getBlockState(new BlockPos(x,y-1,z)).getBlock() == RegistryManager.otherworldGrass;
		for (int i = 0; i < height; i ++){
			if (!world.isAirBlock(new BlockPos(x,y+i,z)) && world.getBlockState(new BlockPos(x,y+i,z)).getBlock() != RegistryManager.leavesWildwood){
				canSpawn = false;
			}
		}
		if (canSpawn){
			GenerationUtil.fillBox(world, x-2, y+height-2, z-2, x+3, y+height+1, z+3, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBoxWithDecay(world, x-1, y+height-2, z-3, x+2, y+height-1, z+4, 0.3f, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBoxWithDecay(world, x-3, y+height-2, z-1, x+4, y+height-2, z+4, 0.3f, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBoxWithDecay(world, x-1, y+height, z-3, x+2, y+height+1, z+4, 0.3f, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBoxWithDecay(world, x-3, y+height, z-1, x+4, y+height+1, z+4, 0.3f, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBox(world, x-1, y+height+1, z-1, x+2, y+height+3, z+2, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBox(world, x, y+height+3, z-1, x+1, y+height+4, z+2, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBox(world, x-1, y+height+3, z, x+2, y+height+4, z+1, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBox(world, x, y+height+1, z-2, x+1, y+height+2, z+3, RegistryManager.leavesWildwood.getDefaultState());
			GenerationUtil.fillBox(world, x-2, y+height+1, z, x+3, y+height+2, z+1, RegistryManager.leavesWildwood.getDefaultState());
			for (int i = 0; i < height+2; i ++){
				world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
		}
	}
	
	public StructureWildwoodTree(float chance, int count){
		super(chance, count);
	}

	@Override
	public void generate(World world, int x, int z, int[][] heights, int[][] bottoms, RootsBiome[][] biomes, float[][] coeffs) {
		if (random.nextFloat() < this.chancePerChunk){
			for (int i = 0; i < this.numPerChunk; i ++){
				int xx = random.nextInt(16);
				int zz = random.nextInt(16);
				if (biomes[0][0].equals(biomes[xx][zz])){
					generateTree(world, x*16+xx, heights[xx][zz], z*16+zz);
				}
			}
		}
	}

}
