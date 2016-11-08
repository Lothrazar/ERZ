package elucent.roots.dimension.otherworld;

<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.block.BlockLogBase;
<<<<<<< HEAD
import net.minecraft.init.Blocks;
=======
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureBoulder implements IWorldGenerator {
	static Random random = new Random();
<<<<<<< HEAD
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
=======
	public static void generatelog(World world, int x, int y, int z){
		int axis = random.nextInt(2);
		if (axis == 0){
			GenerationUtil.fillBox(world, x-1, y, z, x+2, y+1, z+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.X));
		}
		else if (axis == 1){
			GenerationUtil.fillBox(world, x, y, z-1, x+1, y+1, z+2, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Z));
		
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
