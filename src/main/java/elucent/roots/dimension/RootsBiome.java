package elucent.roots.dimension;

public class RootsBiome {
	String name = "null";
	NoiseFunction top = new NoiseFunction();
	NoiseFunction bottom = new NoiseFunction();
	
	public RootsBiome(String name, NoiseFunction topFunction, NoiseFunction bottomFunction){
		this.name = name;
		this.top = topFunction;
		this.bottom = bottomFunction;
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
