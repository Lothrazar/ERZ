package teamroots.roots;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import teamroots.roots.entity.*;
import teamroots.roots.util.Misc;
import teamroots.roots.world.WorldGenFairyPool;

public class RegistryManager {
 
	public static IWorldGenerator worldGenGarden, worldGenFairyPool, worldGenBarrow, worldGenStandingStones, worldGenHut, worldGenLeyMarker;
 
	public static void registerAll(){

 
		GameRegistry.registerWorldGenerator(worldGenFairyPool = new WorldGenFairyPool(), 1);
 
		int id = 0;
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":auspicious_point"), EntityAuspiciousPoint.class, "auspicious_point", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":fire_jet"), EntityFireJet.class, "fire_jet", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":thorn_trap"), EntityThornTrap.class, "thorn_trap", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":petal_shell"), EntityPetalShell.class, "petal_shell", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":time_stop"), EntityTimeStop.class, "time_stop", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":boost"), EntityBoost.class, "boost", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":deer"), EntityDeer.class, "deer", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(new ResourceLocation(Roots.MODID+":deer"), Misc.intColor(161, 132, 88), Misc.intColor(94, 77, 51));
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_life"), EntityRitualLife.class, "ritual_life", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_storm"), EntityRitualStorm.class, "ritual_storm", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_light"), EntityRitualLight.class, "ritual_light", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_fire_storm"), EntityRitualFireStorm.class, "ritual_fire_storm", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":flare"), EntityFlare.class, "flare", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_regrowth"), EntityRitualRegrowth.class, "ritual_regrowth", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_windwall"), EntityRitualWindwall.class, "ritual_windwall", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":sprout"), EntitySprout.class, "sprout", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(new ResourceLocation(Roots.MODID+":sprout"), Misc.intColor(136, 191, 33), Misc.intColor(165, 232, 42));
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":barrow"), EntityBarrow.class, "barrow", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":ritual_warden"), EntityRitualWarden.class, "ritual_warden", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":fairy"), EntityFairy.class, "fairy", id ++, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(new ResourceLocation(Roots.MODID+":fairy"), Misc.intColor(255, 214, 255), Misc.intColor(209, 255, 173));
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":fairy_circle"), EntityFairyCircle.class, "fairy_circle", id ++, Roots.instance, 64, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(Roots.MODID+":blink_projectile"), EntityBlinkProjectile.class, "blink_projectile", id ++, Roots.instance, 64, 1, true);
		
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
 
		RenderingRegistry.registerEntityRenderingHandler(EntityAuspiciousPoint.class, new RenderAuspiciousPoint.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFireJet.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityThornTrap.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityPetalShell.class, new RenderPetalShell.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeStop.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBoost.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualLife.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualStorm.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualLight.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualFireStorm.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFlare.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualRegrowth.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualWindwall.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntitySprout.class, new RenderSprout.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBarrow.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualWarden.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFairy.class, new RenderFairy.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityFairyCircle.class, new RenderNull.Factory());
		RenderingRegistry.registerEntityRenderingHandler(EntityBlinkProjectile.class, new RenderNull.Factory());
		
 
	}
	
 
}
