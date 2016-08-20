package elucent.roots.model.entity;

import elucent.roots.entity.EntitySpriteling;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelNull extends ModelBase
{
  public ModelNull()
  {
    textureWidth = 128;
    textureHeight = 128;
  }
  
  @Override
  public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale){
	  //
  }
}