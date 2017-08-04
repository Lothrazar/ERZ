package teamroots.emberroot.entity.slime;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.world.World;

public class EntityWaterSlime extends EntitySlime {
  public EntityWaterSlime(World worldIn) {
    super(worldIn);
  }
  /**
   * make sure that on death we get one of our own
   */
  @Override
  protected EntitySlime createInstance() {
    return new EntityWaterSlime(this.world);
  }
}
