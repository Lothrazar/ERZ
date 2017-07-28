package teamroots.roots.book;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import teamroots.roots.RegistryManager;
import teamroots.roots.item.ItemPetalDust;
import teamroots.roots.item.ItemPouch;
import teamroots.roots.recipe.RecipeRegistry;
import teamroots.roots.ritual.RitualRegistry;
import teamroots.roots.spell.SpellRegistry;

public class BookRegistry {
	public static Map<String, Book> books = new HashMap<String, Book>();
	
	public static Book herblore_book, spellcraft_book, ritual_book, forbidden_book;
	
	public static void init(){
		herblore_book = new Book("herblore_book");
		herblore_book.addPage(new Page("intro").setText());
		herblore_book.addPage(new Page("contents").setTableContents());
		//STRAW
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.straw,1),"straw").setFurnaceRecipe(new ItemStack(Blocks.TALLGRASS,1,1), new ItemStack(RegistryManager.straw,2)));
		//THATCH
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.thatch,1),"thatch").setCraftingRecipe(new ItemStack[]{
				new ItemStack(RegistryManager.straw,1), new ItemStack(RegistryManager.straw,1), ItemStack.EMPTY,
				new ItemStack(RegistryManager.straw,1), new ItemStack(RegistryManager.straw,1), ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY, ItemStack.EMPTY
		}, new ItemStack(RegistryManager.thatch,2)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.wood_shears,1),"wood_shears").setCraftingRecipe(new ItemStack[]{
				ItemStack.EMPTY,new ItemStack(Blocks.PLANKS,1),ItemStack.EMPTY,
				new ItemStack(Blocks.PLANKS,1),ItemStack.EMPTY,new ItemStack(Blocks.PLANKS,1),
				new ItemStack(Items.STICK,1),new ItemStack(Blocks.PLANKS,1),ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.wood_shears,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.wood_knife,1),"wood_knife").setCraftingRecipe(new ItemStack[]{
				ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(Blocks.PLANKS,1),
				ItemStack.EMPTY,new ItemStack(Blocks.PLANKS,1),ItemStack.EMPTY,
				new ItemStack(Items.STICK,1),ItemStack.EMPTY,ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.wood_knife,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.wood_hammer,1),"wood_hammer").setCraftingRecipe(new ItemStack[]{
				new ItemStack(Blocks.PLANKS,1),new ItemStack(Items.STICK,1),new ItemStack(Blocks.PLANKS,1),
				new ItemStack(Blocks.PLANKS,1),new ItemStack(Items.STICK,1),new ItemStack(Blocks.PLANKS,1),
				ItemStack.EMPTY,new ItemStack(Items.STICK,1),ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.wood_hammer,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.firestarter,1),"firestarter").setCraftingRecipe(new ItemStack[]{
				new ItemStack(Items.COAL,1,1),new ItemStack(Items.STICK,1),ItemStack.EMPTY,
				new ItemStack(Items.STICK,1),ItemStack.EMPTY,ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.firestarter,1)));
		herblore_book.addPage(new Page(new ItemStack(Blocks.MOSSY_COBBLESTONE,1),"moonlight_circle"));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.runestone,1),"runestone").setDisplay(new ItemStack(RegistryManager.runestone,1)));
		herblore_book.addPage(new Page("runestone_brick").setCraftingRecipe(new ItemStack[]{
				new ItemStack(RegistryManager.runestone,1),new ItemStack(RegistryManager.runestone,1),ItemStack.EMPTY,
				new ItemStack(RegistryManager.runestone,1),new ItemStack(RegistryManager.runestone,1),ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY
			}, new ItemStack(RegistryManager.runestone_brick,4)));
		herblore_book.addPage(new Page("chiseled_runestone").setCraftingRecipe(new ItemStack[]{
				new ItemStack(RegistryManager.runestone,1),new ItemStack(Items.FLINT,1),ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.chiseled_runestone,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.wildroot_item,1),"wildroot").setMoonlightRecipe(new ItemStack[]{
				new ItemStack(Items.POTATO,1),
				new ItemStack(Blocks.RED_FLOWER,1,0),
				new ItemStack(Blocks.RED_FLOWER,1,0),
				new ItemStack(Items.WHEAT,1),
				new ItemStack(Items.WHEAT,1)
			}, new ItemStack(RegistryManager.wildroot_item,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.moonglow_leaf,1),"moonglow").setMoonlightRecipe(new ItemStack[]{
			new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.WHITE_TULIP.getMeta()),
			new ItemStack(Blocks.SAPLING,1,0),
			new ItemStack(Blocks.SAPLING,1,2),
			new ItemStack(Blocks.LEAVES,1,0),
			new ItemStack(Blocks.LEAVES,1,2)
		}, new ItemStack(RegistryManager.moonglow_leaf,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.terra_moss_ball,1),"terra_moss").setMoonlightRecipe(new ItemStack[]{
				new ItemStack(Blocks.TALLGRASS,1,1),
				new ItemStack(Blocks.LEAVES,1,1),
				new ItemStack(Blocks.LEAVES,1,1),
				new ItemStack(Blocks.SAPLING,1,1),
				new ItemStack(Blocks.SAPLING,1,1)
			}, new ItemStack(RegistryManager.terra_moss_ball,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.pereskia_blossom,1),"pereskia_flower").setMoonlightRecipe(new ItemStack[]{
				new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.OXEYE_DAISY.getMeta()),
				new ItemStack(Blocks.DOUBLE_PLANT,1,5),
				new ItemStack(Blocks.YELLOW_FLOWER,1),
				new ItemStack(Blocks.YELLOW_FLOWER,1),
				new ItemStack(Blocks.YELLOW_FLOWER,1)
			}, new ItemStack(RegistryManager.pereskia_blossom,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.aubergine_item,1),"aubergine").setMoonlightRecipe(new ItemStack[]{
				new ItemStack(Items.BEETROOT,1),
				new ItemStack(Blocks.LEAVES,1,1),
				new ItemStack(Blocks.LEAVES,1,1),
				new ItemStack(Blocks.LEAVES,1,1),
				new ItemStack(Blocks.DOUBLE_PLANT,1,1)
			}, new ItemStack(RegistryManager.aubergine_item,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.spirit_herb_item,1),"spirit_herb").setMoonlightRecipe(new ItemStack[]{
				new ItemStack(Items.NETHER_WART,1),
				new ItemStack(Blocks.TALLGRASS,1,2),
				new ItemStack(Blocks.TALLGRASS,1,2),
				new ItemStack(Blocks.BROWN_MUSHROOM,1),
				new ItemStack(Blocks.RED_MUSHROOM,1)
			}, new ItemStack(RegistryManager.spirit_herb_item,1)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.dwindle_dust,1),"dwindle_dust").setCraftingRecipe(new ItemStack[]{
				new ItemStack(RegistryManager.wood_hammer,1),new ItemStack(Items.SKULL,1,1),ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.dwindle_dust,3)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.totem_fragment,1),"totem_fragment").setCraftingRecipe(new ItemStack[]{
				new ItemStack(RegistryManager.wood_hammer,1),new ItemStack(Items.TOTEM_OF_UNDYING,1),ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.totem_fragment,3)));
		herblore_book.addPage(new Page(new ItemStack(RegistryManager.bonfire,1),"pyre").setCraftingRecipe(new ItemStack[]{
				ItemStack.EMPTY,new ItemStack(Blocks.LOG,1),ItemStack.EMPTY,
				new ItemStack(Blocks.LOG,1),new ItemStack(RegistryManager.wildroot_item,1),new ItemStack(Blocks.LOG,1),
				new ItemStack(Blocks.COBBLESTONE,1),new ItemStack(Items.COAL,1,1),new ItemStack(Blocks.COBBLESTONE,1),
			}, new ItemStack(RegistryManager.bonfire,1)));
		herblore_book.addPage(new Page("pyre_info").setText());
		books.put("herblore_book", herblore_book);
		

		spellcraft_book = new Book("spellcraft_book");
		spellcraft_book.addPage(new Page("intro"));
		spellcraft_book.addPage(new Page("contents").setTableContents());
		spellcraft_book.addPage(new Page(new ItemStack(RegistryManager.mortar,1),"mortar").setCraftingRecipe(new ItemStack[]{
			new ItemStack(Blocks.COBBLESTONE,1),ItemStack.EMPTY,new ItemStack(Blocks.COBBLESTONE,1),
			new ItemStack(Blocks.COBBLESTONE,1),new ItemStack(Items.COAL,1,1),new ItemStack(Blocks.COBBLESTONE,1),
			ItemStack.EMPTY,new ItemStack(Blocks.COBBLESTONE,1),ItemStack.EMPTY
		}, new ItemStack(RegistryManager.mortar,1)));
		spellcraft_book.addPage(new Page("pestle").setCraftingRecipe(new ItemStack[]{
				ItemStack.EMPTY,ItemStack.EMPTY,new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata()),
				new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata()),new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata()),ItemStack.EMPTY,
				new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata()),new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata()),ItemStack.EMPTY,
			}, new ItemStack(RegistryManager.pestle,1)));
		spellcraft_book.addPage(new Page(new ItemStack(RegistryManager.pouch,1),"pouch").setCraftingRecipe(new ItemStack[]{
				new ItemStack(Items.STRING,1),ItemStack.EMPTY,new ItemStack(Items.STRING,1),
				new ItemStack(Blocks.WOOL,1),new ItemStack(Items.DYE,1,1),new ItemStack(Blocks.WOOL,1),
				ItemStack.EMPTY,new ItemStack(Blocks.WOOL,1),ItemStack.EMPTY
			}, new ItemStack(RegistryManager.pouch,1)));
		spellcraft_book.addPage(new Page("pouch_fill").setCraftingRecipe(new ItemStack[]{
				new ItemStack(RegistryManager.pestle,1),new ItemStack(RegistryManager.moonglow_leaf,1),ItemStack.EMPTY,
				new ItemStack(RegistryManager.pouch,1),ItemStack.EMPTY,ItemStack.EMPTY,
				ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY
			}, ItemPouch.createData(new ItemStack(RegistryManager.pouch,1),RegistryManager.moonglow_leaf.getUnlocalizedName(),1.0)));
		spellcraft_book.addPage(new Page(new ItemStack(RegistryManager.staff,1),"staff").setCraftingRecipe(new ItemStack[]{
				ItemStack.EMPTY,new ItemStack(RegistryManager.terra_moss_ball,1),new ItemStack(Blocks.LOG,1),
				ItemStack.EMPTY,new ItemStack(Items.STICK,1),new ItemStack(RegistryManager.terra_moss_ball,1),
				new ItemStack(Items.STICK,1),ItemStack.EMPTY,ItemStack.EMPTY
			}, new ItemStack(RegistryManager.staff,1)));
		spellcraft_book.addPage(new Page(new ItemStack(RegistryManager.imbuer,1),"imbuer").setCraftingRecipe(new ItemStack[]{
				new ItemStack(Items.STICK,1),ItemStack.EMPTY,new ItemStack(Items.STICK,1),
				ItemStack.EMPTY,new ItemStack(Blocks.STONE,1,0),ItemStack.EMPTY,
				new ItemStack(Items.STICK,1),ItemStack.EMPTY,new ItemStack(Items.STICK,1)
			}, new ItemStack(RegistryManager.imbuer,1)));
		spellcraft_book.addPage(new Page(new ItemStack(RegistryManager.petal_dust,1),"spellcraft").setDisplay(ItemPetalDust.createData(new ItemStack(RegistryManager.petal_dust,1), SpellRegistry.spell_allium.name)));
		spellcraft_book.addPage(new Page(new ItemStack(RegistryManager.wildroot_item,1),"casting"));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.ORANGE_TULIP.getMeta()),"orange_tulip")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_orange_tulip.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.YELLOW_FLOWER,1),"dandelion")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_dandelion.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.ALLIUM.getMeta()),"allium")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_allium.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.HOUSTONIA.getMeta()),"azure_bluet")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_azure_bluet.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.RED_TULIP.getMeta()),"red_tulip")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_red_tulip.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.DOUBLE_PLANT,1,EnumPlantType.ROSE.getMeta()),"rose")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_rose.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.BLUE_ORCHID.getMeta()),"blue_orchid")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_blue_orchid.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.DOUBLE_PLANT,1,EnumPlantType.SYRINGA.getMeta()),"lilac")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_lilac.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.DOUBLE_PLANT,1,EnumPlantType.PAEONIA.getMeta()),"peony")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_peony.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.POPPY.getMeta()),"poppy")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_poppy.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.PINK_TULIP.getMeta()),"pink_tulip")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_pink_tulip.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.WHITE_TULIP.getMeta()),"white_tulip")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_white_tulip.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.DOUBLE_PLANT,1,EnumPlantType.SUNFLOWER.getMeta()),"sunflower")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_sunflower.name)));
		spellcraft_book.addPage(new Page(new ItemStack(Blocks.RED_FLOWER,1,EnumFlowerType.OXEYE_DAISY.getMeta()),"oxeye_daisy")
				.setMortarRecipe(RecipeRegistry.getSpellRecipe(SpellRegistry.spell_oxeye_daisy.name)));
		books.put("spellcraft_book", spellcraft_book);
		
		ritual_book = new Book("ritual_book");
		ritual_book.addPage(new Page("intro").setText());
		ritual_book.addPage(new Page("contents").setTableContents());
		ritual_book.addPage(new Page(new ItemStack(Blocks.LOG,1,2),"fairy_pools").setText());
		ritual_book.addPage(new Page(new ItemStack(RegistryManager.fairy_dust,1),"fairy_dust").setDisplay(new ItemStack(RegistryManager.fairy_dust,1)));
		ritual_book.addPage(new Page(new ItemStack(RegistryManager.chiseled_runestone,1),"standing_stones").setText());
		ritual_book.addPage(new Page(new ItemStack(Items.WHEAT_SEEDS,1),"ritual_life").setRitualRecipe(RitualRegistry.ritual_life));
		ritual_book.addPage(new Page(new ItemStack(Items.WATER_BUCKET,1),"ritual_storm").setRitualRecipe(RitualRegistry.ritual_storm));
		ritual_book.addPage(new Page(new ItemStack(Items.GLOWSTONE_DUST,1),"ritual_light").setRitualRecipe(RitualRegistry.ritual_light));
		ritual_book.addPage(new Page(new ItemStack(Blocks.SAPLING,1,1),"ritual_regrowth").setRitualRecipe(RitualRegistry.ritual_regrowth));
		ritual_book.addPage(new Page(new ItemStack(Items.BLAZE_POWDER,1),"ritual_fire_storm").setRitualRecipe(RitualRegistry.ritual_fire_storm));
		ritual_book.addPage(new Page(new ItemStack(Items.FEATHER,1),"ritual_windwall").setRitualRecipe(RitualRegistry.ritual_windwall));
		ritual_book.addPage(new Page(new ItemStack(Items.LEATHER_CHESTPLATE,1),"ritual_warden").setRitualRecipe(RitualRegistry.ritual_warden));
		books.put("ritual_book", ritual_book);
	}
}
