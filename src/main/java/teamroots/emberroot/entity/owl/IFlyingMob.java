package teamroots.emberroot.entity.owl;
import net.minecraft.entity.EntityCreature;
import teamroots.emberroot.FlyingPathNavigate;

public interface IFlyingMob {
  float getMaxTurnRate();
  float getMaxClimbRate();
  FlyingPathNavigate getFlyingNavigator();
  EntityCreature asEntityCreature();
}
