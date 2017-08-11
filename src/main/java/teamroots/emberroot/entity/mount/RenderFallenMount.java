package teamroots.emberroot.entity.mount;
import java.util.Map;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import teamroots.emberroot.config.ConfigManager;
import teamroots.emberroot.util.RenderUtil;

public class RenderFallenMount extends RenderHorse {
  private static final String[] horseArmorTextures = new String[] {
      null,
      "textures/entity/horse/armor/horse_armor_iron.png",
      "textures/entity/horse/armor/horse_armor_gold.png",
      "textures/entity/horse/armor/horse_armor_diamond.png"
  };
  private static final String textureName = "textures/entity/horse/horse_zombie.png";
  private static final ResourceLocation zombieHorseTexture = new ResourceLocation(textureName);
  private static final Map<String, ResourceLocation> textureCache = Maps.newHashMap();
  public RenderFallenMount(RenderManager rm) {
    super(rm);
    //    super(rm, new ModelHorse(), 0.75F);
  }
  @Override
  public void doRender(EntityHorse entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    if (ConfigManager.renderDebugHitboxes)
      RenderUtil.renderEntityBoundingBox(entity, x, y, z);
  }
  @Override
  protected ResourceLocation getEntityTexture(EntityHorse horse) {
    if (horse.getTotalArmorValue() == 0) {
      return zombieHorseTexture;
    }
    else {
      return getArmoredTexture(horse);
    }
  }
  private ResourceLocation getArmoredTexture(EntityHorse horse) {
    String s = horseArmorTextures[horse.getHorseArmorType().ordinal()];
    ResourceLocation resourcelocation = textureCache.get(s);
    if (resourcelocation == null) {
      resourcelocation = new ResourceLocation("Layered:" + s);
      Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, new LayeredTexture(textureName, s));
      textureCache.put(s, resourcelocation);
    }
    return resourcelocation;
  }
  public static class Factory implements IRenderFactory<EntityHorse> {
    @Override
    public Render<? super EntityHorse> createRenderFor(RenderManager manager) {
      return new RenderFallenMount(manager);
    }
  }
}
