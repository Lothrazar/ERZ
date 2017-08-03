package teamroots.emberroot.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RootsCapabilityManager {
    public static void register()
    {
        CapabilityManager.INSTANCE.register(IPlayerDataCapability.class,new PlayerDataCapabilityStorage(),DefaultPlayerDataCapability.class);
    }

    @SubscribeEvent
    public void addCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY_LOC, new PlayerDataProvider(new DefaultPlayerDataCapability()));
        }
    }
}
