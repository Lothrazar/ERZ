package elucent.roots.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import elucent.roots.RegistryManager;
import elucent.roots.Roots;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRootsLeaves extends BlockLeaves {
	public boolean leavesFancy = true;
	public BlockRootsLeaves(String name, Material material, float hardness){
		super();
		setUnlocalizedName(name);
		setRegistryName(Roots.MODID+":"+name);
		setCreativeTab(Roots.tab);
		setHardness(hardness);
	}

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isFullyOpaque() && rand.nextInt(15) == 1)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)pos.getY() - 0.05D;
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
	
	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, this.DECAYABLE,this.CHECK_DECAY);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean canRenderInLayer(BlockRenderLayer layer){
		return leavesFancy && layer == BlockRenderLayer.CUTOUT_MIPPED || !leavesFancy && layer == BlockRenderLayer.SOLID;
	}

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }


    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return !leavesFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : true;
    }

    public boolean isVisuallyOpaque()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void setGraphicsLevel(boolean fancy)
    {
        this.leavesFancy = fancy;
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return !this.leavesFancy;
    }
	
	@Override
	public int getMetaFromState(IBlockState state){
		if (state.getValue(DECAYABLE) && state.getValue(CHECK_DECAY)){
			return 3;
		}
		if (!state.getValue(DECAYABLE) && state.getValue(CHECK_DECAY)){
			return 2;
		}
		if (state.getValue(DECAYABLE) && !state.getValue(CHECK_DECAY)){
			return 1;
		}
		if (!state.getValue(DECAYABLE) && !state.getValue(CHECK_DECAY)){
			return 0;
		}
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta){
		if (meta == 3){
			return getDefaultState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, true);
		}
		if (meta == 2){
			return getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, true);
		}
		if (meta == 1){
			return getDefaultState().withProperty(DECAYABLE, true).withProperty(CHECK_DECAY, false);
		}
		if (meta == 0){
			return getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false);
		}
		return getDefaultState().withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false);
	}
	
	@Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int chance = this.getSaplingDropChance(state);

        if (fortune > 0)
        {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (rand.nextInt(chance) == 0)
            //ret.add(new ItemStack(RegistryManager.saplingWildwood, 1));

        chance = 200;
        if (fortune > 0)
        {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }

        this.captureDrops(true);
        if (world instanceof World)
            this.dropApple((World)world, pos, state, chance); // Dammet mojang
        ret.addAll(this.captureDrops(false));
        return ret;
    }
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(),"inventory"));
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(new ItemStack(this,1));
		return items;
	}

	@Override
	public EnumType getWoodType(int meta) {
		return EnumType.OAK;
	}
}
