package elucent.roots.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

import elucent.roots.Roots;

/**
 * Created by SirShadow on 25. 08. 2016.
 */
public class ItemPursuitTalisman extends Item
{
    public ItemPursuitTalisman()
    {
    	super();
        setUnlocalizedName("talismanPursuit");
        setCreativeTab(Roots.tab);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }
}
