package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.Lists;

import elucent.roots.PlayerManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.ComponentEffect;
import elucent.roots.component.EnumCastType;
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


public class ComponentApple extends ComponentBase{
	Random random = new Random();
	public ComponentApple(){
		super("apple","Nature's Cure",Items.APPLE,16);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			if (caster instanceof EntityPlayer){
				ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>(((EntityPlayer)caster).getActivePotionEffects());
				((EntityPlayer)caster).clearActivePotions();
				for (int i = 0; i < effects.size(); i ++){
					PotionEffect effect = effects.get(i);
					if (effect.getPotion().getName() == "Soul Fray"){
						((EntityPlayer)caster).addPotionEffect(effect);
					}
					((EntityLivingBase)caster).heal(2.0f+2.0f*(float)potency);
				}
			}
		}
	}
	
	@Override
	public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			List<EntityLivingBase> targets = (List<EntityLivingBase>)world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-0.25,y-0.25,z-0.25,x+0.25,y+0.25,z+0.25));
			if (targets.size() > 0){
				EntityLivingBase entity = targets.get(0);
				ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>(entity.getActivePotionEffects());
				entity.clearActivePotions();
				for (int i = 0; i < effects.size(); i ++){
					PotionEffect effect = effects.get(i);
					if (effect.getPotion().getName() == "Soul Fray"){
						(entity).addPotionEffect(effect);
					}
					entity.heal(4.0f+4.0f*(float)potency);
				}
			}
		}
	}
}
