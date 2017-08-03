package teamroots.emberroot.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import teamroots.emberroot.RegistryManager;
import teamroots.emberroot.Roots; 
import teamroots.emberroot.capability.RootsCapabilityManager; 
import teamroots.emberroot.network.PacketHandler;  
import teamroots.emberroot.util.Fields;
import teamroots.emberroot.util.OfferingUtil;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event){
		RootsCapabilityManager.register();
		//EffectManager.init();
		RegistryManager.registerAll();
		PacketHandler.registerMessages();
	  
		OfferingUtil.init();
	//FeyMagicManager.init();
	}
	
	 
}
