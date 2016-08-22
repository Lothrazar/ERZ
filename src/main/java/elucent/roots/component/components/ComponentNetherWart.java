package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import elucent.roots.ConfigManager;
import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntityAccelerator;
import elucent.roots.entity.EntityNetherInfection;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ComponentNetherWart extends ComponentBase{
	Random random = new Random();
	public ComponentNetherWart(){
		super("netherwart","Inferno",Items.NETHER_WART,10);	
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			Entity entity = Util.getRayTraceEntity(world, (EntityPlayer)caster, 4+2*(int)size);
			if (entity != null){
				if (!world.isRemote){
					double tposX = (entity.posX);
					double tposY = (entity.posY)+entity.getEyeHeight()/2.0f;
					double tposZ = (entity.posZ);
					EntityNetherInfection a = new EntityNetherInfection(world,caster.getUniqueID(),entity,(int)potency,(int)size);
					world.spawnEntityInWorld(a);
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
				if (!world.isRemote){
					double tposX = (entity.posX);
					double tposY = (entity.posY)+entity.getEyeHeight()/2.0f;
					double tposZ = (entity.posZ);
					EntityNetherInfection a = new EntityNetherInfection(world,casterId,entity,(int)potency,(int)size);
					world.spawnEntityInWorld(a);
				}
			}
		}
	}
}
