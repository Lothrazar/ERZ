package elucent.roots.item;

import elucent.roots.RegistryManager;
import elucent.roots.entity.EntityGreaterSprite;
import elucent.roots.entity.EntitySprite;
import elucent.roots.entity.EntitySpriteling;
import elucent.roots.tileentity.TileEntitySpiritFont;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDebugWand extends Item{
	public ItemDebugWand(){
		super();
		setUnlocalizedName("debugWand");
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if (!world.isRemote){
			if (world.getBlockState(pos).getBlock() == RegistryManager.spiritFont && world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos)) == 1){
				TileEntitySpiritFont font = (TileEntitySpiritFont)world.getTileEntity(pos);
				
			}
		}
		return EnumActionResult.SUCCESS;
	}
}
