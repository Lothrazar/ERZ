package teamroots.roots.book;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import teamroots.roots.Constants;
import teamroots.roots.RegistryManager;
import teamroots.roots.item.ItemPetalDust;
import teamroots.roots.recipe.MoonlightRecipe;
import teamroots.roots.recipe.SpellRecipe;
import teamroots.roots.ritual.RitualBase;

public class Page {
	public static enum EnumPageType {
		TABLE_OF_CONTENTS, TEXT, DISPLAY, CRAFTING, MORTAR, MOONLIGHT, FURNACE, RITUAL
	}
	
	public EnumPageType type = EnumPageType.TEXT;
	public ItemStack[] mortarInputs;
	public ItemStack mortarOutput;
	public ItemStack[] moonlightInputs;
	public ItemStack moonlightOutput;
	public ItemStack[] craftingInputs;
	public ItemStack craftingOutput;
	public ItemStack displayStack;
	public ItemStack pageIcon = ItemStack.EMPTY;
	public ItemStack furnaceInput;
	public ItemStack furnaceOutput;
	public String name = "";
	public int[] connectedPages;
	public float reqKnowledge = 0;
	public boolean doesReqKnowledge = false;
	public ItemStack[] ritualInputs;
	
	public Page(ItemStack pageIcon, String pageName){
		this.pageIcon = pageIcon;
		this.name = pageName;
	}

	public Page(String pageName){
		this.name = pageName;
	}
	
	public Page setText(){
		this.type = EnumPageType.TEXT;
		return this;
	}
	
	public boolean enoughKnowledge(ItemStack stack){
		if (doesReqKnowledge){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().hasKey(Constants.KNOWLEDGE)){
					if (stack.getTagCompound().getFloat(Constants.KNOWLEDGE) < reqKnowledge){
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return true;
	}
	
	public Page setMortarRecipe(ItemStack[] inputs, ItemStack output){
		this.type = EnumPageType.MORTAR;
		this.mortarInputs = inputs;
		this.mortarOutput = output;
		return this;
	}
	
	public Page setKnowledgeReqs(float knowledge){
		this.reqKnowledge = knowledge;
		this.doesReqKnowledge = true;
		return this;
	}
	
	public boolean isValidForStack(ItemStack stack){
		return true;
	}
	
	public Page setFurnaceRecipe(ItemStack input, ItemStack output){
		this.type = EnumPageType.FURNACE;
		this.furnaceInput = input;
		this.furnaceOutput = output;
		return this;
	}
	
	public Page setMortarRecipe(SpellRecipe recipe){
		this.type = EnumPageType.MORTAR;
		this.mortarInputs = recipe.ingredients.toArray(new ItemStack[recipe.ingredients.size()]);
		this.mortarOutput = ItemPetalDust.createData(new ItemStack(RegistryManager.petal_dust,1),recipe.result);
		return this;
	}
	
	public Page setMoonlightRecipe(ItemStack[] inputs, ItemStack output){
		this.type = EnumPageType.MOONLIGHT;
		this.moonlightInputs = inputs;
		this.moonlightOutput = output;
		return this;
	}
	
	public Page setRitualRecipe(ItemStack[] inputs){
		this.type = EnumPageType.RITUAL;
		this.ritualInputs = inputs;
		return this;
	}
	
	public Page setRitualRecipe(RitualBase ritual){
		this.type = EnumPageType.RITUAL;
		this.ritualInputs = ritual.ingredients.toArray(new ItemStack[ritual.ingredients.size()]);
		return this;
	}
	
	public Page setMoonlightRecipe(MoonlightRecipe recipe){
		this.type = EnumPageType.MOONLIGHT;
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(new ItemStack(recipe.coreState.getBlock(),1,recipe.coreState.getBlock().getMetaFromState(recipe.coreState)));
		for (int i = 0; i < recipe.outerStates.size(); i ++){
			stacks.add(new ItemStack(recipe.outerStates.get(i).getBlock(),1));
		}
		this.moonlightInputs = stacks.toArray(new ItemStack[stacks.size()]);
		this.moonlightOutput = new ItemStack(recipe.resultState.getBlock(),1);
		return this;
	}
	
	public Page setCraftingRecipe(ItemStack[] inputs, ItemStack output){
		this.type = EnumPageType.CRAFTING;
		this.craftingInputs = inputs;
		this.craftingOutput = output;
		return this;
	}
	
	public Page setTableContents(){
		this.type = EnumPageType.TABLE_OF_CONTENTS;
		return this;
	}
	
	public Page setDisplay(ItemStack stack){
		this.type = EnumPageType.DISPLAY;
		this.displayStack = stack;
		return this;
	}
}
