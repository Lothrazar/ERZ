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

public class StructureFallenLog extends RootsStructure {
	static Random random = new Random();
	public static void generateLog(World world, int x, int y, int z){
		boolean canSpawn = world.getBlockState(new BlockPos(x,y-1,z)).getBlock() == RegistryManager.otherworldGrass;
		if (canSpawn){
			int axis = random.nextInt(2);
			if (axis == 0){
				GenerationUtil.fillBox(world, x-1, y, z, x+2, y+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.X));
			}
			else if (axis == 1){
				GenerationUtil.fillBox(world, x, y, z-1, x+1, y+1, z+2, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Z));
			
			}
		}
	}
	
	public StructureFallenLog(float chance, int count){
		super(chance, count);
	}

	@Override
	public void generate(World world, int x, int z, int[][] heights, int[][] bottoms, RootsBiome[][] biomes, float[][] coeffs) {
		if (random.nextFloat() < this.chancePerChunk){
			for (int i = 0; i < this.numPerChunk; i ++){
				int xx = random.nextInt(16);
				int zz = random.nextInt(16);
				if (biomes[0][0].equals(biomes[xx][zz])){
					generateLog(world, x*16+xx, heights[xx][zz], z*16+zz);
				}
			}
		}
	}

}
