package elucent.roots;

import elucent.roots.block.*;
import elucent.roots.dimension.OtherworldProvider;
import elucent.roots.entity.*;
import elucent.roots.entity.projectile.EntityRitualProjectile;
import elucent.roots.item.*;
import elucent.roots.item.block.ItemBlockSlab;
import elucent.roots.model.ModelHolder;
import elucent.roots.render.RitualProjectileRenderFactory;
import elucent.roots.tileentity.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class RegistryManager {
	public static Item projectileStaff, pixieStone, ancientHourglass, amuletConserving, amuletDischarge, talismanHunger, talismanPursuit, spellweaverLance, otherworldLeaf, otherworldSubstance, debugWand, rootyStew, healingPoultice, growthSalve, runedTablet, druidArmorHead, druidArmorChest, druidArmorLegs, druidArmorBoots, druidRobesHead, druidRobesChest, druidRobesLegs, druidRobesBoots, livingPickaxe, livingSword, livingHoe, livingAxe, livingShovel, dustPetal, pestle, staff, oldRoot, crystalStaff, verdantSprig, infernalStem, dragonsEye,druidKnife,oakTreeBark,spruceTreeBark,birchTreeBark,jungleTreeBark,acaciaTreeBark,darkOakTreeBark,itemCharmRestoration,itemCharmEvocation,itemCharmConjuration ,itemCharmIllusion;
	public static Item spritelingIcon, spriteIcon, greaterSpriteIcon, manaResearchIcon;
	public static ItemBlock itemBlockSpiritBlockSlab, itemBlockSpiritBrickSlab, itemBlockPlankWildwoodSlab, itemBlockRuneStoneSlab, itemBlockRuneStoneBrickSlab, itemBlockRuneStoneTileSlab;
	public static Block leavesElderpine, leavesAzurepine, needleGrass, glowSand, leafyGrass, leavesWildwood, otherworldDirt, otherworldGrass, spiritConduit, spiritFont, standingStoneGrower, standingStoneHealer, standingStoneIgniter, standingStoneEntangler, standingStoneAccelerator, standingStoneAesthetic, standingStoneRepulsor, standingStoneVacuum, mortar, imbuer, altar, druidChalice, standingStoneT1, standingStoneT2, brazier;
	public static Block bridge, spiritBlock, spiritBlockSlab, spiritBlockSlabDouble, spiritBlockStairs, spiritBrick, spiritBrickSlab, spiritBrickSlabDouble, spiritBrickStairs, runeStoneStairs, runeStoneBrickStairs, runeStoneTileStairs, plankWildwoodStairs, barkWildwood, barkWildwoodSymbolGlowing, barkWildwoodSymbol, logWildwood, logWildwoodSymbol, logWildwoodSymbolGlowing, plankWildwood, plankWildwoodSlab, plankWildwoodSlabDouble, runeStone, runeStoneBrick, runeStoneSlabDouble, runeStoneSlab, runeStoneBrickSlab, runeStoneBrickSlabDouble, runeStoneTile, runeStoneTileSlab, runeStoneTileSlabDouble, runeStoneSymbol, runeStoneSymbolGlowing;

	public static Achievement achieveArrowBlock, achieveIllusion, achieveGuardianBoss, achieveSpriteling, achieveHourglass, achieveDischarge, achieveDust, achieveTablet, achieveSpellRose, achieveSpellGrowth, achieveSpellInsanity, achieveMaxModifiers, achieveLotsDamage, achieveTimeStop, achieveAltar, achieveStandingStone, achieveWildwood, achieveShadow, achieveSpellElements, achieveVampire;
	
	public static ToolMaterial livingMaterial = EnumHelper.addToolMaterial("livingMaterial", 2, 192, 6.0f, 2.0f, 18);
	public static ArmorMaterial druidRobesMaterial = EnumHelper.addArmorMaterial("druidRobes", "roots:druidRobes", 10, new int[]{1,5,6,2}, 20, null, 0);
	public static ArmorMaterial druidArmorMaterial = EnumHelper.addArmorMaterial("druidArmor", "roots:druidArmor", 15, new int[]{2,5,7,3}, 10, null, 1.0f);
	
	public static DimensionType dimOtherworld = DimensionType.register("otherworld", "", ConfigManager.otherworldDimensionID, OtherworldProvider.class, false);
	public static BiomeProperties biomeOtherworldProps;
	public static Biome biomeOtherworld;
	
	public static void init(){
		
		/**
		 * REGISTERING ITEMS
		 */
		GameRegistry.registerItem(druidKnife = new ItemDruidKnife(), "druidKnife");
		GameRegistry.registerItem(dustPetal = new DustPetal(), "dustPetal");
		GameRegistry.registerItem(debugWand = new ItemDebugWand(), "debugWand");
		GameRegistry.registerItem(pestle = new ItemPestle(), "pestle");
		GameRegistry.registerItem(staff = new ItemStaff(), "staff");
		GameRegistry.registerItem(crystalStaff = new ItemCrystalStaff(), "crystalStaff");
		GameRegistry.registerItem(oldRoot = new RootsItemFood("oldRoot",1,0.1F,false), "oldRoot");
		GameRegistry.registerItem(verdantSprig = new ItemMaterial("verdantSprig"), "verdantSprig");
		GameRegistry.registerItem(infernalStem = new ItemMaterial("infernalStem"), "infernalStem");
		GameRegistry.registerItem(dragonsEye = new ItemDragonsEye("dragonsEye", 2, 0.1F, false), "dragonsEye");
		GameRegistry.registerItem(oakTreeBark = new ItemTreeBark("oakTreeBark"),"oakTreeBark");
		GameRegistry.registerItem(spruceTreeBark = new ItemTreeBark("spruceTreeBark"),"spruceTreeBark");
		GameRegistry.registerItem(birchTreeBark = new ItemTreeBark("birchTreeBark"),"birchTreeBark");
		GameRegistry.registerItem(jungleTreeBark = new ItemTreeBark("jungleTreeBark"),"jungleTreeBark");
		GameRegistry.registerItem(acaciaTreeBark = new ItemTreeBark("acaciaTreeBark"),"acaciaTreeBark");
		GameRegistry.registerItem(darkOakTreeBark = new ItemTreeBark("darkOakTreeBark"),"darkOakTreeBark");
		GameRegistry.registerItem(livingPickaxe = new ItemLivingPickaxe(),"livingPickaxe");
		GameRegistry.registerItem(livingAxe = new ItemLivingAxe(),"livingAxe");
		GameRegistry.registerItem(livingSword = new ItemLivingSword(),"livingSword");
		GameRegistry.registerItem(livingHoe = new ItemLivingHoe(),"livingHoe");
		GameRegistry.registerItem(livingShovel = new ItemLivingShovel(),"livingShovel");
		GameRegistry.registerItem(druidRobesHead = new ItemDruidRobes(2, EntityEquipmentSlot.HEAD),"druidRobesHead");
		GameRegistry.registerItem(druidRobesChest = new ItemDruidRobes(6, EntityEquipmentSlot.CHEST),"druidRobesChest");
		GameRegistry.registerItem(druidRobesLegs = new ItemDruidRobes(5, EntityEquipmentSlot.LEGS),"druidRobesLegs");
		GameRegistry.registerItem(druidRobesBoots = new ItemDruidRobes(1, EntityEquipmentSlot.FEET),"druidRobesBoots");
		GameRegistry.registerItem(druidArmorHead = new ItemDruidArmor(3, EntityEquipmentSlot.HEAD),"druidArmorHead");
		GameRegistry.registerItem(druidArmorChest = new ItemDruidArmor(7, EntityEquipmentSlot.CHEST),"druidArmorChest");
		GameRegistry.registerItem(druidArmorLegs = new ItemDruidArmor(6, EntityEquipmentSlot.LEGS),"druidArmorLegs");
		GameRegistry.registerItem(druidArmorBoots = new ItemDruidArmor(2, EntityEquipmentSlot.FEET),"druidArmorBoots");
		GameRegistry.registerItem(runedTablet = new ItemRunedTablet(),"runedTablet");
		GameRegistry.registerItem(growthSalve = new ItemGrowthSalve(),"growthSalve");
		GameRegistry.registerItem(healingPoultice = new RootsItemFood("healingPoultice", 0, 0F, false).setAlwaysEdible().setMaxStackSize(8), "healingPoultice"); 
		GameRegistry.registerItem(rootyStew = new ItemRootyStew(), "rootyStew"); 
		GameRegistry.registerItem(manaResearchIcon = new ItemResearchIcon("manaResearchIcon"), "manaResearchIcon");
		GameRegistry.registerItem(itemCharmRestoration = new ItemCharmRestoration("charmRestoration"),"charmRestoration");
		GameRegistry.registerItem(itemCharmEvocation = new ItemCharmEvocation("charmEvocation"),"charmEvocation");
		GameRegistry.registerItem(itemCharmConjuration = new ItemCharmConjuration("charmConjuration"),"charmConjuration");
		GameRegistry.registerItem(itemCharmIllusion = new ItemCharmIllusion("charmIllusion"),"charmIllusion");
		GameRegistry.registerItem(otherworldLeaf = new ItemMaterial("otherworldLeaf"),"otherworldLeaf");
		GameRegistry.registerItem(otherworldSubstance = new ItemMaterial("otherworldSubstance"),"otherworldSubstance");
		GameRegistry.registerItem(spellweaverLance = new ItemSpellweaverLance(),"spellweaverLance");
		GameRegistry.registerItem(talismanHunger = new ItemHungerTalisman(),"talismanHunger");
		GameRegistry.registerItem(talismanPursuit = new ItemPursuitTalisman(),"talismanPursuit");
		GameRegistry.registerItem(amuletDischarge = new ItemDischargeAmulet(),"amuletDischarge");
		GameRegistry.registerItem(amuletConserving = new ItemConservingAmulet(),"amuletConserving");
		GameRegistry.registerItem(ancientHourglass = new ItemAncientHourglass(),"ancientHourglass");
		GameRegistry.registerItem(pixieStone = new ItemPixieStone(),"pixieStone");
		GameRegistry.registerItem(projectileStaff = new ItemProjectileStaff(), "projectileStaff");
		GameRegistry.registerItem(spritelingIcon = new ItemResearchIcon("spritelingIcon"), "spritelingIcon");
		GameRegistry.registerItem(spriteIcon = new ItemResearchIcon("spriteIcon"), "spriteIcon");
		GameRegistry.registerItem(greaterSpriteIcon = new ItemResearchIcon("greaterSpriteIcon"), "greaterSpriteIcon");
		
		/**
		 * REGISTERING BLOCKS
		 */
		GameRegistry.registerBlock(mortar = new BlockMortar(), "mortar");
		GameRegistry.registerBlock(altar = new BlockAltar(), "altar");
		GameRegistry.registerBlock(brazier = new BlockBrazier(), "brazier");
		GameRegistry.registerBlock(imbuer = new BlockImbuer(), "imbuer");
		//GameRegistry.registerBlock(druidChalice = new BlockDruidChalice(),"druidChalice");
		GameRegistry.registerBlock(standingStoneT1 = new BlockStandingStoneT1(),"standingStoneT1");
		GameRegistry.registerBlock(standingStoneT2 = new BlockStandingStoneT2(),"standingStoneT2");
		GameRegistry.registerBlock(standingStoneVacuum = new BlockStandingStoneVacuum(),"standingStoneVacuum");
		GameRegistry.registerBlock(standingStoneRepulsor = new BlockStandingStoneRepulsor(),"standingStoneRepulsor");
		GameRegistry.registerBlock(standingStoneAccelerator = new BlockStandingStoneAccelerator(),"standingStoneAccelerator");
		GameRegistry.registerBlock(standingStoneAesthetic = new BlockAestheticStandingStone(),"standingStoneAesthetic");
		GameRegistry.registerBlock(standingStoneEntangler = new BlockStandingStoneEntangler(),"standingStoneEntangler");
		GameRegistry.registerBlock(standingStoneIgniter = new BlockStandingStoneIgniter(),"standingStoneIgniter");
		GameRegistry.registerBlock(standingStoneGrower = new BlockStandingStoneGrower(),"standingStoneGrower");
		GameRegistry.registerBlock(standingStoneHealer = new BlockStandingStoneHealer(),"standingStoneHealer");
		GameRegistry.registerBlock(bridge = new BlockBridge(),"bridgeBlock");
		GameRegistry.registerBlock(runeStone = new BlockBase("runeStone",Material.ROCK,SoundType.STONE,1.0f));
		GameRegistry.registerBlock(runeStoneStairs = new BlockStairsBase("runeStoneStairs",runeStone.getDefaultState(),SoundType.STONE,1.0f));

		GameRegistry.registerBlock(runeStoneSlabDouble = new BlockDoubleSlabBase("runeStoneSlabDouble",Material.ROCK,SoundType.STONE,1.0f,"pickaxe",0,runeStoneSlab));
		GameRegistry.registerBlock(runeStoneSlab = new BlockSlabBase("runeStoneSlab",Material.ROCK,SoundType.STONE,1.0f,"pickaxe",0,runeStoneSlabDouble));
		((BlockDoubleSlabBase)runeStoneSlabDouble).setSlab(runeStoneSlab);
		GameRegistry.registerItem(itemBlockRuneStoneSlab = new ItemBlockSlab(runeStoneSlab, (BlockDoubleSlabBase)runeStoneSlabDouble),runeStoneSlab.getRegistryName().toString()+"Item");
		
		GameRegistry.registerBlock(runeStoneBrick = new BlockBase("runeStoneBrick",Material.ROCK,SoundType.STONE,1.0f));
		GameRegistry.registerBlock(runeStoneBrickStairs = new BlockStairsBase("runeStoneBrickStairs",runeStoneBrick.getDefaultState(),SoundType.STONE,1.0f));
		GameRegistry.registerBlock(runeStoneBrickSlabDouble = new BlockDoubleSlabBase("runeStoneBrickSlabDouble",Material.ROCK,SoundType.STONE,1.0f,"pickaxe",0,runeStoneBrickSlab));
		GameRegistry.registerBlock(runeStoneBrickSlab = new BlockSlabBase("runeStoneBrickSlab",Material.ROCK,SoundType.STONE,1.0f,"pickaxe",0,runeStoneBrickSlabDouble));
		((BlockDoubleSlabBase)runeStoneBrickSlabDouble).setSlab(runeStoneBrickSlab);
		GameRegistry.registerItem(itemBlockRuneStoneBrickSlab = new ItemBlockSlab(runeStoneBrickSlab, (BlockDoubleSlabBase)runeStoneBrickSlabDouble),runeStoneBrickSlab.getRegistryName().toString()+"Item");
		
		GameRegistry.registerBlock(runeStoneTile = new BlockBase("runeStoneTile",Material.ROCK,SoundType.STONE,1.0f));
		GameRegistry.registerBlock(runeStoneTileStairs = new BlockStairsBase("runeStoneTileStairs",runeStoneTile.getDefaultState(),SoundType.STONE,1.0f));
		GameRegistry.registerBlock(runeStoneTileSlabDouble = new BlockDoubleSlabBase("runeStoneTileSlabDouble",Material.ROCK,SoundType.STONE,1.0f,"pickaxe",0,runeStoneTileSlab));
		GameRegistry.registerBlock(runeStoneTileSlab = new BlockSlabBase("runeStoneTileSlab",Material.ROCK,SoundType.STONE,1.0f,"pickaxe",0,runeStoneTileSlabDouble));
		((BlockDoubleSlabBase)runeStoneTileSlabDouble).setSlab(runeStoneTileSlab);
		GameRegistry.registerItem(itemBlockRuneStoneTileSlab = new ItemBlockSlab(runeStoneTileSlab, (BlockDoubleSlabBase)runeStoneTileSlabDouble),runeStoneTileSlab.getRegistryName().toString()+"Item");
		
		GameRegistry.registerBlock(runeStoneSymbol = new BlockBase("runeStoneSymbol",Material.ROCK,SoundType.STONE,1.0f));
		GameRegistry.registerBlock(runeStoneSymbolGlowing = new BlockBase("runeStoneSymbolGlowing",Material.ROCK,SoundType.STONE,1.0f).setLightLevel(1.0f));
		GameRegistry.registerBlock(plankWildwood = new BlockBase("plankWildwood",Material.WOOD,SoundType.WOOD,1.0f));
		GameRegistry.registerBlock(plankWildwoodStairs = new BlockStairsBase("plankWildwoodStairs",plankWildwood.getDefaultState(),SoundType.WOOD,1.0f));

		GameRegistry.registerBlock(plankWildwoodSlabDouble = new BlockDoubleSlabBase("plankWildwoodSlabDouble",Material.WOOD,SoundType.WOOD,1.0f,"axe",0,plankWildwoodSlab));
		GameRegistry.registerBlock(plankWildwoodSlab = new BlockSlabBase("plankWildwoodSlab",Material.WOOD,SoundType.WOOD,1.0f,"axe",0,plankWildwoodSlabDouble));
		((BlockDoubleSlabBase)plankWildwoodSlabDouble).setSlab(plankWildwoodSlab);
		GameRegistry.registerItem(itemBlockPlankWildwoodSlab = new ItemBlockSlab(plankWildwoodSlab, (BlockDoubleSlabBase)plankWildwoodSlabDouble),plankWildwoodSlab.getRegistryName().toString()+"Item");
		
		GameRegistry.registerBlock(logWildwood = new BlockLogBase("logWildwood",1.0f,"axe",0));
		GameRegistry.registerBlock(logWildwoodSymbol = new BlockLogBase("logWildwoodSymbol",1.0f,"axe",0));
		GameRegistry.registerBlock(logWildwoodSymbolGlowing = new BlockLogBase("logWildwoodSymbolGlowing",1.0f,"axe",0).setLightLevel(1.0f));
		GameRegistry.registerBlock(barkWildwood = new BlockBase("barkWildwood",Material.WOOD,SoundType.WOOD,1.0f));
		GameRegistry.registerBlock(barkWildwoodSymbol = new BlockBase("barkWildwoodSymbol",Material.WOOD,SoundType.WOOD,1.0f));
		GameRegistry.registerBlock(barkWildwoodSymbolGlowing = new BlockBase("barkWildwoodSymbolGlowing",Material.WOOD,SoundType.WOOD,1.0f).setLightLevel(1.0f));
		
		GameRegistry.registerBlock(spiritBlock = new BlockSpirit("spiritBlock",Material.GLASS,1.0f));
		GameRegistry.registerBlock(spiritBlockStairs = new BlockSpiritStairs("spiritBlockStairs",spiritBlock.getDefaultState(),1.0f));
		GameRegistry.registerBlock(spiritBlockSlabDouble = new BlockSpiritDoubleSlab("spiritBlockSlabDouble",Material.GLASS,1.0f,"null",0,spiritBlockSlab));
		GameRegistry.registerBlock(spiritBlockSlab = new BlockSpiritSlab("spiritBlockSlab",Material.GLASS,1.0f,"null",0,spiritBlockSlabDouble));
		((BlockSpiritDoubleSlab)spiritBlockSlabDouble).setSlab(spiritBlockSlab);
		GameRegistry.registerItem(itemBlockSpiritBlockSlab = new ItemBlockSlab(spiritBlockSlab, (BlockSpiritDoubleSlab)spiritBlockSlabDouble),spiritBlockSlab.getRegistryName().toString()+"Item");
		
		GameRegistry.registerBlock(spiritBrick = new BlockSpirit("spiritBrick",Material.GLASS,1.0f));
		GameRegistry.registerBlock(spiritBrickStairs = new BlockSpiritStairs("spiritBrickStairs",spiritBrick.getDefaultState(),1.0f));
		GameRegistry.registerBlock(spiritBrickSlabDouble = new BlockSpiritDoubleSlab("spiritBrickSlabDouble",Material.GLASS,1.0f,"null",0,spiritBrickSlab));
		GameRegistry.registerBlock(spiritBrickSlab = new BlockSpiritSlab("spiritBrickSlab",Material.GLASS,1.0f,"null",0,spiritBrickSlabDouble));
		((BlockSpiritDoubleSlab)spiritBrickSlabDouble).setSlab(spiritBrickSlab);
		GameRegistry.registerItem(itemBlockSpiritBrickSlab = new ItemBlockSlab(spiritBrickSlab, (BlockSpiritDoubleSlab)spiritBrickSlabDouble),spiritBrickSlab.getRegistryName().toString()+"Item");
		
		GameRegistry.registerBlock(spiritFont = new BlockSpiritFont(),"spiritFont");
		GameRegistry.registerBlock(spiritConduit = new BlockSpiritConduit(),"spiritConduit");
		
		GameRegistry.registerBlock(otherworldDirt = new BlockDirtBase("otherworldDirt",Material.GROUND,1.0f));
		GameRegistry.registerBlock(otherworldGrass = new BlockGrassBase("otherworldGrass",otherworldDirt,Material.GROUND,1.0f));
		GameRegistry.registerBlock(leavesWildwood = new BlockRootsLeaves("leavesWildwood",Material.LEAVES,0.2f));
		GameRegistry.registerBlock(leafyGrass = new BlockShrubBase("leafyGrass",0.2f));
		GameRegistry.registerBlock(needleGrass = new BlockGrassBase("needleGrass",otherworldDirt,Material.GROUND,1.0f));
		GameRegistry.registerBlock(leavesElderpine = new BlockRootsLeaves("leavesElderpine",Material.LEAVES,0.2f));
		GameRegistry.registerBlock(leavesAzurepine = new BlockRootsLeaves("leavesAzurepine",Material.LEAVES,0.2f));
		GameRegistry.registerBlock(glowSand = (new BlockSandBase("glowSand",Material.SAND,1.0f)).setLightLevel(1.0f));
		
		/**
		 * REGISTERING TILE ENTITIES
		 */
		GameRegistry.registerTileEntity(TileEntityMortar.class, customTileName("TileEntityMortar"));
		GameRegistry.registerTileEntity(TileEntityImbuer.class, customTileName("TileEntityImbuer"));
		GameRegistry.registerTileEntity(TileEntityAltar.class, customTileName("TileEntityAltar"));
		GameRegistry.registerTileEntity(TileEntityDruidChalice.class,customTileName("TileEntityDruidChalice"));
		GameRegistry.registerTileEntity(TileEntityBrazier.class,customTileName("TileEntityBrazier"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneVacuum.class,customTileName("TileEntityStandingStoneVacuum"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneRepulsor.class,customTileName("TileEntityStandingStoneRepulsor"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneAccelerator.class,customTileName("TileEntityStandingStoneAccelerator"));
		GameRegistry.registerTileEntity(TileEntityAestheticStandingStone.class,customTileName("TileEntityAestheticStandingStone"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneEntangler.class,customTileName("TileEntityStandingStoneEntangler"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneGrower.class,customTileName("TileEntityStandingStoneGrower"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneIgniter.class,customTileName("TileEntityStandingStoneIgniter"));
		GameRegistry.registerTileEntity(TileEntityStandingStoneHealer.class,customTileName("TileEntityStandingStoneHealer"));
		GameRegistry.registerTileEntity(TileEntitySpiritFont.class,customTileName("TileEntitySpiritFont"));
		GameRegistry.registerTileEntity(TileEntitySpiritConduit.class, customTileName("TileEntitySpiritConduit"));
		GameRegistry.registerTileEntity(TileEnityBridge.class,customTileName("TileEntityBridge"));

		GameRegistry.registerFuelHandler(new FuelManager());
		
		/**
		 * REGISTERING BIOMES
		 */

		biomeOtherworldProps = new BiomeProperties("otherworld");

		biomeOtherworldProps.setRainDisabled();
		biomeOtherworldProps.setBaseHeight(0);
		biomeOtherworldProps.setTemperature(0);
		biomeOtherworldProps.setWaterColor(Util.intColor(107, 255, 28));
		
		/**
		 * REGISTERING DIMENSIONS
		 */
		
		DimensionManager.registerDimension(ConfigManager.otherworldDimensionID, dimOtherworld);
	}

	private static String customTileName(String name)
	{
		return Roots.MODID + ":" + name;
	}
	
	public static void registerEntities(){
		EntityRegistry.registerModEntity(EntityTileAccelerator.class, "tileAccelerator", 0, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityAccelerator.class, "entityAccelerator", 1, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySpriteling.class, "spriteling", 2, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(EntitySpriteling.class, Util.intColor(130, 255, 60), Util.intColor(66, 230, 0));
		EntityRegistry.registerModEntity(EntitySprite.class, "sprite", 3, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(EntitySprite.class, Util.intColor(130, 255, 60), Util.intColor(66, 230, 0));
		EntityRegistry.registerModEntity(EntityGreaterSprite.class, "greaterSprite", 4, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(EntityGreaterSprite.class, Util.intColor(130, 255, 60), Util.intColor(66, 230, 0));
		EntityRegistry.registerModEntity(EntitySpriteProjectile.class, "entitySpriteProjectile", 5, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityNetherInfection.class, "entityInfection", 6, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySanctuary.class, "entitySanctuary", 7, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityFrostShard.class, "entityFrostShard", 8, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySpritePlacator.class, "entitySpritePlacator", 9, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySpriteGuardian.class, "spriteGuardian", 10, Roots.instance, 64, 1, true);
		EntityRegistry.registerEgg(EntitySpriteGuardian.class, Util.intColor(66, 230, 0), Util.intColor(130, 255, 60));
		EntityRegistry.registerModEntity(EntitySummoner.class, "entitySummoner", 11, Roots.instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityHomingProjectile.class,"entityHomingProjectile",12,Roots.instance,64,1,true);
		EntityRegistry.registerModEntity(EntitySpellProjectile.class,"entitySpellProjectile",13,Roots.instance,64,1,true);
		EntityRegistry.registerModEntity(EntityDeer.class,"deer",14,Roots.instance,64,1,true);
		EntityRegistry.registerEgg(EntityDeer.class, Util.intColor(161, 132, 88), Util.intColor(94, 77, 51));
		EntityRegistry.registerModEntity(EntityDireWolf.class,"direwolf",15,Roots.instance,64,1,true);
		EntityRegistry.registerEgg(EntityDireWolf.class, Util.intColor(172, 155, 147), Util.intColor(106, 94, 88));
	}
	
	public static void registerRecipes(){
		RecipeRegistry.registerAll();
	}
	
	public static void registerAchievements(){
		achieveTablet = new Achievement("achievement.tablet","tablet",-2,1,RegistryManager.runedTablet,null);
		achieveTablet.registerStat();
		achieveDust = new Achievement("achievement.dust","dust",0,0,RegistryManager.dustPetal,achieveTablet);
		achieveDust.registerStat();
		achieveSpellRose = new Achievement("achievement.spellRose","spellRose",0,-5,new ItemStack(Blocks.DOUBLE_PLANT,1,4),achieveDust);
		achieveSpellRose.registerStat();
		achieveSpellGrowth = new Achievement("achievement.spellGrowth","spellGrowth",-2,-3,new ItemStack(Blocks.DOUBLE_PLANT,1,1),achieveDust);
		achieveSpellGrowth.registerStat();
		achieveSpellInsanity = new Achievement("achievement.spellInsanity","spellInsanity",2,-3,new ItemStack(Blocks.RED_FLOWER,1,0),achieveDust);
		achieveSpellInsanity.registerStat();
		achieveMaxModifiers = new Achievement("achievement.maxModifiers","maxModifiers",-4,-2,new ItemStack(Items.GLOWSTONE_DUST,1),achieveDust);
		achieveMaxModifiers.registerStat();
		achieveLotsDamage = new Achievement("achievement.lotsDamage","lotsDamage",-6,-2,new ItemStack(Items.BLAZE_POWDER,1),achieveMaxModifiers).setSpecial();
		achieveLotsDamage.registerStat();
		achieveAltar = new Achievement("achievement.altar","altar",0,2,new ItemStack(RegistryManager.altar,1),achieveTablet);
		achieveAltar.registerStat();
		achieveStandingStone = new Achievement("achievement.standingStone","standingStone",2,4,new ItemStack(RegistryManager.standingStoneT2,1),achieveAltar);
		achieveStandingStone.registerStat();
		achieveWildwood = new Achievement("achievement.wildwood","wildwood",4,6,new ItemStack(RegistryManager.druidArmorHead,1),achieveStandingStone);
		achieveWildwood.registerStat();
		achieveSpriteling = new Achievement("achievement.spriteling", "spriteling", 2, 6, new ItemStack(RegistryManager.otherworldLeaf,1),achieveStandingStone);
		achieveSpriteling.registerStat();
		achieveIllusion = new Achievement("achievement.illusion", "illusion", 4, 7, new ItemStack(RegistryManager.itemCharmIllusion,1),achieveSpriteling);
		achieveIllusion.registerStat();
		achieveDischarge = new Achievement("achievement.discharge", "discharge", 3, 8, new ItemStack(RegistryManager.amuletDischarge,1),achieveSpriteling);
		achieveDischarge.registerStat();
		achieveArrowBlock = new Achievement("achievement.arrowBlock", "arrowBlock", 2, 7, new ItemStack(Items.ARROW,1),achieveSpriteling);
		achieveArrowBlock.registerStat();
		achieveGuardianBoss = new Achievement("achievement.guardianBoss", "guardianBoss", 2, 9, new ItemStack(RegistryManager.otherworldSubstance,1),achieveSpriteling).setSpecial();
		achieveGuardianBoss.registerStat();
		achieveHourglass = new Achievement("achievement.hourglass", "hourglass", 2, 10, new ItemStack(RegistryManager.ancientHourglass,1),achieveGuardianBoss);
		achieveHourglass.registerStat();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Roots", 
				achieveTablet, 
				achieveDust, 
				achieveSpellRose, 
				achieveSpellGrowth, 
				achieveSpellInsanity, 
				achieveMaxModifiers, 
				achieveLotsDamage, 
				achieveAltar, 
				achieveStandingStone, 
				achieveWildwood, 
				achieveSpriteling,
				achieveArrowBlock,
				achieveIllusion,
				achieveGuardianBoss,
				achieveHourglass,
				achieveDischarge
				));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerItemRenderers(){
		/**
		 * REGISTERING TILE ENTITY RENDERERS
		 */
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMortar.class, new TileEntityMortarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityImbuer.class, new TileEntityImbuerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrazier.class, new TileEntityBrazierRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpiritFont.class, new TileEntitySpiritFontRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySpiritConduit.class, new TileEntitySpiritConduitRenderer());
		
		/**
		 * REGISTERING ITEM MODELS
		 */

		((ItemDruidKnife)druidKnife).initModel();
		((DustPetal)dustPetal).initModel();
		((ItemPestle)pestle).initModel();
		((ItemStaff)staff).initModel();
		((ItemCrystalStaff)crystalStaff).initModel();
		((RootsItemFood)oldRoot).initModel();
		((ItemMaterial)verdantSprig).initModel();
		((ItemMaterial)infernalStem).initModel();
		((ItemDragonsEye)dragonsEye).initModel();
		((ItemTreeBark)oakTreeBark).initModel();
		((ItemTreeBark)spruceTreeBark).initModel();
		((ItemTreeBark)birchTreeBark).initModel();
		((ItemTreeBark)jungleTreeBark).initModel();
		((ItemTreeBark)acaciaTreeBark).initModel();
		((ItemTreeBark)darkOakTreeBark).initModel();
		((ItemLivingPickaxe)livingPickaxe).initModel();
		((ItemLivingAxe)livingAxe).initModel();
		((ItemLivingSword)livingSword).initModel();
		((ItemLivingHoe)livingHoe).initModel();
		((ItemLivingShovel)livingShovel).initModel();
		((ItemDruidRobes)druidRobesHead).initModel();
		((ItemDruidRobes)druidRobesChest).initModel();
		((ItemDruidRobes)druidRobesLegs).initModel();
		((ItemDruidRobes)druidRobesBoots).initModel();
		((ItemDruidArmor)druidArmorHead).initModel();
		((ItemDruidArmor)druidArmorChest).initModel();
		((ItemDruidArmor)druidArmorLegs).initModel();
		((ItemDruidArmor)druidArmorBoots).initModel();
		((ItemRunedTablet)runedTablet).initModel();
		((ItemGrowthSalve)growthSalve).initModel();
		((RootsItemFood)healingPoultice).initModel();
		((ItemRootyStew)rootyStew).initModel();
		((ItemResearchIcon)manaResearchIcon).initModel();
		((ItemMaterial)otherworldLeaf).initModel();
		((ItemMaterial)otherworldSubstance).initModel();
		((ItemSpellweaverLance)spellweaverLance).initModel();
		((ItemHungerTalisman)talismanHunger).initModel();
		((ItemPursuitTalisman)talismanPursuit).initModel();
		((ItemDischargeAmulet)amuletDischarge).initModel();
		((ItemConservingAmulet)amuletConserving).initModel();
		((ItemAncientHourglass)ancientHourglass).initModel();
		((ItemPixieStone)pixieStone).initModel();
		((ItemProjectileStaff)projectileStaff).initModel();
		((ItemResearchIcon)spritelingIcon).initModel();
		((ItemResearchIcon)spriteIcon).initModel();
		((ItemResearchIcon)greaterSpriteIcon).initModel();

		/**Charms**/
		((ItemCharmRestoration)itemCharmRestoration).initModel();
		((ItemCharmConjuration)itemCharmConjuration).initModel();
		((ItemCharmEvocation)itemCharmEvocation).initModel();
		((ItemCharmIllusion)itemCharmIllusion).initModel();
		
		//((BlockDruidChalice)druidChalice).initModel();
		((BlockMortar)mortar).initModel();
		((BlockAltar)altar).initModel();
		((BlockBrazier)brazier).initModel();
		((BlockImbuer)imbuer).initModel();
		((BlockStandingStoneT1)standingStoneT1).initModel();
		((BlockStandingStoneT2)standingStoneT2).initModel();
		((BlockStandingStoneVacuum)standingStoneVacuum).initModel();
		((BlockStandingStoneRepulsor)standingStoneRepulsor).initModel();
		((BlockStandingStoneAccelerator)standingStoneAccelerator).initModel();
		((BlockAestheticStandingStone)standingStoneAesthetic).initModel();
		((BlockStandingStoneEntangler)standingStoneEntangler).initModel();
		((BlockStandingStoneGrower)standingStoneGrower).initModel();
		((BlockStandingStoneIgniter)standingStoneIgniter).initModel();
		((BlockStandingStoneHealer)standingStoneHealer).initModel();
		((BlockBridge)bridge).initModel();
		((BlockBase)runeStone).initModel();
		((BlockStairsBase)runeStoneStairs).initModel();
		((BlockBase)runeStoneBrick).initModel();
		((BlockStairsBase)runeStoneBrickStairs).initModel();
		((BlockBase)runeStoneTile).initModel();
		((BlockStairsBase)runeStoneTileStairs).initModel();
		((BlockBase)runeStoneSymbol).initModel();
		((BlockBase)runeStoneSymbolGlowing).initModel();
		((BlockSlabBase)runeStoneSlab).initModel();
		((BlockDoubleSlabBase)runeStoneSlabDouble).initModel();
		((BlockSlabBase)runeStoneTileSlab).initModel();
		((BlockDoubleSlabBase)runeStoneTileSlabDouble).initModel();
		((BlockSlabBase)runeStoneBrickSlab).initModel();
		((BlockDoubleSlabBase)runeStoneBrickSlabDouble).initModel();
		((BlockBase)plankWildwood).initModel();
		((BlockSlabBase)plankWildwoodSlab).initModel();
		((BlockStairsBase)plankWildwoodStairs).initModel();
		((BlockDoubleSlabBase)plankWildwoodSlabDouble).initModel();
		((BlockLogBase)logWildwood).initModel();
		((BlockLogBase)logWildwoodSymbol).initModel();
		((BlockLogBase)logWildwoodSymbolGlowing).initModel();
		((BlockBase)barkWildwood).initModel();
		((BlockBase)barkWildwoodSymbol).initModel();
		((BlockBase)barkWildwoodSymbolGlowing).initModel();
		((BlockSpirit)spiritBlock).initModel();
		((BlockSpirit)spiritBrick).initModel();
		((BlockSpiritSlab)spiritBlockSlab).initModel();
		((BlockSpiritSlab)spiritBrickSlab).initModel();
		((BlockSpiritDoubleSlab)spiritBlockSlabDouble).initModel();
		((BlockSpiritDoubleSlab)spiritBrickSlabDouble).initModel();
		((BlockSpiritStairs)spiritBlockStairs).initModel();
		((BlockSpiritStairs)spiritBrickStairs).initModel();
		((BlockSpiritFont)spiritFont).initModel();
		((BlockSpiritConduit)spiritConduit).initModel();
		((BlockDirtBase)otherworldDirt).initModel();
		((BlockGrassBase)otherworldGrass).initModel();
		((BlockRootsLeaves)leavesWildwood).initModel();
		((BlockShrubBase)leafyGrass).initModel();
		((BlockSandBase)glowSand).initModel();
		((BlockGrassBase)needleGrass).initModel();
		((BlockRootsLeaves)leavesElderpine).initModel();
		((BlockRootsLeaves)leavesAzurepine).initModel();
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerColorHandlers(){
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemStaff.ColorHandler(), staff);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemCrystalStaff.ColorHandler(), crystalStaff);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemSpellweaverLance.ColorHandler(), spellweaverLance);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new ItemProjectileStaff.ColorHandler(), projectileStaff);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerEntityRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySpriteling.class, new RenderSpriteling(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("spriteling"),0.4f));
		RenderingRegistry.registerEntityRenderingHandler(EntitySprite.class, new RenderSprite(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("sprite"),0.6f));
		RenderingRegistry.registerEntityRenderingHandler(EntityGreaterSprite.class, new RenderGreaterSprite(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("greatersprite"),0.8f));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpriteProjectile.class, new RenderSpriteProjectile(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("null"),0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityFrostShard.class, new RenderFrostShard(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("null"),0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpritePlacator.class, new RenderSpritePlacator(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("null"),0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpriteGuardian.class, new RenderSpriteGuardian(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("spriteguardian"),1.2f));
		RenderingRegistry.registerEntityRenderingHandler(EntityRitualProjectile.class,new RitualProjectileRenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(EntityHomingProjectile.class,new RenderHomingProjectile(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("null"),0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpellProjectile.class,new RenderSpellProjectile(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("null"),0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class,new RenderDeer(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("deer"),0.5f));
		RenderingRegistry.registerEntityRenderingHandler(EntityDireWolf.class,new RenderDireWolf(Minecraft.getMinecraft().getRenderManager(),ModelHolder.entityModels.get("direwolf"),0.5f));
	}
}
