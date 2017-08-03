package teamroots.emberroot.proxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import teamroots.emberroot.RegistryManager;

public class CommonProxy {
  public void preInit(FMLPreInitializationEvent event) {
    RegistryManager.registerAll();
  }
}
