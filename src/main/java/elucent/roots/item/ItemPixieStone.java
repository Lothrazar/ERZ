package elucent.roots.item;

import elucent.roots.ConfigManager;
import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
public class ItemPixieStone extends Item
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
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }
}
