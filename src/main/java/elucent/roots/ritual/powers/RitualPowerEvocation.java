package elucent.roots.ritual.powers;

import elucent.roots.entity.projectile.EntityRitualProjectile;
import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 19.7.2016.
 */
public class RitualPowerEvocation extends RitualPower {
    public RitualPowerEvocation() {
        super("evocation", EnumPowerType.TYPE_TARGET_ANY, 64);
    }

    @Override
    public void onRightClick(EntityPlayer player, World world, BlockPos pos, IBlockState state)
    {
        //Nothing yet
    }
}
