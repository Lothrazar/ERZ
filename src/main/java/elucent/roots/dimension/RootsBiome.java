package elucent.roots.dimension;

import java.util.ArrayList;

import elucent.roots.dimension.otherworld.RootsStructure;
import net.minecraftforge.fml.common.IWorldGenerator;

public class RootsBiome {
	String name = "null";
	NoiseFunction top = new NoiseFunction();
	NoiseFunction bottom = new NoiseFunction();
	public ArrayList<RootsStructure> structures = new ArrayList<RootsStructure>();
	
	public RootsBiome(String name, NoiseFunction topFunction, NoiseFunction bottomFunction){
		this.name = name;
		this.top = topFunction;
		this.bottom = bottomFunction;
	}
	
	public RootsBiome addStructureSpawn(RootsStructure structure){
		this.structures.add(structure);
		return this;
	}
	
	@Override
	public boolean equals(Object object){
		if (object instanceof RootsBiome){
			return ((RootsBiome) object).getName().compareTo(getName()) == 0;
		}
		return false;
	}
	
	public NoiseFunction getTopNoise(){
		return top;
	}
	
	public NoiseFunction getBottomNoise(){
		return bottom;
	}
	
	public String getName(){
		return name;
	}
}
