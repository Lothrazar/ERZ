package teamroots.roots.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import teamroots.roots.entity.EntityFireJet;
import teamroots.roots.entity.EntityThornTrap;

public class SpellRose extends SpellBase {

	public SpellRose(String name) {
		super(name,TextFormatting.RED,255f/255f,32f/255f,64f/255f,32f/255f,255f/255f,96f/255f);
		this.castType = SpellBase.EnumCastType.INSTANTANEOUS;
		this.cooldown = 24;
	}
	
	@Override
	public void cast(EntityPlayer player){
		if (!player.world.isRemote){
			EntityThornTrap trap = new EntityThornTrap(player.world);
			trap.setPlayer(player.getUniqueID());
			trap.setPosition(player.posX+player.getLookVec().xCoord, player.posY+player.getEyeHeight()+player.getLookVec().yCoord, player.posZ+player.getLookVec().zCoord);
			trap.motionX = player.getLookVec().xCoord*0.75f;
			trap.motionY = player.getLookVec().yCoord*0.75f;
			trap.motionZ = player.getLookVec().zCoord*0.75f;
			player.world.spawnEntity(trap);
		}
	}

}
