package elucent.roots.proxy;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import elucent.roots.RootsCapabilityManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentManager;
import elucent.roots.entity.projectile.EntityRitualProjectile;
import elucent.roots.gui.GuiHandler;
import elucent.roots.mutation.MutagenManager;
import elucent.roots.network.MessageUpdateMana;
import elucent.roots.research.ResearchManager;
import elucent.roots.ritual.RitualManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	public static SimpleNetworkWrapper network;
	
	public void preInit(FMLPreInitializationEvent event){
		RegistryManager.init();
		RegistryManager.registerRecipes();
		RootsCapabilityManager.preInit();
		RegistryManager.registerAchievements();
		network = NetworkRegistry.INSTANCE.newSimpleChannel("roots");
		network.registerMessage(MessageUpdateMana.CapsMessageHandler.class, MessageUpdateMana.class, 2, Side.CLIENT);
	}
	
	public void init(FMLInitializationEvent event){
		RegistryManager.registerEntities();
		ComponentManager.init();
		RitualManager.init();
		MutagenManager.init();
	}
	
	public void postInit(FMLPostInitializationEvent event){
		Util.initOres();
		Util.initNaturalBlocks();
		Util.initBerries();
		NetworkRegistry.INSTANCE.registerGuiHandler(Roots.instance, new GuiHandler());
		ResearchManager.init();
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
	
	public void spawnParticleMagicAuraFX(World world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b){
		//
	}
}
