package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;

import elucent.roots.ConfigManager;
import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.RootsNames;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentEffect;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntityFrostShard;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ComponentWhiteTulip extends ComponentBase{
	Random random = new Random();
	public ComponentWhiteTulip(){
		super("whitetulip","Blistering Cold",Blocks.RED_FLOWER,6,8);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			for (int i = 0; i < 6; i ++){
				if (!world.isRemote){
					EntityFrostShard shard = new EntityFrostShard(world);
					shard.initSpecial(3.0f+2.0f*(float)potency);
					shard.setPosition(caster.posX, caster.posY+caster.getEyeHeight(), caster.posZ);
					shard.motionX = 0.75*(caster.getLookVec().xCoord+0.25*size*(random.nextFloat()-0.5));
					shard.motionY = 0.75*(caster.getLookVec().yCoord+0.125*size*(random.nextFloat()-0.5));
					shard.motionZ = 0.75*(caster.getLookVec().zCoord+0.125*size*(random.nextFloat()-0.5));
					shard.setPosition(shard.posX+shard.motionX*2.0, shard.posY+shard.motionY*2.0, shard.posZ+shard.motionZ*2.0);
					world.spawnEntityInWorld(shard);
				}
			}
		}
	}
	
	@Override
	public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL && !world.isRemote){
			for (int i = 0; i < 8; i ++){
				EntityFrostShard shard = new EntityFrostShard(world);
				shard.initSpecial(3.0f+2.0f*(float)potency);
				shard.setPosition(x, y+0.25, z);
				shard.setVelocity(shard.motionX*(1.0+0.5*size), shard.motionY*(1.0+0.5*size), shard.motionZ*(1.0+0.5*size));
				world.spawnEntityInWorld(shard);
			}
		}
	}
}
