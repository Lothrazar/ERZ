package elucent.roots.ritual;

import elucent.roots.PlayerManager;
import elucent.roots.RootsNames;
import elucent.roots.capability.powers.PowerProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPower {
	public String name;
	public EnumPowerType type;
	public int offset;
	public RitualPower(String name, EnumPowerType type, int textureOffset){
		this.name = name;
		this.type = type;
		this.offset = textureOffset;
	}
	
	public String getTagName(){
		return RootsNames.TAG_RITUAL_POWER_BASE+name;
	}
	
	public void onRightClickEntity(EntityPlayer player, World world, Entity entity){
		if (type == EnumPowerType.TYPE_TARGET_ENTITY){
			PowerProvider.get(player).usePower(player);
			if (PowerProvider.get(player).getPowerLeft() == 0){
				PowerProvider.get(player).setPower(player, "none");
			}
		}
	}
	
	public void onRightClickBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state){
		if (type == EnumPowerType.TYPE_TARGET_BLOCK){
			PowerProvider.get(player).usePower(player);
			if (PowerProvider.get(player).getPowerLeft() == 0){
				PowerProvider.get(player).setPower(player, "none");
			}
		}
	}
}
