package elucent.roots.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.util.EnumHandSide;

public class ModelDruidArmor extends ModelArmorBase {
	
	public EntityEquipmentSlot slot;
	
	public ModelDruidArmor(EntityEquipmentSlot slot){
		super(slot);
		ModelArmorHolder m = ModelHolder.armorModels.get("druidArmor");
		this.head = m.head;
		this.armL = m.armL;
		this.armR = m.armR;
		this.chest = m.chest;
		this.legL = m.legL;
		this.legR = m.legR;
		this.bootL = m.bootL;
		this.bootR = m.bootR;
	    this.armorScale = 1.2f;
	}
}
