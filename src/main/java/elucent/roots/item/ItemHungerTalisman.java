package elucent.roots.item;

import elucent.roots.ConfigManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * Created by SirShadow on 23. 08. 2016.
 */
public class ItemHungerTalisman extends ItemTalisman
{

    public static int timer = 0;
    int maxTime;

    public static boolean restart = false;

    public ItemHungerTalisman()
    {
        super("hungerTalisman");
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if (restart)
        {
            tooltip.add(TextFormatting.LIGHT_PURPLE + "Cooldown:");
            tooltip.add(Integer.toString(timer));
            tooltip.add(Integer.toString(maxTime));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        maxTime = ConfigManager.cooldownTime;
        if(restart)
        {
            if (timer < maxTime)
            {
                timer++;
            }
            if(timer == maxTime)
            {
                restart = false;
                timer = 0;
            }
        }

    }


    @SubscribeEvent
    public void onPlayerAttackEvent(LivingAttackEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)event.getEntity();

            if(event.getSource().isExplosion())
            {
                if(timer == 0)
                {
                    if (player.inventory.hasItemStack(new ItemStack(this)))
                    {
                        event.setCanceled(true);
                        restart = true;
                    }
                }
            }
        }

    }
}
