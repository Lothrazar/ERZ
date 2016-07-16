package elucent.roots.capability.powers;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PowerCapabilityStorage implements IStorage<IPowersCapability>{
   public static final PowerCapabilityStorage storage = new PowerCapabilityStorage();
	
	@Override
	public NBTBase writeNBT(Capability<IPowersCapability> capability, IPowersCapability instance, EnumFacing side) {
		NBTTagCompound nbt= new NBTTagCompound();
		nbt.setString("power", ((DefaultPowerCapability)instance).power);
		nbt.setInteger("uses", ((DefaultPowerCapability)instance).powerUse);
		nbt.setInteger("cooldown", ((DefaultPowerCapability)instance).cooldown);
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPowersCapability> capability, IPowersCapability instance, EnumFacing side,NBTBase nbt) {
		 NBTTagCompound tag = (NBTTagCompound) nbt;
		((DefaultPowerCapability)instance).power = tag.getString("power");
		((DefaultPowerCapability)instance).powerUse = tag.getInteger("uses");
		((DefaultPowerCapability)instance).cooldown = tag.getInteger("cooldown");
	}

}
