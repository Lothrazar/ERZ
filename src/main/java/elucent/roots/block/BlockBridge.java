package elucent.roots.block;

import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.tileentity.TileEnityBridge;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by SirShadow for the mod Roots on 20.7.2016.
 */
public class BlockBridge extends Block
{
    public BlockBridge(){
        super(Material.GLASS);
        setUnlocalizedName("bridgeBlock");
        //setCreativeTab(Roots.tab);
        setHardness(0.5f);
        setTickRandomly(true);
    }
    
    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random){
    	for (int j = 0; j < 20; j ++){
			Roots.proxy.spawnParticleMagicSparkleFX(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
		}
    	world.setBlockToAir(pos);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }
	
	@Override
	public boolean isTranslucent(IBlockState state){
		return true;
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
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
