package elucent.roots.capability.powers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IPowersCapability {
	public String getPowerName();
	public int getPowerLeft();
	public void setPower(EntityPlayer player, String power);
	public void usePower(EntityPlayer player);
	public NBTTagCompound saveNBTData();
	public void loadNBTData(NBTTagCompound compound);
	public void dataChanged(EntityPlayer player);
	public int getCooldown();
	public boolean isCooling();
	public void startCooldown(EntityPlayer player);
}
