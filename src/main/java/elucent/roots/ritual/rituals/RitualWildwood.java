package elucent.roots.ritual.rituals;

import java.util.List;

import elucent.roots.RegistryManager;
import elucent.roots.ritual.RitualBase;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualWildwood extends RitualBase {
	public RitualWildwood(String name, double r, double g, double b) {
		super(name, r, g, b);
	}
	
	@Override
	public void doEffect(World world, BlockPos pos, List<ItemStack> inventory, List<ItemStack> incenses){
		inventory.clear();
		for (int i = -2; i < 3; i ++){
			for (int j = -2; j < 3; j ++){
				for (int k = 0; k < 5; k ++){
					if (world.getBlockState(pos.add(i,k,j)).getBlock() instanceof BlockLog){
						world.setBlockState(pos.add(i,k,j), RegistryManager.logWildwood.getDefaultState());
					}
				}
			}
		}
	}
}
