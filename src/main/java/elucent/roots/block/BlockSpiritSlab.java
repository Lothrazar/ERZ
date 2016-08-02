package elucent.roots.block;

import java.util.Random;

import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockPurpurSlab.Half;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSpiritSlab extends BlockSlab {
	private Block doubleSlab;
	
	public BlockSpiritSlab(String name, Material material, float hardness, String tool, int level, Block doubleSlab){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setHardness(hardness);
		this.doubleSlab = doubleSlab;
		setHarvestLevel(tool,level);
		setLightLevel(1.0f);
		//setCreativeTab(Roots.tab);
	}
	
	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer){
		if (layer == BlockRenderLayer.TRANSLUCENT){
			return true;
		}
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
	public boolean isTranslucent(IBlockState state){
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
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
	
    @SideOnly(Side.CLIENT)
	protected static boolean isHalfSlab(IBlockState state){
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
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
		return state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1; 
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, HALF);
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}

	@Override
	public String getUnlocalizedName(int meta)
	{
		return null;
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

	public static enum DummyEnum {
		BLARG
	}
}
