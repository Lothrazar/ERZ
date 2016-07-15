package elucent.roots.ritual;

import java.util.ArrayList;

import elucent.roots.RootsNames;
import elucent.roots.ritual.powers.RitualPowerBreed;
import elucent.roots.ritual.powers.RitualPowerFlare;
import elucent.roots.ritual.powers.RitualPowerGrow;
import elucent.roots.ritual.powers.RitualPowerLifeDrain;
import net.minecraft.entity.player.EntityPlayer;

public class RitualPowerManager {
	public static ArrayList<RitualPower> powers = new ArrayList<RitualPower>();
	
	public static void init(){
		powers.add(new RitualPowerFlare());
		powers.add(new RitualPowerGrow());
		powers.add(new RitualPowerBreed());
		powers.add(new RitualPowerLifeDrain());
	}
	
	public static RitualPower getPlayerPower(EntityPlayer player){
		if (player.getEntityData().hasKey(RootsNames.TAG_HAS_RITUAL_POWER)){
			for (int i = 0; i < powers.size(); i ++){
				if (player.getEntityData().hasKey(powers.get(i).getTagName())){
					return powers.get(i);
				}
			}
		}
		return null;
	}
	
	public static RitualPower getPowerFromName(String name){
		for (int i = 0; i < powers.size(); i ++){
			if (powers.get(i).name == name){
				return powers.get(i);
			}
		}
		return null;
	}
}
