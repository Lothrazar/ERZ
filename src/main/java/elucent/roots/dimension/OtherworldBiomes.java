package elucent.roots.dimension;

import elucent.roots.dimension.otherworld.StructureAzurePineTree;
import elucent.roots.util.NoiseGenUtil;

public class OtherworldBiomes {
	static NoiseFunction forestTopFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64+(int)(NoiseGenUtil.getOctave(seed, x, y, 256)*48*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 128)*32*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 32)*16*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 8)*4*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 4)*2*(0.5f+0.5f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 2)*1*(0.25f+0.75f*coeff));
		}
	};
	
	static NoiseFunction forestBottomFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64-(int)(NoiseGenUtil.getOctave(seed, x, y, 512)*32*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 50)*16*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 10)*4*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 5)*2*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 3)*1*(0.5f+0.5f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 1)*1*(0.25f+0.75f*coeff));
		}
	};

	static NoiseFunction plainsTopFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64+(int)(NoiseGenUtil.getOctave(seed, x, y, 256)*48*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 128)*32*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 32)*16*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 8)*4*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 4)*2*(0.5f+0.5f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 2)*1*(0.25f+0.75f*coeff));
		}
	};
	
	static NoiseFunction plainsBottomFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64-(int)(NoiseGenUtil.getOctave(seed, x, y, 512)*32*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 50)*16*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 10)*4*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 5)*2*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 3)*1*(0.5f+0.5f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 1)*1*(0.25f+0.75f*coeff));
		}
	};
	
	static NoiseFunction desertTopFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64+(int)(NoiseGenUtil.getOctave(seed, x, y, 256)*24.0f*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 128)*18.0f*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 32)*12.0f*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 8)*4.0f*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 4)*1.0f*(0.5f+0.5f*coeff));
		}
	};
	
	static NoiseFunction desertBottomFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64-(int)(NoiseGenUtil.getOctave(seed, x, y, 512)*16.0f*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 50)*12.0f*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 10)*4.0f*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 5)*2.0f*(0.25f+0.75f*coeff));
		}
	};
	
	static NoiseFunction hillsTopFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64+(int)(NoiseGenUtil.getOctave(seed, x, y, 256)*48*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 128)*32*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 32)*24*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 8)*6*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 4)*2*(0.5f+0.5f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 2)*1*(0.25f+0.75f*coeff));
		}
	};
	
	static NoiseFunction hillsBottomFunction = new NoiseFunction(){
		@Override
		public int getScaledNoiseAtPos(long seed, float coeff, int x, int y){
			return 64-(int)(NoiseGenUtil.getOctave(seed, x, y, 512)*32*coeff 
					 + NoiseGenUtil.getOctave(seed, x, y, 50)*16*(0.0625f+0.9375f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 10)*4*(0.125f+0.875f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 5)*2*(0.25f+0.75f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 3)*1*(0.5f+0.5f*coeff)
					 + NoiseGenUtil.getOctave(seed, x, y, 1)*1*(0.25f+0.75f*coeff));
		}
	};
	
	public static RootsBiome biomeWoods = new RootsBiome("woods", forestTopFunction, forestBottomFunction)
			.addStructureSpawn(new StructureAzurePineTree(0.1f,1));
	public static RootsBiome biomeShrubland = new RootsBiome("shrubland", forestTopFunction, forestBottomFunction);
	public static RootsBiome biomePlains = new RootsBiome("plains", plainsTopFunction, plainsBottomFunction);
	public static RootsBiome biomeGlowingSands = new RootsBiome("glowingSands", desertTopFunction, desertBottomFunction);
	public static RootsBiome biomeDeepwood = new RootsBiome("deepwood", hillsTopFunction, hillsBottomFunction);
}
