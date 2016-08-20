package elucent.roots.block;

import elucent.roots.Roots;
import elucent.roots.tileentity.TileEnityBridge;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by SirShadow for the mod Roots on 20.7.2016.
 */
public class BlockBridge extends Block implements ITileEntityProvider
{
    public BlockBridge(){
        super(Material.GROUND);
        setUnlocalizedName("bridgeBlock");
        setCreativeTab(Roots.tab);
        setHardness(1.0f);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }

    /*@Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }*/

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEnityBridge();
    }
}
