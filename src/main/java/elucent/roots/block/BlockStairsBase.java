package elucent.roots.block;

import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStairsBase extends BlockStairs {
	public BlockStairsBase(String name, IBlockState state, SoundType type, float hardness){
		super(state);
		setUnlocalizedName(name);
		setSoundType(type);
		setRegistryName(Roots.MODID+":"+name);
		setCreativeTab(Roots.tab);
		setHardness(hardness);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
