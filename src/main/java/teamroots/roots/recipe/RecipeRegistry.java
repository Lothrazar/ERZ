package teamroots.roots.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import teamroots.roots.RegistryManager;
import teamroots.roots.block.BlockAubergine;
import teamroots.roots.block.BlockMoonglow;
import teamroots.roots.block.BlockPereskiaFlower;
import teamroots.roots.block.BlockSpiritHerb;

public class RecipeRegistry {
	public static ArrayList<MoonlightRecipe> moonlightRecipes = new ArrayList<MoonlightRecipe>();
	public static ArrayList<SpellRecipe> spellRecipes = new ArrayList<SpellRecipe>();
	public static ArrayList<MortarRecipe> mortarRecipes = new ArrayList<MortarRecipe>();
	public static ArrayList<LecternScribingRecipe> scribingRecipes = new ArrayList<LecternScribingRecipe>();
	
	public static MoonlightRecipe getMoonlightRecipe(IBlockState core, IBlockState outer1, IBlockState outer2, IBlockState outer3, IBlockState outer4){
		for (int i = 0; i < moonlightRecipes.size(); i ++){
			if (moonlightRecipes.get(i).matches(core, outer1, outer2, outer3, outer4)){
				return moonlightRecipes.get(i);
			}
		}
		return null;
	}

	public static MoonlightRecipe getMoonlightRecipe(IBlockState result){
		for (int i = 0; i < moonlightRecipes.size(); i ++){
			if (moonlightRecipes.get(i).resultState.getBlock() == result.getBlock() && moonlightRecipes.get(i).resultState.getBlock().getMetaFromState(moonlightRecipes.get(i).resultState) == result.getBlock().getMetaFromState(result)){
				return moonlightRecipes.get(i);
			}
		}
		return null;
	}
	
	public static SpellRecipe getSpellRecipe(List<ItemStack> items){
		for (int i = 0; i < spellRecipes.size(); i ++){
			if (spellRecipes.get(i).matches(items)){
				return spellRecipes.get(i);
			}
		}
		return null;
	}
	
	public static SpellRecipe getSpellRecipe(String spell){
		for (int i = 0; i < spellRecipes.size(); i ++){
			if (spellRecipes.get(i).result.compareTo(spell) == 0){
				return spellRecipes.get(i);
			}
		}
		return null;
	}
	
	public static MortarRecipe getMortarRecipe(List<ItemStack> items){
		for (int i = 0; i < mortarRecipes.size(); i ++){
			if (mortarRecipes.get(i).matches(items)){
				return mortarRecipes.get(i);
			}
		}
		return null;
	}
	
	public static LecternScribingRecipe getScribingRecipe(ItemStack book, ItemStack core){
		for (int i = 0; i < scribingRecipes.size(); i ++){
			if (scribingRecipes.get(i).matches(book, core)){
				return scribingRecipes.get(i);
			}
		}
		return null;
	}
	
	public static void init(){
		OreDictionary.registerOre("rootsHerb", RegistryManager.aubergine_seeds);
		OreDictionary.registerOre("rootsHerb", RegistryManager.moonglow_leaf);
		OreDictionary.registerOre("rootsHerb", RegistryManager.terra_moss_ball);
		OreDictionary.registerOre("rootsHerb", RegistryManager.pereskia_bulb);
		OreDictionary.registerOre("rootsHerb", RegistryManager.wildroot_item);
		OreDictionary.registerOre("rootsHerb", RegistryManager.pereskia_blossom);
		
		GameRegistry.addRecipe(new RecipePowderMash());
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.pestle,1),true,new Object[]{
				"  X",
				"XX ",
				"XX ",
				'X',new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata())
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.pestle,1),true,new Object[]{
				"X  ",
				" XX",
				" XX",
				'X',new ItemStack(Blocks.STONE,1,EnumType.DIORITE.getMetadata())
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.mortar,1),true,new Object[]{
				"X X",
				"XCX",
				" X ",
				'X',"cobblestone",
				'C',new ItemStack(Items.COAL,1,1)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.pouch,1),true,new Object[]{
				"S S",
				"LDL",
				" L ",
				'S',"string",
				'L',new ItemStack(Blocks.WOOL,1,OreDictionary.WILDCARD_VALUE),
				'D',new ItemStack(Items.DYE,1,OreDictionary.WILDCARD_VALUE)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.imbuer,1),true,new Object[]{
				"S S",
				" B ",
				"S S",
				'S',"stickWood",
				'B',new ItemStack(Blocks.STONE,1,0)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.staff,1),true,new Object[]{
				" HL",
				" SH",
				"S  ",
				'L',"logWood",
				'S',"stickWood",
				'H',"rootsHerb"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.staff,1),true,new Object[]{
				"LH ",
				"HS ",
				"  S",
				'L',"logWood",
				'S',"stickWood",
				'H',"rootsHerb"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.thatch,2),true,new Object[]{
				"SS",
				"SS",
				'S',RegistryManager.straw
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.wood_shears,1),true,new Object[]{
				" P ",
				"P P",
				"SP ",
				'S',"stickWood",
				'P',"plankWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.wood_knife,1),true,new Object[]{
				"  X",
				" X ",
				"S  ",
				'S',"stickWood",
				'X',"plankWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.stone_knife,1),true,new Object[]{
				"  X",
				" X ",
				"S  ",
				'S',"stickWood",
				'X',"cobblestone"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.iron_knife,1),true,new Object[]{
				"  X",
				" X ",
				"S  ",
				'S',"stickWood",
				'X',"ingotIron"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.gold_knife,1),true,new Object[]{
				"  X",
				" X ",
				"S  ",
				'S',"stickWood",
				'X',"ingotGold"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.diamond_knife,1),true,new Object[]{
				"  X",
				" X ",
				"S  ",
				'S',"stickWood",
				'X',"gemDiamond"
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.wood_hammer,1),true,new Object[]{
				"XSX",
				"XSX",
				" S ",
				'S',"stickWood",
				'X',"plankWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.stone_hammer,1),true,new Object[]{
				"XSX",
				"XSX",
				" S ",
				'S',"stickWood",
				'X',"cobblestone"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.iron_hammer,1),true,new Object[]{
				"XSX",
				"XSX",
				" S ",
				'S',"stickWood",
				'X',"ingotIron"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.gold_hammer,1),true,new Object[]{
				"XSX",
				"XSX",
				" S ",
				'S',"stickWood",
				'X',"ingotGold"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.diamond_hammer,1),true,new Object[]{
				"XSX",
				"XSX",
				" S ",
				'S',"stickWood",
				'X',"gemDiamond"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegistryManager.firestarter,1),new Object[]{
				"stickWood","stickWood",new ItemStack(Items.COAL,1,1)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.bonfire,1),true,new Object[]{
				" L ",
				"LWL",
				"SCS",
				'S',"cobblestone",
				'L',"logWood",
				'W',"rootsHerb",
				'C',new ItemStack(Items.COAL,1,1)
		}));
		GameRegistry.addRecipe(new RecipeHammerSmash(new ItemStack(Items.SKULL,1,1), new ItemStack(RegistryManager.dwindle_dust,4)));
		GameRegistry.addRecipe(new RecipeHammerSmash(new ItemStack(Items.TOTEM,1,1), new ItemStack(RegistryManager.totem_fragment,4)));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegistryManager.runestone_brick,4),true,new Object[]{
				"SS",
				"SS",
				'S',RegistryManager.runestone
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegistryManager.chiseled_runestone,1),new Object[]{
				new ItemStack(RegistryManager.runestone,1),new ItemStack(Items.FLINT,1)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegistryManager.book_base,1),new Object[]{
				new ItemStack(RegistryManager.bark_birch,1),new ItemStack(RegistryManager.bark_birch,1),new ItemStack(RegistryManager.bark_birch,1),new ItemStack(Items.LEATHER,1)
		}));
		
		RecipeSorter.register("recipe_book_copying", RecipeBookCopying.class, Category.SHAPELESS, "");
		RecipeSorter.register("recipe_powder_mash", RecipePowderMash.class, Category.SHAPELESS, "");
		RecipeSorter.register("recipe_pouch_clear", RecipePouchClear.class, Category.SHAPELESS, "");
		RecipeSorter.register("recipe_hammer_smash", RecipeHammerSmash.class, Category.SHAPELESS, "");
		
		GameRegistry.addRecipe(new RecipeBookCopying(new ItemStack(RegistryManager.herblore_book,1)));
		GameRegistry.addRecipe(new RecipeBookCopying(new ItemStack(RegistryManager.spellcraft_book,1)));
		
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(Blocks.TALLGRASS,1,1), new ItemStack(RegistryManager.straw,2), 1.0f);
		
		moonlightRecipes.add(new MoonlightRecipe(Blocks.RED_FLOWER.getStateFromMeta(6), new MoonlightRecipe.ConditionMetaMatch(),
				Blocks.SAPLING.getStateFromMeta(2), new MoonlightRecipe.ConditionPropertyMatch(BlockSapling.TYPE),
				Blocks.LEAVES.getStateFromMeta(2), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT),
				Blocks.LEAVES.getDefaultState(), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT),
				Blocks.SAPLING.getDefaultState(), new MoonlightRecipe.ConditionPropertyMatch(BlockSapling.TYPE), 
				RegistryManager.moonglow.getDefaultState().withProperty(BlockMoonglow.AGE, 7)));
		moonlightRecipes.add(new MoonlightRecipe(Blocks.TALLGRASS.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockTallGrass.TYPE), 
				Blocks.SAPLING.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockSapling.TYPE), 
				Blocks.LEAVES.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT), 
				Blocks.LEAVES.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT), 
				Blocks.SAPLING.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockSapling.TYPE), 
				RegistryManager.terra_moss.getDefaultState().withProperty(BlockMoonglow.AGE, 7)));
		moonlightRecipes.add(new MoonlightRecipe(Blocks.POTATOES.getDefaultState().withProperty(BlockCrops.AGE, 7), new MoonlightRecipe.ConditionPropertyMatch(BlockCrops.AGE), 
				Blocks.RED_FLOWER.getDefaultState(), new MoonlightRecipe.ConditionMetaMatch(), 
				Blocks.RED_FLOWER.getDefaultState(), new MoonlightRecipe.ConditionMetaMatch(), 
				Blocks.WHEAT.getStateFromMeta(7), new MoonlightRecipe.ConditionPropertyMatch(BlockCrops.AGE), 
				Blocks.WHEAT.getStateFromMeta(7), new MoonlightRecipe.ConditionPropertyMatch(BlockCrops.AGE), 
				RegistryManager.wildroot.getDefaultState().withProperty(BlockMoonglow.AGE, 7)));
		moonlightRecipes.add(new MoonlightRecipe(Blocks.RED_FLOWER.getStateFromMeta(EnumFlowerType.OXEYE_DAISY.getMeta()), new MoonlightRecipe.ConditionMetaMatch(),
				Blocks.DOUBLE_PLANT.getStateFromMeta(5), new MoonlightRecipe.ConditionPropertyMatch(BlockDoublePlant.VARIANT),
				Blocks.YELLOW_FLOWER.getDefaultState(), new MoonlightRecipe.ConditionAlways(), 
				Blocks.YELLOW_FLOWER.getDefaultState(), new MoonlightRecipe.ConditionAlways(), 
				Blocks.YELLOW_FLOWER.getDefaultState(), new MoonlightRecipe.ConditionAlways(),
				RegistryManager.pereskia.getDefaultState().withProperty(BlockPereskiaFlower.AGE, 7)));
		moonlightRecipes.add(new MoonlightRecipe(Blocks.BEETROOTS.getDefaultState().withProperty(BlockBeetroot.BEETROOT_AGE, 3), new MoonlightRecipe.ConditionPropertyMatch(BlockBeetroot.BEETROOT_AGE), 
				Blocks.DOUBLE_PLANT.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockDoublePlant.VARIANT), 
				Blocks.LEAVES.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT), 
				Blocks.LEAVES.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT), 
				Blocks.LEAVES.getStateFromMeta(1), new MoonlightRecipe.ConditionPropertyMatch(BlockOldLog.VARIANT), 
				RegistryManager.aubergine.getDefaultState().withProperty(BlockAubergine.AGE, 7)));
		moonlightRecipes.add(new MoonlightRecipe(Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, 3), new MoonlightRecipe.ConditionPropertyMatch(BlockNetherWart.AGE), 
				Blocks.TALLGRASS.getStateFromMeta(2), new MoonlightRecipe.ConditionPropertyMatch(BlockTallGrass.TYPE), 
				Blocks.TALLGRASS.getStateFromMeta(2), new MoonlightRecipe.ConditionPropertyMatch(BlockTallGrass.TYPE), 
				Blocks.BROWN_MUSHROOM.getDefaultState(), new MoonlightRecipe.ConditionAlways(), 
				Blocks.RED_MUSHROOM.getDefaultState(), new MoonlightRecipe.ConditionAlways(), 
				RegistryManager.spirit_herb.getDefaultState().withProperty(BlockSpiritHerb.AGE, 7)));
		
		spellRecipes.add((new SpellRecipe("spell_orange_tulip")).addIngredient(new ItemStack(Items.DYE,1,14)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,5)).addIngredient(new ItemStack(Items.GUNPOWDER,1)).addIngredient(new ItemStack(Items.COAL,1,1)).addIngredient(new ItemStack(RegistryManager.wildroot_item,1)));
		spellRecipes.add((new SpellRecipe("spell_red_tulip")).addIngredient(new ItemStack(Items.DYE,1,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,4)).addIngredient(new ItemStack(Blocks.VINE,1)).addIngredient(new ItemStack(RegistryManager.moonglow_leaf,1)).addIngredient(new ItemStack(RegistryManager.wildroot_item,1)));
		spellRecipes.add((new SpellRecipe("spell_dandelion")).addIngredient(new ItemStack(Items.FEATHER,1)).addIngredient(new ItemStack(Blocks.YELLOW_FLOWER,1)).addIngredient(new ItemStack(Items.SNOWBALL,1)).addIngredient(new ItemStack(RegistryManager.moonglow_leaf,1)).addIngredient(new ItemStack(Items.WHEAT,1)));
		spellRecipes.add((new SpellRecipe("spell_rose")).addIngredient(new ItemStack(Blocks.CACTUS,1)).addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,4)).addIngredient(new ItemStack(Items.BONE,1)).addIngredient(new ItemStack(Items.FERMENTED_SPIDER_EYE,1)).addIngredient(new ItemStack(RegistryManager.terra_moss_ball,1)));
		spellRecipes.add((new SpellRecipe("spell_azure_bluet")).addIngredient(new ItemStack(Items.FLINT,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,3)).addIngredient(new ItemStack(Items.DYE,1,15)).addIngredient(new ItemStack(RegistryManager.terra_moss_ball,1)).addIngredient(new ItemStack(RegistryManager.wildroot_item,1)));
		spellRecipes.add((new SpellRecipe("spell_peony")).addIngredient(new ItemStack(Items.MELON_SEEDS,1)).addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,5)).addIngredient(new ItemStack(Items.DYE,1,9)).addIngredient(new ItemStack(RegistryManager.moonglow_leaf,1)).addIngredient(new ItemStack(RegistryManager.pereskia_bulb,1)));
		spellRecipes.add((new SpellRecipe("spell_allium")).addIngredient(new ItemStack(Items.SPIDER_EYE,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,2)).addIngredient(new ItemStack(Items.SLIME_BALL,1)).addIngredient(new ItemStack(RegistryManager.terra_moss_ball,1)).addIngredient(new ItemStack(RegistryManager.wildroot_item,1)));
		spellRecipes.add((new SpellRecipe("spell_white_tulip")).addIngredient(new ItemStack(Items.ENDER_PEARL,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,6)).addIngredient(new ItemStack(Items.STRING,1)).addIngredient(new ItemStack(RegistryManager.moonglow_leaf,1)).addIngredient(new ItemStack(RegistryManager.pereskia_blossom,1)));
		spellRecipes.add((new SpellRecipe("spell_oxeye_daisy")).addIngredient(new ItemStack(Items.NETHER_WART,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,8)).addIngredient(new ItemStack(Items.DYE,1,0)).addIngredient(new ItemStack(RegistryManager.pereskia_blossom,1)).addIngredient(new ItemStack(RegistryManager.aubergine_seeds,1)));
		spellRecipes.add((new SpellRecipe("spell_sunflower")).addIngredient(new ItemStack(Items.GLOWSTONE_DUST,1)).addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,0)).addIngredient(new ItemStack(Items.DYE,1,11)).addIngredient(new ItemStack(RegistryManager.pereskia_blossom,1)).addIngredient(new ItemStack(RegistryManager.wildroot_item,1)));
		spellRecipes.add((new SpellRecipe("spell_pink_tulip")).addIngredient(new ItemStack(Items.BEETROOT,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,7)).addIngredient(new ItemStack(Items.BEETROOT_SEEDS,1)).addIngredient(new ItemStack(Items.ROTTEN_FLESH,1)).addIngredient(new ItemStack(RegistryManager.pereskia_bulb,1)));
		spellRecipes.add((new SpellRecipe("spell_lilac")).addIngredient(new ItemStack(Blocks.SAPLING,1,2)).addIngredient(new ItemStack(Blocks.DOUBLE_PLANT,1,1)).addIngredient(new ItemStack(Blocks.SAPLING,1,1)).addIngredient(new ItemStack(RegistryManager.terra_moss_ball,1)).addIngredient(new ItemStack(RegistryManager.pereskia_blossom,1)));
		spellRecipes.add((new SpellRecipe("spell_blue_orchid")).addIngredient(new ItemStack(Items.RABBIT_FOOT,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,1)).addIngredient(new ItemStack(Items.SUGAR,1)).addIngredient(new ItemStack(RegistryManager.aubergine_seeds,1)).addIngredient(new ItemStack(RegistryManager.moonglow_leaf,1)));
		spellRecipes.add((new SpellRecipe("spell_poppy")).addIngredient(new ItemStack(Blocks.BROWN_MUSHROOM,1)).addIngredient(new ItemStack(Blocks.RED_FLOWER,1,0)).addIngredient(new ItemStack(Blocks.RED_MUSHROOM,1)).addIngredient(new ItemStack(RegistryManager.aubergine_seeds,1)).addIngredient(new ItemStack(Items.DYE,1,3)));
	}
}
