package elucent.roots.model;

import java.util.HashMap;

public class ModelHolder {
	public static HashMap<String, ModelArmorHolder> armorModels = new HashMap<String, ModelArmorHolder>();
	
	public static void init(){
		armorModels.put("druidArmor", new ModelArmorHolder("druidArmor"));
		armorModels.put("druidRobes", new ModelArmorHolder("druidRobes"));
	}
}
