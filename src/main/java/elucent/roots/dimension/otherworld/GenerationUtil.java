package elucent.roots.dimension.otherworld;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GenerationUtil {
	static Random random = new Random();
	public static void fillBox(World world, int x1, int y1, int z1, int x2, int y2, int z2, IBlockState state){
		for (int i = x1; i < x2; i ++){
			for (int j = y1; j < y2; j ++){
				for (int k = z1; k < z2; k ++){
					world.setBlockState(new BlockPos(i,j,k), state);
				}
			}
		}
	}
	
	public static void fillBoxWithDecay(World world, int x1, int y1, int z1, int x2, int y2, int z2, float decay, IBlockState state){
		for (int i = x1; i < x2; i ++){
			for (int j = y1; j < y2; j ++){
				for (int k = z1; k < z2; k ++){
					if (random.nextFloat() > decay){
						world.setBlockState(new BlockPos(i,j,k), state);
					}
				}
			}
		}
	}
	
	public static void generateDisc(World world, int x, int y, int z, double r, IBlockState state){
		for (double i = x-r; i < x+r; i ++){
			for (double k = z-r; k < z+r; k ++){
				if ((i-x)*(i-x)+(k-z)*(k-z) < r*r){
					world.setBlockState(new BlockPos(i,y,k), state);
				}
			}
		}
	}
	
	public static void generateDiscWithDecay(World world, int x, int y, int z, double r, float decay, IBlockState state){
		for (double i = x-r; i < x+r; i ++){
			for (double k = z-r; k < z+r; k ++){
				if ((i-x)*(i-x)+(k-z)*(k-z) < r*r){
					if (random.nextFloat() > decay){
						world.setBlockState(new BlockPos(i,y,k), state);
					}
				}
			}
		}
	}
}
