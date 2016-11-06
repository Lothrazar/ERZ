package elucent.roots.item;

import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by SirShadow for the mod Roots on 17.7.2016.
 */
public class ItemCharm extends Item
{
    public ItemCharm(String name)
    {
        super();
        setUnlocalizedName(name);
        //setCreativeTab(Roots.tab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}
