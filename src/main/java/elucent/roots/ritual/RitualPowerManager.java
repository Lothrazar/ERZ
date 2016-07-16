package elucent.roots.ritual;

import java.util.ArrayList;
import java.util.HashMap;

import elucent.roots.PlayerManager;
import elucent.roots.RootsNames;
import elucent.roots.capability.powers.IPowersCapability;
import elucent.roots.capability.powers.PowerProvider;
import elucent.roots.ritual.powers.RitualNull;
import elucent.roots.ritual.powers.RitualPowerBreed;
import elucent.roots.ritual.powers.RitualPowerFlare;
import elucent.roots.ritual.powers.RitualPowerGrow;
import elucent.roots.ritual.powers.RitualPowerLifeDrain;
import net.minecraft.entity.player.EntityPlayer;

public class RitualPowerManager {
	public static HashMap<String,RitualPower> powers = new HashMap<String,RitualPower>();
	
	public static void init(){
		powers.put("flare", new RitualPowerFlare());
		powers.put("grow",  new RitualPowerGrow());
		powers.put("breed", new RitualPowerBreed());
		powers.put("lifedrain", new RitualPowerLifeDrain());
		powers.put("none", new RitualNull());
	}
	
	public static RitualPower getPlayerPower(EntityPlayer player){
			return powers.get(PowerProvider.get(player).getPowerName());
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
