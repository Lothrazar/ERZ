package elucent.roots.ritual;

import elucent.roots.capability.powers.PowerProvider;
import elucent.roots.ritual.powers.*;
import elucent.roots.ritual.powers.charms.RitualPowerConjuration;
import elucent.roots.ritual.powers.charms.RitualPowerEvocation;
import elucent.roots.ritual.powers.charms.RitualPowerIllusion;
import elucent.roots.ritual.powers.charms.RitualPowerRestoration;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;

public class RitualPowerManager {
	public static HashMap<String,RitualPower> powers = new HashMap<String,RitualPower>();
	
	public static void init(){
		powers.put("flare", new RitualPowerFlare());
		powers.put("grow",  new RitualPowerGrow());
		powers.put("breed", new RitualPowerBreed());
		powers.put("lifedrain", new RitualPowerLifeDrain());
		powers.put("restoration", new RitualPowerRestoration());
		powers.put("evocation", new RitualPowerEvocation());
		powers.put("illusion", new RitualPowerIllusion());
		powers.put("conjuration", new RitualPowerConjuration());
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
