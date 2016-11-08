package elucent.roots.block;

import java.util.Random;

import javax.annotation.Nullable;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrassBase extends Block{
	Block dirtBlock;
	public BlockGrassBase(String name, Block dirtBlock, Material material, float hardness){
		super(material);
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setCreativeTab(Roots.tab);
		setHardness(hardness);
		setTickRandomly(true);
		this.dirtBlock = dirtBlock;
	}
	
	@Override
	public boolean requiresUpdates(){
		return true;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if (!world.isRemote)
        {
            if (world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2)
            {
            	world.setBlockState(pos, dirtBlock.getDefaultState());
            }
            else
            {
                if (world.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !world.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = world.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = world.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == dirtBlock && world.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(world, pos.up()) <= 2)
                        {
                        	world.setBlockState(blockpos, getDefaultState());
                        }
                    }
                }
            }
        }
	}
	
	@Override
	public SoundType getSoundType(){
		return SoundType.PLANT;
	}
	
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type){
		return Blocks.GRASS.canCreatureSpawn(state, world, pos, type);
	}
	
	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable){
		return Blocks.DIRT.canSustainPlant(state, world, pos, direction, plantable);
	}
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return dirtBlock.getItemDropped(Blocks.DIRT.getDefaultState(), rand, fortune);
    }
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}
}
