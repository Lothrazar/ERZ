package elucent.roots.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelSpriteling extends ModelBase
{
  //fields
    ModelRenderer head1;
    ModelRenderer head2;
    ModelRenderer head3;
    ModelRenderer head4;
    ModelRenderer tail1;
  
  public ModelSpriteling()
  {
    textureWidth = 128;
    textureHeight = 128;
    
    head1 = new ModelRenderer(this, 0, 16);
  	head1.addBox(-3F, -3F, -3F, 6, 6, 6);
  	head1.setRotationPoint(0F, 0F, 0F);
  	head1.setTextureSize(128, 128);
  	head1.mirror = true;
  	setRotation(head1, 0F, 0F, 0F);
  	head2 = new ModelRenderer(this, 48, 32);
  	head2.addBox(-1F, -1F, -1F, 2, 2, 2);
  	head2.setRotationPoint(0F, 0F, 0F);
  	head2.setTextureSize(128, 128);
  	head2.mirror = true;
  	setRotation(head2, 0F, 0F, 0F);
  	head3 = new ModelRenderer(this, 48, 16);
  	head3.addBox(0F, -1F, -2F, 4, 2, 4);
  	head3.setRotationPoint(3F, 0F, 0F);
  	head3.setTextureSize(128, 128);
  	head3.mirror = true;
  	setRotation(head3, 0F, -0.3926991F, 0.3926991F);
  	head4 = new ModelRenderer(this, 48, 16);
  	head4.addBox(-4F, -1F, -2F, 4, 2, 4);
  	head4.setRotationPoint(-3F, 0F, 0F);
  	head4.setTextureSize(128, 128);
  	head4.mirror = true;
  	setRotation(head4, 0F, 0.3926991F, -0.3926991F);
  	tail1 = new ModelRenderer(this, 48, 0);
  	tail1.addBox(-2F, 0F, -2F, 4, 6, 4);
  	tail1.setRotationPoint(0F, 0F, 1F);
  	tail1.setTextureSize(128, 128);
  	tail1.mirror = true;
  	setRotation(tail1, 1.308997F, 0F, 0F);
  	head1.addChild(head3);
  	head1.addChild(head4);
  	head1.addChild(tail1);
  }
  
  @Override
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
	  GlStateManager.translate(0, 1.5, 0);
	  setRotation(head2, entity.rotationPitch, (float) (entity.rotationYaw+Math.toRadians(90.0)), 0);
	  head2.render(1.0f/16.0f);
	  GlStateManager.enableAlpha();
	  GlStateManager.enableBlend();
	  GlStateManager.color(1, 1, 1, 0.75f);
	  setRotation(head1, entity.rotationPitch, (float) (entity.rotationYaw+Math.toRadians(90.0)), 0);
	  setRotation(head1.childModels.get(2),(float) Math.toRadians(90.0),0,0);
	  head1.render(1.0f/16.0f);
	  GlStateManager.translate(0, -1.5, 0);
	  GlStateManager.color(1, 1, 1, 1);
	  GlStateManager.disableAlpha();
	  GlStateManager.disableBlend();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}