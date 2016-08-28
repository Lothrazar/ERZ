package elucent.roots.item;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by SirShadow on 23. 08. 2016.
 */
public class ItemTalisman extends Item
{
    public ItemTalisman(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Roots.tab);
        setMaxStackSize(1);
        setNoRepair();

        RegistryManager.TALISMAN_ITEM.add(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModelsAndVariants()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName().toString()));
    }
}
