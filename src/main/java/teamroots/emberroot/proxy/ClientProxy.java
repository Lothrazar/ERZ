package teamroots.emberroot.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamroots.emberroot.model.ModelDeer;
import teamroots.emberroot.model.ModelFairy; 
import teamroots.emberroot.model.ModelSprout;
import teamroots.emberroot.particle.ParticleRenderer;
import teamroots.emberroot.util.ShaderUtil;

public class ClientProxy extends CommonProxy{

	public static ParticleRenderer particleRenderer = new ParticleRenderer();
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		super.preInit(event);
		ShaderUtil.init();

    ModelDeer.instance = new ModelDeer();
    ModelSprout.instance = new ModelSprout();
    ModelFairy.instance = new ModelFairy();
	}
	
 
}
