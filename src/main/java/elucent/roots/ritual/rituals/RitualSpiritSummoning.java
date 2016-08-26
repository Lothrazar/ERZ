package elucent.roots.ritual.rituals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.entity.EntityGreaterSprite;
import elucent.roots.entity.EntitySprite;
import elucent.roots.entity.EntitySpriteling;
import elucent.roots.entity.EntitySummoner;
import elucent.roots.ritual.RitualBase;
import elucent.roots.tileentity.TileEntityAltar;
import elucent.roots.tileentity.TileEntityBrazier;

public class RitualSpiritSummoning extends RitualSummoning {
	public Class result = null;
	public int numFlowers = 0;
	public int numSpritelings = 0;
	public int numSprites = 0;
	public int numGreaterSprites = 0;
	public boolean large = false;
	
	public RitualSpiritSummoning init(Class entity, int flowerCount, boolean isLarge){
		this.result = entity;
		this.large = isLarge;
		this.numFlowers = flowerCount;
		return this;
	}
	
	public RitualSpiritSummoning setSpiritCosts(int spritelings, int sprites, int greaterSprites){
		numSpritelings = spritelings;
		numSprites = sprites;
		numGreaterSprites = greaterSprites;
		return this;
	}

	public RitualSpiritSummoning(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
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
		ArrayList<ItemStack> uniqueFlowers = new ArrayList<ItemStack>();
		for (int i = 0; i < test.size(); i ++){
			if (Block.getBlockFromItem(test.get(i).getItem()) == Blocks.RED_FLOWER || Block.getBlockFromItem(test.get(i).getItem()) == Blocks.YELLOW_FLOWER || Block.getBlockFromItem(test.get(i).getItem()) == Blocks.DOUBLE_PLANT || Util.hasOreDictPrefix(test.get(i),"flower") || Util.hasOreDictPrefix(test.get(i),"mysticFlower")){
				if (!Util.containsItem(uniqueFlowers, test.get(i).getItem(), test.get(i).getMetadata())){
					uniqueFlowers.add(test.get(i));
				}
			}
		}
		List<EntitySpriteling> spritelings = world.getEntitiesWithinAABB(EntitySpriteling.class, new AxisAlignedBB(pos.getX()-7.5,pos.getY()-7.5,pos.getZ()-7.5,pos.getX()+8.5,pos.getY()+22.5,pos.getZ()+8.5));
		List<EntitySprite> sprites = world.getEntitiesWithinAABB(EntitySprite.class, new AxisAlignedBB(pos.getX()-7.5,pos.getY()-7.5,pos.getZ()-7.5,pos.getX()+8.5,pos.getY()+22.5,pos.getZ()+8.5));
		List<EntityGreaterSprite> greaterSprites = world.getEntitiesWithinAABB(EntityGreaterSprite.class, new AxisAlignedBB(pos.getX()-7.5,pos.getY()-7.5,pos.getZ()-7.5,pos.getX()+8.5,pos.getY()+22.5,pos.getZ()+8.5));
		if (spritelings.size() < numSpritelings){
			return false;
		}
		if (sprites.size() < numSprites){
			return false;
		}
		if (greaterSprites.size() < numGreaterSprites){
			return false;
		}
		return uniqueFlowers.size() == numFlowers && Util.itemListsMatchWithSize(ingredients,((TileEntityAltar)world.getTileEntity(pos)).inventory);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		if (Util.itemListsMatchWithSize(inventory, this.ingredients)){
			EntitySummoner summoner = new EntitySummoner(world,result,pos.getX()+0.5,pos.getY()+2.5,pos.getZ()+0.5,color,secondaryColor,large);
			world.spawnEntityInWorld(summoner);
			inventory.clear();
			world.getTileEntity(pos).markDirty();
		}
	}
}
