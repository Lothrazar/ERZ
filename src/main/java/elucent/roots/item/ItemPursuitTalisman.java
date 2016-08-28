package elucent.roots.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

/**
 * Created by SirShadow on 25. 08. 2016.
 */
public class ItemPursuitTalisman extends ItemTalisman
{
    public ItemPursuitTalisman()
    {
        super("pursuitTalisman");
    }

    @SubscribeEvent
    public void onEnemyKilled(LivingDeathEvent event)
    {
        if (event.getSource() == null || event.getSource().getEntity() == null || !(event.getSource().getEntity() instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

        if (player.inventory.hasItemStack(new ItemStack(this)))
        {
            int x, y, z;
            x = (int) player.posX;
            y = (int) player.posY;
            z = (int) player.posZ;

            ArrayList<EntityLivingBase> entities = (ArrayList<EntityLivingBase>) player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - 2.0, y - 2.0, z - 2.0, x + 3.0, y + 3.0, z + 3.0));
            for (int i = 0; i < entities.size(); i++)
            {
                if (entities.get(i).getUniqueID() != player.getUniqueID())
                {
                    entities.get(i).setFire(5);
                }
            }
        }
        else
        {
            player.addChatComponentMessage(new TextComponentString("Hehhehs!!"));
        }
    }
}
