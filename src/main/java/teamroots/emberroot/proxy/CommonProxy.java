package teamroots.emberroot.proxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import teamroots.emberroot.Const;
import teamroots.emberroot.RegistryManager;
import teamroots.emberroot.entity.golem.MessageEmberSizedBurstFX;

public class CommonProxy {
  public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
  public void preInit(FMLPreInitializationEvent event) {
    RegistryManager.registerAll();
    int id = 0;
    INSTANCE.registerMessage(MessageEmberSizedBurstFX.MessageHolder.class, MessageEmberSizedBurstFX.class, id++, Side.CLIENT);
  }
}
