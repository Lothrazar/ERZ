package elucent.roots.dimension.otherworld;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.block.BlockLogBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureBoulder implements IWorldGenerator {
	static Random random = new Random();
	public static void generateRock(World world, int x, int y, int z, int size){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		positions.add(new BlockPos(x,y,z));
		while (positions.size() < size*4){
			int direction = random.nextInt(3);
			if (direction == 0){
				positions.add(positions.get(random.nextInt(positions.size())).add(random.nextInt(3)-1, 0, 0));
			}
			if (direction == 1){
				positions.add(positions.get(random.nextInt(positions.size())).add(0, random.nextInt(3)-1, 0));
			}
			if (direction == 2){
				positions.add(positions.get(random.nextInt(positions.size())).add(0, 0, random.nextInt(3)-1));
			}
		}
		for (int i = 0; i < positions.size(); i ++){
			world.setBlockState(positions.get(i), Blocks.COBBLESTONE.getDefaultState());
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
