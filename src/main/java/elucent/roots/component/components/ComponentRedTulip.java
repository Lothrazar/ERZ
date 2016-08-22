package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;

import elucent.roots.PlayerManager;
import elucent.roots.RootsNames;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentEffect;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntitySanctuary;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ComponentRedTulip extends ComponentBase{
	Random random = new Random();
	public ComponentRedTulip(){
		super("redtulip","Devil's Flower",Blocks.RED_FLOWER,6);
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			BlockPos pos = Util.getRayTrace(world, (EntityPlayer)caster, 6+2*(int)size);
			if (!world.isRemote){
				world.spawnEntityInWorld(new EntitySanctuary(world, pos.getX()+0.5, pos.getY()+1.0, pos.getZ()+0.5, (int)potency, (int)size));
			}
		}
	}

	@Override
	public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			BlockPos pos = new BlockPos(x,y,z);
			if (!world.isRemote){
				world.spawnEntityInWorld(new EntitySanctuary(world, pos.getX()+0.5, pos.getY()+1.0, pos.getZ()+0.5, (int)potency, (int)size));
			}
		}
	}
}
