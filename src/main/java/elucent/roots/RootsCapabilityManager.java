package elucent.roots;

import elucent.roots.capability.mana.DefaultManaCapability;
import elucent.roots.capability.mana.IManaCapability;
import elucent.roots.capability.mana.ManaCapabilityStorage;
import elucent.roots.capability.mana.ManaProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RootsCapabilityManager {
 
    public static void preInit(){
    	CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), DefaultManaCapability.class);
    }
	
	@SubscribeEvent
	public void onAddCapabilities(AttachCapabilitiesEvent.Entity e){
		if (e.getEntity() instanceof EntityPlayer){
			if(!e.getEntity().hasCapability(ManaProvider.manaCapability, null)){
				e.addCapability(new ResourceLocation("roots:manaCapability"), new ManaProvider(new DefaultManaCapability()));
			}
		}
	}
}
