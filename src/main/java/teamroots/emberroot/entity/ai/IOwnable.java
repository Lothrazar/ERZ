package teamroots.emberroot.entity.ai;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;

public interface IOwnable<O extends EntityCreature, T extends EntityLivingBase> {
  T getOwner();
  void setOwner(T owner);
  O asEntity();
}
