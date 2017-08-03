package teamroots.emberroot.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamroots.emberroot.model.ModelHolder;
import teamroots.emberroot.particle.ParticleRenderer;
import teamroots.emberroot.util.ShaderUtil;

public class ClientProxy extends CommonProxy{

	public static ParticleRenderer particleRenderer = new ParticleRenderer();
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		ShaderUtil.init();
		ModelHolder.init();
	}
	
 
}
