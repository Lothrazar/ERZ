package elucent.roots.ritual.powers.charms;

import elucent.roots.ritual.EnumPowerType;
import elucent.roots.ritual.RitualPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Created by SirShadow for the mod Roots on 17.7.2016.
 */
public class RitualPowerRestoration extends RitualPower {
    public RitualPowerRestoration() {
        super("restoration", EnumPowerType.TYPE_TARGET_ENTITY, 64);
    }

    @Override
    public void onRightClickEntity(EntityPlayer player, World world, Entity entity) {
        super.onRightClickEntity(player, world, entity);
        if (entity instanceof EntityLivingBase) {
            ArrayList<EntityLivingBase> hostileTargets = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - 4.0, entity.posY - 4.0, entity.posZ - 4.0, entity.posX + 4.0, entity.posY + 4.0, entity.posZ + 4.0));
            for (int i = 0; i < hostileTargets.size(); i++) {
                if (hostileTargets.get(i).getUniqueID() != player.getUniqueID()) {
                    Vec3d look = player.getLookVec();

                    double x = look.xCoord;
                    double z = look.zCoord;
                    hostileTargets.get(i).setVelocity(x, 0, z);
                }
            }
            /**
             * All the potion effects that are going to be removed
             */
            player.removePotionEffect(MobEffects.HUNGER);
            player.removePotionEffect(MobEffects.BLINDNESS);
            player.removePotionEffect(MobEffects.INSTANT_DAMAGE);
            player.removePotionEffect(MobEffects.WEAKNESS);
            player.removePotionEffect(MobEffects.WITHER);
            player.removePotionEffect(MobEffects.NAUSEA);
            player.removePotionEffect(MobEffects.MINING_FATIGUE);
            player.removePotionEffect(MobEffects.POISON);
            player.removePotionEffect(MobEffects.SLOWNESS);
            player.removePotionEffect(MobEffects.SATURATION);


        }
    }
}

