package elucent.roots.dimension.otherworld;

import java.util.Random;

import elucent.roots.RegistryManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StructureTowerHelper {
	static Random random = new Random();
	
	public static class StructRoom {
		public int type = 0;
		public boolean up = false, down = false, east = false, west = false, north = false, south = false;
		public StructRoom(int type, boolean up, boolean down, boolean east, boolean west, boolean north, boolean south){
			this.type = type;
			this.up = up;
			this.down = down;
			this.east = east;
			this.west = west;
			this.north = north;
			this.south = south;
		}
		
		public void generateLayer1(World world, int x1, int y1, int z1, int x2, int y2, int z2){
			if (type == 1){
				for (int i = x1; i < x2; i ++){
					for (int j = y1; j < y2; j ++){
						for (int k = z1; k < z2; k ++){
							world.setBlockState(new BlockPos(i,j,k), RegistryManager.runeStone.getDefaultState());
						}
					}
				}
				for (int i = x1+1; i < x2-1; i ++){
					for (int j = y1+1; j < y2-1; j ++){
						for (int k = z1+1; k < z2-1; k ++){
							world.setBlockState(new BlockPos(i,j,k), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (up){
					for (int i = x1+1; i < x2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(i,y1,j), RegistryManager.runeStoneTile.getDefaultState());
						}
					}
				}
				else {
					for (int i = x1+1; i < x2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(i,y1,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (down){
					for (int i = x1+1; i < x2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(i,y2-1,j), RegistryManager.runeStoneTile.getDefaultState());
						}
					}
				}
				else {
					for (int i = x1+1; i < x2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(i,y2-1,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (west){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x2-1,i,j), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x2-1,i,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (east){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x1,i,j), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x1,i,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (north){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = x1+1; j < x2-1; j ++){
							world.setBlockState(new BlockPos(j,i,z1), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = x1+1; j < x2-1; j ++){
							world.setBlockState(new BlockPos(j,i,z1), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (south){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = x1+1; j < x2-1; j ++){
							world.setBlockState(new BlockPos(j,i,z2-1), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = x1+1; j < x2-1; j ++){
							world.setBlockState(new BlockPos(j,i,z2-1), Blocks.AIR.getDefaultState());
						}
					}
				}
			}
			if (type == 2){
				for (int i = x1-2; i < x2+2; i ++){
					for (int j = y1-2; j < y2+2; j ++){
						for (int k = z1-2; k < z2+2; k ++){
							world.setBlockState(new BlockPos(i,j,k), RegistryManager.runeStone.getDefaultState());
						}
					}
				}
				for (int i = x1-1; i < x2+1; i ++){
					for (int j = y1-1; j < y2+1; j ++){
						for (int k = z1-1; k < z2+1; k ++){
							world.setBlockState(new BlockPos(i,j,k), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (up){
					for (int i = x1-1; i < x2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(i,y1-2,j), RegistryManager.runeStoneTile.getDefaultState());
						}
					}
				}
				else {
					for (int i = x1-1; i < x2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(i,y1-2,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (down){
					for (int i = x1-1; i < x2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(i,y2+1,j), RegistryManager.runeStoneTile.getDefaultState());
						}
					}
				}
				else {
					for (int i = x1-1; i < x2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(i,y2+1,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (west){
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(x2+1,i,j), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(x2+1,i,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (east){
					for (int i = y1-1; i < y2-1; i ++){
						for (int j = z1-1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x1-2,i,j), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = z1-1; j < z2+1; j ++){
							world.setBlockState(new BlockPos(x1-2,i,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (north){
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = x1-1; j < x2+1; j ++){
							world.setBlockState(new BlockPos(j,i,z1-2), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = x1-1; j < x2+1; j ++){
							world.setBlockState(new BlockPos(j,i,z1-2), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (south){
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = x1-1; j < x2+1; j ++){
							world.setBlockState(new BlockPos(j,i,z2+1), RegistryManager.runeStoneBrick.getDefaultState());
						}
					}
				}
				else {
					for (int i = y1-1; i < y2+1; i ++){
						for (int j = x1-1; j < x2+1; j ++){
							world.setBlockState(new BlockPos(j,i,z2+1), Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
		
		public void generateLayer2(World world, int x1, int y1, int z1, int x2, int y2, int z2){
			if (type == 1){
				for (int i = x1+1; i < x2-1; i ++){
					for (int j = y1+1; j < y2-1; j ++){
						for (int k = z1+1; k < z2-1; k ++){
							world.setBlockState(new BlockPos(i,j,k), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (!up){
					for (int i = x1+1; i < x2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(i,y1,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (!down){
					for (int i = x1+1; i < x2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(i,y2-1,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (!west){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x2-1,i,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (!east){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = z1+1; j < z2-1; j ++){
							world.setBlockState(new BlockPos(x1,i,j), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (!north){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = x1+1; j < x2-1; j ++){
							world.setBlockState(new BlockPos(j,i,z1), Blocks.AIR.getDefaultState());
						}
					}
				}
				if (!south){
					for (int i = y1+1; i < y2-1; i ++){
						for (int j = x1+1; j < x2-1; j ++){
							world.setBlockState(new BlockPos(j,i,z2-1), Blocks.AIR.getDefaultState());
						}
					}
				}
			}
		}
	}
	
	public static void generateCastle(World world, int x, int y, int z, int size){
		StructRoom[][][] data = new StructRoom[9][9][9];
		for (int i = 0; i < 9; i ++){
			for (int j = 0; j < 9; j ++){
				for (int k = 0; k < 9; k ++){
					data[i][j][k] = new StructRoom(0,false,false,false,false,false,false);
				}
			}
		}
		
		data[4][4][4] = new StructRoom(1,true,true,true,true,true,true);
		int sizeCopy = size;
		while (sizeCopy > 0){
			for (int i = 0; i < 9 && sizeCopy > 0; i ++){
				for (int j = 0; j < 9 && sizeCopy > 0; j ++){
					for (int k = 0; k < 9 && sizeCopy > 0; k ++){
						if (data[i][j][k].type == 1){
							sizeCopy --;
							int chance = random.nextInt(6);
							if (chance == 0 && (i-1) >= 0){
								if (data[i-1][j][k].type == 0){
									data[i-1][j][k] = new StructRoom(1,true,true,true,false,true,true);
									data[i][j][k].east = false;
								}
							}
							if (chance == 1 && (i+1) < 9){
								if (data[i+1][j][k].type == 0){
									data[i+1][j][k] = new StructRoom(1,true,true,false,true,true,true);
									data[i][j][k].west = false;
								}
							}
							if (chance == 2 && (j-1) >= 0){
								if (data[i][j-1][k].type == 0){
									data[i][j-1][k] = new StructRoom(1,true,false,true,true,true,true);
									data[i][j][k].up = false;
								}
							}
							if (chance == 3 && (j+1) < 9){
								if (data[i][j+1][k].type == 0){
									data[i][j+1][k] = new StructRoom(1,false,true,true,true,true,true);
									data[i][j][k].down = false;
								}
							}
							if (chance == 4 && (k-1) >= 0){
								if (data[i][j][k-1].type == 0){
									data[i][j][k-1] = new StructRoom(1,true,true,true,true,true,false);
									data[i][j][k].north = false;
								}
							}
							if (chance == 5 && (k+1) < 9){
								if (data[i][j][k+1].type == 0){
									data[i][j][k+1] = new StructRoom(1,true,true,true,true,false,true);
									data[i][j][k].south = false;
								}
							}
						}
					}
				}
			}
		}
		StructRoom[][][] newData = new StructRoom[17][17][17];
		for (int i = 0; i < 17; i ++){
			for (int j = 0; j < 17; j ++){
				for (int k = 0; k < 17; k ++){
					newData[i][j][k] = new StructRoom(0,false,false,false,false,false,false);
				}
			}
		}
		for (int i = 0; i < 9; i ++){
			for (int j = 0; j < 9; j ++){
				for (int k = 0; k < 9; k ++){
					newData[i*2][j*2][k*2] = data[i][j][k];
				}
			}
		}
		for (int i = 0; i < 17; i += 2){
			for (int j = 0; j < 17; j += 2){
				for (int k = 0; k < 17; k += 2){
					if (i-2 >= 0){
						if (!newData[i][j][k].east && !newData[i-2][j][k].west && newData[i][j][k].type == 1 && newData[i-2][j][k].type == 1){
							newData[i-1][j][k] = new StructRoom(1,true,true,false,false,true,true);
						}
					}
					if (i+2 < 17){
						if (!newData[i][j][k].west && !newData[i+2][j][k].east && newData[i][j][k].type == 1 && newData[i+2][j][k].type == 1){
							newData[i+1][j][k] = new StructRoom(1,true,true,false,false,true,true);
						}
					}
					if (j-2 >= 0){
						if (!newData[i][j][k].up && !newData[i][j-2][k].down && newData[i][j][k].type == 1 && newData[i][j-2][k].type == 1){
							newData[i][j-1][k] = new StructRoom(1,false,false,true,true,true,true);
						}
					}
					if (j+2 < 17){
						if (!newData[i][j][k].down && !newData[i][j+2][k].up && newData[i][j][k].type == 1 && newData[i][j+2][k].type == 1){
							newData[i][j+1][k] = new StructRoom(1,false,false,true,true,true,true);
						}
					}
					if (k-2 >= 0){
						if (!newData[i][j][k].north && !newData[i][j][k-2].south && newData[i][j][k].type == 1 && newData[i][j][k-2].type == 1){
							newData[i][j][k-1] = new StructRoom(1,true,true,true,true,false,false);
						}
					}
					if (k+2 < 17){
						if (!newData[i][j][k].south && !newData[i][j][k+2].north && newData[i][j][k].type == 1 && newData[i][j][k+2].type == 1){
							newData[i][j][k+1] = new StructRoom(1,true,true,true,true,false,false);
						}
					}
				}
			}
		}
		for (int i = 0; i < 17; i ++){
			for (int j = 0; j < 17; j ++){
				for (int k = 0; k < 17; k ++){
					newData[i][j][k].generateLayer1(world, x+(i-4)*4-2, y+(j-4)*4-2, z+(k-4)*4-2, x+(i-4)*4+3, y+(j-4)*4+3, z+(k-4)*4+3);
				}
			}
		}
		for (int i = 0; i < 17; i ++){
			for (int j = 0; j < 17; j ++){
				for (int k = 0; k < 17; k ++){
					newData[i][j][k].generateLayer2(world, x+(i-4)*4-2, y+(j-4)*4-2, z+(k-4)*4-2, x+(i-4)*4+3, y+(j-4)*4+3, z+(k-4)*4+3);
				}
			}
		}
	}
	
	public static void makeBoxRoom(World world, int x1, int y1, int z1, int x2, int y2, int z2, boolean[] wallToggle){
		
	}

}
