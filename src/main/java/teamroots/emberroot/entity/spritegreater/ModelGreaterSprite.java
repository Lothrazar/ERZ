package teamroots.emberroot.entity.spritegreater;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import teamroots.emberroot.util.Util;

public class ModelGreaterSprite extends ModelBase {
  public static ModelGreaterSprite instance;
  //fields
  ModelRenderer stonehead1;
  ModelRenderer stonehead2;
  ModelRenderer stonehead3;
  ModelRenderer head1;
  ModelRenderer head2;
  ModelRenderer head3;
  ModelRenderer head4;
  ModelRenderer crest1;
  ModelRenderer crest2;
  ModelRenderer crest3;
  ModelRenderer crest4;
  ModelRenderer body1;
  ModelRenderer body2;
  ModelRenderer body3;
  ModelRenderer body4;
  ModelRenderer body5;
  ModelRenderer leg1;
  ModelRenderer leg2;
  ModelRenderer leg3;
  ModelRenderer leg4;
  ModelRenderer leg5;
  ModelRenderer leg6;
  public ModelGreaterSprite() {
    textureWidth = 128;
    textureHeight = 128;
    stonehead1 = new ModelRenderer(this, 16, 32);
    stonehead1.addBox(-3F, -3F, -3F, 6, 6, 6);
    stonehead1.setRotationPoint(0F, 0F, -1F);
    stonehead1.setTextureSize(128, 128);
    stonehead1.mirror = true;
    setRotation(stonehead1, 0F, 0F, 0F);
    stonehead2 = new ModelRenderer(this, 0, 32);
    stonehead2.addBox(-2F, -2F, -2F, 4, 4, 4);
    stonehead2.setRotationPoint(0F, 0F, 5F);
    stonehead2.setTextureSize(128, 128);
    stonehead2.mirror = true;
    setRotation(stonehead2, 0F, 0F, 0F);
    stonehead3 = new ModelRenderer(this, 48, 32);
    stonehead3.addBox(-1F, -1F, -1F, 2, 2, 2);
    stonehead3.setRotationPoint(0F, 0F, 9F);
    stonehead3.setTextureSize(128, 128);
    stonehead3.mirror = true;
    setRotation(stonehead3, 0F, 0F, 0F);
    head1 = new ModelRenderer(this, 64, 0);
    head1.addBox(-6F, -6F, -6F, 12, 12, 12);
    head1.setRotationPoint(0F, 0F, 0F);
    head1.setTextureSize(128, 128);
    head1.mirror = true;
    setRotation(head1, 0F, 0F, 0F);
    head2 = new ModelRenderer(this, 0, 16);
    head2.addBox(-6F, -3F, -3F, 6, 6, 6);
    head2.setRotationPoint(-5F, 0F, -2F);
    head2.setTextureSize(128, 128);
    head2.mirror = true;
    setRotation(head2, 0F, 0.5235988F, 0F);
    head3 = new ModelRenderer(this, 0, 16);
    head3.addBox(0F, -3F, -3F, 6, 6, 6);
    head3.setRotationPoint(5F, 0F, -2F);
    head3.setTextureSize(128, 128);
    head3.mirror = true;
    setRotation(head3, 0F, -0.5235988F, 0F);
    head4 = new ModelRenderer(this, 0, 16);
    head4.addBox(-3F, -6F, -3F, 6, 6, 6);
    head4.setRotationPoint(0F, -5F, -2F);
    head4.setTextureSize(128, 128);
    head4.mirror = true;
    setRotation(head4, -0.5235988F, 0F, 0F);
    crest1 = new ModelRenderer(this, 32, 0);
    crest1.addBox(-2F, 0F, -2F, 4, 8, 4);
    crest1.setRotationPoint(-3F, -5F, 0F);
    crest1.setTextureSize(128, 128);
    crest1.mirror = true;
    setRotation(crest1, 0.7853982F, 0F, 2.879793F);
    crest2 = new ModelRenderer(this, 32, 0);
    crest2.addBox(-2F, 0F, -2F, 4, 8, 4);
    crest2.setRotationPoint(3F, -5F, 0F);
    crest2.setTextureSize(128, 128);
    crest2.mirror = true;
    setRotation(crest2, 0.7853982F, 0F, -2.879793F);
    crest3 = new ModelRenderer(this, 32, 0);
    crest3.addBox(-2F, 0F, -2F, 4, 8, 4);
    crest3.setRotationPoint(0F, -4F, 5F);
    crest3.setTextureSize(128, 128);
    crest3.mirror = true;
    setRotation(crest3, 0.7853982F, 0F, 3.141593F);
    crest4 = new ModelRenderer(this, 32, 0);
    crest4.addBox(-2F, 0F, -2F, 4, 8, 4);
    crest4.setRotationPoint(0F, -2F, 10F);
    crest4.setTextureSize(128, 128);
    crest4.mirror = true;
    setRotation(crest4, 1.047198F, 0F, 3.141593F);
    body1 = new ModelRenderer(this, 0, 0);
    body1.addBox(-4F, -4F, 0F, 8, 8, 8);
    body1.setRotationPoint(0F, 0F, 4F);
    body1.setTextureSize(128, 128);
    body1.mirror = true;
    setRotation(body1, 0F, 0F, 0F);
    body2 = new ModelRenderer(this, 0, 16);
    body2.addBox(-3F, -3F, 0F, 6, 6, 6);
    body2.setRotationPoint(0F, 0F, 8F);
    body2.setTextureSize(128, 128);
    body2.mirror = true;
    setRotation(body2, 0F, 0F, 0F);
    body3 = new ModelRenderer(this, 32, 0);
    body3.addBox(-2F, 0F, -2F, 4, 8, 4);
    body3.setRotationPoint(0F, 0F, 6F);
    body3.setTextureSize(128, 128);
    body3.mirror = true;
    setRotation(body3, 1.570796F, 0F, 0F);
    body4 = new ModelRenderer(this, 32, 0);
    body4.addBox(-2F, 0F, -2F, 4, 8, 4);
    body4.setRotationPoint(0F, 8F, 0F);
    body4.setTextureSize(128, 128);
    body4.mirror = true;
    setRotation(body4, 1.570796F, 0F, 0F);
    body5 = new ModelRenderer(this, 32, 0);
    body5.addBox(-2F, 0F, -2F, 4, 8, 4);
    body5.setRotationPoint(0F, 8F, 0F);
    body5.setTextureSize(128, 128);
    body5.mirror = true;
    setRotation(body5, 1.570796F, 0F, 0F);
    leg1 = new ModelRenderer(this, 32, 0);
    leg1.addBox(-2F, 0F, -2F, 4, 8, 4);
    leg1.setRotationPoint(2F, 3F, 3F);
    leg1.setTextureSize(128, 128);
    leg1.mirror = true;
    setRotation(leg1, 0.5235988F, 0F, -0.5235988F);
    leg2 = new ModelRenderer(this, 32, 0);
    leg2.addBox(-2F, 0F, -2F, 4, 8, 4);
    leg2.setRotationPoint(-2F, 3F, 3F);
    leg2.setTextureSize(128, 128);
    leg2.mirror = true;
    setRotation(leg2, 0.5235988F, 0F, 0.5235988F);
    leg3 = new ModelRenderer(this, 32, 0);
    leg3.addBox(-2F, 0F, -2F, 4, 8, 4);
    leg3.setRotationPoint(-1.5F, 2F, 6F);
    leg3.setTextureSize(128, 128);
    leg3.mirror = true;
    setRotation(leg3, 0.7853982F, 0F, 0.7853982F);
    leg4 = new ModelRenderer(this, 32, 0);
    leg4.addBox(-2F, 0F, -2F, 4, 8, 4);
    leg4.setRotationPoint(-1F, 1F, 8F);
    leg4.setTextureSize(128, 128);
    leg4.mirror = true;
    setRotation(leg4, 1.047198F, 0F, 0.5235988F);
    leg5 = new ModelRenderer(this, 32, 0);
    leg5.addBox(-2F, 0F, -2F, 4, 8, 4);
    leg5.setRotationPoint(1.5F, 2F, 6F);
    leg5.setTextureSize(128, 128);
    leg5.mirror = true;
    setRotation(leg5, 0.7853982F, 0F, -0.7853982F);
    leg6 = new ModelRenderer(this, 32, 0);
    leg6.addBox(-2F, 0F, -2F, 4, 8, 4);
    leg6.setRotationPoint(1F, 1F, 8F);
    leg6.setTextureSize(128, 128);
    leg6.mirror = true;
    setRotation(leg6, 1.047198F, 0F, -0.5235988F);
    stonehead1.addChild(stonehead2);
    stonehead1.addChild(stonehead3);
    head1.addChild(head2);
    head1.addChild(head3);
    head1.addChild(head4);
    head1.addChild(crest1);
    head1.addChild(crest2);
    head1.addChild(crest3);
    head1.addChild(crest4);
    body4.addChild(body5);
    body3.addChild(body4);
    body2.addChild(body3);
    body1.addChild(body2);
    head1.addChild(body1);
    head1.addChild(leg1);
    head1.addChild(leg2);
    head1.addChild(leg3);
    head1.addChild(leg4);
    head1.addChild(leg5);
    head1.addChild(leg6);
  }
  @Override
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    float pTicks = ageInTicks - (int) ageInTicks;
    float pitch1 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevPitch2, ((EntityGreaterSprite) entity).prevPitch1, pTicks);
    float pitch2 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevPitch3, ((EntityGreaterSprite) entity).prevPitch2, pTicks);
    float pitch3 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevPitch4, ((EntityGreaterSprite) entity).prevPitch3, pTicks);
    float pitch4 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevPitch5, ((EntityGreaterSprite) entity).prevPitch4, pTicks);
    float yaw1 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevYaw2, ((EntityGreaterSprite) entity).prevYaw1, pTicks);
    float yaw2 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevYaw3, ((EntityGreaterSprite) entity).prevYaw2, pTicks);
    float yaw3 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevYaw4, ((EntityGreaterSprite) entity).prevYaw3, pTicks);
    float yaw4 = (float) Util.interpolate(((EntityGreaterSprite) entity).prevYaw5, ((EntityGreaterSprite) entity).prevYaw4, pTicks);
    GlStateManager.pushAttrib();
    GlStateManager.color(1f, 1f, 1f, 1f);
    GlStateManager.translate(0, 1.25, 0);
    setRotation(stonehead1, 0, 0, 0);
    GlStateManager.rotate(-0.5f * (float) Math.PI - (float) Math.toDegrees(entity.rotationYaw), 0, 1, 0);
    GlStateManager.rotate((float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    if (((EntityGreaterSprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) 18.0f * (20.0f - (float) ((EntityGreaterSprite) entity).twirlTimer), 0, 0, 1);
    }
    if (!((EntityGreaterSprite) entity).getDataManager().get(((EntityGreaterSprite) entity).stunned).booleanValue()) {
      GlStateManager.rotate((float) (15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    }
    stonehead1.render(1.0f / 16.0f);
    if (!((EntityGreaterSprite) entity).getDataManager().get(((EntityGreaterSprite) entity).stunned).booleanValue()) {
      GlStateManager.rotate((float) (-15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    }
    if (((EntityGreaterSprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) -18.0f * (20.0f - (float) ((EntityGreaterSprite) entity).twirlTimer), 0, 0, 1);
    }
    GlStateManager.rotate(-(float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    GlStateManager.rotate((float) Math.toDegrees(entity.rotationYaw) + 0.5f * (float) Math.PI, 0, 1, 0);
    GlStateManager.enableAlpha();
    GlStateManager.enableBlend();
    GlStateManager.disableLighting();
    GlStateManager.color(1, 1, 1, 0.75f);
    setRotation(head1, 0, 0, 0);
    setRotation(body2, -2.0f * (pitch1 - ((EntityGreaterSprite) entity).rotationPitch), -2.0f * (yaw1 - ((EntityGreaterSprite) entity).rotationYaw), 0);
    setRotation(body3, 0.5f * (float) Math.PI - 2.0f * (pitch2 - pitch1), -2.0f * (yaw2 - yaw1), 0);
    setRotation(body4, -2.0f * (pitch3 - pitch2), 0, 2.0f * (yaw3 - yaw2));
    setRotation(body5, -2.0f * (pitch4 - pitch3), 0, 2.0f * (yaw4 - yaw3));
    GlStateManager.rotate(-0.5f * (float) Math.PI - (float) Math.toDegrees(entity.rotationYaw), 0, 1, 0);
    GlStateManager.rotate((float) Math.toDegrees(-entity.rotationPitch / 2.0f), 1, 0, 0);
    if (((EntityGreaterSprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) 18.0f * (20.0f - (float) ((EntityGreaterSprite) entity).twirlTimer), 0, 0, 1);
    }
    if (!((EntityGreaterSprite) entity).getDataManager().get(((EntityGreaterSprite) entity).stunned).booleanValue()) {
      GlStateManager.rotate((float) (15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    }
    head1.render(1.0f / 16.0f);
    if (!((EntityGreaterSprite) entity).getDataManager().get(((EntityGreaterSprite) entity).stunned).booleanValue()) {
      GlStateManager.rotate((float) (-15.0f * Math.sin(Math.PI * 2.0 * (((double) (entity.ticksExisted % 20)) / 20.0))), 0, 0, 1);
    }
    if (((EntityGreaterSprite) entity).twirlTimer > 0) {
      GlStateManager.rotate((float) -18.0f * (20.0f - (float) ((EntityGreaterSprite) entity).twirlTimer), 0, 0, 1);
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