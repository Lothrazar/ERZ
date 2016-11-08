package elucent.roots.item;

import elucent.roots.ConfigManager;
import elucent.roots.Roots;
<<<<<<< HEAD
import elucent.roots.capability.mana.ManaProvider;
import elucent.roots.network.MessageDirectedTerraBurstFX;
import elucent.roots.network.PacketHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
=======
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Created by SirShadow on 23. 08. 2016.
 */
<<<<<<< HEAD
public class ItemPixieStone extends Item implements IManaRelatedItem
=======
public class ItemPixieStone extends Item
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
{
	Random random = new Random();
    public ItemPixieStone()
    {
        super();
        setUnlocalizedName("pixieStone");
        setCreativeTab(Roots.tab);
        setMaxStackSize(1);
    }
    
    @Override
<<<<<<< HEAD
    public EnumAction getItemUseAction(ItemStack stack){
    	return EnumAction.BOW;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack){
    	return 72000;
    }
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected){
		if (!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("expense", 0);
		}
		else {
			if (stack.getTagCompound().getInteger("expense") > 0){
				if (entity.onGround){
					stack.getTagCompound().setInteger("expense", 0);
				}
			}
		}
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
		return slotChanged || oldStack.getItem() != newStack.getItem();
	}
    
    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
    	player.motionX = player.getLookVec().xCoord;
    	player.motionY = player.getLookVec().yCoord;
    	player.motionZ = player.getLookVec().zCoord;
    	((EntityPlayer)player).getCapability(ManaProvider.manaCapability, null).setMana((EntityPlayer)player, ((EntityPlayer)player).getCapability(ManaProvider.manaCapability, null).getMana()-2f);
    	player.fallDistance = 0;
    	if (((EntityPlayer)player).getCapability(ManaProvider.manaCapability, null).getMana() == 0){
    		stack.getTagCompound().setInteger("expense",(int)((EntityPlayer)player).getCapability(ManaProvider.manaCapability, null).getMaxMana());
    	}
    	if (stack.getTagCompound().getInteger("expense") >= ((EntityPlayer)player).getCapability(ManaProvider.manaCapability, null).getMaxMana()){
    		player.resetActiveHand();
    	}
    	if (!player.getEntityWorld().isRemote){
    		PacketHandler.INSTANCE.sendToAll(new MessageDirectedTerraBurstFX((float)player.posX,(float)player.posY+player.height/2.0f,(float)player.posZ,-(float)player.getLookVec().xCoord,-(float)player.getLookVec().yCoord,-(float)player.getLookVec().zCoord));
    	}	
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
    	if (player.hasCapability(ManaProvider.manaCapability, null) && !player.onGround){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().getInteger("expense") < player.getCapability(ManaProvider.manaCapability, null).getMaxMana()){
		    		player.setActiveHand(hand);
		        	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,stack);
				}
			}
    	}
    	return new ActionResult<ItemStack>(EnumActionResult.PASS,stack);
=======
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
    	if (stack.hasTagCompound()){
    		if (stack.getTagCompound().getBoolean("active")){
    			stack.getTagCompound().setBoolean("active", false);
    		}
    		else {
    			stack.getTagCompound().setBoolean("active", true);
    		}
    	}
    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS,stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (!stack.hasTagCompound()){
    		stack.setTagCompound(new NBTTagCompound());
    		stack.getTagCompound().setBoolean("active", false);
    	}
    	else {
    		if (stack.getTagCompound().getBoolean("active")){
		    	if (entityIn.motionY < -0.65){
		    		entityIn.motionY = 0.25;
		    		entityIn.fallDistance = 0;
		    		for (int j = 0; j < 20; j ++){
						Roots.proxy.spawnParticleMagicSparkleFX(world, entityIn.posX, entityIn.posY, entityIn.posZ, Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), Math.pow(0.95f*(random.nextFloat()-0.5f),3.0), 76, 230, 0);
					}
		    		if (entityIn instanceof EntityPlayer){
		    			((EntityPlayer)entityIn).velocityChanged = true;
		    		}
		    	}
	    	}
	    }
    }
    
    @Override
    public boolean hasEffect(ItemStack stack){
    	if (stack.hasTagCompound()){
    		if (stack.getTagCompound().getBoolean("active")){
    			return true;
    		}
    	}
    	return false;
>>>>>>> 513884af035d63cee30da3c9f8d1ffd5b51b0114
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }
}
