package elucent.roots.capability.powers;

import elucent.roots.network.MessageUpdateCaps;
import elucent.roots.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class DefaultPowerCapability implements IPowersCapability{
	public String power = "none";
	public int powerUse = 20;
	public boolean cooling = false;
	public int cooldown = 0;

	@Override
	public void setPower(EntityPlayer player, String power) {
		if(this.power != power){
			this.power = power;
			this.powerUse = 20;
			dataChanged(player);
		}
	}
	
	@Override
	public int getPowerLeft() {
		return powerUse;
	}

	@Override
	public void usePower(EntityPlayer player) {
		this.powerUse -= 1;
		this.cooldown = 20;
		this.cooling = true;
		startCooldown(player);
	}

	@Override
	public String getPowerName() {
		return power;
	}
	
	@Override
	public boolean isCooling() {
		return cooling;
	}
	
	@Override
	public int getCooldown(){
		return cooldown;
	}
	
	@Override
	public void startCooldown(EntityPlayer player) {
		if(!power.equals("none") && cooling){
			if(cooldown != 0){
				cooldown -= 1;
				dataChanged(player);
			} else{
				cooling = false;
			}	
		}
	}
	
	@Override
	public void dataChanged(EntityPlayer player) {
		if(player != null && !player.getEntityWorld().isRemote){
			CommonProxy.network.sendTo(new MessageUpdateCaps(saveNBTData()), (EntityPlayerMP) player);
		}
	}

	@Override
	public NBTTagCompound saveNBTData() {
		return (NBTTagCompound)PowerCapabilityStorage.storage.writeNBT(PowerProvider.powerCapability, this, null);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		PowerCapabilityStorage.storage.readNBT(PowerProvider.powerCapability, this, null, compound);
	}
	
}
