package teamroots.emberroot.proxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import teamroots.emberroot.Const;
import teamroots.emberroot.RegistryManager;
import teamroots.emberroot.entity.golem.MessageGolemLaserFX;

public class CommonProxy {
  public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
  public void preInit(FMLPreInitializationEvent event) {
    RegistryManager.registerAll();
    int id = 0;
    INSTANCE.registerMessage(MessageGolemLaserFX.MessageHolder.class, MessageGolemLaserFX.class, id++, Side.CLIENT);
  }
}
