package teamroots.emberroot.entity.sprite;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import teamroots.emberroot.util.Util;

/**
 * made using tabula by Elucent
 * 
 *
 */
public class ModelSprite extends ModelBase {
  public static ModelSprite instance;
  //fields
  ModelRenderer head1;
  ModelRenderer head2;
  ModelRenderer head3;
  ModelRenderer body1;
  ModelRenderer body2;
  ModelRenderer body3;
  ModelRenderer backarm1;
  ModelRenderer backarm2;
  ModelRenderer frontarm1;
  ModelRenderer frontarm2;
  ModelRenderer crest1;
  ModelRenderer crest2;
  public ModelSprite() {
    textureWidth = 128;
    textureHeight = 128;
    head1 = new ModelRenderer(this, 0, 0);
    head1.addBox(-4F, -4F, -4F, 8, 8, 8);
    head1.setRotationPoint(0F, 0F, 0F);
    head1.setTextureSize(128, 128);
    head1.mirror = true;
    setRotation(head1, 0F, 0F, 0F);
    head2 = new ModelRenderer(this, 48, 32);
    head2.addBox(-1F, -1F, -1F, 2, 2, 2);
    head2.setRotationPoint(0F, 0F, 4F);
    head2.setTextureSize(128, 128);
    head2.mirror = true;
    setRotation(head2, 0F, 0F, 0F);
    head3 = new ModelRenderer(this, 0, 32);
    head3.addBox(-2F, -2F, -2F, 4, 4, 4);
    head3.setRotationPoint(0F, 0F, -1F);
    head3.setTextureSize(128, 128);
    head3.mirror = true;
    setRotation(head3, 0F, 0F, 0F);
    body1 = new ModelRenderer(this, 0, 16);
    body1.addBox(-3F, -3F, -3F, 6, 6, 6);
    body1.setRotationPoint(0F, 0F, 7F);
    body1.setTextureSize(128, 128);
    body1.mirror = true;
    setRotation(body1, 0F, 0F, 0F);
    body2 = new ModelRenderer(this, 48, 0);
    body2.addBox(-2F, 0F, -2F, 4, 6, 4);
    body2.setRotationPoint(0F, 0F, 3F);
    body2.setTextureSize(128, 128);
    body2.mirror = true;
    setRotation(body2, -0.5f * (float) Math.PI, 0F, 0F);
    body3 = new ModelRenderer(this, 48, 0);
    body3.addBox(-2F, 0F, -2F, 4, 6, 4);
    body3.setRotationPoint(0F, 6F, 0F);
    body3.setTextureSize(128, 128);
    body3.mirror = true;
    setRotation(body3, -0.5f * (float) Math.PI, 0F, 0F);
    backarm1 = new ModelRenderer(this, 48, 0);
    backarm1.addBox(-2F, 0F, -2F, 4, 6, 4);
    backarm1.setRotationPoint(1F, 1F, 5F);
    backarm1.setTextureSize(128, 128);
    backarm1.mirror = true;
    setRotation(backarm1, 0.7853982F, 0F, -0.6544985F);
    backarm2 = new ModelRenderer(this, 48, 0);
    backarm2.addBox(-2F, 0F, -2F, 4, 6, 4);
    backarm2.setRotationPoint(-1F, 1F, 5F);
    backarm2.setTextureSize(128, 128);
    backarm2.mirror = true;
    setRotation(backarm2, 0.7853982F, 0F, 0.6544985F);
    frontarm1 = new ModelRenderer(this, 48, 0);
    frontarm1.addBox(-2F, 0F, -2F, 4, 6, 4);
    frontarm1.setRotationPoint(-2F, 2F, 1F);
    frontarm1.setTextureSize(128, 128);
    frontarm1.mirror = true;
    setRotation(frontarm1, 0.5235988F, 0F, 0.6544985F);
    frontarm2 = new ModelRenderer(this, 48, 0);
    frontarm2.addBox(-2F, 0F, -2F, 4, 6, 4);
    frontarm2.setRotationPoint(2F, 2F, 1F);
    frontarm2.setTextureSize(128, 128);
    frontarm2.mirror = true;
    setRotation(frontarm2, 0.5235988F, 0F, -0.6544985F);
    crest1 = new ModelRenderer(this, 32, 0);
    crest1.addBox(-2F, 0F, -2F, 4, 8, 4);
    crest1.setRotationPoint(0F, -1F, -4F);
    crest1.setTextureSize(128, 128);
    crest1.mirror = true;
    setRotation(crest1, -0.5235988F, 3.141593F, 3.141593F);
    crest2 = new ModelRenderer(this, 32, 0);
    crest2.addBox(-2F, 0F, -2F, 4, 8, 4);
    crest2.setRotationPoint(0F, -2F, 0F);
    crest2.setTextureSize(128, 128);
    crest2.mirror = true;
    setRotation(crest2, -1.047198F, 3.141593F, 3.141593F);
    head2.addChild(head3);
    head1.addChild(backarm1);
    head1.addChild(backarm2);
    body2.addChild(body3);
    body1.addChild(body2);
    head1.addChild(body1);
    head1.addChild(frontarm1);
    head1.addChild(frontarm2);
    head1.addChild(crest1);
    head1.addChild(crest2);
  }
  @Override
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    float pTicks = ageInTicks - (int) ageInTicks;
    float pitch1 = (float) Util.interpolate(((EntitySprite) entity).prevPitch2, ((EntitySprite) entity).prevPitch1, pTicks);
    float pitch2 = (float) Util.interpolate(((EntitySprite) entity).prevPitch3, ((EntitySprite) entity).prevPitch2, pTicks);
    float pitch3 = (float) Util.interpolate(((EntitySprite) entity).prevPitch4, ((EntitySprite) entity).prevPitch3, pTicks);
    float yaw1 = (float) Util.interpolate(((EntitySprite) entity).prevYaw2, ((EntitySprite) entity).prevYaw1, pTicks);
    float yaw2 = (float) Util.interpolate(((EntitySprite) entity).prevYaw3, ((EntitySprite) entity).prevYaw2, pTicks);
    float yaw3 = (float) Util.interpolate(((EntitySprite) entity).prevYaw4, ((EntitySprite) entity).prevYaw3, pTicks);
    GlStateManager.pushAttrib();
    GlStateManager.color(1f, 1f, 1f, 1f);
    GlStateManager.translate(0, 1.25, 0);
    setRotation(head2, 0, 0, 0);
    GlStateManager.rotate(-0.5f * (float) Math.PI - (float) Math.toDegrees(entity.rotationYaw), 0, 1, 0);
    GlStateManager.rotate((float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    if (((EntitySprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) 18.0f * (20.0f - (float) ((EntitySprite) entity).twirlTimer), 0, 0, 1);
    }
    GlStateManager.rotate((float) (15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    head2.render(1.0f / 16.0f);
    GlStateManager.rotate((float) (-15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    if (((EntitySprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) -18.0f * (20.0f - (float) ((EntitySprite) entity).twirlTimer), 0, 0, 1);
    }
    GlStateManager.rotate(-(float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    GlStateManager.rotate((float) Math.toDegrees(entity.rotationYaw) + 0.5f * (float) Math.PI, 0, 1, 0);
    GlStateManager.enableAlpha();
    GlStateManager.enableBlend();
    GlStateManager.disableLighting();
    GlStateManager.color(1, 1, 1, 0.75f);
    setRotation(head1, 0, 0, 0);
    setRotation(body1, -2.0f * (pitch1 - ((EntitySprite) entity).rotationPitch), -2.0f * (yaw1 - ((EntitySprite) entity).rotationYaw), 0);
    setRotation(body2, 0.5f * (float) Math.PI - 2.0f * (pitch2 - pitch1), -2.0f * (yaw2 - yaw1), 0);
    setRotation(body3, -2.0f * (pitch3 - pitch2), 0, 2.0f * (yaw3 - yaw2));
    GlStateManager.rotate(-0.5f * (float) Math.PI - (float) Math.toDegrees(entity.rotationYaw), 0, 1, 0);
    GlStateManager.rotate((float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    if (((EntitySprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) 18.0f * (20.0f - (float) ((EntitySprite) entity).twirlTimer), 0, 0, 1);
    }
    GlStateManager.rotate((float) (15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    head1.render(1.0f / 16.0f);
    GlStateManager.rotate((float) (-15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    if (((EntitySprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) -18.0f * (20.0f - (float) ((EntitySprite) entity).twirlTimer), 0, 0, 1);
    }
    GlStateManager.rotate(-(float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    GlStateManager.rotate((float) (entity.rotationYaw) + 0.5f * (float) Math.PI, 0, 1, 0);
    GlStateManager.translate(0, -(1.25), 0);
    GlStateManager.color(1f, 1f, 1f, 1f);
    GlStateManager.disableAlpha();
    GlStateManager.disableBlend();
    GlStateManager.enableLighting();
    GlStateManager.popAttrib();
  }
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}