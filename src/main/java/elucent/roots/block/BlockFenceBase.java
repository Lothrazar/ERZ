package elucent.roots.block;

import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFenceBase extends BlockFence {
	public BlockFenceBase(String name, Material material, float hardness){
		super(material, MapColor.WOOD);
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setCreativeTab(Roots.tab);
		setHardness(hardness);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
