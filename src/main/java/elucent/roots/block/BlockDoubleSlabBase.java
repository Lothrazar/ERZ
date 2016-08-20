package elucent.roots.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockPurpurSlab.Half;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDoubleSlabBase extends BlockSlab {
	public Block slab;
	
	public BlockDoubleSlabBase(String name, Material material, float hardness, String tool, int level, Block slab){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setHardness(hardness);
		this.slab = slab;
		setHarvestLevel(tool,level);
		//setCreativeTab(Roots.tab);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult trace, World world, BlockPos pos, EntityPlayer player){
		return new ItemStack(this.slab,1);
	}
	
	public void setSlab(Block slab){
		this.slab = slab;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		IBlockState iblockstate = this.getDefaultState();
		if (!this.isDouble())
			iblockstate = iblockstate.withProperty(HALF, (meta) == 0 ?
				                                             BlockSlab.EnumBlockHalf.BOTTOM :
				                                             BlockSlab.EnumBlockHalf.TOP);

		return iblockstate;
	}

	@Override
	public int getMetaFromState(IBlockState state){
		byte b0 = 0;
		int i = b0;

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
		{
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, HALF);
	}

	@Override
	public boolean isDouble()
	{
		return true;
	}

	@Override
	public String getUnlocalizedName(int meta)
	{
		return getUnlocalizedName();
	}

	@Override
	public IProperty getVariantProperty()
	{
		return HALF;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return 0;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		System.out.println("DEBUG");
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(new ItemStack(Item.getItemFromBlock(this.slab),1));
		drops.add(new ItemStack(Item.getItemFromBlock(this.slab),1));
		return drops;
	}

	public static enum DummyEnum {
		BLARG
	}
}
