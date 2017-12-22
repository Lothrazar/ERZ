package teamroots.emberroot.entity.owl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import teamroots.emberroot.Const;

public class ItemOwlEgg extends Item {
  private static final float VELOCITY_DEFAULT = 1.5F;
  private static final float INACCURACY_DEFAULT = 1.0F;
  private static final float PITCHOFFSET = 0.0F;
  public static final String NAME = "owl_egg";
  public ItemOwlEgg() {
    setUnlocalizedName(NAME);
    setRegistryName(new ResourceLocation(Const.MODID, NAME));
  }
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.getHeldItem(hand);
    if (!playerIn.capabilities.isCreativeMode) {
      itemStackIn.shrink(1);
    }
    worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.BLOCKS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    if (!worldIn.isRemote) {
      EntityOwlEgg entityEgg = new EntityOwlEgg(worldIn, playerIn);
      //without setHeading the egg just falls to the players feet
      entityEgg.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, PITCHOFFSET, VELOCITY_DEFAULT, INACCURACY_DEFAULT);
      worldIn.spawnEntity(entityEgg);
    }
    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
  }
}