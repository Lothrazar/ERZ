package elucent.roots.dimension.otherworld;

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

public class StructureStandingStone implements IWorldGenerator {
	static Random random = new Random();
	public static void generateStone(World world, int x, int y, int z){
		int height = random.nextInt(3)+3;
		for (int i = 0; i < height; i ++){
			if (i == height-1){
				if (random.nextInt(4) == 0){
					world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.runeStoneSymbolGlowing.getDefaultState());
				}
				else {
					world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.runeStoneSymbol.getDefaultState());
				}
			}
			else {
				world.setBlockState(new BlockPos(x,y+i,z), RegistryManager.runeStoneBrick.getDefaultState());
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
