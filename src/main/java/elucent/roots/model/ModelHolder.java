package elucent.roots.model;

import java.util.HashMap;

import elucent.roots.model.entity.ModelGreaterSprite;
import elucent.roots.model.entity.ModelNull;
import elucent.roots.model.entity.ModelSprite;
import elucent.roots.model.entity.ModelSpriteling;
import net.minecraft.client.model.ModelBase;

public class ModelHolder {
	public static HashMap<String, ModelArmorHolder> armorModels = new HashMap<String, ModelArmorHolder>();
	public static HashMap<String, ModelBase> entityModels = new HashMap<String, ModelBase>();
	
	public static void init(){
		armorModels.put("druidArmor", new ModelArmorHolder("druidArmor"));
		armorModels.put("druidRobes", new ModelArmorHolder("druidRobes"));
		
		entityModels.put("spriteling", new ModelSpriteling());
		entityModels.put("sprite", new ModelSprite());
		entityModels.put("greatersprite", new ModelGreaterSprite());
		entityModels.put("null", new ModelNull());
	}
}
