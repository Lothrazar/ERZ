package elucent.roots.block;

import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLogBase extends BlockRotatedPillar {
	public BlockLogBase(String name, float hardness, String tool, int level) {
		super(Material.WOOD);
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setHardness(hardness);
		setHarvestLevel(tool,level);
		setCreativeTab(Roots.tab);
	}
	
	@Override public boolean canSustainLeaves(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }
	@Override public boolean isWood(net.minecraft.world.IBlockAccess world, BlockPos pos){ return true; }

	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS);
    }
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
