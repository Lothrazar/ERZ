package teamroots.roots.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamroots.roots.RegistryManager;
import teamroots.roots.model.ModelHolder;
import teamroots.roots.particle.ParticleRenderer;
import teamroots.roots.util.ShaderUtil;

public class ClientProxy extends CommonProxy{

	public static ParticleRenderer particleRenderer = new ParticleRenderer();
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		ShaderUtil.init();
		ModelHolder.init();
	}
	
 
}
