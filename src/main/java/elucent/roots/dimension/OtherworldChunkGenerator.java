package elucent.roots.dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.dimension.otherworld.StructureFallenLog;
import elucent.roots.dimension.otherworld.StructureStump;
import elucent.roots.dimension.otherworld.StructureTowerHelper;
import elucent.roots.dimension.otherworld.StructureWildwoodShrub;
import elucent.roots.dimension.otherworld.StructureWildwoodTree;
import elucent.roots.dimension.otherworld.StructureAzurePineTree;
import elucent.roots.dimension.otherworld.StructureAzureShrub;
import elucent.roots.dimension.otherworld.StructureElderPineTree;
import elucent.roots.entity.EntitySpriteling;
import elucent.roots.entity.EntitySprite;
import elucent.roots.entity.EntityGreaterSprite;
import elucent.roots.util.NoiseGenUtil;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

public class OtherworldChunkGenerator implements IChunkGenerator {
	Random random = new Random();
	World world;
	List<SpawnListEntry> passiveCreatures = new ArrayList<SpawnListEntry>();
	List<RootsBiome> biomeOrder = new ArrayList<RootsBiome>();
	
	public int getHeight(float biomeCoeff, float islandCoeff, int x, int y){
		RootsBiome lower = biomeOrder.get(Math.min(biomeOrder.size()-1, (int)Math.floor(biomeCoeff)));
		RootsBiome higher = biomeOrder.get(Math.min(biomeOrder.size()-1, (int)Math.ceil(biomeCoeff)));
		return (int)((1.0f-Util.fract(biomeCoeff))*(float)lower.getTopNoise().getScaledNoiseAtPos(world.getSeed(), islandCoeff, x, y) + Util.fract(biomeCoeff)*(float)higher.getTopNoise().getScaledNoiseAtPos(world.getSeed(), islandCoeff, x, y));
	}
	
	public int getBottom(float biomeCoeff, float islandCoeff, int x, int y){
		RootsBiome lower = biomeOrder.get(Math.min(biomeOrder.size()-1, (int)Math.floor(biomeCoeff)));
		RootsBiome higher = biomeOrder.get(Math.min(biomeOrder.size()-1, (int)Math.ceil(biomeCoeff)));
		return (int)((1.0f-Util.fract(biomeCoeff))*(float)lower.getBottomNoise().getScaledNoiseAtPos(world.getSeed(), islandCoeff, x, y) + Util.fract(biomeCoeff)*(float)higher.getBottomNoise().getScaledNoiseAtPos(world.getSeed(), islandCoeff, x, y));
	}
	
	public OtherworldChunkGenerator(World world){
		this.world = world;
		passiveCreatures.add(new SpawnListEntry(EntitySpriteling.class, 6, 1, 3));
		passiveCreatures.add(new SpawnListEntry(EntitySprite.class, 3, 1, 1));
		passiveCreatures.add(new SpawnListEntry(EntityGreaterSprite.class, 1, 1, 1));
		passiveCreatures.add(new SpawnListEntry(EntitySheep.class,50,2,5));
		passiveCreatures.add(new SpawnListEntry(EntityWolf.class,20,2,5));
		
		biomeOrder.add(OtherworldBiomes.biomeGlowingSands);
		biomeOrder.add(OtherworldBiomes.biomePlains);
		biomeOrder.add(OtherworldBiomes.biomeShrubland);
		biomeOrder.add(OtherworldBiomes.biomeWoods);
		biomeOrder.add(OtherworldBiomes.biomeDeepwood);
	}
	
	public float getIslandCoefficient(int x, int y){
		return 2.0f*((NoiseGenUtil.getOctave(world.getSeed(), x, y, 1)*0.0078125f
				+NoiseGenUtil.getOctave(world.getSeed(), x, y, 4)*0.015625f
				+NoiseGenUtil.getOctave(world.getSeed(), x, y, 10)*0.03125f
				+NoiseGenUtil.getOctave(world.getSeed(), x, y, 16)*0.0625f
				+NoiseGenUtil.getOctave(world.getSeed(), x, y, 32)*0.125f
				+NoiseGenUtil.getOctave(world.getSeed(), x, y, 50)*0.25f
				+NoiseGenUtil.getOctave(world.getSeed(), x, y, 100)*0.5f)-0.5f);
	}
	
	public RootsBiome getBiome(int x, int z){
		double val = (NoiseGenUtil.getOctave(world.getSeed(), x, z, 60)*0.5
				+NoiseGenUtil.getOctave(world.getSeed(), x, z, 150)*1.0
				+NoiseGenUtil.getOctave(world.getSeed(), x, z, 300)*1.5
				+NoiseGenUtil.getOctave(world.getSeed(), x, z, 500)*2.0);
		int index = (int)Math.floor((val/5.0f-0.0001f)*(float)biomeOrder.size());
		return biomeOrder.get(index);
	}
	
	public float getBiomeCoeff(int x, int z){
		double val = (NoiseGenUtil.getOctave(world.getSeed(), x, z, 60)*0.5
				+NoiseGenUtil.getOctave(world.getSeed(), x, z, 150)*1.0
				+NoiseGenUtil.getOctave(world.getSeed(), x, z, 300)*1.5
				+NoiseGenUtil.getOctave(world.getSeed(), x, z, 500)*2.0);
		return (float) ((val/5.0f-0.0001f)*(float)biomeOrder.size());
	}

	@Override
	public Chunk provideChunk(int x, int z) {
		Chunk c = new Chunk(world,x,z);
		int[][] heights = new int[16][16];
		int[][] bottoms = new int[16][16];
		float[][] coeffs = new float[16][16];
		RootsBiome[][] biomes = new RootsBiome[16][16];
		float[][] biomeCoeffs = new float[16][16];
		for (int i = 0; i < 16; i ++){
			for (int j = 0; j < 16; j ++){
				biomes[i][j] = getBiome(x*16+i,z*16+j);
				coeffs[i][j] = getIslandCoefficient(x*16+i,z*16+j);
				biomeCoeffs[i][j] = getBiomeCoeff(x*16+i,z*16+j);
				heights[i][j] = getHeight(biomeCoeffs[i][j],coeffs[i][j],x*16+i,z*16+j);
				bottoms[i][j] = getBottom(biomeCoeffs[i][j],coeffs[i][j],x*16+i,z*16+j);
			}
		}
		for (int i = 0; i < 16; i ++){
			for (int j = 0; j < 16; j ++){
				if (coeffs[i][j] >= -0.012){
					RootsBiome biome = biomes[i][j];
					if (biome == OtherworldBiomes.biomePlains || biome == OtherworldBiomes.biomeShrubland || biome == OtherworldBiomes.biomeWoods){
						for (int k = bottoms[i][j]; k < heights[i][j]; k ++){
							if (k == heights[i][j]-1){
								c.setBlockState(new BlockPos(x*16+i,k,z*16+j), RegistryManager.otherworldGrass.getDefaultState());
							}
							else if (k >= heights[i][j]-4 && k < heights[i][j]-1){
								c.setBlockState(new BlockPos(x*16+i,k,z*16+j), RegistryManager.otherworldDirt.getDefaultState());
							}
							else c.setBlockState(new BlockPos(x*16+i,k,z*16+j), Blocks.STONE.getDefaultState());
						}
					}
					else if (biome == OtherworldBiomes.biomeDeepwood){
						for (int k = bottoms[i][j]; k < heights[i][j]; k ++){
							if (k == heights[i][j]-1){
								c.setBlockState(new BlockPos(x*16+i,k,z*16+j), RegistryManager.needleGrass.getDefaultState());
							}
							else if (k >= heights[i][j]-4 && k < heights[i][j]-1){
								c.setBlockState(new BlockPos(x*16+i,k,z*16+j), RegistryManager.otherworldDirt.getDefaultState());
							}
							else c.setBlockState(new BlockPos(x*16+i,k,z*16+j), Blocks.STONE.getDefaultState());
						}
					}
					else if (biome == OtherworldBiomes.biomeGlowingSands){
						for (int k = bottoms[i][j]; k < heights[i][j]; k ++){
							if (k == heights[i][j]-1){
								c.setBlockState(new BlockPos(x*16+i,k,z*16+j), RegistryManager.glowSand.getDefaultState());
							}
							else if (k >= heights[i][j]-4 && k < heights[i][j]-1){
								c.setBlockState(new BlockPos(x*16+i,k,z*16+j), RegistryManager.glowSand.getDefaultState());
							}
							else c.setBlockState(new BlockPos(x*16+i,k,z*16+j), Blocks.STONE.getDefaultState());
						}
					}
				}
			}
		}
		return c;
	}

	@Override
	public void populate(int x, int z) {
		int xx = random.nextInt(16);
		int zz = random.nextInt(16);
		RootsBiome b = getBiome(x*16+xx,z*16+zz);
		if (b.equals(OtherworldBiomes.biomePlains)){
			if (random.nextInt(10) == 0){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomePlains){
					StructureWildwoodTree.generateTree(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			if (random.nextBoolean()){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomePlains){
					StructureWildwoodShrub.generateShrub(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 4; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomePlains){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						if (random.nextInt(5) != 0){
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.RED_FLOWER.getStateFromMeta(random.nextInt(9)));
						}
						else {
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.YELLOW_FLOWER.getDefaultState());
						}
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 160; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomePlains){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), RegistryManager.leafyGrass.getDefaultState());
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			xx = random.nextInt(16);
			zz = random.nextInt(16);
			if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomePlains){
				
			}
		}
		if (getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeShrubland){
			for (int i = 0; i < 1; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeShrubland){
					StructureWildwoodTree.generateTree(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 4+random.nextInt(7); i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeShrubland){
					StructureWildwoodShrub.generateShrub(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 4; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeShrubland){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						if (random.nextInt(5) != 0){
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.RED_FLOWER.getStateFromMeta(random.nextInt(9)));
						}
						else {
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.YELLOW_FLOWER.getDefaultState());
						}
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 160; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeShrubland){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), RegistryManager.leafyGrass.getDefaultState());
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			xx = random.nextInt(16);
			zz = random.nextInt(16);
			if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeShrubland){
				if (random.nextInt(8) == 0){
					StructureFallenLog.generatelog(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
			}
		}
		if (getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeWoods){
			for (int i = 0; i < 2+random.nextInt(2); i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeWoods){
					StructureWildwoodTree.generateTree(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 4+random.nextInt(5); i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeWoods){
					StructureWildwoodShrub.generateShrub(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 5; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeWoods){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						if (random.nextInt(5) != 0){
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.RED_FLOWER.getStateFromMeta(random.nextInt(9)));
						}
						else {
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.YELLOW_FLOWER.getDefaultState());
						}
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 120; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeWoods){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), RegistryManager.leafyGrass.getDefaultState());
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			xx = random.nextInt(16);
			zz = random.nextInt(16);
			if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeWoods){
				if (random.nextInt(40) == 0){
					StructureStump.generateStump(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				if (random.nextInt(8) == 0){
					StructureFallenLog.generatelog(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
			}
		}
		if (getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeDeepwood){
			for (int i = 0; i < random.nextInt(2); i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeDeepwood){
					StructureElderPineTree.generateTree(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 1+random.nextInt(2); i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeDeepwood){
					StructureAzurePineTree.generateTree(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 4+random.nextInt(5); i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeDeepwood){
					StructureAzureShrub.generateShrub(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			for (int i = 0; i < 5; i ++){
				if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeDeepwood){
					if (world.isAirBlock(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz))){
						if (random.nextInt(5) != 0){
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.RED_FLOWER.getStateFromMeta(random.nextInt(9)));
						}
						else {
							world.setBlockState(new BlockPos(x*16+xx,getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz),z*16+zz), Blocks.YELLOW_FLOWER.getDefaultState());
						}
					}
				}
				xx = random.nextInt(16);
				zz = random.nextInt(16);
			}
			xx = random.nextInt(16);
			zz = random.nextInt(16);
			if (getIslandCoefficient(x*16+xx,z*16+zz) > 0.025 && getBiome(x*16+xx,z*16+zz) == OtherworldBiomes.biomeDeepwood){
				if (random.nextInt(40) == 0){
					StructureStump.generateStump(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
				if (random.nextInt(8) == 0){
					StructureFallenLog.generatelog(world, x*16+xx, getHeight(getBiomeCoeff(x*16+xx,z*16+zz),getIslandCoefficient(x*16+xx,z*16+zz),x*16+xx,z*16+zz), z*16+zz);
				}
			}
		}
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		world.provider.setAllowedSpawnTypes(true, true);
		if (creatureType == EnumCreatureType.CREATURE){
			return this.passiveCreatures;
		}
		return new ArrayList<SpawnListEntry>();
	}

	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
		return new BlockPos(0,0,0);
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		// TODO Auto-generated method stub
		return false;
	}
}
