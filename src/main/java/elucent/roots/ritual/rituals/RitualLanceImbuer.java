package elucent.roots.ritual.rituals;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.item.IImbuable;
import elucent.roots.item.ItemCastingBase;
import elucent.roots.item.ItemCrystalStaff;
import elucent.roots.item.ItemMeleeCastingBase;
import elucent.roots.ritual.RitualBase;

public class RitualLanceImbuer extends RitualBase {

	public RitualLanceImbuer() {
		super("lanceImbuer", 255, 255, 255);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		ItemStack toSpawn = new ItemStack(RegistryManager.spellweaverLance, 1);
		ItemCrystalStaff.createData(toSpawn,2);
		for (int i = 0; i < incenses.size() && i < 2; i ++){
			if (incenses.get(i) != null){
				if (incenses.get(i).getItem() instanceof IImbuable){
					IImbuable im = (IImbuable)incenses.get(i).getItem();
					((ItemMeleeCastingBase)toSpawn.getItem()).setEffectInSlot(toSpawn, i+1, im.getEffect(incenses.get(i)), im.getPotency(incenses.get(i)), im.getEfficiency(incenses.get(i)), im.getSize(incenses.get(i)));
				}
			}
		}
		if (!world.isRemote){
			EntityItem item = new EntityItem(world,pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5,toSpawn);
			item.forceSpawn = true;
			world.spawnEntityInWorld(item);
		}
		inventory.clear();
		world.getTileEntity(pos).markDirty();
	}
}
