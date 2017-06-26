package teamroots.roots.recipe;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import teamroots.roots.RegistryManager;
import teamroots.roots.item.IHerb;
import teamroots.roots.item.ItemPestle;
import teamroots.roots.item.ItemPouch;

public class RecipePouchClear implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		int pouchCount = 0;
		for (int i = 0; i < inv.getSizeInventory(); i ++){
			if (inv.getStackInSlot(i) != ItemStack.EMPTY){
				if (inv.getStackInSlot(i).getItem() instanceof ItemPouch){
					pouchCount ++;
				}
				else {
					return false;
				}
			}
		}
		return pouchCount == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return new ItemStack(RegistryManager.pouch,1);
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(RegistryManager.pouch,1);
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> remaining = NonNullList.create();
		inv.clear();
		return remaining;
	}

}
