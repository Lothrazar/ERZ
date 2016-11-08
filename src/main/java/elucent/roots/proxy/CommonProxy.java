package elucent.roots.proxy;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.RootsCapabilityManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentManager;
import elucent.roots.gui.GuiHandler;
import elucent.roots.item.ItemHungerTalisman;
import elucent.roots.item.ItemPursuitTalisman;
import elucent.roots.network.MessageUpdateMana;
import elucent.roots.network.PacketHandler;
import elucent.roots.research.ResearchManager;
import elucent.roots.ritual.RitualManager;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	public static SimpleNetworkWrapper network;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		registerEvents();
		PacketHandler.registerMessages();
		RegistryManager.init();
		RegistryManager.registerRecipes();
		RootsCapabilityManager.preInit();
		RegistryManager.registerAchievements();
	}
	
	public void init(FMLInitializationEvent event){
		RegistryManager.registerEntities();
		ComponentManager.init();
		RitualManager.init();
	}
	
	public void postInit(FMLPostInitializationEvent event){
		Util.initOres();
		Util.initNaturalBlocks();
		NetworkRegistry.INSTANCE.registerGuiHandler(Roots.instance, new GuiHandler());
		ResearchManager.init();
	}

	public void registerEvents()
	{
		MinecraftForge.EVENT_BUS.register(new ItemHungerTalisman());
		MinecraftForge.EVENT_BUS.register(new ItemPursuitTalisman());
	}
	
	public void spawnParticleMagicFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAltarLineFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAltarFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAltarBigFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicSparkleFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicSmallSparkleFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicSparklePulseFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
	
	public void spawnParticleMagicSparkleScalableFX(World world, int lifetime, double x, double y, double z, double vx, double vy, double vz, float scale, double r, double g, double b){
		//
	}
}
