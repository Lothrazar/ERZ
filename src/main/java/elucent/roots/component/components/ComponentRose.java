package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.UUID;

import elucent.roots.ConfigManager;
import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.RootsNames;
import elucent.roots.Util;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ComponentRose extends ComponentBase {
	public ComponentRose(){
		super("rosebush", "Rose's Thorns", Blocks.DOUBLE_PLANT,4,8);
	}
	
	@Override
	public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-(2.0+size),y-(2.0+size),z-size,x+(2.0+size),y+(2.0+size),z+(2.0+size)));
			int damageDealt = 0;
			for (int i = 0; i < targets.size(); i ++){
				if (targets.get(i).getUniqueID() != caster.getUniqueID()){
					if (targets.get(i) instanceof EntityPlayer && ConfigManager.disablePVP){
					}
					else {
						if (caster instanceof EntityPlayer){
							if (!((EntityPlayer)caster).hasAchievement(RegistryManager.achieveSpellRose)){
								PlayerManager.addAchievement(((EntityPlayer)caster), RegistryManager.achieveSpellRose);
							}
						}
						targets.get(i).attackEntityFrom(DamageSource.cactus, (int)(7+2*potency));
						targets.get(i).attackEntityAsMob(caster);
						Util.addTickTracking(targets.get(i));
						targets.get(i).setLastAttacker(caster);
						targets.get(i).setRevengeTarget((EntityLivingBase)caster);
						targets.get(i).getEntityData().setFloat(RootsNames.TAG_SPELL_THORNS_DAMAGE, 2.0f+(float)potency);
					}
				}
			}
			if (damageDealt > 80){
				if (caster instanceof EntityPlayer){
					if (!((EntityPlayer)caster).hasAchievement(RegistryManager.achieveLotsDamage)){
						PlayerManager.addAchievement(((EntityPlayer)caster), RegistryManager.achieveLotsDamage);
					}
				}
			}
		}
	}
	
	@Override
	public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
		if (type == EnumCastType.SPELL){
			EntityPlayer player = world.getPlayerEntityByUUID(casterId);
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-(2.0+size),y-(2.0+size),z-size,x+(2.0+size),y+(2.0+size),z+(2.0+size)));
			int damageDealt = 0;
			for (int i = 0; i < targets.size(); i ++){
				if (targets.get(i).getUniqueID() != casterId){
					if (targets.get(i) instanceof EntityPlayer && ConfigManager.disablePVP){
					}
					else {
						if (player != null){
							if (!player.hasAchievement(RegistryManager.achieveSpellRose)){
								PlayerManager.addAchievement(player, RegistryManager.achieveSpellRose);
							}
						}
						targets.get(i).attackEntityFrom(DamageSource.cactus, (int)(7+2*potency));
						Util.addTickTracking(targets.get(i));
						if (player != null){
							targets.get(i).attackEntityAsMob(player);
							targets.get(i).setLastAttacker(player);
							targets.get(i).setRevengeTarget((EntityLivingBase)player);
						}
						targets.get(i).getEntityData().setFloat(RootsNames.TAG_SPELL_THORNS_DAMAGE, 2.0f+(float)potency);
					}
				}
			}
			if (damageDealt > 80){
				if (player != null){
					if (!player.hasAchievement(RegistryManager.achieveLotsDamage)){
						PlayerManager.addAchievement(player, RegistryManager.achieveLotsDamage);
					}
				}
			}
		}
	}
}
