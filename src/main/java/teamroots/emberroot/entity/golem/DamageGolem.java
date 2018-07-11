package teamroots.emberroot.entity.golem;

import net.minecraft.util.DamageSource;

public class DamageGolem extends DamageSource {

  public DamageGolem() {
    super("ember");
    this.setMagicDamage();
  }
}
