package elucent.roots.ritual.rituals;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.item.ItemCastingBase;
import elucent.roots.item.ItemMeleeCastingBase;
import elucent.roots.ritual.RitualBase;
import elucent.roots.tileentity.TileEntityAltar;
import elucent.roots.tileentity.TileEntityBrazier;

public class RitualRecharge extends RitualBase {

	public RitualRecharge(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	public RitualRecharge(String name, double r, double g, double b, double r2, double g2, double b2) {
		super(name, r, g, b, r2, g2, b2);
	}
	
	public boolean matches(World world, BlockPos pos){
		if (positions.size() > 0){
			for (int i = 0; i < positions.size(); i ++){
				if (world.getBlockState(pos.add(positions.get(i).getX(),positions.get(i).getY(),positions.get(i).getZ())).getBlock() != blocks.get(i)){
					return false;
				}
			}
		}
		ArrayList<ItemStack> test = new ArrayList<ItemStack>();
		for (int i = -7; i < 8; i ++){
			for (int j = -7; j < 8; j ++){
				if (world.getBlockState(pos.add(i,0,j)).getBlock() == RegistryManager.brazier){
					if (world.getTileEntity(pos.add(i,0,j)) != null){
						TileEntityBrazier teb = (TileEntityBrazier)world.getTileEntity(pos.add(i,0,j));
						if (teb.burning){
							test.add(teb.heldItem);
						}
					}
				}
			}
		}
		ArrayList<ItemStack> stafflessInventory = new ArrayList<ItemStack>();
		for (int i = 0; i < ((TileEntityAltar)world.getTileEntity(pos)).inventory.size(); i ++){
			if (!(((TileEntityAltar)world.getTileEntity(pos)).inventory.get(i).getItem() instanceof ItemCastingBase) && !(((TileEntityAltar)world.getTileEntity(pos)).inventory.get(i).getItem() instanceof ItemMeleeCastingBase)){
				stafflessInventory.add(((TileEntityAltar)world.getTileEntity(pos)).inventory.get(i));
			}
		}
		return Util.itemListsMatch(incenses, test) && Util.itemListsMatchWithSize(ingredients,stafflessInventory);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		for (int i = 0; i < inventory.size(); i ++){
			if (inventory.get(i) != null){
				if (inventory.get(i).getItem() instanceof ItemCastingBase){
					System.out.println(inventory.get(i).getTagCompound().getInteger("numSlots"));
					for (int j = 0; j < inventory.get(i).getTagCompound().getInteger("numSlots"); j ++){
						inventory.get(i).getTagCompound().setInteger("uses"+(j+1), inventory.get(i).getTagCompound().getInteger("maxUses"+(j+1)));
					}
					if (!world.isRemote){
						world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5,inventory.get(i)));
					}
					inventory.clear();
					world.getTileEntity(pos).markDirty();
					world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 8);
					return;
				}
				else if (inventory.get(i).getItem() instanceof ItemMeleeCastingBase){
					for (int j = 0; j < inventory.get(i).getTagCompound().getInteger("numSlots"); j ++){
						inventory.get(i).getTagCompound().setInteger("uses"+(j+1), inventory.get(i).getTagCompound().getInteger("maxUses"+(j+1)));
					}
					if (!world.isRemote){
						world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5,inventory.get(i)));
					}
					inventory.clear();
					world.getTileEntity(pos).markDirty();
					world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 8);
				}
			}
		}
		inventory.clear();
		world.getTileEntity(pos).markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 8);
	}
}
