package elucent.roots.model;

import java.util.HashMap;

import elucent.roots.model.entity.ModelDeer;
import elucent.roots.model.entity.ModelDireWolf;
import elucent.roots.model.entity.ModelGreaterSprite;
import elucent.roots.model.entity.ModelNull;
import elucent.roots.model.entity.ModelSprite;
import elucent.roots.model.entity.ModelSpriteGuardianHead;
import elucent.roots.model.entity.ModelSpriteGuardianSegment;
import elucent.roots.model.entity.ModelSpriteGuardianSegmentFirst;
import elucent.roots.model.entity.ModelSpriteGuardianSegmentLarge;
import elucent.roots.model.entity.ModelSpriteGuardianTail;
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
		entityModels.put("spriteguardian", new ModelSpriteGuardianHead());
		entityModels.put("spriteguardiansegment", new ModelSpriteGuardianSegment());
		entityModels.put("spriteguardiansegmentlarge", new ModelSpriteGuardianSegmentLarge());
		entityModels.put("spriteguardiansegmentfirst", new ModelSpriteGuardianSegmentFirst());
		entityModels.put("spriteguardiantail", new ModelSpriteGuardianTail());
		entityModels.put("deer", new ModelDeer());
		entityModels.put("direwolf", new ModelDireWolf());
	}
}
