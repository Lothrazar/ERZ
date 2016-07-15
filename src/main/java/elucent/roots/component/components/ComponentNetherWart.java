package elucent.roots.component.components;

import java.util.ArrayList;
import java.util.Random;

import elucent.roots.ConfigManager;
import elucent.roots.PlayerManager;
import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
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
			int damageDealt = 0;
			ArrayList<EntityLivingBase> targets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x-size,y-size,z-size,x+size,y+size,z+size));
			for (int i = 0; i < targets.size(); i ++){
				if (targets.get(i).getUniqueID() != caster.getUniqueID()){
					if (targets.get(i) instanceof EntityPlayer && ConfigManager.disablePVP){
						
					}
					else {
						damageDealt += (int)(11+2*potency);
						targets.get(i).attackEntityFrom(DamageSource.inFire, (int)(11+2*potency));
						targets.get(i).setFire((int) (2+potency));
						targets.get(i).setLastAttacker(caster);
						targets.get(i).setRevengeTarget((EntityLivingBase)caster);
						((EntityLivingBase)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:slowness"), 100, 0));
						((EntityLivingBase)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:weakness"), 100, 0));
						((EntityLivingBase)caster).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:mining_fatigue"), 100, 0));
						targets.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:speed"), 100, 0));
						targets.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:haste"), 100, 0));
						targets.get(i).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:strength"), 100, 0));
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
}
