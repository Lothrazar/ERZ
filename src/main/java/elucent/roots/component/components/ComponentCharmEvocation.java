package elucent.roots.component.components;

import elucent.roots.RegistryManager;
import elucent.roots.component.ComponentBase;
import elucent.roots.component.EnumCastType;
import elucent.roots.entity.projectile.EntityRitualProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by SirShadow on 20. 08. 2016.
 */
public class ComponentCharmEvocation extends ComponentBase
{
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
                if (caster instanceof EntityPlayer)
                {
                    EntityRitualProjectile projectile = new EntityRitualProjectile(world,(EntityPlayer)caster ,10);
                    projectile.setHeadingFromThrower(caster, ((EntityPlayer) caster).rotationPitch, ((EntityPlayer) caster).rotationYaw, 0.0F, 1.5F, 1.0F);
                    world.spawnEntityInWorld(projectile);
                }
            }
        }
    }
}
