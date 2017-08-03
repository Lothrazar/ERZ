package teamroots.emberroot.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import teamroots.emberroot.Const;
import teamroots.emberroot.network.message.*; 

public class PacketHandler {
	public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);

    private static int id = 0;
    
    public static void registerMessages(){
        INSTANCE.registerMessage(MessageMoonlightBurstFX.MessageHolder.class,MessageMoonlightBurstFX.class,id ++,Side.CLIENT); 
        INSTANCE.registerMessage(MessageMoonlightSparkleFX.MessageHolder.class,MessageMoonlightSparkleFX.class,id ++,Side.CLIENT);
        INSTANCE.registerMessage(MessagePlayerDataUpdate.MessageHolder.class,MessagePlayerDataUpdate.class,id ++,Side.CLIENT);
        INSTANCE.registerMessage(MessageFairyDustBurstFX.MessageHolder.class,MessageFairyDustBurstFX.class,id ++,Side.CLIENT);
        INSTANCE.registerMessage(MessageFairyTameFX.MessageHolder.class,MessageFairyTameFX.class,id ++,Side.CLIENT);
    }
}
