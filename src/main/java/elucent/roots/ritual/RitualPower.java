package elucent.roots.ritual;

import elucent.roots.RootsNames;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualPower {
	public String name;
	public EnumPowerType type;
	public RitualPower(String name, EnumPowerType type){
		this.name = name;
		this.type = type;
	}
	
	public String getTagName(){
		return RootsNames.TAG_RITUAL_POWER_BASE+name;
	}
	
	public void onRightClickEntity(EntityPlayer player, World world, Entity entity){
		if (type == EnumPowerType.TYPE_TARGET_ENTITY){
			player.getEntityData().setInteger(getTagName(), player.getEntityData().getInteger("RMOD_ritualPower_"+name)-1);
			player.getEntityData().setInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN, 5);
			if (player.getEntityData().getInteger(getTagName()) == 0){
				player.getEntityData().removeTag(getTagName());
				player.getEntityData().removeTag(RootsNames.TAG_HAS_RITUAL_POWER);
				player.getEntityData().removeTag(RootsNames.TAG_RITUAL_POWER_COOLDOWN);
			}
		}
	}
	
	public void onRightClickBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state){
		if (type == EnumPowerType.TYPE_TARGET_BLOCK){
			player.getEntityData().setInteger(getTagName(), player.getEntityData().getInteger("RMOD_ritualPower_"+name)-1);
			player.getEntityData().setInteger(RootsNames.TAG_RITUAL_POWER_COOLDOWN, 5);
			if (player.getEntityData().getInteger(getTagName()) == 0){
				player.getEntityData().removeTag(getTagName());
				player.getEntityData().removeTag(RootsNames.TAG_HAS_RITUAL_POWER);
				player.getEntityData().removeTag(RootsNames.TAG_RITUAL_POWER_COOLDOWN);
			}
		}
	}
}
