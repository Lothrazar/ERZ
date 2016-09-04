package elucent.roots.component.components;

import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by SirShadow for the mod Roots on 26.7.2016.
 */
public class ComponentCharmRestoration extends ComponentBase {
    public ComponentCharmRestoration() {
        super("restoration", "Restoration", RegistryManager.itemCharmRestoration, 16);
    }

    @Override
    public void doEffect(World world, Entity caster, EnumCastType type, double x, double y, double z, double potency, double duration, double size) {
        if (type == EnumCastType.SPELL) {
            ArrayList<EntityMob> hostile = (ArrayList<EntityMob>) world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(x - 4.0, y - 4.0, z - 4.0, x + 4.0, y + 4.0, z + 4.0));
            for (int i = 0; i < hostile.size(); i++) {
            	hostile.get(i).knockBack(null, 0.6f+(float)potency*0.2f, x-hostile.get(i).posX, z-hostile.get(i).posZ);
            }
            ((EntityLivingBase)caster).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,100,2));
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.HUNGER);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.BLINDNESS);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.INSTANT_DAMAGE);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.WEAKNESS);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.WITHER);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.NAUSEA);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.MINING_FATIGUE);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.POISON);
            ((EntityLivingBase)caster).removePotionEffect(MobEffects.SLOWNESS);
        }
    }

    @Override
    public void doEffect(World world, UUID casterId, Vec3d direction, EnumCastType type, double x, double y, double z, double potency, double duration, double size){
        if (type == EnumCastType.SPELL) {
            ArrayList<EntityMob> hostile = (ArrayList<EntityMob>) world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(x - 4.0, y - 4.0, z - 4.0, x + 4.0, y + 4.0, z + 4.0));
            for (int i = 0; i < hostile.size(); i++) {
            	hostile.get(i).knockBack(null, 0.6f+(float)potency*0.2f, x-hostile.get(i).posX, z-hostile.get(i).posZ);
            }
        }
    }
}
