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

public class StructureBoulder implements IWorldGenerator {
	static Random random = new Random();
	public static void generatelog(World world, int x, int y, int z){
		int axis = random.nextInt(2);
		if (axis == 0){
			GenerationUtil.fillBox(world, x-1, y, z, x+2, y+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.X));
		}
		else if (axis == 1){
			GenerationUtil.fillBox(world, x, y, z-1, x+1, y+1, z+2, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Z));
		
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
