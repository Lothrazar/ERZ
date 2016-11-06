package elucent.roots.item;

import net.minecraft.item.ItemStack;

public interface IImbuable {
	public String getEffect(ItemStack stack);
	public int getPotency(ItemStack stack);
	public int getEfficiency(ItemStack stack);
	public int getSize(ItemStack stack);
}
