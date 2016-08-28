package elucent.roots.item;

import java.util.List;
import java.util.Random;

import net.minecraft.util.text.TextFormatting;

import elucent.roots.Roots;
import elucent.roots.RootsCapabilityManager;
import elucent.roots.Util;
import elucent.roots.capability.mana.ManaProvider;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntityAttachedSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpellweaverLance extends ItemMeleeCastingBase implements IManaRelatedItem {
	Random random = new Random();
	
	public ItemSpellweaverLance(){
		super("spellweaverLance",2,5.0f,1.3f);
	}
	
	@Override
	public double getCost(ComponentBase comp, double potency, double efficiency){
		return super.getCost(comp, potency+1, efficiency);
	}
	
	@Override
	public int getUseCount(double efficiency){
		return 97+(int)(48*efficiency);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
	
	@Override
	public void doEffect(World world, EntityPlayer player, ComponentBase component, double potency, double efficiency, double size){
		Entity entity = Util.getRayTraceEntity(world, player, 4);
		if (entity instanceof EntityLivingBase && !world.isRemote){
			world.spawnEntityInWorld(new EntityAttachedSpell(world,player,entity,component,potency*0.5,efficiency*0.5,size*0.5));
		}
	}
	
	public static class ColorHandler implements IItemColor {
		public ColorHandler(){
			//
		}
		@Override
		public int getColorFromItemstack(ItemStack stack, int layer) {
			if (stack.hasTagCompound()){
				if (layer == 2){
					ComponentBase comp = ComponentManager.getComponentFromName(getEffect(stack));
					if (comp != null){
						return Util.intColor((int)comp.primaryColor.xCoord,(int)comp.primaryColor.yCoord,(int)comp.primaryColor.zCoord);
					}
				}
				if (layer == 1){
					ComponentBase comp = ComponentManager.getComponentFromName(getEffect(stack));
					if (comp != null){
						return Util.intColor((int)comp.secondaryColor.xCoord,(int)comp.secondaryColor.yCoord,(int)comp.secondaryColor.zCoord);
					}
				}
			}
			return Util.intColor(255, 255, 255);
		}
	}
}
