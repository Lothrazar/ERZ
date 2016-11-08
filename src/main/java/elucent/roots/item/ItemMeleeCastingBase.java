package elucent.roots.item;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.capability.mana.ManaProvider;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import elucent.roots.event.SpellCastEvent;
import elucent.roots.network.MessageSpellCastFX;
import elucent.roots.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ItemMeleeCastingBase extends ItemTool {
	public static SoundEvent castingSound = new SoundEvent(new ResourceLocation("roots:meleeCast"));
	public int spellSlots = 0;
	
	public ItemMeleeCastingBase(String name, int spellSlots, float attackDamage, float attackSpeed){
		super(attackDamage, attackSpeed-4.0f, ToolMaterial.WOOD, Sets.newHashSet(new Block[]{}));
		this.setMaxDamage(-1);
		this.spellSlots = spellSlots;
		setUnlocalizedName(name);
		setCreativeTab(Roots.tab);
	}
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack){
		if (stack.hasTagCompound()){
			return 1.0-(double)getUses(stack)/(double)getMaxUses(stack);
		}
		return 1.0;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack){
		if (stack.hasTagCompound()){
			if (getUses(stack) < getMaxUses(stack)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack){
		return EnumAction.NONE;
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity){
		if (entity instanceof EntityLivingBase){
			if (((EntityLivingBase)entity).hurtResistantTime == 0){
				this.cast(stack, player.getEntityWorld(), player, EnumHand.MAIN_HAND);
			}
		}
		return false;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack){
		return 72000;
	}
	
	public static void createData(ItemStack stack, int spellSlots){
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("selected", 1);
		stack.getTagCompound().setInteger("numSlots", spellSlots);
		for (int i = 1; i < spellSlots+1; i ++){
			stack.getTagCompound().setDouble("potency"+i, 0);
			stack.getTagCompound().setDouble("efficiency"+i, 0);
			stack.getTagCompound().setDouble("size"+i, 0);
			stack.getTagCompound().setString("effect"+i, "");
			stack.getTagCompound().setInteger("maxUses"+i, 0);
			stack.getTagCompound().setInteger("uses"+i, 0);
		}
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}
	
	public double getCost(ComponentBase component, double potency, double efficiency){
		return (component.xpCost+(float)potency*4.0f)/(0.75*efficiency+1);//(component.xpCost + potency)*(((1.0+(0.75/((double)efficiency)))/2.0))*(1.0/((0.75+(1.0/((double)potency)))/2.0));
	}
	
	public void cast(ItemStack stack, World world, EntityLivingBase player, EnumHand hand){
		if (stack.hasTagCompound()){
			BlockPos pos = new BlockPos(player.posX,player.posY,player.posZ);
			if (true){
				world.playSound(player.posX, player.posY, player.posZ, this.castingSound, SoundCategory.PLAYERS, 0.95f+0.1f*itemRand.nextFloat(), 0.95f+0.1f*itemRand.nextFloat(), false);
				ComponentBase comp = ComponentManager.getComponentFromName(this.getEffect(stack));
				double potency = this.getPotency(stack);
				double efficiency = this.getEfficiency(stack);
				double size = this.getSize(stack);
				if (comp != null){
					if (((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null
						&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null
						&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null
						&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.FEET) != null){
						if (((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() instanceof ItemDruidRobes
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof ItemDruidRobes
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() instanceof ItemDruidRobes
							&& ((EntityPlayer)player).getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() instanceof ItemDruidRobes){
							efficiency += 1;
						}
					}
					SpellCastEvent event = new SpellCastEvent((EntityPlayer)player,comp,potency,efficiency,size);
					MinecraftForge.EVENT_BUS.post(event);
					potency = event.getPotency();
					efficiency = event.getEfficiency();
					size = event.getSize();
					double cost = this.getCost(comp, potency, efficiency);
					Random random = new Random();
					if (((EntityPlayer)player).hasCapability(ManaProvider.manaCapability, null) && ManaProvider.get((EntityPlayer)player).getMana() >= ((float)cost) && !event.isCanceled()){
						ManaProvider.get((EntityPlayer)player).setMana((EntityPlayer)player, ManaProvider.get((EntityPlayer)player).getMana()-(((float)cost)));
						this.doEffect(world,(EntityPlayer)player,comp,this.getPotency(stack),this.getEfficiency(stack),this.getSize(stack));
						decrementUses(stack, stack.getTagCompound().getInteger("spellSlots"), player, hand);
						if (!world.isRemote){
							PacketHandler.INSTANCE.sendToAll(new MessageSpellCastFX(comp.getName(),(float)player.posX,(float)player.posY,(float)player.posZ,player.getLookVec()));
						}
					}
				}
			}
		}
	}
	
	public static void decrementUses(ItemStack stack, int spellSlots, EntityLivingBase player, EnumHand hand){
		stack.getTagCompound().setInteger("uses"+getSlot(stack), getUses(stack)-1);
		if (getUses(stack) < 0){
			((ItemMeleeCastingBase)stack.getItem()).setEffectInSlot(stack,getSlot(stack),"",0,0,0);
			boolean isEmpty = true;
			for (int i = 0; i < spellSlots; i ++){
				if (!stack.getTagCompound().getString("effect"+i).equals("")){
					isEmpty = false;
				}
			}
			if (((ItemMeleeCastingBase)stack.getItem()).destroyWhenEmpty()){
				player.setItemStackToSlot(hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, null);
			}
		}
	}
	
	public boolean destroyWhenEmpty(){
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
		if (stack.hasTagCompound()){
			ComponentBase comp = ComponentManager.getComponentFromName(this.getEffect(stack));
			if (comp != null){
				tooltip.add(TextFormatting.GOLD + I18n.format("roots.tooltip.spelltypeheading.name") + ": " + comp.getTextColor() + comp.getEffectName());
				tooltip.add(TextFormatting.RED + "  +" + this.getPotency(stack) + " " + I18n.format("roots.tooltip.spellpotency.name") + ".");
				tooltip.add(TextFormatting.RED + "  +" + this.getEfficiency(stack) + " " + I18n.format("roots.tooltip.spellefficiency.name") + ".");
				tooltip.add(TextFormatting.RED + "  +" + this.getSize(stack) + " " + I18n.format("roots.tooltip.spellsize.name") + ".");
				tooltip.add("");
				tooltip.add(TextFormatting.GOLD + Integer.toString(getUses(stack)) + I18n.format("roots.tooltip.usesremaining.name"));
			}
		}
	}
	
	public int getUseCount(double efficiency){
		return 33+(int)(16*efficiency);
	}
	
	public void setEffectInSlot(ItemStack stack, int slot, String effect, double potency, double efficiency, double size){
		if (slot > spellSlots){
			slot = spellSlots;
		}
		stack.getTagCompound().setString("effect"+slot, effect);
		stack.getTagCompound().setDouble("potency"+slot, potency);
		stack.getTagCompound().setDouble("efficiency"+slot, efficiency);
		stack.getTagCompound().setDouble("size"+slot, size);
		stack.getTagCompound().setInteger("maxUses"+slot, getUseCount(efficiency));
		stack.getTagCompound().setInteger("uses"+slot, stack.getTagCompound().getInteger("maxUses"+slot));
	}
	
	public void doEffect(World world, EntityPlayer player, ComponentBase component, double potency, double efficiency, double size){
		component.doEffect(world, player, EnumCastType.SPELL, player.posX+3.0*player.getLookVec().xCoord, player.posY+3.0*player.getLookVec().yCoord, player.posZ+3.0*player.getLookVec().zCoord, potency, efficiency, size);
	}
	
	public static int getSlot(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("selected");
		}
		return 0;
	}
	
	public static int getMaxUses(ItemStack stack){
		return stack.getTagCompound().getInteger("maxUses"+stack.getTagCompound().getInteger("selected"));
	}
	
	public static int getUses(ItemStack stack){
		return stack.getTagCompound().getInteger("uses"+stack.getTagCompound().getInteger("selected"));
	}
	
	public static double getPotency(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getDouble("potency"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static double getEfficiency(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getDouble("efficiency"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static double getSize(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getDouble("size"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static String getEffect(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getString("effect"+stack.getTagCompound().getInteger("selected"));
		}
		return null;
	}
	
	public static String getEffect(ItemStack stack, int slot){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getString("effect"+slot);
		}
		return null;
	}
	
	@Override
	public int getItemStackLimit(){
		return 1;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
		if (stack.hasTagCompound() && !player.isSneaking()){
		}
		else if (stack.hasTagCompound()){
			stack.getTagCompound().setInteger("selected", stack.getTagCompound().getInteger("selected")+1);
			if (stack.getTagCompound().getInteger("selected") > spellSlots){
				stack.getTagCompound().setInteger("selected", 1);
			}
			return new ActionResult(EnumActionResult.FAIL,stack);
		}
		return new ActionResult(EnumActionResult.FAIL,stack);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged){
		if (oldS.hasTagCompound() && newS.hasTagCompound()){
			if (this.getEffect(oldS) != this.getEffect(newS) || oldS.getTagCompound().getInteger("selected") != newS.getTagCompound().getInteger("selected") || slotChanged){
				return true;
			}
		}
		return slotChanged;
	}
}
