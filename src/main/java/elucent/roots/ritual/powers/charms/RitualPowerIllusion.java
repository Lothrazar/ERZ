package elucent.roots.ritual.powers.charms;

import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 18.7.2016.
 */
public class RitualPowerIllusion extends RitualPower
{
    public RitualPowerIllusion()
    {
        super("illusion", EnumPowerType.TYPE_TARGET_ENTITY, 64);
    }

    @Override
    public void onRightClickEntity(EntityPlayer player, World world, Entity entity)
    {
        //Nothing yet
    }
}
