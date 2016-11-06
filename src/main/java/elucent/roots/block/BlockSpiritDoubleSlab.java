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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpiritDoubleSlab extends BlockSlab {
	public Block slab;
	
	public BlockSpiritDoubleSlab(String name, Material material, float hardness, String tool, int level, Block slab){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setHardness(hardness);
		this.slab = slab;
		setHarvestLevel(tool,level);
		setLightLevel(1.0f);
		//setCreativeTab(Roots.tab);
	}
	
	@Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face){
        return false;
    }
	
	@Override
	public boolean isFullBlock(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer){
		if (layer == BlockRenderLayer.TRANSLUCENT){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isTranslucent(IBlockState state){
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random){
		int side = random.nextInt(6);
		if (side == 0){
			Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX(), pos.getY()+random.nextDouble(), pos.getZ()+random.nextDouble(), 0, 0, 0, 76, 230, 0);
		}
		if (side == 1){
			Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX()+1.0, pos.getY()+random.nextDouble(), pos.getZ()+random.nextDouble(), 0, 0, 0, 76, 230, 0);
		}
		if (side == 2){
			Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX()+random.nextDouble(), pos.getY(), pos.getZ()+random.nextDouble(), 0, 0, 0, 76, 230, 0);
		}
		if (side == 3){
			Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX()+random.nextDouble(), pos.getY()+1.0, pos.getZ()+random.nextDouble(), 0, 0, 0, 76, 230, 0);
		}
		if (side == 4){
			Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX()+random.nextDouble(), pos.getY()+random.nextDouble(), pos.getZ(), 0, 0, 0, 76, 230, 0);
		}
		if (side == 5){
			Roots.proxy.spawnParticleMagicAuraFX(world, pos.getX()+random.nextDouble(), pos.getY()+random.nextDouble(), pos.getZ()+1.0, 0, 0, 0, 76, 230, 0);
		}
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
