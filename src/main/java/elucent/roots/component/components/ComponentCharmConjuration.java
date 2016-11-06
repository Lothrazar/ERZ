package elucent.roots.component.components;

import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.projectile.EntityRitualProjectile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

import org.lwjgl.Sys;

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

            int BridgeSize = 10 + (int)size;
        	BlockPos bridgePos = Util.getRayTrace(world, ((EntityPlayer)caster), 16);
            EnumFacing bridgeFace = Util.getRayFace(world,((EntityPlayer)caster), 16);
            if (!world.isAirBlock(bridgePos)){
	            for(int i = 0;i <= 4;i++)
	            {
	            	buildBlock(world, bridgePos.offset(bridgeFace,i+1));
	            }
            }

        }
    }

    @Override
    public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if(type == EnumCastType.SPELL)
        {

            int BridgeSize = 4 + (int)size;
            if (world.getPlayerEntityByUUID(casterId) != null){
            	BlockPos bridgePos = Util.getRayTrace(world, world.getPlayerEntityByUUID(casterId), 16);
	            EnumFacing bridgeFace = Util.getRayFace(world,world.getPlayerEntityByUUID(casterId), 16);
	            if (!world.isAirBlock(bridgePos)){
		            for(int i = 0;i <= 4;i++)
		            {
		            	buildBlock(world, bridgePos.offset(bridgeFace,i+1));
		            }
	            }
            }

        }
    }

    private static void buildBlock(World world,BlockPos pos)
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
