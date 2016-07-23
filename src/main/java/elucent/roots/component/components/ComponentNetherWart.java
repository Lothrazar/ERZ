package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;

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
					if (world.getEntitiesWithinAABB(EntityAccelerator.class, new AxisAlignedBB(entity.posX-0.1,entity.posY-0.1,entity.posZ-0.1,entity.posX+0.1,entity.posY+0.1,entity.posZ+0.1)).size() == 0){
						EntityNetherInfection a = new EntityNetherInfection(world,caster.getUniqueID(),entity,(int)potency,(int)size);
						world.spawnEntityInWorld(a);
					}
				}
			}
		}
	}
}
