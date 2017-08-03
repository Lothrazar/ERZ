package teamroots.emberroot.entity.golem;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.player.EntityPlayer;

public interface IEmberParticle {
  boolean alive();
  boolean isAdditive();
  boolean renderThroughBlocks();
  void onUpdate();
  void renderParticle(BufferBuilder buffer, EntityPlayer player, float partialTicks, float f, float f4, float f1, float f2, float f3);
}
