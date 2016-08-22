package elucent.roots.item;

import java.util.List;
import java.util.Random;

import elucent.roots.Roots;
import elucent.roots.Util;
import elucent.roots.capability.mana.ManaProvider;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentManager;
import elucent.roots.component.EnumCastType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemCastingBase extends Item{
	public static SoundEvent castingSound = new SoundEvent(new ResourceLocation("roots:staffCast"));
	public static int spellSlots = 0;
	
	public ItemCastingBase(String name, int spellSlots){
		super();
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
		return EnumAction.BOW;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack){
		return 72000;
	}
	
	public static void createData(ItemStack stack){
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setInteger("selected", 1);
		for (int i = 1; i < spellSlots+1; i ++){
			stack.getTagCompound().setInteger("potency"+i, 0);
			stack.getTagCompound().setInteger("efficiency"+i, 0);
			stack.getTagCompound().setInteger("size"+i, 0);
			stack.getTagCompound().setString("effect"+i, "");
			stack.getTagCompound().setInteger("maxUses"+i, 0);
			stack.getTagCompound().setInteger("uses"+i, 0);
		}
	}
	
	public double getCost(ComponentBase component, int potency, int efficiency){
		return (component.xpCost+(float)potency*2.0f)/(0.75*efficiency+1);//(component.xpCost + potency)*(((1.0+(0.75/((double)efficiency)))/2.0))*(1.0/((0.75+(1.0/((double)potency)))/2.0));
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft){
		if (timeLeft < (72000-12)){
			this.cast(stack,world,player);
		}
	}
	
	public void cast(ItemStack stack, World world, EntityLivingBase player){
		if (stack.hasTagCompound()){
			BlockPos pos = new BlockPos(player.posX,player.posY,player.posZ);
			if (true){
				world.playSound(player.posX, player.posY, player.posZ, this.castingSound, SoundCategory.PLAYERS, 0.95f+0.1f*itemRand.nextFloat(), 0.95f+0.1f*itemRand.nextFloat(), false);
				ComponentBase comp = ComponentManager.getComponentFromName(this.getEffect(stack));
				if (comp != null){
					int potency = this.getPotency(stack);
					int efficiency = this.getEfficiency(stack);
					int size = this.getSize(stack);
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
					double cost = this.getCost(comp, potency, efficiency);
					System.out.println("COST: " + cost);
					Random random = new Random();
					if (((EntityPlayer)player).hasCapability(ManaProvider.manaCapability, null) && ManaProvider.get((EntityPlayer)player).getMana() >= ((float)cost)){
						ManaProvider.get((EntityPlayer)player).setMana((EntityPlayer)player, ManaProvider.get((EntityPlayer)player).getMana()-(((float)cost)));
						this.doEffect(world,(EntityPlayer)player,comp,this.getPotency(stack),this.getEfficiency(stack),this.getSize(stack));
						for (int i = 0 ; i < 90; i ++){
							double offX = random.nextFloat()*0.5-0.25;
							double offY = random.nextFloat()*0.5-0.25;
							double offZ = random.nextFloat()*0.5-0.25;
							double coeff = (offX+offY+offZ)/1.5+0.5;
							double dx = (player.getLookVec().xCoord+offX)*coeff;
							double dy = (player.getLookVec().yCoord+offY)*coeff;
							double dz = (player.getLookVec().zCoord+offZ)*coeff;
							if (random.nextBoolean()){
								Roots.proxy.spawnParticleMagicFX(world, player.posX+dx, player.posY+1.5+dy, player.posZ+dz, dx, dy, dz, comp.primaryColor.xCoord, comp.primaryColor.yCoord, comp.primaryColor.zCoord);
							}
							else {
								Roots.proxy.spawnParticleMagicFX(world, player.posX+dx, player.posY+1.5+dy, player.posZ+dz, dx, dy, dz, comp.secondaryColor.xCoord, comp.secondaryColor.yCoord, comp.secondaryColor.zCoord);
							}
						}
					}
				}
			}
		}
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
	
	public int getUseCount(int efficiency){
		return 33+16*efficiency;
	}
	
	public void setEffectInSlot(ItemStack stack, int slot, String effect, int potency, int efficiency, int size){
		if (slot > spellSlots){
			slot = spellSlots;
		}
		stack.getTagCompound().setString("effect"+slot, effect);
		stack.getTagCompound().setInteger("potency"+slot, potency);
		stack.getTagCompound().setInteger("efficiency"+slot, efficiency);
		stack.getTagCompound().setInteger("size"+slot, size);
		stack.getTagCompound().setInteger("maxUses"+slot, getUseCount(efficiency));
		stack.getTagCompound().setInteger("uses"+slot, stack.getTagCompound().getInteger("maxUses"+slot));
	}
	
	public void doEffect(World world, EntityPlayer player, ComponentBase component, int potency, int efficiency, int size){
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
	
	public static Integer getPotency(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("potency"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static Integer getEfficiency(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("efficiency"+stack.getTagCompound().getInteger("selected"));
		}
		return 0;
	}
	
	public static Integer getSize(ItemStack stack){
		if (stack.hasTagCompound()){
			return stack.getTagCompound().getInteger("size"+stack.getTagCompound().getInteger("selected"));
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
			if(world.isRemote && Minecraft.getMinecraft().currentScreen != null){
				return new ActionResult(EnumActionResult.FAIL, stack);
			} else{
				player.setActiveHand(hand);
				return new ActionResult(EnumActionResult.PASS, stack);
			}
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
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
		if (stack.hasTagCompound()){
			ComponentBase comp = ComponentManager.getComponentFromName(this.getEffect(stack));
			int potency = this.getPotency(stack);
			int efficiency = this.getEfficiency(stack);
			int size = this.getSize(stack);
			if (comp != null){
				comp.castingAction((EntityPlayer) player, count, potency, efficiency, size);
				if (itemRand.nextBoolean()){	
					Roots.proxy.spawnParticleMagicLineFX(player.getEntityWorld(), player.posX+2.0*(itemRand.nextFloat()-0.5), player.posY+2.0*(itemRand.nextFloat()-0.5)+1.0, player.posZ+2.0*(itemRand.nextFloat()-0.5), player.posX, player.posY+1.0, player.posZ, comp.primaryColor.xCoord, comp.primaryColor.yCoord, comp.primaryColor.zCoord);
				}
				else {
					Roots.proxy.spawnParticleMagicLineFX(player.getEntityWorld(), player.posX+2.0*(itemRand.nextFloat()-0.5), player.posY+2.0*(itemRand.nextFloat()-0.5)+1.0, player.posZ+2.0*(itemRand.nextFloat()-0.5), player.posX, player.posY+1.0, player.posZ, comp.secondaryColor.xCoord, comp.secondaryColor.yCoord, comp.secondaryColor.zCoord);
				}
			}
		}
	}
}
