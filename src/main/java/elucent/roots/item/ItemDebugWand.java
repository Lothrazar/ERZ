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
		if (!world.isRemote){
			EntitySpriteling spriteling = new EntitySpriteling(world);
			spriteling.setPosition(pos.getX()+0.5,pos.getY()+1.5,pos.getZ()+0.5);
			spriteling.onInitialSpawn(world.getDifficultyForLocation(pos), null);
			world.spawnEntityInWorld(spriteling);
		}
		return EnumActionResult.SUCCESS;
	}
}
