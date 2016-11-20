package elucent.roots.dimension.otherworld;

import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.block.BlockLogBase;
import elucent.roots.dimension.RootsBiome;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureStump extends RootsStructure {
	static Random random = new Random();
	public static void generateStump(World world, int x, int posY, int z){
		boolean canSpawn = world.getBlockState(new BlockPos(x,posY-1,z)).getBlock() == RegistryManager.otherworldGrass;
		for (int i = 0; i < 5; i ++){
			if (!world.isAirBlock(new BlockPos(x,posY+i,z)) && world.getBlockState(new BlockPos(x,posY+i,z)).getBlock() != RegistryManager.leavesElderpine){
				canSpawn = false;
			}
		}
		if (canSpawn){
			int y = posY-1;
			GenerationUtil.fillBox(world, x-2, y, z-1, x-1, y+3, z+2, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBox(world, x+2, y, z-1, x+3, y+3, z+2, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBox(world, x-1, y, z-2, x+2, y+3, z-1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBox(world, x-1, y, z+2, x+2, y+3, z+3, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBoxWithDecay(world, x-2, y+3, z-1, x-1, y+6, z+2, 0.15f, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBoxWithDecay(world, x+2, y+3, z-1, x+3, y+6, z+2, 0.15f, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBoxWithDecay(world, x-1, y+3, z-2, x+2, y+6, z-1, 0.15f, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			GenerationUtil.fillBoxWithDecay(world, x-1, y+3, z+2, x+2, y+6, z+3, 0.15f, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			int posX = x-3;
			int posZ = z-1;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posZ += 2;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posX += 6;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posZ -= 2;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posX = x-1;
			posZ = z-3;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posX += 2;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posZ += 6;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			posX -= 2;
			if (random.nextBoolean()){
				GenerationUtil.fillBox(world, posX, y, posZ, posX+1, y+random.nextInt(4), posZ+1, RegistryManager.logWildwood.getDefaultState().withProperty(BlockLogBase.AXIS, EnumFacing.Axis.Y));
			}
			int chance = random.nextInt(4);
			if (chance == 0){
				GenerationUtil.fillBox(world, x-2, y, z, x-2, y+1, z+1, Blocks.AIR.getDefaultState());
			}
			if (chance == 1){
				GenerationUtil.fillBox(world, x+2, y, z, x+3, y+1, z+1, Blocks.AIR.getDefaultState());
			}
			if (chance == 2){
				GenerationUtil.fillBox(world, x, y, z-3, x+1, y+1, z-2, Blocks.AIR.getDefaultState());
			}
			if (chance == 3){
				GenerationUtil.fillBox(world, x, y, z+2, x+1, y+1, z+3, Blocks.AIR.getDefaultState());
			}
		}
	}
	
	public StructureStump(float chance, int count){
		super(chance, count);
	}

	@Override
	public void generate(World world, int x, int z, int[][] heights, int[][] bottoms, RootsBiome[][] biomes, float[][] coeffs) {
		if (random.nextFloat() < this.chancePerChunk){
			for (int i = 0; i < this.numPerChunk; i ++){
				int xx = random.nextInt(16);
				int zz = random.nextInt(16);
				if (biomes[0][0].equals(biomes[xx][zz])){
					generateStump(world, x*16+xx, heights[xx][zz], z*16+zz);
				}
			}
		}
	}

}
