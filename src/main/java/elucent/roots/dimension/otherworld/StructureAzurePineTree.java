package elucent.roots.dimension.otherworld;

import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.block.BlockLogBase;
import elucent.roots.dimension.OtherworldChunkGenerator;
import elucent.roots.dimension.RootsBiome;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureAzurePineTree extends RootsStructure {
	static Random random = new Random();
	
	public static void generateTree(World world, int x, int y, int z){
		int height = random.nextInt(8)+10;
		boolean canSpawn = world.getBlockState(new BlockPos(x,y-1,z)).getBlock() == RegistryManager.needleGrass;
		for (int i = 0; i < height; i ++){
			if (!world.isAirBlock(new BlockPos(x,y+i,z)) && world.getBlockState(new BlockPos(x,y+i,z)).getBlock() != RegistryManager.leavesAzurepine){
				canSpawn = false;
			}
		}
		if (canSpawn){
			for (int i = 0; i < height; i ++){
				if (i > height/4){
					if (i % 2 == 0){
						float radius = (6.5f*(0.5f+0.5f*(2.0f/3.0f)*(((float)height)-((float)i))/((float)height)));
						GenerationUtil.generateDiscWithDecay(world, x,y+i,z, radius, 0.15f, RegistryManager.leavesAzurepine.getDefaultState());
						GenerationUtil.generateDiscWithDecay(world, x,y+i+1,z, radius-2.0f, 0.15f, RegistryManager.leavesAzurepine.getDefaultState());
						GenerationUtil.fillBox(world, x-(int)(radius-2), y+i, z, x+1+(int)(radius-2), y+i+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.X));
						GenerationUtil.fillBox(world, x, y+i, z-(int)(radius-2), x+1, y+i+1, z+(int)(radius-2)+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Z));
					}
					if (i == height-1){
						for (int j = 5; j > 0; j --){
							GenerationUtil.generateDisc(world, x,y+i+(5-j)+1,z, j/3.0f, RegistryManager.leavesAzurepine.getDefaultState());
						}
					}
				}
				world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
		}
	}
	
	public StructureAzurePineTree(float chance, int count){
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
