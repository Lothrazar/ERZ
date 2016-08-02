package elucent.roots.component.components;

import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 27.7.2016.
 */
public class ComponentCharmConjuration extends ComponentBase
{
    public ComponentCharmConjuration()
    {
        super("conjuration", "Conjuration", RegistryManager.itemCharmConjuration, 8);
    }

    @Override
    public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if(type == EnumCastType.SPELL)
        {

            int BridgeSize = 10 * (int)size;

            for(int i = 0;i <= size;i++)
            {
                BlockPos bridgePos = getThePosForBlock(world,(EntityPlayer)caster, i + BridgeSize);

                buildBlock((EntityPlayer)caster,world,bridgePos);
            }
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

    private static BlockPos getThePosForBlock(World world, EntityPlayer player, int reachDistance){
        double x = player.posX;
        double y = player.posY  - 2;
        double z = player.posZ;
        for (int i = 0; i < reachDistance*40.0; i ++){
            x += player.getLookVec().xCoord*0.025;
            y += player.getLookVec().yCoord*0.025;
            z += player.getLookVec().zCoord*0.025;
            if (!world.getBlockState(new BlockPos(x,y,z)).getBlock().isFullCube(world.getBlockState(new BlockPos(x,y,z)))){
                return new BlockPos(x,y,z);
            }
        }
        return new BlockPos(x,y,z);
    }
}
