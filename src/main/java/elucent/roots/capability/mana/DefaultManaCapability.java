package elucent.roots.capability.mana;

import elucent.roots.network.MessageUpdateMana;
import elucent.roots.network.PacketHandler;
import elucent.roots.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class DefaultManaCapability implements IManaCapability {
	public float maxMana = 40;
	public float mana = 40;
	
	@Override
	public float getMana() {
		return mana;
	}
	
	@Override
	public float getMaxMana() {
		return maxMana;
	}

	@Override
	public void setMana(EntityPlayer player, float mana) {
		this.mana = mana;
		if (mana < 0){
			this.mana = 0;
		}
		if (mana > getMaxMana()){
			this.mana = getMaxMana();
		}
		dataChanged(player);
	}

	@Override
	public void setMaxMana(float maxMana) {
		this.maxMana = maxMana;
	}

	@Override
	public void dataChanged(EntityPlayer player) {
		if(player != null && !player.getEntityWorld().isRemote){
			PacketHandler.INSTANCE.sendTo(new MessageUpdateMana(saveNBTData()), (EntityPlayerMP) player);
		}
	}

	@Override
	public NBTTagCompound saveNBTData() {
		return (NBTTagCompound)ManaCapabilityStorage.storage.writeNBT(ManaProvider.manaCapability, this, null);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		ManaCapabilityStorage.storage.readNBT(ManaProvider.manaCapability, this, null, compound);
	}
}
