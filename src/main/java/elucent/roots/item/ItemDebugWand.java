package elucent.roots.item;

import elucent.roots.entity.EntitySpriteling;
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
		world.spawnEntityInWorld(new EntitySpriteling(world,pos.getX(),pos.getY()+2,pos.getZ()));
		return EnumActionResult.SUCCESS;
	}
}
