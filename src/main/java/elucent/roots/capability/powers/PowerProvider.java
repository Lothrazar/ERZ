package elucent.roots.capability.powers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

public class PowerProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>{
	private IPowersCapability cPower = null;
	
	public PowerProvider(){
		cPower = new DefaultPowerCapability();
	}
	
	public PowerProvider(IPowersCapability p){
		this.cPower = p;
	}
	
	@CapabilityInject(IPowersCapability.class)
	public static final Capability<IPowersCapability> powerCapability = null;

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == powerCapability;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
    	if (powerCapability != null && capability == powerCapability) return (T)cPower;
    	return null;
    }
    
    public static IPowersCapability get(EntityPlayer player) {
		return player != null && player.hasCapability(powerCapability, null)? player.getCapability(powerCapability, null): null;
    }
	
	@Override
	public NBTTagCompound serializeNBT() {
		return cPower.saveNBTData();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		cPower.loadNBTData(nbt);
	}

}
