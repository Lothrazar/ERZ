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

public class StructureAzurePineTree implements IWorldGenerator {
	static Random random = new Random();
	public static void generateTree(World world, int x, int y, int z){
		int height = random.nextInt(8)+10;
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

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		
	}

}
