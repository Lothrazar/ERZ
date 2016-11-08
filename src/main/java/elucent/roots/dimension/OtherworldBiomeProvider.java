package elucent.roots.dimension;

import java.util.ArrayList;

import elucent.roots.RegistryManager;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;

public class OtherworldBiomeProvider extends BiomeProvider {
	World world;
	public OtherworldBiomeProvider(World world){
		super();
		this.world = world;
	}
	
	@Override
	public ArrayList<Biome> getBiomesToSpawnIn(){
		ArrayList<Biome> biomes = new ArrayList<Biome>();
		biomes.add(RegistryManager.biomeOtherworld);
		return biomes;
	}
}
