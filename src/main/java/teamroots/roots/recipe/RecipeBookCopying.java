package teamroots.roots.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import teamroots.roots.RegistryManager;
import teamroots.roots.item.IHerb;
import teamroots.roots.item.ItemPestle;
import teamroots.roots.item.ItemPouch;

public class RecipeBookCopying implements IRecipe {
	ItemStack book = ItemStack.EMPTY;
	public RecipeBookCopying(ItemStack book){
		this.book = book;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		int bookCount = 0;
		int copyBookCount = 0;
		int inkCount = 0;
		int featherCount = 0;
		for (int i = 0; i < inv.getSizeInventory(); i ++){
			if (inv.getStackInSlot(i) != ItemStack.EMPTY){
				if (inv.getStackInSlot(i).getItem() instanceof ItemBook){
					bookCount ++;
				}
				else if (inv.getStackInSlot(i).getItem() == book.getItem()){
					copyBookCount ++;
				}
				else if (inv.getStackInSlot(i).getItem() instanceof ItemDye && inv.getStackInSlot(i).getItemDamage() == 0){
					inkCount ++;
				}
				else if (inv.getStackInSlot(i).getItem() == Items.FEATHER){
					featherCount ++;
				}
				else {
					return false;
				}
			}
		}
		return bookCount == 1 && copyBookCount == 1 && featherCount == 1 && inkCount == 1;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		for (int i = 0; i < inv.getSizeInventory(); i ++){
			if (inv.getStackInSlot(i) != ItemStack.EMPTY){
				if (inv.getStackInSlot(i).getItem() == book.getItem()){
					return inv.getStackInSlot(i).copy();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public int getRecipeSize() {
		return 4;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return book;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> remaining = NonNullList.create();
		for (int i = 0; i < inv.getSizeInventory(); i ++){
			if (inv.getStackInSlot(i) != ItemStack.EMPTY){
				if (inv.getStackInSlot(i).getItem() == book.getItem()){
					remaining.add(inv.getStackInSlot(i).copy());
				}
			}
		}
		inv.clear();
		return remaining;
	}

}
