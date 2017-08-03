package teamroots.emberroot.util;
import net.minecraft.entity.Entity;

public interface IRenderEntityLater {
  public void renderLater(Entity entity, double dx, double dy, double dz, float entityYaw, float partialTicks);
}
