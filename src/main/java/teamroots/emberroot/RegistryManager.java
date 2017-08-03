package teamroots.emberroot;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamroots.emberroot.entity.EntityFairyCircle;
import teamroots.emberroot.entity.deer.EntityDeer;
import teamroots.emberroot.entity.deer.RenderDeer;
import teamroots.emberroot.entity.fairy.EntityFairy;
import teamroots.emberroot.entity.fairy.RenderFairy;
import teamroots.emberroot.entity.sprout.EntitySprout;
import teamroots.emberroot.entity.sprout.RenderSprout;
import teamroots.emberroot.util.Misc;
import teamroots.emberroot.world.WorldGenFairyPool;

public class RegistryManager {
 
	public static IWorldGenerator worldGenFairyPool ;
 
	public static void registerAll(){

 
		GameRegistry.registerWorldGenerator(worldGenFairyPool = new WorldGenFairyPool(), 1);
 
		int id = 0;
		//EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"auspicious_point"), EntityAuspiciousPoint.class, "auspicious_point", id ++, Roots.instance, 64, 20, true);
	//	EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"fire_jet"), EntityFireJet.class, "fire_jet", id ++, Roots.instance, 64, 20, true); 
//		EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"petal_shell"), EntityPetalShell.class, "petal_shell", id ++, Roots.instance, 64, 1, true); 
		//EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"boost"), EntityBoost.class, "boost", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"deer"), EntityDeer.class, "deer", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(      new ResourceLocation(Const.MODID,"deer"), Misc.intColor(161, 132, 88), Misc.intColor(94, 77, 51)); 
		//EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"flare"), EntityFlare.class, "flare", id ++, Roots.instance, 64, 1, true); 
		EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"sprout"), EntitySprout.class, "sprout", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(      new ResourceLocation(Const.MODID,"sprout"), Misc.intColor(136, 191, 33), Misc.intColor(165, 232, 42));
		//EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"barrow"), EntityBarrow.class, "barrow", id ++, Roots.instance, 64, 20, true); 
		EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"fairy"), EntityFairy.class, "fairy", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(      new ResourceLocation(Const.MODID,"fairy"), Misc.intColor(255, 214, 255), Misc.intColor(209, 255, 173));
		EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"fairy_circle"), EntityFairyCircle.class, "fairy_circle", id ++, Roots.instance, 64, 20, true);
	//	EntityRegistry.registerModEntity(new ResourceLocation(Const.MODID,"blink_projectile"), EntityBlinkProjectile.class, "blink_projectile", id ++, Roots.instance, 64, 1, true);
		
		List<BiomeEntry> biomeEntries = new ArrayList<BiomeEntry>();
		biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.COOL));
		biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.DESERT));
		biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.ICY));
		biomeEntries.addAll(BiomeManager.getBiomes(BiomeType.WARM));
		List<Biome> biomes = new ArrayList<Biome>();
		for (BiomeEntry b : biomeEntries){
			biomes.add(b.biome);
		}
		biomes.addAll(BiomeManager.oceanBiomes);
		
		EntityRegistry.addSpawn(EntityDeer.class, ConfigManager.deerSpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomes.toArray(new Biome[biomes.size()]));
		EntityRegistry.addSpawn(EntitySprout.class, ConfigManager.sproutSpawnWeight, 3, 7, EnumCreatureType.CREATURE, biomes.toArray(new Biome[biomes.size()]));
		
 	}
    
 
    
 
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
    public void registerRendering(ModelRegistryEvent event){
  
		//RenderingRegistry.registerEntityRenderingHandler(EntityFireJet.class, new RenderNull.Factory());
 
		//RenderingRegistry.registerEntityRenderingHandler(EntityBoost.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer.Factory());
 
	//	RenderingRegistry.registerEntityRenderingHandler(EntityFlare.class, new RenderNull.Factory());

		RenderingRegistry.registerEntityRenderingHandler(EntitySprout.class, new RenderSprout.Factory());
	//	RenderingRegistry.registerEntityRenderingHandler(EntityBarrow.class, new RenderNull.Factory());

		RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderFairy.Factory());
		//RenderingRegistry.registerEntityRenderingHandler(EntityFairyCircle.class, new RenderNull.Factory());
		//RenderingRegistry.registerEntityRenderingHandler(EntityBlinkProjectile.class, new RenderNull.Factory());
		
 
	}
	
 
}
