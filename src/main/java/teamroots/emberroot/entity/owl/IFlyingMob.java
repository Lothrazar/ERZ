package teamroots.emberroot.entity.owl;
import net.minecraft.entity.EntityCreature;

public interface IFlyingMob {
  float getMaxTurnRate();
  float getMaxClimbRate();
  FlyingPathNavigate getFlyingNavigator();
  EntityCreature asEntityCreature();
}
