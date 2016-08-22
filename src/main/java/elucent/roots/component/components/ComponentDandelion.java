package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ComponentDandelion extends ComponentBase{
	Random rnd = new Random();
	public ComponentDandelion(){
		super("dandelion","Dandelion Winds",Blocks.YELLOW_FLOWER,8);
		
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-(4.0+size),y-(4.0+size),z-(4.0+size),x+(4.0+size),y+(4.0+size),z+(4.0+size)));
			for(int i = 0; i < targets.size();i++){
				if(targets.get(i).getUniqueID() != caster.getUniqueID()){
					targets.get(i).motionX = caster.getLookVec().xCoord;
					targets.get(i).motionY = (float)(potency==0?0.6:0.6+0.3*potency);
					targets.get(i).motionZ = caster.getLookVec().zCoord;
					if (targets.get(i) instanceof EntityPlayer){
						((EntityPlayer)targets.get(i)).velocityChanged = true;
					}
				}
			}
		}
	}
	
	@Override
	public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){	
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-(4.0+size),y-(4.0+size),z-(4.0+size),x+(4.0+size),y+(4.0+size),z+(4.0+size)));
			for(int i = 0; i < targets.size();i++){
				if(targets.get(i).getUniqueID() != casterId){
					double dist = Math.sqrt((targets.get(i).posX-x)*(targets.get(i).posX-x)+(targets.get(i).posZ-z)*(targets.get(i).posZ-z));
					targets.get(i).motionX = (targets.get(i).posX-x)/dist;
					targets.get(i).motionY = (float)(potency==0?0.6:0.6+0.3*potency);
					targets.get(i).motionZ = (targets.get(i).posZ-z)/dist;
					if (targets.get(i) instanceof EntityPlayer){
						((EntityPlayer)targets.get(i)).velocityChanged = true;
					}
				}
			}
		}
	}
}