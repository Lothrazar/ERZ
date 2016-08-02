package elucent.roots.capability.mana;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class ManaProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound>{
	private IManaCapability cMana = null;
	
	public ManaProvider(){
		cMana = new DefaultManaCapability();
	}
	
	public ManaProvider(IManaCapability p){
		this.cMana = p;
	}
	
	@CapabilityInject(IManaCapability.class)
	public static final Capability<IManaCapability> manaCapability = null;

	@Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing){
        return capability == manaCapability;
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing){
    	if (manaCapability != null && capability == manaCapability) return (T)cMana;
    	return null;
    }
    
    public static IManaCapability get(EntityPlayer player) {
		return player != null && player.hasCapability(manaCapability, null)? player.getCapability(manaCapability, null): null;
    }
	
	@Override
	public NBTTagCompound serializeNBT() {
		return cMana.saveNBTData();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		cMana.loadNBTData(nbt);
	}

}
