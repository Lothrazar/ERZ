package elucent.roots.component.components;

import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

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
            ArrayList<EntityLivingBase> hostile = (ArrayList<EntityLivingBase>) world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - size, y - size, z - size, x + size, y + size, z + size));
            for (int i = 0; i < hostile.size(); i++) {
                EntityPlayer player = (EntityPlayer) caster;
                if (hostile.get(i).getUniqueID() != player.getUniqueID()) {
                    Vec3d look = player.getLookVec();

                    double x1 = look.xCoord;
                    double z1 = look.zCoord;
                    hostile.get(i).setVelocity(x1, 0, z1);
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
            }
        }
    }
}
