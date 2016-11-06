package elucent.roots.item;

import elucent.roots.ConfigManager;
import elucent.roots.RegistryManager;
import elucent.roots.dimension.RootsTeleporter;
import elucent.roots.entity.EntityGreaterSprite;
import elucent.roots.entity.EntitySprite;
import elucent.roots.entity.EntitySpriteling;
import elucent.roots.tileentity.TileEntitySpiritFont;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class ItemDebugWand extends Item{
	public ItemDebugWand(){
		super();
		setUnlocalizedName("debugWand");
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if (!world.isRemote){
			player.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP) player, ConfigManager.otherworldDimensionID, new RootsTeleporter(world.getMinecraftServer().worldServerForDimension(ConfigManager.otherworldDimensionID)));
		}
		return EnumActionResult.SUCCESS;
	}
}
