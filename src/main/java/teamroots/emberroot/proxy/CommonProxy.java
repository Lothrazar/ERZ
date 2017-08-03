package teamroots.emberroot.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamroots.emberroot.RegistryManager;
import teamroots.emberroot.capability.RootsCapabilityManager;
import teamroots.emberroot.network.PacketHandler;
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
