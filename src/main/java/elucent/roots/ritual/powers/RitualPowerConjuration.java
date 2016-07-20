package elucent.roots.ritual.powers;

import elucent.roots.RegistryManager;
import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
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
    public void onRightClickBlock(EntityPlayer player, World world, BlockPos pos, IBlockState state, EnumFacing facing)
    {
        super.onRightClickBlock(player,world,pos,state,facing);
        buildBridge(player,world,facing,pos,10);
    }


    /**
     *
     * @param player
     * @param world
     * @param sideHit
     * @param pos
     * @param bridgeSize sets the length of the bridge
     */
    private static void buildBridge(EntityPlayer player,World world,EnumFacing sideHit,BlockPos pos,int bridgeSize)
    {

        for(int i = 0;i <= bridgeSize;i++)
        {
            /**
             * Get's the opposite side of the one you right clicked
             */
            BlockPos bridgePos = pos.offset(sideHit.getOpposite(),i);

            buildBlock(player,world,bridgePos);
        }
    }

    private static void buildBlock(EntityPlayer player,World world,BlockPos pos)
    {
        if(!world.isBlockLoaded(pos))
        {
            return;
        }

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block == null||block.isAir(state,world,pos)||block.isReplaceable(world,pos))
        {
            if(!world.isRemote)
            {
                world.setBlockState(pos, RegistryManager.bridge.getDefaultState());
            }
        }
    }
}

