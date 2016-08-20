package elucent.roots.capability.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaCapabilityStorage implements IStorage<IManaCapability>{
	public static final ManaCapabilityStorage storage = new ManaCapabilityStorage();
		
	@Override
	public NBTBase writeNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side) {
		NBTTagCompound nbt= new NBTTagCompound();
		nbt.setFloat("mana", ((DefaultManaCapability)instance).mana);
		nbt.setFloat("max_mana", ((DefaultManaCapability)instance).maxMana);
		return nbt;
	}

	@Override
	public void readNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side,NBTBase nbt) {
		 NBTTagCompound tag = (NBTTagCompound) nbt;
		((DefaultManaCapability)instance).mana = tag.getFloat("mana");
		((DefaultManaCapability)instance).maxMana = tag.getFloat("max_mana");
	}

}
