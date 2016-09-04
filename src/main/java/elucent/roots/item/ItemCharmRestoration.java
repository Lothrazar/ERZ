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
public class ItemCharmRestoration extends Item implements IImbuable
{
    public ItemCharmRestoration(String name)
    {
        super();
        setUnlocalizedName(name);
        setCreativeTab(Roots.tab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(),"inventory"));
    }

	@Override
	public String getEffect(ItemStack stack) {
		return "restoration";
	}

	@Override
	public int getPotency(ItemStack stack) {
		return 0;
	}

	@Override
	public int getEfficiency(ItemStack stack) {
		return 0;
	}

	@Override
	public int getSize(ItemStack stack) {
		return 0;
	}
}
