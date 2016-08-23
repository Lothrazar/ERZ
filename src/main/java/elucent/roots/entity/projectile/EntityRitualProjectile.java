package elucent.roots.entity.projectile;

import elucent.roots.Roots;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

/**
 * Created by SirShadow for the mod Roots on 18.7.2016.
 */
public class EntityRitualProjectile extends EntityThrowable
{
    float potency = 1;


    public EntityRitualProjectile(World worldIn)
    {
        super(worldIn);
    }

    public EntityRitualProjectile(World worldIn,EntityLivingBase entityLivingBase ,float potency)
    {
        super(worldIn,entityLivingBase);
        this.potency = potency;
    }

    public EntityRitualProjectile(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }


    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null)
        {
            if (result.entityHit instanceof EntityLivingBase)
            {
                /**
                 * What it does when  it hist the entity
                 */
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this,result.entityHit),potency);
            }
        }

        for (int j = 0; j < 8; ++j)
        {
            //worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE,this.posX,this.posY,this.posZ,3,3,3);
            Roots.proxy.spawnParticleMagicAuraFX(worldObj,this.posX,this.posY,this.posZ,0,0,0,255,255,255);
        }
        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
}
