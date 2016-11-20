package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.EntityHomingProjectile;
import elucent.roots.entity.projectile.EntityRitualProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Created by SirShadow on 20. 08. 2016.
 */
public class ComponentCharmEvocation extends ComponentBase
{
	Random random = new Random();
    public ComponentCharmEvocation()
    {
        super("evocation", "Evocation", RegistryManager.itemCharmEvocation,8);
    }

    @Override
    public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size)
    {
        if(type == EnumCastType.SPELL)
        {
        	if(!world.isRemote)
            {
        		for (int k = 0; k < 5; k ++){
	                List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-16.0,y-16.0,z-16.0,x+16.0,y+16.0,z+16.0));
	                ArrayList<EntityLivingBase> trimmedTargets = new ArrayList<EntityLivingBase>();
	                for (int i = 0; i < targets.size(); i ++){
	                	if (targets.get(i).getUniqueID().compareTo(caster.getUniqueID()) != 0){
	                		trimmedTargets.add(targets.get(i));
	                	}
	                }
	                if (trimmedTargets.size() > 0){
	                	Vec3d direction = ((EntityPlayer)caster).getLookVec();
	                    EntityHomingProjectile projectile = new EntityHomingProjectile(world);
	                    projectile.setPosition(x, y, z);
	                    projectile.motionX = 0;
	                    projectile.motionY = 0;
	                    projectile.motionZ = 0;
	                    projectile.rotationPitch = 0;
	                    projectile.rotationYaw = 0;
	                    projectile.onInitialSpawn(world.getDifficultyForLocation(projectile.getPosition()), null);
	                    projectile.initSpecial(trimmedTargets.get(random.nextInt(trimmedTargets.size())), 6.0f, new Vec3d(76,230,0));
	                    world.spawnEntityInWorld(projectile);
	                }
        		}
            }
        }
    }

    @Override
    public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size)
    {
        if(type == EnumCastType.SPELL)
        {
            if(!world.isRemote)
            {
            	for (int k = 0; k < 5; k ++){
	            	List<EntityLivingBase> targets = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-16.0,y-16.0,z-16.0,x+16.0,y+16.0,z+16.0));
	                ArrayList<EntityLivingBase> trimmedTargets = new ArrayList<EntityLivingBase>();
	                for (int i = 0; i < targets.size(); i ++){
	                	if (targets.get(i).getUniqueID().compareTo(casterId) != 0){
	                		trimmedTargets.add(targets.get(i));
	                	}
	                }
	                if (trimmedTargets.size() > 0){
	                    EntityHomingProjectile projectile = new EntityHomingProjectile(world);
	                    projectile.setPosition(x, y, z);
	                    projectile.motionX = 0;
	                    projectile.motionY = 0;
	                    projectile.motionZ = 0;
	                    projectile.rotationPitch = 0;
	                    projectile.rotationYaw = 0;
	                    projectile.onInitialSpawn(world.getDifficultyForLocation(projectile.getPosition()), null);
	                    projectile.initSpecial(trimmedTargets.get(random.nextInt(trimmedTargets.size())), 6.0f, new Vec3d(76,230,0));
	                    world.spawnEntityInWorld(projectile);
	                }
            	}
            }
        }
    }
}
