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

public class StructureWildwoodShrub implements IWorldGenerator {
	static Random random = new Random();
	public static void generateShrub(World world, int x, int y, int z){
		int height = random.nextInt(1)+1;
		GenerationUtil.fillBox(world, x-1, y+height, z-1, x+2, y+height+1, z+2, RegistryManager.leavesWildwood.getDefaultState());
		GenerationUtil.fillBox(world, x, y+height+1, z-1, x+1, y+height+2, z+2, RegistryManager.leavesWildwood.getDefaultState());
		GenerationUtil.fillBox(world, x-1, y+height+1, z, x+2, y+height+2, z+1, RegistryManager.leavesWildwood.getDefaultState());
		GenerationUtil.fillBox(world, x, y, z, x+1, y+height+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
