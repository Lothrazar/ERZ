package elucent.roots.network;

import elucent.roots.Roots;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Roots.MODID);
	private static int id = 0;

    public static void registerMessages()
    {
        INSTANCE.registerMessage(MessageSpellCastFX.MessageHolder.class,MessageSpellCastFX.class,id ++, Side.CLIENT);
        INSTANCE.registerMessage(MessageTerraBurstFX.MessageHolder.class,MessageTerraBurstFX.class,id ++, Side.CLIENT);
		INSTANCE.registerMessage(MessageUpdateMana.CapsMessageHandler.class, MessageUpdateMana.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(MessageDirectedTerraBurstFX.MessageHolder.class,MessageDirectedTerraBurstFX.class,id ++, Side.CLIENT);
      }
}
