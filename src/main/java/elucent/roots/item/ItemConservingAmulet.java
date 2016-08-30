package elucent.roots.item;

import elucent.roots.ConfigManager;
import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by SirShadow on 23. 08. 2016.
 */
public class ItemConservingAmulet extends Item
{

    public ItemConservingAmulet()
    {
        super();
        setUnlocalizedName("amuletConserving");
        setCreativeTab(Roots.tab);
        setMaxStackSize(1);
    }
    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldS, ItemStack newS, boolean slotChanged){
    	if (oldS.hasTagCompound() && newS.hasTagCompound()){
    		if (oldS.getTagCompound().getInteger("charge") > 0 && newS.getTagCompound().getInteger("charge") == 0){
    			return true;
    		}
    	}
    	return slotChanged;
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	if (!stack.hasTagCompound()){
    		stack.setTagCompound(new NBTTagCompound());
    		stack.getTagCompound().setInteger("charge", 0);
    	}
    }
    
    @Override
    public boolean hasEffect(ItemStack stack){
    	if (stack.hasTagCompound()){
    		if (stack.getTagCompound().getInteger("charge") > 40){
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
