package elucent.roots.render.projectile;

import elucent.roots.Roots;
import elucent.roots.entity.projectile.EntityRitualProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

/**
 * Created by SirShadow for the mod Roots on 18.7.2016.
 */
public class RenderRitulaProjectile extends Render<EntityRitualProjectile>
{
    private final RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

    Random random = new Random();


    public RenderRitulaProjectile(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    public void doRender(EntityRitualProjectile entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        for (int i = 0; i < 2; i ++) {
            int side = random.nextInt(6);
            if (side == 0) {
                Roots.proxy.spawnParticleMagicAuraFX(entity.worldObj, entity.posX, entity.posY + random.nextDouble(), entity.posZ + random.nextDouble(), 0, 0, 0, 231, 65, 243);
            }
            if (side == 1) {
                Roots.proxy.spawnParticleMagicAuraFX(entity.worldObj, entity.posX + 1.0, entity.posY + random.nextDouble(), entity.posZ + random.nextDouble(), 0, 0, 0, 142, 14, 151);
            }
            if (side == 2) {
                Roots.proxy.spawnParticleMagicAuraFX(entity.worldObj, entity.posX + random.nextDouble(), entity.posY, entity.posZ + random.nextDouble(), 0, 0, 0, 241, 123, 249);
            }
            if (side == 3) {
                Roots.proxy.spawnParticleMagicAuraFX(entity.worldObj, entity.posX + random.nextDouble(), entity.posY + 1.0, entity.posZ + random.nextDouble(), 0, 0, 0, 231, 65, 243);
            }
            if (side == 4) {
                Roots.proxy.spawnParticleMagicAuraFX(entity.worldObj, entity.posX + random.nextDouble(), entity.posY + random.nextDouble(), entity.posZ, 0, 0, 0, 241, 123, 249);
            }
            if (side == 5) {
                Roots.proxy.spawnParticleMagicAuraFX(entity.worldObj, entity.posX + random.nextDouble(), entity.posY + random.nextDouble(), entity.posZ + 1.0, 0, 0, 0, 142, 14, 151);
            }
        }
        //this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        //this.renderItem.renderItem(new ItemStack(ModItems.soulSnare), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityRitualProjectile entity)
    {
        return new ResourceLocation(Roots.MODID,"textures/entities/");
    }
}
