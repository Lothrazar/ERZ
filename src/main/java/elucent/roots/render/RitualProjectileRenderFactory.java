package elucent.roots.render;

import elucent.roots.entity.projectile.EntityRitualProjectile;
import elucent.roots.render.projectile.RenderRitulaProjectile;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * Created by SirShadow for the mod Roots on 18.7.2016.
 */
public class RitualProjectileRenderFactory implements IRenderFactory<EntityRitualProjectile>
{
    @Override
    public Render<? super EntityRitualProjectile> createRenderFor(RenderManager manager)
    {
        return new RenderRitulaProjectile(manager);
    }
}
