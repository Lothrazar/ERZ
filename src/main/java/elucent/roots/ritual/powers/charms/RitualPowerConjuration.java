package elucent.roots.ritual.powers.charms;

import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 18.7.2016.
 */
public class RitualPowerConjuration extends RitualPower
{
    public RitualPowerConjuration()
    {
        super("conjuration", EnumPowerType.TYPE_TARGET_BLOCK, 64);
    }

    @Override
    public void onRightClickBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state)
    {
        //Nothing yet
    }
}

